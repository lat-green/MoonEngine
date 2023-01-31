package com.greentree.engine.moon.base.assets.scene;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.greentree.commons.data.resource.Resource;
import com.greentree.commons.util.classes.ClassUtil;
import com.greentree.commons.util.classes.info.TypeInfo;
import com.greentree.commons.util.exception.WrappedException;
import com.greentree.commons.util.string.RefStringBuilder;
import com.greentree.commons.xml.XMLElement;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.ResultAssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.Value;
import com.greentree.engine.moon.assets.value.function.Value1Function;
import com.greentree.engine.moon.assets.value.provider.ValueProvider;
import com.greentree.engine.moon.base.layer.Layer;
import com.greentree.engine.moon.base.name.Name;
import com.greentree.engine.moon.base.scene.Scene;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.system.ECSSystem;
import com.greentree.engine.moon.ecs.system.debug.DebugSystems;
import com.greentree.engine.moon.ecs.system.debug.PrintStreamSystemsProfiler;

public class XMLSceneAssetSerializator implements AssetSerializator<Scene> {
	
	@Override
	public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
		return manager.canLoad(XMLElement.class, key);
	}
	
	@Override
	public Value<Scene> load(LoadContext context, AssetKey ckey) {
		if(context.canLoad(XMLElement.class, ckey)) {
			final var res = context.load(XMLElement.class, ckey);
			return context.map(res, new XMLWorldFunction(context));
		}
		return null;
	}
	
	
	private static final class XMLWorldFunction implements Value1Function<XMLElement, Scene> {
		
		private static final long serialVersionUID = 1L;
		private final ObjectXMLBuilder builder = new ObjectXMLBuilder();
		private final LoadContext context;
		
		public XMLWorldFunction(LoadContext context) {
			this.context = context;
			
			builder.add(new XMLTypeAddapter() {
				
				@SuppressWarnings("unchecked")
				@Override
				public <T> Constructor<T> newInstance(Context c, TypeInfo<T> type,
						XMLElement xml_value) {
					if(ClassUtil.isExtends(Value.class, type.toClass())) {
						final var xml_value_text = xml_value.getContent();
						final var value_type = type.getTypeArguments()[0].getBoxing();
						final var value = context.load(value_type, xml_value_text);
						return new ValueConstructor<>((T) value);
					}
					return null;
				}
			});
			builder.add(new XMLTypeAddapter() {
				
				@SuppressWarnings("unchecked")
				@Override
				public <T> Constructor<T> newInstance(Context c, TypeInfo<T> type,
						XMLElement xml_value) {
					if(ClassUtil.isExtends(ValueProvider.class, type.toClass())) {
						final var xml_value_text = xml_value.getContent();
						final var value_type = type.getTypeArguments()[0].getBoxing();
						final var value = context.load(value_type, xml_value_text).openProvider();
						return new ValueConstructor<>((T) value);
					}
					return null;
				}
			});
			builder.add(new XMLTypeAddapter() {
				
				@Override
				public <T> Constructor<T> newInstance(Context c, TypeInfo<T> type,
						XMLElement xml_value) {
					final var xml_value_text = xml_value.getContent();
					final var key = new ResultAssetKey(xml_value_text);
					if(context.isDeepValid(type, key)) {
						final var v = context.loadData(type, key);
						return new ValueConstructor<>(v);
					}
					return null;
				}
			});
			
		}
		
		@Override
		public Scene apply(XMLElement xml_scene) {
			return new Scene() {
				
				@Override
				public void build(World world) {
					for(var xml_entity : xml_scene.getChildrens("entity"))
						addEntity(world, xml_entity);
					for(var xml_entity_ref : xml_scene.getChildrens("entity_ref")) {
						final var file = xml_entity_ref.getAttribute("file");
						final var res = context.loadData(Resource.class, file);
						
						final RefStringBuilder builder;
						try {
							builder = RefStringBuilder.build(res.open());
						}catch(IOException e) {
							e.printStackTrace();
							continue;
						}
						
						final var map = new HashMap<String, String>();
						
						for(var xmLproperty : xml_entity_ref.getChildrens("property")) {
							final var name = xmLproperty.getAttribute("name");
							final var value = xmLproperty.getContent();
							map.put(name, value);
						}
						
						final var text = builder.toString(map);
						final var xml = context.loadData(XMLElement.class,
								new ResultAssetKey(text));
						
						addEntity(world, xml);
					}
				}
				
				@Override
				public ECSSystem getSystems(Iterable<? extends ECSSystem> globalSystems) {
					final var systems = new ArrayList<ECSSystem>();
					for(var system : globalSystems)
						systems.add(system);
					for(var xml_system : xml_scene.getChildrens("system"))
						try {
							systems.add(newFromXML(xml_system));
						}catch(ClassNotFoundException e1) {
							e1.printStackTrace();
						}
					try {
						return new DebugSystems(
								new PrintStreamSystemsProfiler(new File("log/systems_init.log"),
										new File("log/systems_update.log"),
										new File("log/systems_destroy.log")),
								systems);
					}catch(FileNotFoundException e) {
						throw new WrappedException(e);
					}
				}
				
				private void addEntity(World world, XMLElement xml_entity) {
					final var name_atr = xml_entity.getAttribute("name");
					final var layer_atr = xml_entity.getAttribute("layer");
					final var entity = world.newEntity();
					try(final var lock = entity.lock()) {
						if(name_atr != null)
							lock.add(new Name(name_atr));
						if(layer_atr != null)
							lock.add(new Layer(layer_atr));
						
						for(var xml_component : xml_entity.getChildrens("component"))
							try {
								lock.add(newFromXML(xml_component));
							}catch(ClassNotFoundException e) {
								e.printStackTrace();
							}
					}
				}
				
				private <T> T newFromXML(XMLElement xml_element) throws ClassNotFoundException {
					final var systemClassName = xml_element.getAttribute("type");
					@SuppressWarnings("unchecked")
					final var systemClass = (Class<T>) ClassUtil
							.loadClassInAllPackages(systemClassName);
					return builder.build(systemClass, xml_element);
				}
				
			};
		}
		
	}
	
}
