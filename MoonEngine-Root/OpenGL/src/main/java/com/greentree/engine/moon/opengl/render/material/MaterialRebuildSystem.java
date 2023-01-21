package com.greentree.engine.moon.opengl.render.material;

import com.greentree.common.graphics.sgl.shader.GLShaderProgram;
import com.greentree.common.renderer.material.Material;
import com.greentree.common.renderer.material.MaterialPropertiesImpl;
import com.greentree.common.renderer.opengl.material.GLTextureProperty;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.Values;
import com.greentree.commons.assets.value.function.Value2Function;
import com.greentree.engine.moon.base.AssetManagerProperty;
import com.greentree.engine.moon.base.scene.EnginePropertiesWorldComponent;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.FilterBuilder;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.render.MeshRenderer;

public final class MaterialRebuildSystem implements InitSystem, UpdateSystem, DestroySystem {
	
	private static final FilterBuilder RENDERERS = new FilterBuilder()
			.required(GLPBRMeshRendener.class).ignore(MeshRenderer.class);
	private Value<GLShaderProgram> program;
	private Filter renderes;
	
	@Override
	public void destroy() {
		program.close();
		renderes.close();
	}
	
	@Override
	public void init(World world) {
		renderes = RENDERERS.build(world);
		
		final var manager = world.get(EnginePropertiesWorldComponent.class).properties()
				.get(AssetManagerProperty.class).manager();
		program = manager.loadAsync(GLShaderProgram.class, "pbr_mapping.glsl");
	}
	
	private static final class MaterialRebuildAssetSerializator
			implements Value2Function<GLShaderProgram, GLPBRMaterial, Material> {
		
		private static final long serialVersionUID = 1L;
		
		@Override
		public Material apply(GLShaderProgram program, GLPBRMaterial material) {
			final var ps = new MaterialPropertiesImpl();
			
			ps.put("material.albedo", new GLTextureProperty(material.albedo()));
			ps.put("material.ao", new GLTextureProperty(material.ambientOcclusion()));
			ps.put("material.displacement", new GLTextureProperty(material.displacement()));
			ps.put("material.metallic", new GLTextureProperty(material.metallic()));
			ps.put("material.normal", new GLTextureProperty(material.normal()));
			ps.put("material.roughness", new GLTextureProperty(material.roughness()));
			
			ps.put("ao_scale", 1f);
			ps.put("height_scale", 1f);
			
			return new Material(ps, program);
		}
		
	}
	
	@Override
	public void update() {
		for(var e : renderes) {
			final var material = e.get(GLPBRMeshRendener.class).material;
			
			final var m = Values.map(program, material, new MaterialRebuildAssetSerializator());
			
			e.add(new MeshRenderer(m));
		}
	}
	
}
