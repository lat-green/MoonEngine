package com.greentree.engine.moon.base.scene.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import com.greentree.engine.moon.base.scene.EnginePropertiesWorldComponent;
import com.greentree.engine.moon.base.scene.Scene;
import com.greentree.engine.moon.base.scene.SceneManager;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.ECSSystem;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.module.EngineProperties;

public final class SceneManagerBase implements SceneManager {
	
	private Scene nextScene;
	private boolean loadScene = false;
	private World currentWorld;
	private ECSSystem currentSystems;
	private final Collection<ECSSystem> globalSystems = new ArrayList<>();
	private final EngineProperties properties;
	
	public SceneManagerBase(EngineProperties properties) {
		this.properties = properties;
	}
	
	@Override
	public void addGlobalSystem(ECSSystem system) {
		Objects.requireNonNull(system);
		globalSystems.add(system);
	}
	
	@Override
	public synchronized void set(Scene scene) {
		if(loadScene)
			throw new IllegalArgumentException("set scene on load scene");
		nextScene = scene;
	}
	
	public void update() {
		if(nextScene != null)
			loadScene();
		if(currentSystems instanceof UpdateSystem usystem)
			usystem.update();
	}
	
	public void clearScene() {
		if(currentWorld != null)
			currentWorld.close();
		if(currentSystems instanceof DestroySystem dsystem)
			dsystem.destroy();
	}
	
	private synchronized void loadScene() {
		if(loadScene)
			throw new IllegalArgumentException("load scene on load scene");
		loadScene = true;
		
		clearScene();
		
		final var currentWorld = new World();
		currentWorld.add(new EnginePropertiesWorldComponent(properties));
		
		currentSystems = nextScene.getSystems(globalSystems);
		if(currentSystems instanceof InitSystem isystem)
			isystem.init(currentWorld);
		
		nextScene.build(currentWorld);
		
		nextScene = null;
		
		loadScene = false;
	}
	
}
