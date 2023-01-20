package com.greentree.engine.moon.render.scene;

import com.greentree.common.ecs.Entity;
import com.greentree.common.ecs.World;
import com.greentree.common.ecs.filter.Filter;
import com.greentree.common.ecs.filter.FilterBuilder;
import com.greentree.common.renderer.camera.Camera;
import com.greentree.common.renderer.light.DirectionLight;
import com.greentree.common.renderer.light.PointLight;
import com.greentree.common.renderer.scene.Model;
import com.greentree.common.renderer.scene.SceneContext;
import com.greentree.commons.util.function.LambdaSaveFunction;
import com.greentree.commons.util.function.SaveFunction;
import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.render.MeshRenderer;
import com.greentree.engine.moon.render.camera.CameraComponent;
import com.greentree.engine.moon.render.camera.Cameras;
import com.greentree.engine.moon.render.light.direction.DirectionLightComponent;
import com.greentree.engine.moon.render.light.point.PointLightComponent;
import com.greentree.engine.moon.render.mesh.MeshComponent;


public class MoonSceneContext implements SceneContext, AutoCloseable {
	
	private static final FilterBuilder MODELS = new FilterBuilder().required(MeshComponent.class)
			.required(MeshRenderer.class);
	private static final FilterBuilder POINT_LIGTH = new FilterBuilder()
			.required(PointLightComponent.class);
	private static final FilterBuilder DIRECTION_LIGTH = new FilterBuilder()
			.required(DirectionLightComponent.class);
	private static final FilterBuilder CAMERAS = new FilterBuilder()
			.required(CameraComponent.class);
	
	private final Filter entity_models, entity_point_ligths, entity_direction_ligths,
			entity_cameras;
	
	private Iterable<MoonModel> models;
	private Iterable<MoonCamera> cameras;
	private Iterable<MoonPointLight> point_ligths;
	private Iterable<MoonDirectionLight> direction_ligths;
	
	private final SaveFunction<Entity, MoonCamera> camerasMap = new LambdaSaveFunction<>(
			MoonCamera::new);
	private Cameras Cameras;
	
	public MoonSceneContext(World world) {
		Cameras = world.get(Cameras.class);
		
		entity_models = MODELS.build(world);
		entity_point_ligths = POINT_LIGTH.build(world);
		entity_direction_ligths = DIRECTION_LIGTH.build(world);
		entity_cameras = CAMERAS.build(world);
		models = IteratorUtil.map(entity_models, MoonModel::new);
		point_ligths = IteratorUtil.map(entity_point_ligths, MoonPointLight::new);
		direction_ligths = IteratorUtil.map(entity_direction_ligths, MoonDirectionLight::new);
		cameras = IteratorUtil.map(entity_cameras, camerasMap);
	}
	
	@Override
	public Iterable<? extends Camera> cameras() {
		return cameras;
	}
	
	@Override
	public void close() {
		entity_models.close();
		entity_point_ligths.close();
		entity_direction_ligths.close();
		entity_cameras.close();
		Cameras = null;
	}
	
	@Override
	public Iterable<? extends DirectionLight> directionLights() {
		return direction_ligths;
	}
	
	@Override
	public Iterable<? extends Model> models() {
		return models;
	}
	
	@Override
	public Iterable<? extends PointLight> pointLights() {
		return point_ligths;
	}
	
	@Override
	public Camera mainCamera() {
		return camerasMap.apply(Cameras.main());
	}
	
	
}
