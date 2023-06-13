package com.greentree.engine.moon.monkey.input;

import com.greentree.commons.math.Mathf;
import com.greentree.engine.moon.base.component.ReadComponent;
import com.greentree.engine.moon.base.component.WriteComponent;
import com.greentree.engine.moon.base.property.modules.ReadProperty;
import com.greentree.engine.moon.base.time.Time;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.builder.FilterBuilder;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.ecs.system.WorldInitSystem;
import com.greentree.engine.moon.monkey.MinY;
import com.greentree.engine.moon.monkey.Velocity;
import com.greentree.engine.moon.signals.DevicesProperty;
import com.greentree.engine.moon.signals.device.Devices;

public class ControllerSystem implements WorldInitSystem, UpdateSystem {

    private static final FilterBuilder BUILDER = new FilterBuilder()
            .require(Controller.class).require(MinY.class);

    private Filter<?> filter;

    private Time time;

    private Devices input;

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
            final var t = c.get(Transform.class).position;
            final var minY = c.get(MinY.class).value();
            var move = input.get(PlayerInput.Move);
            var jump = input.get(PlayerButton.Jump);
            t.x(t.x() + co.speed() * move * time.delta());
            if (jump && Mathf.equals(t.y(), minY)) {
                c.get(Velocity.class).value.set(0, 2.5f, 0);
            }
        }
    }

}
