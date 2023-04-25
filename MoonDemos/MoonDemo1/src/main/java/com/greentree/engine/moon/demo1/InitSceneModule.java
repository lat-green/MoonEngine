package com.greentree.engine.moon.demo1;

import com.greentree.engine.moon.base.AssetManagerProperty;
import com.greentree.engine.moon.base.scene.Scene;
import com.greentree.engine.moon.base.scene.SceneManagerProperty;
import com.greentree.engine.moon.demo1.controller.Controller3D;
import com.greentree.engine.moon.demo1.controller.PlayerInput;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.system.ECSSystem;
import com.greentree.engine.moon.modules.EngineProperties;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.ReadProperty;
import com.greentree.engine.moon.modules.WriteProperty;
import com.greentree.engine.moon.signals.DevicesProperty;
import com.greentree.engine.moon.signals.Key;
import com.greentree.engine.moon.signals.device.MousePosition;
import com.greentree.engine.moon.signals.device.SignalMapper;

public class InitSceneModule implements LaunchModule {
	
	@WriteProperty({SceneManagerProperty.class})
	@ReadProperty({AssetManagerProperty.class})
	@Override
	public void launch(EngineProperties context) {
		final var manager = context.get(AssetManagerProperty.class).manager();
		
		final var scenes = context.get(SceneManagerProperty.class).manager();
		
		final var scene = manager.loadData(Scene.class, "world1.xml");
		
		scenes.set(new Scene() {
			
			@Override
			public void build(World world) {
				scene.build(world);
				{
					final var signals = context.get(DevicesProperty.class).devices();
					
					SignalMapper.map(signals, signals, Key.S, Key.W, PlayerInput.MoveZ);
					SignalMapper.map(signals, signals, Key.A, Key.D, PlayerInput.MoveX);
					SignalMapper.map(signals, signals, Key.LEFT_SHIFT, Key.SPACE, PlayerInput.MoveY);
					SignalMapper.map(signals, signals, MousePosition.X, PlayerInput.LookX);
					SignalMapper.map(signals, signals, MousePosition.Y, PlayerInput.LookY, Controller3D.BORDER);
				}
			}
			
			@Override
			public ECSSystem getSystems(Iterable<? extends ECSSystem> globalSystems) {
				//				globalSystems = IteratorUtil.union(globalSystems, IteratorUtil.iterable(new LogFPSSystem()));
				return scene.getSystems(globalSystems);
			}
		});
	}
	
}
