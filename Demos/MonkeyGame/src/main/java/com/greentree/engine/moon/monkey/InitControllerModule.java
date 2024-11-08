package com.greentree.engine.moon.monkey;

import com.greentree.engine.moon.base.property.modules.WriteProperty;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.property.EngineProperties;
import com.greentree.engine.moon.monkey.input.PlayerButton1;
import com.greentree.engine.moon.monkey.input.PlayerButton2;
import com.greentree.engine.moon.monkey.input.PlayerInput1;
import com.greentree.engine.moon.monkey.input.PlayerInput2;
import com.greentree.engine.moon.signals.DevicesProperty;
import com.greentree.engine.moon.signals.Key;
import com.greentree.engine.moon.signals.device.SignalMapper;

public class InitControllerModule implements LaunchModule {

    @WriteProperty({DevicesProperty.class})
    @Override
    public void launch(EngineProperties properties) {
        final var signals = properties.get(DevicesProperty.class).devices();
        SignalMapper.map(signals, signals, Key.UP, PlayerButton1.Jump);
        SignalMapper.map(signals, signals, Key.LEFT, Key.RIGHT, PlayerInput1.Move);
        SignalMapper.map(signals, signals, Key.W, PlayerButton2.Jump);
        SignalMapper.map(signals, signals, Key.A, Key.D, PlayerInput2.Move);
    }

}
