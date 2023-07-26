package com.greentree.engine.moon.editor.ui;

import com.greentree.common.graphics.sgl.font.BasicFont;
import com.greentree.engine.moon.base.component.ReadComponent;
import com.greentree.engine.moon.base.property.modules.WriteProperty;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.builder.FilterBuilder;
import com.greentree.engine.moon.ecs.pool.EmptyEntityStrategy;
import com.greentree.engine.moon.ecs.pool.EntityPool;
import com.greentree.engine.moon.ecs.pool.StackEntityPool;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.ecs.system.WorldInitSystem;
import com.greentree.engine.moon.render.window.WindowProperty;
import com.greentree.engine.moon.signals.MouseButton;
import com.greentree.engine.moon.signals.ui.Click;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.opengl.GL11.*;

public final class ButtonSystem implements WorldInitSystem, UpdateSystem, DestroySystem {

    private static final BasicFont FONT = new BasicFont(new java.awt.Font(null, 0, 12), false);

    private static final FilterBuilder BUTTONS = new FilterBuilder().require(Button.class);
    private static final FilterBuilder CLICKS = new FilterBuilder().require(Click.class);

    private Filter<?> buttons, clicks;

    private EntityPool pool;

    private SceneProperties properties;

    @Override
    public void destroy() {
        pool.close();
    }

    @Override
    public void init(World world, SceneProperties properties) {
        this.properties = properties;
        pool = new StackEntityPool(world, EmptyEntityStrategy.INSTANCE);
        buttons = BUTTONS.build(world);
        clicks = CLICKS.build(world);
    }

    @ReadComponent({Click.class})
    @WriteProperty({WindowProperty.class})
    @Override
    public void update() {
        glClearColor(.6f, .6f, .6f, 1);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        for (var button : buttons) {
            final var t = button.get(Transform.class).getModelMatrix();
            var name = button.get(Button.class).name();
            glPushMatrix();
            glLoadIdentity();
            try (final var stack = MemoryStack.create().push()) {
                final var buf = stack.mallocFloat(16);
                t.get(buf);
                glMultMatrixf(buf);
            }
            final var temp = new Matrix4f();
            t.invert(temp);
            for (var e : clicks) {
                final var c = e.get(Click.class);
                if (c.button() != MouseButton.MOUSE_BUTTON_LEFT)
                    continue;
                System.out.println(name);
            }
            glColor3f(1, 1, 1);
            glBegin(GL_QUADS);
            glVertex2f(1, 1);
            glVertex2f(1, -1);
            glVertex2f(-1, -1);
            glVertex2f(-1, 1);
            glEnd();
            glColor3f(0, 0, 0);
            FONT.drawString(0, 0, name);
            glPopMatrix();
        }
        properties.get(WindowProperty.class)
                .window().swapBuffer();
    }

}
