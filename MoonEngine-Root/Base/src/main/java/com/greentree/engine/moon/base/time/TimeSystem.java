package com.greentree.engine.moon.base.time;

import com.greentree.engine.moon.base.property.modules.CreateProperty;
import com.greentree.engine.moon.base.property.modules.WriteProperty;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;

public class TimeSystem implements InitSystem, UpdateSystem {

    private Time time;

    @WriteProperty({Time.class})
    @Override
    public void update() {
        time.step();
    }

    @CreateProperty({Time.class})
    @Override
    public void init(SceneProperties properties) {
        this.time = new Time();
        properties.add(time);
    }

}
