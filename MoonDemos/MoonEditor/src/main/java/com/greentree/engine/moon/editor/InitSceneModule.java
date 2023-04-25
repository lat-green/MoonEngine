package com.greentree.engine.moon.editor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

import com.greentree.commons.util.exception.WrappedException;
import com.greentree.engine.moon.base.AssetManagerProperty;
import com.greentree.engine.moon.base.layer.LayerSystem;
import com.greentree.engine.moon.base.name.NameSystem;
import com.greentree.engine.moon.base.scene.Scene;
import com.greentree.engine.moon.base.scene.SceneManagerProperty;
import com.greentree.engine.moon.base.time.TimeSystem;
import com.greentree.engine.moon.base.transform.RectTransform;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.system.ECSSystem;
import com.greentree.engine.moon.ecs.system.debug.DebugSystems;
import com.greentree.engine.moon.ecs.system.debug.PrintStreamSystemsProfiler;
import com.greentree.engine.moon.editor.ui.Button;
import com.greentree.engine.moon.editor.ui.ButtonSystem;
import com.greentree.engine.moon.editor.ui.ClickSystem;
import com.greentree.engine.moon.editor.ui.RectTransforUpdate;
import com.greentree.engine.moon.modules.EngineProperties;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.ReadProperty;
import com.greentree.engine.moon.modules.WriteProperty;
import com.greentree.engine.moon.render.camera.CameraSystem;

public class InitSceneModule implements LaunchModule {
	
	@WriteProperty({SceneManagerProperty.class})
	@ReadProperty({AssetManagerProperty.class})
	@Override
	public void launch(EngineProperties context) {
		final var scenes = context.get(SceneManagerProperty.class).manager();
		scenes.set(new Scene() {
			
			@Override
			public void build(World world) {
				final var b = world.newEntity();
				try(final var lock = b.lock()) {
					lock.add(new Transform());
					lock.add(new RectTransform(-.1f, -.1f, .1f, .1f, 0));
					lock.add(new Button("Hello"));
				}
			}
			
			@Override
			public ECSSystem getSystems(Iterable<? extends ECSSystem> globalSystems) {
				final var systems = new ArrayList<ECSSystem>();
				for(var s : globalSystems)
					systems.add(s);
				Collections.addAll(systems, new TimeSystem(), new CameraSystem(), new LayerSystem(),
						new NameSystem(), new LogSystem(), new RectTransforUpdate(),
						new ButtonSystem(), new ClickSystem());
				
				try {
					return new DebugSystems(new PrintStreamSystemsProfiler(
							new File("log/systems_init.log"), new File("log/systems_update.log"),
							new File("log/systems_destroy.log")), systems);
				}catch(FileNotFoundException e) {
					throw new WrappedException(e);
				}
			}
			
		});
		
	}
}
