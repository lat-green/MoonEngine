package com.greentree.engine.moon.render.pipeline;

import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;

import com.greentree.commons.image.Color;
import com.greentree.commons.math.vector.AbstractVector3f;
import com.greentree.commons.math.vector.Vector3f;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.FilterBuilder;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.render.MeshRenderer;
import com.greentree.engine.moon.render.camera.CameraComponent;
import com.greentree.engine.moon.render.camera.CameraUtil;
import com.greentree.engine.moon.render.camera.Cameras;
import com.greentree.engine.moon.render.light.HasShadow;
import com.greentree.engine.moon.render.light.direction.DirectionLightComponent;
import com.greentree.engine.moon.render.light.point.PointLightComponent;
import com.greentree.engine.moon.render.material.FloatPropertyImpl;
import com.greentree.engine.moon.render.material.IntPropertyImpl;
import com.greentree.engine.moon.render.material.Material;
import com.greentree.engine.moon.render.mesh.MeshComponent;
import com.greentree.engine.moon.render.pipeline.target.RenderTargetTextute;

public final class ForvardRendering implements InitSystem, UpdateSystem, DestroySystem {
	
	private static final float FAR_PLANE = 250;
	private static final int SHADOW_SIZE = 512;
	
	private static final FilterBuilder CAMERAS = new FilterBuilder()
			.required(CameraComponent.class);
	private static final FilterBuilder POINT_LIGHT = new FilterBuilder()
			.required(PointLightComponent.class);
	private static final FilterBuilder DIR_LIGTH = new FilterBuilder()
			.required(DirectionLightComponent.class);
	private static final FilterBuilder RENDERER = new FilterBuilder().required(MeshRenderer.class);
	
	private Filter cameras, point_ligth, dir_ligth, renderer;
	
	private RenderContext context;
	private World world;
	
	@Override
	public void destroy() {
		world = null;
	}
	
	@Override
	public void init(World world) {
		this.world = world;
		cameras = CAMERAS.build(world);
		point_ligth = POINT_LIGHT.build(world);
		dir_ligth = DIR_LIGTH.build(world);
		renderer = RENDERER.build(world);
	}
	
	@Override
	public void update() {
		final var pointShadows = new HashMap<Entity, RenderTargetTextute>();
		final var directionShadows = new HashMap<Entity, RenderTargetTextute>();
		for(var light : point_ligth)
			if(light.contains(HasShadow.class)) {
				final var target = context.createRenderTarget().addDepthCubeMapTexture()
						.build(SHADOW_SIZE, SHADOW_SIZE);
				pointShadows.put(light, target);
				
				final var shadowMatrices = getShadowMatrices(light.get(Transform.class).position);
				
				try(final var buffer = target.buffer()) {
					buffer.clearRenderTargetDepth();
					for(var i = 0; i < shadowMatrices.length; i++)
						for(var m : renderer)
							buffer.drawMesh(m.get(MeshComponent.class).mesh().get(),
									m.get(Transform.class).getModelMatrix(),
									mapCubeMapShadowMaterial(
											context.getDefaultCubeMapShadowMaterial(), light,
											shadowMatrices[i], i));
				}
			}
		for(var light : dir_ligth)
			if(light.contains(HasShadow.class)) {
				final var target = context.createRenderTarget().addDepthTexture().build(SHADOW_SIZE,
						SHADOW_SIZE);
				directionShadows.put(light, target);
				
				try(final var buffer = target.buffer()) {
					buffer.clearRenderTargetDepth();
					for(var m : renderer)
						buffer.drawMesh(m.get(MeshComponent.class).mesh().get(),
								m.get(Transform.class).getModelMatrix(),
								mapShadowMaterial(context.getDefaultShadowMaterial()));
				}
			}
		for(var camera : cameras) {
			final var target = context.getTarget(camera);
			try(final var buffer = target.buffer()) {
				if(context.getSkyBox(camera) != null) {
					buffer.clearRenderTargetDepth();
					final var skybox = context.getSkyBox(camera);
					buffer.drawSkyBox(skybox);
				}else
					buffer.clearRenderTarget(Color.gray, 1);
				for(var m : renderer)
					buffer.drawMesh(m.get(MeshComponent.class).mesh().get(),
							m.get(Transform.class).getModelMatrix(),
							mapMaterial(m.get(MeshRenderer.class).material().get(), pointShadows,
									directionShadows));
			}
		}
		final var camera = world.get(Cameras.class).main();
		final var texture = context.getTarget(camera).getColorTexture();
		try(final var buffer = context.buffer()) {
			buffer.drawTexture(texture);
		}
		context.swapBuffer();
		
		for(var t : pointShadows.values())
			t.close();
		for(var t : directionShadows.values())
			t.close();
	}
	
	private Matrix4f[] getShadowMatrices(AbstractVector3f pos) {
		var shadowMatrices = new Matrix4f[6];
		shadowMatrices[0] = new Matrix4f().lookAt(pos.toJoml(),
				new Vector3f(1.0f, 0.0f, 0.0f).add(pos).toJoml(),
				new Vector3f(0.0f, -1.0f, 0.0f).toJoml());
		shadowMatrices[1] = new Matrix4f().lookAt(pos.toJoml(),
				new Vector3f(-1.0f, 0.0f, 0.0f).add(pos).toJoml(),
				new Vector3f(0.0f, -1.0f, 0.0f).toJoml());
		shadowMatrices[2] = new Matrix4f().lookAt(pos.toJoml(),
				new Vector3f(0.0f, 1.0f, 0.0f).add(pos).toJoml(),
				new Vector3f(0.0f, 0.0f, 1.0f).toJoml());
		shadowMatrices[3] = new Matrix4f().lookAt(pos.toJoml(),
				new Vector3f(0.0f, -1.0f, 0.0f).add(pos).toJoml(),
				new Vector3f(0.0f, 0.0f, -1.0f).toJoml());
		shadowMatrices[4] = new Matrix4f().lookAt(pos.toJoml(),
				new Vector3f(0.0f, 0.0f, 1.0f).add(pos).toJoml(),
				new Vector3f(0.0f, -1.0f, 0.0f).toJoml());
		shadowMatrices[5] = new Matrix4f().lookAt(pos.toJoml(),
				new Vector3f(0.0f, 0.0f, -1.0f).add(pos).toJoml(),
				new Vector3f(0.0f, -1.0f, 0.0f).toJoml());
		
		{
			var shadowProj = new Matrix4f(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, -1, 1, 0, 0, 0, 0);
			
			for(var i = 0; i < 6; i++)
				shadowProj.mul(shadowMatrices[i], shadowMatrices[i]);
		}
		return shadowMatrices;
	}
	
	private Material mapCubeMapShadowMaterial(Material material, Entity light,
			Matrix4f shadowMatrix, int face) {
		material = mapMaterial0(material);
		final var properties = material.properties();
		
		properties.put("lightPos", light.get(Transform.class).position);
		properties.put("face", face);
		properties.put("projectionView", shadowMatrix);
		
		return material;
	}
	
	private Material mapMaterial(Material material, Map<Entity, RenderTargetTextute> pointShadows,
			Map<Entity, RenderTargetTextute> directionShadows) {
		material = mapMaterial0(material);
		final var properties = material.properties();
		{
			var i = 0;
			for(var light : point_ligth) {
				var name = "point_light[" + i + "].";
				properties.put(name + "position", light.get(Transform.class).position);
				properties.putRGB(name + "color", light.get(PointLightComponent.class).color());
				properties.put(name + "intensity",
						light.get(PointLightComponent.class).intensity());
				
				if(light.contains(HasShadow.class))
					properties.put(name + "depth", pointShadows.get(light).getDepthTexture());
				
				i++;
			}
			properties.put("count_point_light", new IntPropertyImpl(i));
		}
		{
			var i = 0;
			for(var light : dir_ligth) {
				var name = "dir_light[" + i + "].";
				properties.put(name + "direction", light.get(Transform.class).direction());
				properties.putRGB(name + "color", light.get(DirectionLightComponent.class).color());
				properties.put(name + "intensity",
						light.get(DirectionLightComponent.class).intensity());
				
				if(light.contains(HasShadow.class)) {
					final Matrix4f lightSpaceMatrix;
					
					final var view = CameraUtil.getView(light);
					final var projection = light.get(DirectionLightComponent.class)
							.getProjectionMatrix();
					
					lightSpaceMatrix = new Matrix4f().identity().mul(projection).mul(view);
					
					properties.put("lightSpaceMatrix[" + i + "]", lightSpaceMatrix);
					
					properties.put(name + "depth", directionShadows.get(light).getDepthTexture());
				}
				i++;
			}
			properties.put("count_dir_light", new IntPropertyImpl(i));
		}
		return material;
	}
	
	private Material mapMaterial0(Material material) {
		final var properties = material.properties();
		properties.put("far_plane", new FloatPropertyImpl(FAR_PLANE));
		return material;
	}
	
	private Material mapShadowMaterial(Material material) {
		material = mapMaterial0(material);
		return material;
	}
	
}
