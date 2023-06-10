package com.greentree.engine.moon.monkey.input;

import com.greentree.engine.moon.base.component.ReadComponent;
import com.greentree.engine.moon.base.component.WriteComponent;
import com.greentree.engine.moon.base.property.modules.ReadProperty;
import com.greentree.engine.moon.base.time.Time;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.builder.FilterBuilder;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.ecs.system.WorldInitSystem;
import com.greentree.engine.moon.signals.DevicesProperty;
import com.greentree.engine.moon.signals.device.Devices;

public class ControllerSystem implements WorldInitSystem, UpdateSystem, DestroySystem {

    private static final FilterBuilder BUILDER = new FilterBuilder()
            .require(Controller.class);

    private Filter<?> filter;

    private Time time;

    private Devices input;

    @Override
    public void destroy() {
        filter = null;
        time = null;
        input = null;
    }

    @ReadProperty({Time.class})
    @Override
    public void init(World world, SceneProperties sceneProperties) {
        filter = BUILDER.build(world);
        time = sceneProperties.get(Time.class);
        input = sceneProperties.get(DevicesProperty.class).devices();
    }

    @ReadProperty({Time.class})
    @WriteComponent({Transform.class})
    @ReadComponent({Controller.class})
    @Override
    public void update() {
        for (var c : filter) {
            final var co = c.get(Controller.class);
            final var t = c.get(Transform.class);
            var move = input.get(PlayerInput.Move);
            var jump = input.get(PlayerButton.Jump);

        }
    }

}
