package com.greentree.engine.moon.base.assets.scene;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.greentree.commons.assets.key.AssetKey;
import com.greentree.commons.assets.key.ResultAssetKeyImpl;
import com.greentree.commons.assets.serializator.AssetSerializator;
import com.greentree.commons.assets.serializator.context.LoadContext;
import com.greentree.commons.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.function.Value1Function;
import com.greentree.commons.data.resource.Resource;
import com.greentree.commons.util.exception.WrappedException;
import com.greentree.commons.util.string.RefStringBuilder;
import com.greentree.commons.xml.XMLElement;
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
		{
			final var res = context.load(XMLElement.class, ckey);
			if(res != null)
				return context.map(res, new XMLWorldFunction(context));
		}
		return null;
	}
	
	
	private static final class XMLWorldFunction implements Value1Function<XMLElement, Scene> {
		
		private static final long serialVersionUID = 1L;
		private final LoadContext context;
		
		public XMLWorldFunction(LoadContext context) {
			this.context = context;
		}
		
		@Override
		public Scene apply(XMLElement xml_scene) {
			return new Scene() {
				
				@Override
				public void build(World world) {
					for(var xml_entity : xml_scene.getChildrens("entity")) {
						addEntity(world, xml_entity);
					}
					for(var xml_entity_ref : xml_scene.getChildrens("entity_ref")) {
						final var file = xml_entity_ref.getAttribute("file");
						final var res = context.load(Resource.class, file).get();
						
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
						final var xml = context.load(XMLElement.class, new ResultAssetKeyImpl(text))
								.get();
						
						addEntity(world, xml);
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
								lock.add(ObjectXMLBuilder.newFromXML(context, xml_component));
							}catch(ClassNotFoundException e) {
								e.printStackTrace();
							}
					}
				}
				
				@Override
				public ECSSystem getSystems(Iterable<? extends ECSSystem> globalSystems) {
					final var systems = new ArrayList<ECSSystem>();
					for(var system : globalSystems)
						systems.add(system);
					for(var xml_system : xml_scene.getChildrens("system"))
						try {
							systems.add(ObjectXMLBuilder.newFromXML(context, xml_system));
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
				
			};
		}
		
	}
	
}
