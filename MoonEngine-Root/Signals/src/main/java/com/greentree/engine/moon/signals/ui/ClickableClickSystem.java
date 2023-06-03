package com.greentree.engine.moon.signals.ui;

import com.greentree.commons.math.vector.AbstractMutableVector3f;
import com.greentree.commons.math.vector.Vector3f;
import com.greentree.engine.moon.base.component.CreateComponent;
import com.greentree.engine.moon.base.component.ReadComponent;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.Entity;
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
import com.greentree.engine.moon.signals.MouseButton;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.Collection;

public final class ClickableClickSystem implements WorldInitSystem, UpdateSystem, DestroySystem {

    private static final FilterBuilder CLICKABLES = new FilterBuilder().require(Clickable.class);
    private static final FilterBuilder CLICKS = new FilterBuilder().require(Click.class);
    private static final FilterBuilder CLICKABLE_CLICKS = new FilterBuilder().require(ClickableClick.class);
    private final Collection<ClickableClick> clickEvent = new ArrayList<>();

    private Filter<? extends WorldEntity> clickables;
    private Filter<? extends WorldEntity> clicks;
    private Filter<? extends WorldEntity> clickableClicks;

    private EntityPool pool;

    @Override
    public void destroy() {
        pool.close();
    }

    @Override
    public void init(World world, SceneProperties sceneProperties) {
        pool = new StackEntityPool(world, EmptyEntityStrategy.INSTANCE);
        clickables = CLICKABLES.build(world);
        clicks = CLICKS.build(world);
        clickableClicks = CLICKABLE_CLICKS.build(world);
    }

    @CreateComponent({ClickableClick.class})
    @ReadComponent({Click.class})
    @Override
    public void update() {
        for (var button : clickables) {
            final var t = button.get(Transform.class).getModelMatrix();
            final var temp = new Matrix4f();
            t.invert(temp);
            AbstractMutableVector3f vec = new Vector3f();
            for (var e : clicks) {
                final var c = e.get(Click.class);
                if (c.button() != MouseButton.MOUSE_BUTTON_LEFT)
                    continue;
                vec.set(c.x(), c.y(), 0f);
                vec = vec.times(temp).toMutable();
                if (-1 <= vec.x() && vec.x() <= 1 && -1 <= vec.y() && vec.y() <= 1) {
                    click(button);
                    break;
                }
            }
        }
        for (var c : clickableClicks)
            c.delete();
        for (var c : clickEvent) {
            final var e = pool.get();
            e.add(c);
        }
        clickEvent.clear();
    }

    private void click(Entity button) {
        clickEvent.add(new ClickableClick(button));
    }

}
