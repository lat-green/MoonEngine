package test.com.greentree.engine.moon.base.systems;

import com.greentree.engine.moon.base.component.WriteComponent;
import com.greentree.engine.moon.base.property.modules.ReadProperty;
import com.greentree.engine.moon.base.property.modules.WriteProperty;
import com.greentree.engine.moon.base.scene.SceneManagerProperty;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.InitSystem;

public class BSystem implements InitSystem, DestroySystem {

    @Override
    public String toString() {
        return "B";
    }

    @WriteComponent({TestComponent1.class})
    @Override
    public void init(SceneProperties sceneProperties) {
    }

    @ReadProperty(SceneManagerProperty.class)
    @Override
    public void destroy() {
    }

}
