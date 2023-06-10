package com.greentree.engine.moon.monkey;

import com.greentree.engine.moon.base.property.modules.WriteProperty;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.monkey.input.PlayerButton;
import com.greentree.engine.moon.monkey.input.PlayerInput;
import com.greentree.engine.moon.signals.DevicesProperty;
import com.greentree.engine.moon.signals.Key;
import com.greentree.engine.moon.signals.device.SignalMapper;

public class InitControllerSystem implements InitSystem {

    @WriteProperty({DevicesProperty.class})
    @Override
    public void init(SceneProperties sceneProperties) {
        final var signals = sceneProperties.get(DevicesProperty.class).devices();
        SignalMapper.map(signals, signals, Key.W, PlayerButton.Jump);
        SignalMapper.map(signals, signals, Key.SPACE, PlayerButton.Jump);
        SignalMapper.map(signals, signals, Key.A, Key.D, PlayerInput.Move);
    }

}
