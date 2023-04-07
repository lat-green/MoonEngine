package com.greentree.engine.moon.render.mesh;

import com.greentree.engine.moon.assets.value.provider.ConstProvider;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.annotation.CreateComponent;
import com.greentree.engine.moon.ecs.annotation.ReadComponent;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.FilterBuilder;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.render.MaterialUtil;
import com.greentree.engine.moon.render.pipeline.material.Material;
import com.greentree.engine.moon.render.pipeline.material.MaterialPropertiesBase;
import com.greentree.engine.moon.render.shader.ShaderProgramData;

public class SpriteMeshGenerator implements InitSystem, UpdateSystem, DestroySystem {
	
	private static final FilterBuilder SPRITE_MESHS = new FilterBuilder().required(SpriteRenderer.class)
			.ignore(MeshComponent.class);
	private static final FilterBuilder SPRITE_RENDERS = new FilterBuilder().required(SpriteRenderer.class)
			.ignore(MeshRenderer.class);
	
	private Filter sprite_meshs;
	private Filter sprite_renders;
	
	private StaticMesh MESH;
	private ShaderProgramData SHADER;
	
	@Override
	public void init(World world) {
		sprite_meshs = SPRITE_MESHS.build(world);
		sprite_renders = SPRITE_RENDERS.build(world);
		MESH = MeshUtil.QUAD;
		SHADER = MaterialUtil.getDefaultSpriteShader();
	}
	
	@Override
	public void destroy() {
		sprite_meshs.close();
		sprite_meshs = null;
		sprite_renders.close();
		sprite_renders = null;
	}
	
	@ReadComponent({SpriteRenderer.class})
	@CreateComponent({MeshRenderer.class, MeshComponent.class})
	@Override
	public void update() {
		for(var e : sprite_meshs) {
			final var mesh = ConstProvider.newValue(MESH);
			final var c = new MeshComponent(mesh);
			e.add(c);
		}
		for(var e : sprite_renders) {
			final var m = new Material(SHADER, new MaterialPropertiesBase());
			m.properties().put("render_texture", e.get(SpriteRenderer.class).texture().get());
			final var c = new MeshRenderer(ConstProvider.newValue(m));
			e.add(c);
		}
	}
	
	
	
}
