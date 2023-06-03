package test.com.greentree.engine.moon.base.systems;

import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.InitSystem;
import org.jetbrains.annotations.NotNull;

public class ESystem implements InitSystem {

    @Override
    public String toString() {
        return "E";
    }

    @Override
    public void init(@NotNull SceneProperties sceneProperties) {
    }

}
