package com.greentree.engine.moon.opengl.render.material;

import com.greentree.common.ecs.World;
import com.greentree.common.ecs.filter.Filter;
import com.greentree.common.ecs.filter.FilterBuilder;
import com.greentree.common.ecs.system.DestroySystem;
import com.greentree.common.ecs.system.InitSystem;
import com.greentree.common.ecs.system.UpdateSystem;
import com.greentree.common.graphics.sgl.shader.GLShaderProgram;
import com.greentree.common.renderer.material.Material;
import com.greentree.common.renderer.material.MaterialPropertiesImpl;
import com.greentree.common.renderer.opengl.material.GLTextureProperty;
import com.greentree.commons.assets.value.ConstWrappedValue;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.map.MapValueImpl;
import com.greentree.engine.moon.base.AssetManagerProperty;
import com.greentree.engine.moon.base.scene.EnginePropertiesWorldComponent;
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
	
	@Override
	public void update() {
		for(var e : renderes) {
			final var material = e.get(GLPBRMeshRendener.class).material;
			
			final var m = ConstWrappedValue.newValue(material,
					new MapValueImpl<GLPBRMaterial, Material>() {
						
						private static final long serialVersionUID = 1L;
						
						@Override
						protected Material map(GLPBRMaterial value) {
							final var ps = new MaterialPropertiesImpl();
							
							ps.put("material.albedo", new GLTextureProperty(value.albedo()));
							ps.put("material.ao", new GLTextureProperty(value.ambientOcclusion()));
							ps.put("material.displacement",
									new GLTextureProperty(value.displacement()));
							ps.put("material.metallic", new GLTextureProperty(value.metallic()));
							ps.put("material.normal", new GLTextureProperty(value.normal()));
							ps.put("material.roughness", new GLTextureProperty(value.roughness()));
							
							ps.put("ao_scale", 1f);
							ps.put("height_scale", 1f);
							
							return new Material(ps, program.get());
						}
						
						@Override
						protected Material map(GLPBRMaterial value, Material dest) {
							return map(value);
						}
						
					});
			
			e.add(new MeshRenderer(m));
		}
	}
	
}
