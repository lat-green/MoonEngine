package com.greentree.engine.moon.base.assets.scene

import com.greentree.commons.reflection.ClassUtil
import com.greentree.commons.reflection.info.TypeInfo
import com.greentree.commons.xml.XMLElement
import com.greentree.engine.moon.assets.Value1Function
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.key.AssetKey
import com.greentree.engine.moon.assets.key.ResourceAssetKey
import com.greentree.engine.moon.assets.serializator.AssetSerializator
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader
import com.greentree.engine.moon.assets.serializator.loader.load
import com.greentree.engine.moon.assets.serializator.manager.loadAsync
import com.greentree.engine.moon.base.AssetManagerProperty
import com.greentree.engine.moon.base.assets
import com.greentree.engine.moon.base.assets.scene.adapters.Constructor
import com.greentree.engine.moon.base.assets.scene.adapters.Context
import com.greentree.engine.moon.base.assets.scene.adapters.ObjectXMLBuilder
import com.greentree.engine.moon.base.assets.scene.adapters.ValueConstructor
import com.greentree.engine.moon.base.assets.scene.adapters.XMLTypeAddapter
import com.greentree.engine.moon.base.assets.scene.adapters.findClass
import com.greentree.engine.moon.base.assets.text.RefStringBuilderAssetKey
import com.greentree.engine.moon.base.component.AnnotationUtil
import com.greentree.engine.moon.base.layer.Layer
import com.greentree.engine.moon.base.name.Name
import com.greentree.engine.moon.base.name.Names
import com.greentree.engine.moon.base.parent.Parent
import com.greentree.engine.moon.ecs.Entity
import com.greentree.engine.moon.ecs.World
import com.greentree.engine.moon.ecs.component.Component
import com.greentree.engine.moon.ecs.scene.Scene
import com.greentree.engine.moon.ecs.scene.SceneProperties
import com.greentree.engine.moon.ecs.scene.SimpleScene
import com.greentree.engine.moon.ecs.scene.WorldProperty
import com.greentree.engine.moon.ecs.system.DestroySystem
import com.greentree.engine.moon.ecs.system.ECSSystem
import com.greentree.engine.moon.ecs.system.InitSystem
import com.greentree.engine.moon.ecs.system.UpdateSystem
import com.greentree.engine.moon.ecs.system.debug.DebugSystems
import com.greentree.engine.moon.ecs.system.debug.PrintStreamSystemsProfiler
import org.apache.logging.log4j.LogManager
import java.io.File

object XMLSceneAssetSerializator : AssetSerializator<Scene> {

	override fun load(manager: AssetLoader.Context, key: AssetKey): Asset<Scene> {
		val res = manager.load<XMLElement>(key)
		return res.map(XMLWorldFunction)
	}

	private object XMLWorldFunction : Value1Function<XMLElement, Scene> {

		override fun apply(xml_scene: XMLElement): Scene {
			return object : SimpleScene {
				val builder = ObjectXMLBuilder()
				val names: MutableMap<String, Entity> = HashMap()
				override fun build(properties: SceneProperties) {
					val context = properties.get(AssetManagerProperty::class.java).manager
					builder.add(object : XMLTypeAddapter {
						override fun <T> newInstance(
							c: Context,
							type: TypeInfo<T>,
							xml_value: XMLElement,
						): Constructor<T>? {
							if(ClassUtil.isExtends(Asset::class.java, type.toClass())) {
								if(type.typeArguments.size == 0) throw UnsupportedOperationException("asset type without Type Arguments")
								val value_type = type.typeArguments[0].boxing
								c.newInstance(AssetKey::class.java, xml_value).use { key ->
									val value = context.loadAsync(value_type, key.value())
									if(!value.isValid()) {
										value.value
										throw UnsupportedOperationException("build not valid asset $value from $xml_value")
									}
									return ValueConstructor(value as T)
								}
							}
							return null
						}

						override fun getLoadOnly(): Class<*> {
							return Asset::class.java
						}
					})
					builder.add(object : XMLTypeAddapter {
						override fun <T> newInstance(
							context: Context,
							type: TypeInfo<T>,
							xml_value: XMLElement,
						): Constructor<T>? {
							if(!ClassUtil.isExtends(Entity::class.java, type.toClass())) return null
							val name = xml_value.content
							return Constructor { names[name] as T? }
						}
					})
					val world = properties.get(WorldProperty::class.java).world
					val xml_entities = ArrayList<XMLElement>()
					for(xml_entity_ref in xml_scene.getChildrens("entity_ref")) xml_entities.add(
						buildEntityRef(
							properties,
							xml_entity_ref
						)
					)
					for(xml_entity in xml_scene.getChildrens("entity")) xml_entities.add(xml_entity)
					val iter = xml_entities.iterator()
					while(iter.hasNext()) {
						val xml_entity = iter.next()
						try {
							addEntity(properties, world, xml_entity)
							iter.remove()
						} catch(e: RuntimeException) {
						}
					}
					for(xml_entity in xml_entities) addEntity(properties, world, xml_entity)
				}

				override fun getSystems(): ECSSystem {
					val initSystems = ArrayList<InitSystem>()
					val updateSystems = ArrayList<UpdateSystem>()
					val destroySystems = ArrayList<DestroySystem>()
					for(xml_system in xml_scene.getChildrens("system")) try {
						val system = newFromXML(ECSSystem::class.java, xml_system)
						if(system is InitSystem) initSystems.add(system)
						if(system is UpdateSystem) updateSystems.add(system)
						if(system is DestroySystem) destroySystems.add(system)
					} catch(e: RuntimeException) {
						LOG.error("load system", e)
					} catch(e: ClassNotFoundException) {
						LOG.error("load system", e)
					}
					val log = File("log")
					log.mkdirs()
					AnnotationUtil.sortInit(initSystems)
					AnnotationUtil.sortUpdate(updateSystems)
					AnnotationUtil.sortDestroy(destroySystems)
					return DebugSystems(
						PrintStreamSystemsProfiler(
							File(log, "systems_init.log"),
							File(log, "systems_update.log"), File(log, "systems_destroy.log")
						),
						initSystems, updateSystems, destroySystems
					)
				}

				@Throws(ClassNotFoundException::class)
				private fun <T> newFromXML(baseClass: Class<T>, xml_element: XMLElement): T {
					val systemClassName = xml_element.getAttribute("type")
					val cls = findClass(baseClass, systemClassName)
					builder.newInstance(cls, xml_element).use { c ->
						return c.value()
					}
				}

				private fun buildEntityRef(properties: SceneProperties, xml_entity_ref: XMLElement): XMLElement {
					val context = properties.assets
					val file = xml_entity_ref.getAttribute("file")
					val map = HashMap<String, String>()
					for(xmLproperty in xml_entity_ref.getChildrens("property")) {
						val name = xmLproperty.getAttribute("name")
						val value = xmLproperty.content
						map[name] = value
					}
					return context.load(
						XMLElement::class.java,
						RefStringBuilderAssetKey(ResourceAssetKey(file), map)
					).value
				}

				private fun addEntity(properties: SceneProperties, world: World, xml_entity: XMLElement): Entity {
					val name_atr = xml_entity.getAttribute("name")
					val layer_atr = xml_entity.getAttribute("layer")
					val parent_atr = xml_entity.getAttribute("parent")
					val entity = world.newEntity()
					entity.lock().use { lock ->
						if(name_atr != null) {
							names[name_atr] = entity
							lock.add(Name(name_atr))
						}
						if(layer_atr != null) lock.add(Layer(layer_atr))
						for(xml_component in xml_entity.getChildrens("component")) try {
							val c = newFromXML(
								Component::class.java, xml_component
							)
							lock.add(c)
						} catch(e: RuntimeException) {
							LOG.error("load entity", e)
						} catch(e: ClassNotFoundException) {
							LOG.error("load entity", e)
						}
					}
					if(parent_atr != null) {
						val parent = properties.get(Names::class.java)[parent_atr]
						Parent.setParent(entity, parent)
					}
					for(xml_child in xml_entity.getChildrens("entity")) {
						Parent.setParent(addEntity(properties, world, xml_child), entity)
					}
					for(xml_child in xml_entity.getChildrens("entity_ref")) {
						Parent.setParent(addEntity(properties, world, buildEntityRef(properties, xml_child)), entity)
					}
					return entity
				}
			}
		}
	}

	private val LOG = LogManager.getLogger(
		XMLSceneAssetSerializator::class.java
	)
}
