package test.com.greentree.engine.moon.base.systems;

import com.greentree.engine.moon.base.component.CreateComponent;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.InitSystem;
import org.jetbrains.annotations.NotNull;

public class ASystem implements InitSystem, DestroySystem {

    @Override
    public String toString() {
        return "A";
    }

    @CreateComponent({TestComponent1.class})
    @Override
    public void init(@NotNull SceneProperties sceneProperties) {
    }

    @Override
    public void destroy() {
    }

}
