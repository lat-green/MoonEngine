package com.greentree.engine.moon.render;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.greentree.commons.image.Color;
import com.greentree.commons.util.time.PointTimer;
import com.greentree.engine.moon.base.transform.Transform;
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
import com.greentree.engine.moon.render.light.direction.DirectionLightTarget;
import com.greentree.engine.moon.render.light.point.PointLightComponent;
import com.greentree.engine.moon.render.light.point.PointLightTarget;
import com.greentree.engine.moon.render.mesh.MeshComponent;
import com.greentree.engine.moon.render.mesh.MeshRenderer;
import com.greentree.engine.moon.render.pipeline.RenderLibrary;
import com.greentree.engine.moon.render.pipeline.RenderLibraryProperty;
import com.greentree.engine.moon.render.pipeline.material.MaterialProperties;
import com.greentree.engine.moon.render.pipeline.material.MaterialPropertiesBase;
import com.greentree.engine.moon.render.pipeline.material.MaterialPropertiesWithParent;
import com.greentree.engine.moon.render.window.Window;
import com.greentree.engine.moon.render.window.WindowProperty;

public final class ForvardRendering implements InitSystem, UpdateSystem, DestroySystem {
	
	private static final float FAR_PLANE = 250;
	
	private static final FilterBuilder CAMERAS = new FilterBuilder().required(CameraTarget.class);
	private static final FilterBuilder POINT_LIGHT = new FilterBuilder().required(PointLightTarget.class);
	private static final FilterBuilder DIR_LIGTH = new FilterBuilder().required(DirectionLightTarget.class);
	private static final FilterBuilder RENDERER = new FilterBuilder().required(MeshRenderer.class);
	private static final MaterialProperties POINT_SHADOW[] = new MaterialProperties[6];
	static {
		final var shadowMatrices = getShadowMatrices();
		for(int i = 0; i < POINT_SHADOW.length; i++) {
			POINT_SHADOW[i] = new MaterialPropertiesBase();
			
			POINT_SHADOW[i].put("far_plane", FAR_PLANE);
			POINT_SHADOW[i].put("face", i);
			POINT_SHADOW[i].put("projectionView", shadowMatrices[i]);
		}
	}
	
	private Filter cameras, point_ligth, dir_ligth, renderer;
	
	private RenderLibrary library;
	private Window window;
	private World world;
	
	@Override
	public void destroy() {
		world = null;
	}
	
	@ReadWorldComponent({RenderLibraryProperty.class,WindowProperty.class})
	@Override
	public void init(World world) {
		this.world = world;
		library = world.get(RenderLibraryProperty.class).library();
		window = world.get(WindowProperty.class).window();
		cameras = CAMERAS.build(world);
		point_ligth = POINT_LIGHT.build(world);
		dir_ligth = DIR_LIGTH.build(world);
		renderer = RENDERER.build(world);
	}
	
	@Override
	public void update() {
		{
			final var t = new PointTimer();
			final var shader = MaterialUtil.getDefaultCubeMapShadowShader(library);
			for(var light : point_ligth)
				if(light.contains(HasShadow.class)) {
					t.point();
					
					final var target = light.get(PointLightTarget.class).target();
					
					try(final var buffer = target.buffer()) {
						buffer.clearRenderTargetDepth();
						for(var i = 0; i < POINT_SHADOW.length; i++) {
							final var properties = new MaterialPropertiesWithParent(POINT_SHADOW[i]);
							properties.put("lightPos", light.get(Transform.class).position);
							for(var m : renderer) {
								final var mesh = m.get(MeshComponent.class).mesh().get();
								final var model = m.get(Transform.class).getModelMatrix();
								buffer.drawMesh(library, mesh, model, shader, properties);
							}
						}
						t.point();
					}
				}
			t.point();
			System.out.println(t);
		}
		//		for(var light : dir_ligth)
		//			if(light.contains(HasShadow.class)) {
		//				final var target = light.get(DirectionLightTarget.class).target();
		//				
		//				try(final var buffer = target.buffer()) {
		//					buffer.clearRenderTargetDepth();
		//					for(var m : renderer) {
		//						final var mesh = m.get(MeshComponent.class).mesh().get();
		//						final var model = m.get(Transform.class).getModelMatrix();
		//						final var shader = MaterialUtil.getDefaultShadowShader(library);
		//						final var prperties = new MaterialPropertiesBase();
		//						mapShadowMaterial(prperties);
		//						buffer.drawMesh(library, mesh, model, shader, prperties);
		//					}
		//				}
		//			}
		//		for(var camera : cameras) {
		//			final var target = camera.get(CameraTarget.class).target();
		//			try(final var buffer = target.buffer()) {
		//				if(camera.contains(SkyBoxComponent.class)) {
		//					buffer.clearRenderTargetDepth();
		//					final var texture = camera.get(SkyBoxComponent.class).texture().get();
		//					final var shader = MaterialUtil.getDefaultSkyBoxShader(library);
		//					final var prperties = new MaterialPropertiesBase();
		//					prperties.put("skybox", texture);
		//					buffer.drawSkyBox(shader, prperties);
		//				}else
		//					buffer.clearRenderTarget(Color.gray, 1);
		//				for(var m : renderer) {
		//					final var mesh = m.get(MeshComponent.class).mesh().get();
		//					final var model = m.get(Transform.class).getModelMatrix();
		//					final var material = m.get(MeshRenderer.class).material().get();
		//					final var properties = material.properties();
		//					mapMaterial(properties);
		//					buffer.drawMesh(library, mesh, model, material);
		//				}
		//			}
		//		}
		//		final var shader = MaterialUtil.getDefaultSpriteShader(library);
		//		final var prperties = new MaterialPropertiesBase();
		//		final var camera = world.get(Cameras.class).main();
		//		prperties.put("render_texture", camera.get(CameraTarget.class).target().getColorTexture());
		//		try(final var buffer = library.screanRenderTarget().buffer()) {
		//			buffer.drawTexture(library, shader, prperties);
		//		}
		window.swapBuffer();
	}
	
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
