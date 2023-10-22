package com.greentree.engine.moon.demo1.controller;

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
import com.greentree.engine.moon.signals.DevicesProperty;
import com.greentree.engine.moon.signals.device.Devices;

public class Controller3DSystem implements WorldInitSystem, UpdateSystem {

    private static final FilterBuilder BUILDER = new FilterBuilder().require(Controller3D.class);

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
    @ReadComponent(Controller3D.class)
    @Override
    public void update() {
        for (var c : filter) {
            final var co = c.get(Controller3D.class);
            final var t = c.get(Transform.class);
            var look = input.get(PlayerInput.LookX, PlayerInput.LookY);
            var move = input.get(PlayerInput.MoveX, PlayerInput.MoveY, PlayerInput.MoveZ);
            t.rotation.identity();
            t.rotation.rotateY(-look.x());
            t.rotation.rotateX(-look.y());
            var direction = t.rotation.times(move.magnitude(co.speed() * time.delta()));
            t.position.plusAssign(direction);
        }
    }

}
