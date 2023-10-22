package com.greentree.engine.moon.render.scene

import com.greentree.commons.graphics.smart.mesh.Mesh
import com.greentree.commons.graphics.smart.scene.Camera
import com.greentree.commons.graphics.smart.scene.DirectionLight
import com.greentree.commons.graphics.smart.scene.Model
import com.greentree.commons.graphics.smart.scene.PointLight
import com.greentree.commons.graphics.smart.scene.RenderScene
import com.greentree.commons.graphics.smart.scene.SceneObject
import com.greentree.commons.graphics.smart.shader.material.Material
import com.greentree.commons.graphics.smart.target.FrameBuffer
import com.greentree.commons.graphics.smart.texture.Texture
import com.greentree.commons.image.Color
import com.greentree.commons.math.vector.AbstractVector3f
import com.greentree.engine.moon.base.transform.Transform
import com.greentree.engine.moon.ecs.Entity
import com.greentree.engine.moon.ecs.WorldEntity
import com.greentree.engine.moon.ecs.component.Component
import com.greentree.engine.moon.ecs.filter.Filter
import com.greentree.engine.moon.render.camera.CameraComponent
import com.greentree.engine.moon.render.camera.CameraTarget
import com.greentree.engine.moon.render.camera.SkyBoxComponent
import com.greentree.engine.moon.render.light.HasShadow
import com.greentree.engine.moon.render.light.direction.DirectionLightComponent
import com.greentree.engine.moon.render.light.point.PointLightComponent
import com.greentree.engine.moon.render.mesh.MeshComponent
import com.greentree.engine.moon.render.mesh.MeshRenderer
import org.joml.Matrix4f
import org.joml.Matrix4fc

class ECSRenderScene(
	private val ecsCameras: Filter<out WorldEntity>,
	private val ecsModels: Filter<out WorldEntity>,
	private val ecsPointLights: Filter<out WorldEntity>,
	private val ecsDirectionLights: Filter<out WorldEntity>,
) : RenderScene {

	override val cameras: Iterable<Camera>
		get() = ecsCameras.map(WorldEntity::toCamera)
	override val directionLights: Iterable<DirectionLight>
		get() = ecsDirectionLights.map(WorldEntity::toDirectionLight)
	override val models: Iterable<Model>
		get() = ecsModels.map(WorldEntity::toModel)
	override val pointLights: Iterable<PointLight>
		get() = ecsPointLights.map(WorldEntity::toPointLight)
}

private inline fun <reified T : Component> Entity.get() = get(T::class.java)
private inline fun <reified T : Component> Entity.has() = contains(T::class.java)

abstract class AbstractSceneObject(
	val entity: Entity,
) : SceneObject {

	private val component: SceneObjectParamsComponent

	init {
		if(entity.has<SceneObjectParamsComponent>())
			component = entity.get<SceneObjectParamsComponent>()
		else {
			component = SceneObjectParamsComponent()
			entity.add(component)
		}
	}

	override val options
		get() = component

	override fun equals(other: Any?): Boolean {
		if(this === other) return true
		if(javaClass != other?.javaClass) return false
		other as AbstractSceneObject
		if(entity != other.entity) return false
		return true
	}

	override fun hashCode(): Int {
		return entity.hashCode()
	}
}

class SceneObjectParamsComponent : Component, SceneObject.Params {

	private val map = HashMap<String, SceneObject.Params.Param>(5)

	override fun copy(): Component {
		return this
	}

	override fun get(name: String) = map.getOrPut(name) { MapParam() }
}

class MapParam : SceneObject.Params.Param {

	private val map = HashMap<Class<*>, Any>(5)

	override fun <T : Any> getOptional(cls: Class<T>) = run {
		map[cls] as T?
	}

	override fun <T : Any> set(cls: Class<T>, value: T) {
		map[cls] = value
	}
}

fun Entity.toCamera(): Camera {
	return ECSCamera(this)
}

class ECSCamera(entity: Entity) : Camera, AbstractSceneObject(entity) {

	override val direction: AbstractVector3f
		get() = entity.get<Transform>().direction()
	override val height: Float
		get() = entity.get<CameraComponent>().height + 0f
	override val position: AbstractVector3f
		get() = entity.get<Transform>().position
	override val projection: Matrix4fc
		get() = entity.get<CameraComponent>().projection
	override val skybox: Texture?
		get() {
			if(entity.has<SkyBoxComponent>())
				return entity.get<SkyBoxComponent>().texture.value
			return null
		}
	override val target: FrameBuffer
		get() = entity.get<CameraTarget>().target
	override val width: Float
		get() = entity.get<CameraComponent>().width + 0f
}

fun Entity.toDirectionLight(): DirectionLight {
	return ECSDirectionLight(this)
}

class ECSDirectionLight(entity: Entity) : DirectionLight, AbstractSceneObject(entity) {

	override val color: Color
		get() = entity.get<DirectionLightComponent>().color
	override val direction: AbstractVector3f
		get() = entity.get<Transform>().direction()
	override val hasShadow: Boolean
		get() = entity.has<HasShadow>()
	override val intensity: Float
		get() = entity.get<DirectionLightComponent>().intensity
	override val position: AbstractVector3f
		get() = entity.get<Transform>().position
	override val size: Float
		get() = 50f
}

fun Entity.toModel(): Model {
	return ECSModel(this)
}

class ECSModel(entity: Entity) : Model, AbstractSceneObject(entity) {

	override val material: Material
		get() = entity.get<MeshRenderer>().material.value
	override val mesh: Mesh
		get() = entity.get<MeshComponent>().mesh.value
	override val model: Matrix4f
		get() = entity.get<Transform>().modelMatrix
}

fun Entity.toPointLight(): PointLight {
	return ECSPointLight(this)
}

class ECSPointLight(entity: Entity) : PointLight, AbstractSceneObject(entity) {

	override val color: Color
		get() = entity.get<PointLightComponent>().color
	override val hasShadow: Boolean
		get() = entity.has<HasShadow>()
	override val intensity: Float
		get() = entity.get<PointLightComponent>().intensity
	override val position: AbstractVector3f
		get() = entity.get<Transform>().position
}
