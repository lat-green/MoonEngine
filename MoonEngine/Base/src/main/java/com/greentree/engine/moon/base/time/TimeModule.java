package com.greentree.engine.moon.base.time;

import com.greentree.engine.moon.base.property.modules.CreateProperty;
import com.greentree.engine.moon.base.property.modules.WriteProperty;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.UpdateModule;
import com.greentree.engine.moon.modules.property.EngineProperties;

public class TimeModule implements LaunchModule, UpdateModule {

    private Time time;

    @WriteProperty({Time.class})
    @Override
    public void update() {
        time.step();
    }

    @CreateProperty({Time.class})
    @Override
    public void launch(EngineProperties properties) {
        this.time = new Time();
        properties.add(time);
    }

}
