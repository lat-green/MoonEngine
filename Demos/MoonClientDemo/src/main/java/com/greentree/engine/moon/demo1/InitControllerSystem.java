package com.greentree.engine.moon.demo1;

import com.greentree.engine.moon.base.property.modules.WriteProperty;
import com.greentree.engine.moon.demo1.controller.Controller3D;
import com.greentree.engine.moon.demo1.controller.PlayerInput;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.signals.DevicesProperty;
import com.greentree.engine.moon.signals.Key;
import com.greentree.engine.moon.signals.MousePosition;
import com.greentree.engine.moon.signals.device.SignalMapper;

public class InitControllerSystem implements InitSystem {

    @WriteProperty({DevicesProperty.class})
    @Override
    public void init(SceneProperties sceneProperties) {
        final var signals = sceneProperties.get(DevicesProperty.class).devices();
        SignalMapper.map(signals, signals, Key.S, Key.W, PlayerInput.MoveZ);
        SignalMapper.map(signals, signals, Key.D, Key.A, PlayerInput.MoveX);
        SignalMapper.map(signals, signals, Key.LEFT_SHIFT, Key.SPACE, PlayerInput.MoveY);
        SignalMapper.map(signals, signals, MousePosition.X, PlayerInput.LookX);
        SignalMapper.map(signals, signals, MousePosition.Y, PlayerInput.LookY, Controller3D.BORDER);
    }

}
