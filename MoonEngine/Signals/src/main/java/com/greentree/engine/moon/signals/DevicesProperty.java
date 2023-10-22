package com.greentree.engine.moon.signals;

import com.greentree.engine.moon.ecs.scene.SceneProperty;
import com.greentree.engine.moon.modules.property.EngineProperty;
import com.greentree.engine.moon.signals.device.Devices;
import com.greentree.engine.moon.signals.device.HashMapDevices;

public record DevicesProperty(Devices devices) implements EngineProperty, SceneProperty {

    public DevicesProperty() {
        this(new HashMapDevices());
    }

    public DevicesProperty {
    }

}
