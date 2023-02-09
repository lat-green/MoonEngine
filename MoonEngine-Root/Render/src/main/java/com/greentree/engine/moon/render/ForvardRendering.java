package com.greentree.engine.moon.render;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.greentree.commons.image.Color;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.annotation.ReadComponent;
import com.greentree.engine.moon.ecs.annotation.ReadWorldComponent;
import com.greentree.engine.moon.ecs.annotation.WriteComponent;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.FilterBuilder;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.render.camera.CameraComponent;
import com.greentree.engine.moon.render.camera.CameraTarget;
import com.greentree.engine.moon.render.camera.CameraUtil;
import com.greentree.engine.moon.render.camera.SkyBoxComponent;
import com.greentree.engine.moon.render.light.HasShadow;
import com.greentree.engine.moon.render.light.direction.DirectionLightComponent;
import com.greentree.engine.moon.render.light.direction.DirectionLightTarget;
import com.greentree.engine.moon.render.light.point.PointLightComponent;
import com.greentree.engine.moon.render.light.point.PointLightTarget;
import com.greentree.engine.moon.render.mesh.MeshComponent;
import com.greentree.engine.moon.render.mesh.MeshRenderer;
import com.greentree.engine.moon.render.pipeline.RenderLibrary;
import com.greentree.engine.moon.render.pipeline.RenderLibraryProperty;
import com.greentree.engine.moon.render.pipeline.material.MaterialProperties;
import com.greentree.engine.moon.render.pipeline.material.MaterialPropertiesBase;

public final class ForvardRendering implements InitSystem, UpdateSystem, DestroySystem {
	
	private static final float FAR_PLANE = 250;
	
	private static final FilterBuilder CAMERAS = new FilterBuilder().required(CameraTarget.class);
	private static final FilterBuilder POINT_LIGHT = new FilterBuilder().required(PointLightTarget.class);
	private static final FilterBuilder DIR_LIGTH = new FilterBuilder().required(DirectionLightTarget.class);
	private static final FilterBuilder RENDERER = new FilterBuilder().required(MeshRenderer.class);
	private static final MaterialProperties SUPER_POINT_SHADOW, POINT_SHADOW[] = new MaterialProperties[6];
	static {
		SUPER_POINT_SHADOW = new MaterialPropertiesBase();
		SUPER_POINT_SHADOW.put("far_plane", FAR_PLANE);
		
		final var shadowMatrices = getShadowMatrices();
		for(var i = 0; i < POINT_SHADOW.length; i++) {
			POINT_SHADOW[i] = SUPER_POINT_SHADOW.newChildren();
			POINT_SHADOW[i].put("face", i);
			POINT_SHADOW[i].put("projectionView", shadowMatrices[i]);
		}
	}
	
	private Filter cameras, point_ligth, dir_ligth, renderer;
	
	private RenderLibrary library;
	
	private static Matrix4f[] getShadowMatrices() {
		final var zeroVector = new Vector3f();
		var shadowMatrices = new Matrix4f[6];
		shadowMatrices[0] = new Matrix4f().lookAt(zeroVector, new Vector3f(1.0f, 0.0f, 0.0f),
				new Vector3f(0.0f, -1.0f, 0.0f));
		shadowMatrices[1] = new Matrix4f().lookAt(zeroVector, new Vector3f(-1.0f, 0.0f, 0.0f),
				new Vector3f(0.0f, -1.0f, 0.0f));
		shadowMatrices[2] = new Matrix4f().lookAt(zeroVector, new Vector3f(0.0f, 1.0f, 0.0f),
				new Vector3f(0.0f, 0.0f, 1.0f));
		shadowMatrices[3] = new Matrix4f().lookAt(zeroVector, new Vector3f(0.0f, -1.0f, 0.0f),
				new Vector3f(0.0f, 0.0f, -1.0f));
		shadowMatrices[4] = new Matrix4f().lookAt(zeroVector, new Vector3f(0.0f, 0.0f, 1.0f),
				new Vector3f(0.0f, -1.0f, 0.0f));
		shadowMatrices[5] = new Matrix4f().lookAt(zeroVector, new Vector3f(0.0f, 0.0f, -1.0f),
				new Vector3f(0.0f, -1.0f, 0.0f));
		
		{
			var shadowProj = new Matrix4f(1, 0, 0, 0, 0, 1, 0, 0, 0, 0, -1, 1, 0, 0, 0, 0);
			
			for(var i = 0; i < 6; i++)
				shadowProj.mul(shadowMatrices[i], shadowMatrices[i]);
		}
		return shadowMatrices;
	}
	
	@Override
	public void destroy() {
		library = null;
		cameras.close();
		cameras = null;
		point_ligth.close();
		dir_ligth.close();
		renderer.close();
	}
	
	@ReadWorldComponent({RenderLibraryProperty.class})
	@Override
	public void init(World world) {
		library = world.get(RenderLibraryProperty.class).library();
		cameras = CAMERAS.build(world);
		point_ligth = POINT_LIGHT.build(world);
		dir_ligth = DIR_LIGTH.build(world);
		renderer = RENDERER.build(world);
	}
	
	@ReadComponent({MeshComponent.class,Transform.class,SkyBoxComponent.class,CameraComponent.class,
			PointLightComponent.class,DirectionLightComponent.class,HasShadow.class})
	@WriteComponent({CameraTarget.class,DirectionLightTarget.class,PointLightTarget.class,CameraTarget.class})
	@Override
	public void update() {
		final var tempModelMatrix = new Matrix4f();
		{
			final var shader = MaterialUtil.getDefaultCubeMapShadowShader(library);
			for(var light : point_ligth)
				if(light.contains(HasShadow.class)) {
					SUPER_POINT_SHADOW.put("lightPos", light.get(Transform.class).position);
					final var target = light.get(PointLightTarget.class).target();
					try(final var buffer = target.buffer()) {
						buffer.clearDepth(1);
						buffer.enableCullFace();
						buffer.enableDepthTest();
						for(var m : renderer) {
							final var mesh = m.get(MeshComponent.class).mesh().get();
							final var model = m.get(Transform.class).getModelMatrix(tempModelMatrix);
							SUPER_POINT_SHADOW.put("model", model);
							for(MaterialProperties properties : POINT_SHADOW)
								buffer.drawMesh(mesh, shader, properties);
						}
					}
				}
		}
		for(var light : dir_ligth)
			if(light.contains(HasShadow.class)) {
				final var target = light.get(DirectionLightTarget.class).target();
				try(final var buffer = target.buffer()) {
					buffer.clearDepth(1);
					buffer.enableCullFace();
					buffer.enableDepthTest();
					for(var m : renderer) {
						final var mesh = m.get(MeshComponent.class).mesh().get();
						final var model = m.get(Transform.class).getModelMatrix(tempModelMatrix);
						final var shader = MaterialUtil.getDefaultShadowShader(library);
						final var properties = new MaterialPropertiesBase();
						mapShadowMaterial(properties);
						properties.put("model", model);
						buffer.drawMesh(mesh, shader, properties);
					}
				}
			}
		for(var camera : cameras) {
			final var target = camera.get(CameraTarget.class).target();
			try(final var buffer = target.buffer()) {
				buffer.clearDepth(1);
				if(camera.contains(SkyBoxComponent.class)) {
					final var texture = camera.get(SkyBoxComponent.class).texture().get();
					final var shader = MaterialUtil.getDefaultSkyBoxShader(library);
					buffer.drawSkyBox(library, shader, texture);
				}else
					buffer.clearColor(Color.gray);
				buffer.enableCullFace();
				buffer.enableDepthTest();
				for(var m : renderer) {
					final var mesh = m.get(MeshComponent.class).mesh().get();
					final var model = m.get(Transform.class).getModelMatrix(tempModelMatrix);
					final var material = m.get(MeshRenderer.class).material().get();
					final var properties = material.properties().newChildren();
					mapMaterial(properties);
					properties.put("model", model);
					buffer.drawMesh(mesh, material.shader(), properties);
				}
			}
		}
	}
	
	private void mapMaterial(MaterialProperties properties) {
		mapMaterial0(properties);
		{
			var i = 0;
			for(var light : point_ligth) {
				var name = "point_light[" + i + "].";
				properties.put(name + "position", light.get(Transform.class).position);
				properties.putRGB(name + "color", light.get(PointLightComponent.class).color());
				properties.put(name + "intensity", light.get(PointLightComponent.class).intensity());
				
				if(light.contains(HasShadow.class))
					properties.put(name + "depth", light.get(PointLightTarget.class).target().getDepthTexture());
				
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
				properties.put(name + "intensity", light.get(DirectionLightComponent.class).intensity());
				
				if(light.contains(HasShadow.class)) {
					final Matrix4f lightSpaceMatrix;
					
					final var view = CameraUtil.getView(light);
					final var projection = light.get(DirectionLightComponent.class).getProjectionMatrix();
					
					lightSpaceMatrix = new Matrix4f().identity().mul(projection).mul(view);
					
					properties.put("lightSpaceMatrix[" + i + "]", lightSpaceMatrix);
					
					properties.put(name + "depth", light.get(DirectionLightTarget.class).target().getDepthTexture());
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
