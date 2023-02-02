package com.greentree.engine.moon.render;

import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;

import com.greentree.commons.image.Color;
import com.greentree.commons.math.vector.AbstractVector3f;
import com.greentree.commons.math.vector.Vector3f;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.annotation.ReadWorldComponent;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.FilterBuilder;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.render.camera.CameraTarget;
import com.greentree.engine.moon.render.camera.CameraUtil;
import com.greentree.engine.moon.render.camera.Cameras;
import com.greentree.engine.moon.render.camera.SkyBoxComponent;
import com.greentree.engine.moon.render.light.HasShadow;
import com.greentree.engine.moon.render.light.direction.DirectionLightComponent;
import com.greentree.engine.moon.render.light.point.PointLightComponent;
import com.greentree.engine.moon.render.mesh.MeshComponent;
import com.greentree.engine.moon.render.mesh.MeshRenderer;
import com.greentree.engine.moon.render.pipeline.RenderLibrary;
import com.greentree.engine.moon.render.pipeline.material.MaterialProperties;
import com.greentree.engine.moon.render.pipeline.material.MaterialPropertiesBase;
import com.greentree.engine.moon.render.pipeline.target.RenderTargetTextute;

public final class ForvardRendering implements InitSystem, UpdateSystem, DestroySystem {
	
	private static final float FAR_PLANE = 250;
	private static final int SHADOW_SIZE = 512;
	
	private static final FilterBuilder CAMERAS = new FilterBuilder().required(CameraTarget.class);
	private static final FilterBuilder POINT_LIGHT = new FilterBuilder()
			.required(PointLightComponent.class);
	private static final FilterBuilder DIR_LIGTH = new FilterBuilder()
			.required(DirectionLightComponent.class);
	private static final FilterBuilder RENDERER = new FilterBuilder().required(MeshRenderer.class);
	
	private Filter cameras, point_ligth, dir_ligth, renderer;
	
	private RenderLibrary context;
	private World world;
	
	@Override
	public void destroy() {
		world = null;
	}
	
	@ReadWorldComponent({RenderContextProperty.class})
	@Override
	public void init(World world) {
		this.world = world;
		context = world.get(RenderContextProperty.class).context();
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
						for(var m : renderer) {
							final var mesh = m.get(MeshComponent.class).mesh().get();
							final var model = m.get(Transform.class).getModelMatrix();
							final var material = MaterialUtil
									.getDefaultCubeMapShadowMaterial(context);
							final var prperties = material.properties();
							mapCubeMapShadowMaterial(prperties, light, shadowMatrices[i], i);
							buffer.drawMesh(mesh, model, material);
						}
				}
			}
		for(var light : dir_ligth)
			if(light.contains(HasShadow.class)) {
				final var target = context.createRenderTarget().addDepthTexture().build(SHADOW_SIZE,
						SHADOW_SIZE);
				directionShadows.put(light, target);
				
				try(final var buffer = target.buffer()) {
					buffer.clearRenderTargetDepth();
					for(var m : renderer) {
						final var mesh = m.get(MeshComponent.class).mesh().get();
						final var model = m.get(Transform.class).getModelMatrix();
						final var material = MaterialUtil.getDefaultShadowMaterial(context);
						final var prperties = material.properties();
						mapShadowMaterial(prperties);
						buffer.drawMesh(mesh, model, material);
					}
				}
			}
		for(var camera : cameras) {
			final var target = camera.get(CameraTarget.class).target();
			try(final var buffer = target.buffer()) {
				if(camera.contains(SkyBoxComponent.class)) {
					buffer.clearRenderTargetDepth();
					final var material = camera.get(SkyBoxComponent.class).texture().get();
					buffer.drawSkyBox(material);
				}else
					buffer.clearRenderTarget(Color.gray, 1);
				for(var m : renderer) {
					final var mesh = m.get(MeshComponent.class).mesh().get();
					final var model = m.get(Transform.class).getModelMatrix();
					final var material = m.get(MeshRenderer.class).material().get();
					final var prperties = material.properties();
					mapMaterial(prperties, pointShadows, directionShadows);
					buffer.drawMesh(mesh, model, material);
				}
			}
		}
		final var material = MaterialUtil.getDefaultSpriteMaterial(context);
		
		final var camera = world.get(Cameras.class).main();
		camera.get(CameraTarget.class).target()
				.getColorTexture(material.properties().get("texture"));
		try(final var buffer = context.buffer()) {
			buffer.drawTexture(material.shader(), material.properties());
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
	
	private void mapCubeMapShadowMaterial(MaterialProperties properties, Entity light,
			Matrix4f shadowMatrix, int face) {
		mapMaterial0(properties);
		
		properties.put("lightPos", light.get(Transform.class).position);
		properties.put("face", face);
		properties.put("projectionView", shadowMatrix);
	}
	
	private void mapMaterial(MaterialProperties properties,
			Map<Entity, RenderTargetTextute> pointShadows,
			Map<Entity, RenderTargetTextute> directionShadows) {
		mapMaterial0(properties);
		{
			var i = 0;
			for(var light : point_ligth) {
				var name = "point_light[" + i + "].";
				properties.put(name + "position", light.get(Transform.class).position);
				properties.putRGB(name + "color", light.get(PointLightComponent.class).color());
				properties.put(name + "intensity",
						light.get(PointLightComponent.class).intensity());
				
				if(light.contains(HasShadow.class))
					pointShadows.get(light).getDepthTexture(properties.get(name + "depth"));
				
				i++;
			}
			properties.put("count_point_light", i);
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
					
					directionShadows.get(light).getDepthTexture(properties.get(name + "depth"));
				}
				i++;
			}
			properties.put("count_dir_light", i);
		}
	}
	
	private void mapMaterial0(MaterialProperties properties) {
		properties.put("far_plane", FAR_PLANE);
	}
	
	private void mapShadowMaterial(MaterialProperties properties) {
		mapMaterial0(properties);
	}
	
}
