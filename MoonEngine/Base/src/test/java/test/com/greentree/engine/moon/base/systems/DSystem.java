package test.com.greentree.engine.moon.base.systems;

import com.greentree.engine.moon.base.component.DestroyComponent;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.WorldInitSystem;

public class DSystem implements WorldInitSystem {

    @Override
    public String toString() {
        return "D";
    }

    @DestroyComponent({TestComponent1.class})
    @Override
    public void init(World world, SceneProperties sceneProperties) {
    }

}
