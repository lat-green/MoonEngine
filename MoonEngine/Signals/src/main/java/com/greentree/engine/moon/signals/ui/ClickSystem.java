package com.greentree.engine.moon.signals.ui;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.engine.moon.base.property.modules.ReadProperty;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.WorldEntity;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.builder.FilterBuilder;
import com.greentree.engine.moon.ecs.pool.EmptyEntityStrategy;
import com.greentree.engine.moon.ecs.pool.EntityPool;
import com.greentree.engine.moon.ecs.pool.StackEntityPool;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.ecs.system.WorldInitSystem;
import com.greentree.engine.moon.signals.DevicesProperty;
import com.greentree.engine.moon.signals.MouseButton;
import com.greentree.engine.moon.signals.MousePosition;
import com.greentree.engine.moon.signals.device.Devices;

import java.util.ArrayList;
import java.util.Collection;

public final class ClickSystem implements WorldInitSystem, UpdateSystem, DestroySystem {

    private static final FilterBuilder CLICKS = new FilterBuilder().require(Click.class);
    private final Collection<Click> clickEvent = new ArrayList<>();
    private Filter<? extends WorldEntity> clicks;
    private EntityPool pool;
    private World world;
    private Devices devices;

    private ListenerCloser lc1, lc2, lc3;

    @Override
    public void destroy() {
        lc1.close();
        lc2.close();
        lc3.close();
        clickEvent.clear();
    }

    @Override
    public void update() {
        for (var c : clicks)
            c.delete();
        for (var c : clickEvent) {
            final var e = pool.get();
            e.add(c);
        }
        clickEvent.clear();
    }

    @ReadProperty({DevicesProperty.class, DevicesProperty.class})
    @Override
    public void init(World world, SceneProperties properties) {
        this.world = world;
        pool = new StackEntityPool(world, EmptyEntityStrategy.INSTANCE);
        clicks = CLICKS.build(world);
        devices = properties.get(DevicesProperty.class).devices();
        lc1 = addListener(MouseButton.MOUSE_BUTTON_LEFT);
        lc2 = addListener(MouseButton.MOUSE_BUTTON_RIGHT);
        lc3 = addListener(MouseButton.MOUSE_BUTTON_MIDDLE);
    }

    private ListenerCloser addListener(MouseButton button) {
        return devices.onPress(button, () -> {
            click(button);
        });
    }

    private void click(MouseButton button) {
        final var x = devices.get(MousePosition.X);
        final var y = devices.get(MousePosition.Y);
        clickEvent.add(new Click(x, y, button));
    }

}
