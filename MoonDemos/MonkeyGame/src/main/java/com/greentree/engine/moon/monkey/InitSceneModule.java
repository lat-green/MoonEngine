package com.greentree.engine.moon.monkey;

import com.greentree.engine.moon.base.AssetManagerProperty;
import com.greentree.engine.moon.base.scene.Scene;
import com.greentree.engine.moon.base.scene.SceneManagerProperty;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.system.ECSSystem;
import com.greentree.engine.moon.modules.EngineProperties;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.ReadProperty;
import com.greentree.engine.moon.modules.WriteProperty;
import com.greentree.engine.moon.monkey.input.PlayerButton;
import com.greentree.engine.moon.monkey.input.PlayerInput;
import com.greentree.engine.moon.signals.DevicesProperty;
import com.greentree.engine.moon.signals.Key;
import com.greentree.engine.moon.signals.device.SignalMapper;

public class InitSceneModule implements LaunchModule {
	
	@WriteProperty({SceneManagerProperty.class})
	@ReadProperty({AssetManagerProperty.class})
	@Override
	public void launch(EngineProperties context) {
		final var manager = context.get(AssetManagerProperty.class).manager();
		
		final var scenes = context.get(SceneManagerProperty.class).manager();
		
		final var scene = manager.loadData(Scene.class, "game.xml");
		
		scenes.set(new Scene() {
			
			@Override
			public void build(World world) {
				scene.build(world);
				{
					final var signals = context.get(DevicesProperty.class).devices();
					
					SignalMapper.map(signals, signals, Key.W, PlayerButton.Jump);
					SignalMapper.map(signals, signals, Key.SPACE, PlayerButton.Jump);
					SignalMapper.map(signals, signals, Key.A, Key.D, PlayerInput.Move);
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
