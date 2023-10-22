package test.com.greentree.engine.moon.base.systems;

import com.greentree.engine.moon.base.component.ReadComponent;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.InitSystem;

public class CSystem implements InitSystem {

    @Override
    public String toString() {
        return "C";
    }

    @ReadComponent(TestComponent1.class)
    @Override
    public void init(SceneProperties sceneProperties) {
    }

}
