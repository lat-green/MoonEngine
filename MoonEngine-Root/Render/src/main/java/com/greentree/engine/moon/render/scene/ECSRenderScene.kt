package com.greentree.engine.moon.render.scene

import com.greentree.commons.graphics.smart.mesh.Mesh
import com.greentree.commons.graphics.smart.scene.Camera
import com.greentree.commons.graphics.smart.scene.DirectionLight
import com.greentree.commons.graphics.smart.scene.Model
import com.greentree.commons.graphics.smart.scene.PointLight
import com.greentree.commons.graphics.smart.scene.RenderScene
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
import com.greentree.engine.moon.render.light.HasShadow
import com.greentree.engine.moon.render.light.direction.DirectionLightComponent
import com.greentree.engine.moon.render.light.point.PointLightComponent
import com.greentree.engine.moon.render.mesh.MeshComponent
import org.joml.Matrix4f
import org.joml.Matrix4fc

class ECSRenderScene(
	val ecsCameras: Filter<out WorldEntity>,
	val ecsModels: Filter<out WorldEntity>,
	val ecsPointLights: Filter<out WorldEntity>,
	val ecsDirectionLights: Filter<out WorldEntity>,
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

fun Entity.toCamera(): Camera {
	return object : Camera {
		override val direction
			get() = get<Transform>().direction()
		override val height: Float
			get() = get<CameraComponent>().height + 0f
		override val position: AbstractVector3f
			get() = get<Transform>().position
		override val projection: Matrix4fc
			get() = get<CameraComponent>().projection
		override val skybox: Texture?
			get() {
//				if(has<SkyBoxComponent>())
//					return get<SkyBoxComponent>().texture.value
//				return null
				TODO()
			}
		override val target: FrameBuffer
			get() = TODO()//get<CameraTarget>().target
		override val width: Float
			get() = get<CameraComponent>().width + 0f
	}
}

fun Entity.toDirectionLight(): DirectionLight {
	return object : DirectionLight {
		override val color: Color
			get() = get<DirectionLightComponent>().color
		override val direction: AbstractVector3f
			get() = get<Transform>().direction()
		override val hasShadow: Boolean
			get() = has<HasShadow>()
		override val intensity: Float
			get() = get<DirectionLightComponent>().intensity
		override val position: AbstractVector3f
			get() = get<Transform>().position
		override val size: Float
			get() = 50f
		override val target: FrameBuffer
			get() = TODO()//get<DirectionLightTarget>().target
	}
}

fun Entity.toModel(): Model {
	return object : Model {
		override val material: Material
			get() = TODO()//get<MeshRenderer>().material.value
		override val mesh: Mesh
			get() = get<MeshComponent>().mesh.value
		override val model: Matrix4f
			get() = get<Transform>().modelMatrix
	}
}

fun Entity.toPointLight(): PointLight {
	return object : PointLight {
		override val color: Color
			get() = get<PointLightComponent>().color
		override val hasShadow: Boolean
			get() = has<HasShadow>()
		override val intensity: Float
			get() = get<PointLightComponent>().intensity
		override val position: AbstractVector3f
			get() = get<Transform>().position
		override val target: FrameBuffer
			get() = TODO()//get<PointLightTarget>().target
	}
}
