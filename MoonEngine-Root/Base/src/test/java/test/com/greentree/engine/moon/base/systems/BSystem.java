package test.com.greentree.engine.moon.base.systems;

import com.greentree.engine.moon.base.component.WriteComponent;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.InitSystem;

public class BSystem implements InitSystem {

    @Override
    public String toString() {
        return "B";
    }

    @WriteComponent({TestComponent1.class})
    @Override
    public void init(SceneProperties sceneProperties) {
    }

}
