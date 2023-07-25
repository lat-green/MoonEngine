package com.greentree.engine.moon.base.assets.scene;

import com.greentree.commons.data.resource.Resource;
import com.greentree.commons.reflection.ClassUtil;
import com.greentree.commons.reflection.info.TypeInfo;
import com.greentree.commons.util.string.RefStringBuilder;
import com.greentree.commons.xml.XMLElement;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.asset.Value1Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.ResourceAssetKey;
import com.greentree.engine.moon.assets.key.ResultAssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;
import com.greentree.engine.moon.base.component.AnnotationUtil;
import com.greentree.engine.moon.base.layer.Layer;
import com.greentree.engine.moon.base.name.Name;
import com.greentree.engine.moon.base.name.Names;
import com.greentree.engine.moon.base.parent.Parent;
import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.component.Component;
import com.greentree.engine.moon.ecs.scene.Scene;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.scene.SimpleScene;
import com.greentree.engine.moon.ecs.scene.WorldProperty;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.ECSSystem;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.ecs.system.debug.DebugSystems;
import com.greentree.engine.moon.ecs.system.debug.PrintStreamSystemsProfiler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class XMLSceneAssetSerializator implements AssetSerializator<Scene> {

    private static final Logger LOG = LogManager.getLogger(XMLSceneAssetSerializator.class);

    @Override
    public boolean canLoad(AssetManager manager, AssetKey key) {
        return manager.canLoad(XMLElement.class, key);
    }

    @Override
    public Asset<Scene> load(AssetManager manager, AssetKey ckey) {
        if (manager.canLoad(XMLElement.class, ckey)) {
            final var res = manager.load(XMLElement.class, ckey);
            return AssetKt.map(res, new XMLWorldFunction(manager));
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<T> findClass(Class<T> baseClass, String className) throws ClassNotFoundException {
        return (Class<T>) ClassUtil.loadClassInAllPackages(baseClass, className);
    }

    private static final class XMLWorldFunction implements Value1Function<XMLElement, Scene> {

        private static final long serialVersionUID = 1L;
        private final ObjectXMLBuilder builder = new ObjectXMLBuilder();
        private final AssetManager context;
        private final Map<String, Entity> names = new HashMap<>();

        public XMLWorldFunction(AssetManager context) {
            this.context = context;
            builder.add(new XMLTypeAddapter() {

                @Override
                public <T> Constructor<T> newInstance(Context c, TypeInfo<T> type, XMLElement xml_value) {
                    final var xml_value_text = xml_value.getContent();
                    final var key = new ResultAssetKey(xml_value_text);
                    if (context.canLoad(type, key)) {
                        final var v = context.load(type, key).getValue();
                        return new ValueConstructor<>(v);
                    }
                    return null;
                }
            });
            builder.add(new XMLTypeAddapter() {

                @SuppressWarnings("unchecked")
                @Override
                public <T> Constructor<T> newInstance(Context c, TypeInfo<T> type, XMLElement xml_value) {
                    if (ClassUtil.isExtends(Asset.class, type.toClass())) {
                        final var value_type = type.getTypeArguments()[0].getBoxing();
                        try (final var key = c.newInstance(AssetKey.class, xml_value)) {
                            final var value = context.load(value_type, key.value());
                            return new ValueConstructor<>((T) value);
                        }
                    }
                    return null;
                }

                @Override
                public Class<?> getLoadOnly() {
                    return Asset.class;
                }
            });
            builder.add(new XMLTypeAddapter() {

                @Override
                public <T> Constructor<T> newInstance(Context c, TypeInfo<T> type, XMLElement xml_value) {
                    var iter = xml_value.getChildrens();
                    if (iter.isEmpty())
                        return null;
                    final var key = new ResultAssetKey(xml_value.getChildrens().iterator().next());
                    if (context.canLoad(type, key)) {
                        final var v = context.load(type, key).getValue();
                        return new ValueConstructor<>(v);
                    }
                    return null;
                }
            });
            builder.add(new XMLTypeAddapter() {

                @SuppressWarnings("unchecked")
                @Override
                public <T> Constructor<T> newInstance(Context context, TypeInfo<T> type, XMLElement xml_value) {
                    if (AssetKey.class == type.toClass()) {
                        final var xml_value_text = xml_value.getContent();
                        final var xml_value_type = xml_value.getAttribute("type");
                        final AssetKey key;
                        if (xml_value_type == null) {
                            key = new ResourceAssetKey(xml_value_text);
                        } else {
                            final Class<AssetKey> cls;
                            try {
                                cls = findClass(AssetKey.class, xml_value_type);
                            } catch (ClassNotFoundException e) {
                                throw new IllegalArgumentException(e);
                            }
                            try (final var c = context.newInstance(cls, xml_value)) {
                                key = c.value();
                            }
                        }
                        return new ValueConstructor<>((T) key);
                    }
                    return null;
                }

                @Override
                public Class<?> getLoadOnly() {
                    return AssetKey.class;
                }
            });
            builder.add(new XMLTypeAddapter() {

                @Override
                public <T> Constructor<T> newInstance(Context context, TypeInfo<T> type, XMLElement xml_value) {
                    if (!ClassUtil.isExtends(Entity.class, type.toClass()))
                        return null;
                    var name = xml_value.getContent();
                    return () -> (T) names.get(name);
                }

            });
        }

        @Override
        public Scene apply(XMLElement xml_scene) {
            names.clear();
            return new SimpleScene() {

                @Override
                public void build(SceneProperties properties) {
                    var world = properties.get(WorldProperty.class).getWorld();
                    var xml_entities = new ArrayList<XMLElement>();
                    for (var xml_entity_ref : xml_scene.getChildrens("entity_ref"))
                        xml_entities.add(buildEntityRef(xml_entity_ref));
                    for (var xml_entity : xml_scene.getChildrens("entity"))
                        xml_entities.add(xml_entity);
                    var iter = xml_entities.iterator();
                    while (iter.hasNext()) {
                        var xml_entity = iter.next();
                        try {
                            addEntity(properties, world, xml_entity);
                            iter.remove();
                        } catch (RuntimeException e) {
                        }
                    }
                    for (var xml_entity : xml_entities)
                        addEntity(properties, world, xml_entity);
                }

                @Override
                public ECSSystem getSystems(Iterable<? extends ECSSystem> globalSystems) {
                    final var initSystems = new ArrayList<InitSystem>();
                    final var updateSystems = new ArrayList<UpdateSystem>();
                    final var destroySystems = new ArrayList<DestroySystem>();
                    for (var system : globalSystems) {
                        if (system instanceof InitSystem s)
                            initSystems.add(s);
                        if (system instanceof UpdateSystem s)
                            updateSystems.add(s);
                        if (system instanceof DestroySystem s)
                            destroySystems.add(s);
                    }
                    for (var xml_system : xml_scene.getChildrens("system"))
                        try {
                            var system = newFromXML(ECSSystem.class, xml_system);
                            if (system instanceof InitSystem s)
                                initSystems.add(s);
                            if (system instanceof UpdateSystem s)
                                updateSystems.add(s);
                            if (system instanceof DestroySystem s)
                                destroySystems.add(s);
                        } catch (RuntimeException | ClassNotFoundException e) {
                            LOG.error("", e);
                        }
                    final var log = new File("log");
                    log.mkdirs();
                    AnnotationUtil.sortInit(initSystems);
                    AnnotationUtil.sortUpdate(updateSystems);
                    AnnotationUtil.sortDestroy(destroySystems);
                    return new DebugSystems(
                            new PrintStreamSystemsProfiler(new File(log, "systems_init.log"),
                                    new File(log, "systems_update.log"), new File(log, "systems_destroy.log")),
                            initSystems, updateSystems, destroySystems);
                }

                private <T> T newFromXML(Class<T> baseClass, XMLElement xml_element) throws ClassNotFoundException {
                    final var systemClassName = xml_element.getAttribute("type");
                    final var cls = findClass(baseClass, systemClassName);
                    try (final var c = builder.newInstance(cls, xml_element)) {
                        if (c == null)
                            return null;
                        return c.value();
                    }
                }

                private XMLElement buildEntityRef(XMLElement xml_entity_ref) {
                    final var file = xml_entity_ref.getAttribute("file");
                    final var res = context.load(Resource.class, file).getValue();
                    final RefStringBuilder builder;
                    try {
                        builder = RefStringBuilder.build(res.open());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    final var map = new HashMap<String, String>();
                    for (var xmLproperty : xml_entity_ref.getChildrens("property")) {
                        final var name = xmLproperty.getAttribute("name");
                        final var value = xmLproperty.getContent();
                        map.put(name, value);
                    }
                    final var text = builder.toString(map);
                    return context.load(XMLElement.class, new ResultAssetKey(text)).getValue();
                }

                private Entity addEntity(SceneProperties properties, World world, XMLElement xml_entity) {
                    final var name_atr = xml_entity.getAttribute("name");
                    final var layer_atr = xml_entity.getAttribute("layer");
                    final var parent_atr = xml_entity.getAttribute("parent");
                    final var entity = world.newEntity();
                    try (final var lock = entity.lock()) {
                        if (name_atr != null) {
                            names.put(name_atr, entity);
                            lock.add(new Name(name_atr));
                        }
                        if (layer_atr != null)
                            lock.add(new Layer(layer_atr));
                        for (var xml_component : xml_entity.getChildrens("component"))
                            try {
                                final var c = newFromXML(Component.class, xml_component);
                                if (c != null)
                                    lock.add(c);
                            } catch (RuntimeException | ClassNotFoundException e) {
                                LOG.error("", e);
                            }
                    }
                    if (parent_atr != null) {
                        var parent = properties.get(Names.class).get(parent_atr);
                        Parent.setParent(entity, parent);
                    }
                    for (var xml_child : xml_entity.getChildrens("entity")) {
                        Parent.setParent(addEntity(properties, world, xml_child), entity);
                    }
                    for (var xml_child : xml_entity.getChildrens("entity_ref")) {
                        Parent.setParent(addEntity(properties, world, buildEntityRef(xml_child)), entity);
                    }
                    return entity;
                }

            };
        }

    }

}
