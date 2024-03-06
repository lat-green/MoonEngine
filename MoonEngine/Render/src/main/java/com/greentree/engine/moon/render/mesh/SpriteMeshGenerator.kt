package com.greentree.engine.moon.render.mesh

import com.greentree.commons.graphics.smart.mesh.Mesh
import com.greentree.commons.graphics.smart.shader.Shader
import com.greentree.commons.graphics.smart.shader.material.Material
import com.greentree.engine.moon.assets.asset.Asset
import com.greentree.engine.moon.assets.loader.loadAsset
import com.greentree.engine.moon.base.AssetManagerProperty
import com.greentree.engine.moon.base.component.CreateComponent
import com.greentree.engine.moon.base.component.ReadComponent
import com.greentree.engine.moon.base.property.modules.ReadProperty
import com.greentree.engine.moon.ecs.World
import com.greentree.engine.moon.ecs.WorldEntity
import com.greentree.engine.moon.ecs.filter.Filter
import com.greentree.engine.moon.ecs.filter.builder.FilterBuilder
import com.greentree.engine.moon.ecs.scene.SceneProperties
import com.greentree.engine.moon.ecs.system.UpdateSystem
import com.greentree.engine.moon.ecs.system.WorldInitSystem
import com.greentree.engine.moon.render.MaterialUtil

class SpriteMeshGenerator : WorldInitSystem, UpdateSystem {

	private var sprite_meshes: Filter<out WorldEntity>? = null
	private var sprite_renders: Filter<out WorldEntity>? = null
	private var mesh: Asset<Mesh>? = null
	private var shader: Asset<Shader>? = null

	@ReadProperty(AssetManagerProperty::class)
	override fun init(world: World, sceneProperties: SceneProperties) {
		val manager = sceneProperties.get(AssetManagerProperty::class.java).manager
		mesh = manager.loadAsset(MeshUtil.QUAD_SPRITE)
		shader = manager.loadAsset(MaterialUtil.getDefaultSpriteShader())
		sprite_meshes = SPRITE_MESHES.build(world)
		sprite_renders = SPRITE_NO_MESH_RENDERS.build(world)
	}

	@ReadComponent(SpriteRenderer::class)
	@CreateComponent(MeshRenderer::class, MeshComponent::class)
	override fun update() {
		for(e in sprite_meshes!!) {
			val c = MeshComponent(mesh)
			e.add(c)
		}
		for(e in sprite_renders!!) {
			val c = MeshRenderer(object : Asset<Material> {
				override val value: Material
					get() {
						val m = shader!!.value.newMaterial()
						m.put("render_texture", e.get(SpriteRenderer::class.java).texture().value)
						return m
					}
			})
			e.add(c)
		}
	}

	companion object {

		private val SPRITE_MESHES = FilterBuilder().require(SpriteRenderer::class.java)
			.ignore(MeshComponent::class.java)
		private val SPRITE_NO_MESH_RENDERS = FilterBuilder().require(SpriteRenderer::class.java)
			.ignore(MeshRenderer::class.java)
	}
}
