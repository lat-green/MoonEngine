package com.greentree.engine.moon.editor.ui;

import static org.lwjgl.opengl.GL11.*;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import com.greentree.common.graphics.sgl.font.BasicFont;
import com.greentree.commons.math.vector.Vector3f;
import com.greentree.engine.moon.base.scene.EnginePropertiesWorldComponent;
import com.greentree.engine.moon.base.transform.Transform;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.annotation.ReadComponent;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.FilterBuilder;
import com.greentree.engine.moon.ecs.pool.EmptyEntityStrategy;
import com.greentree.engine.moon.ecs.pool.EntityPool;
import com.greentree.engine.moon.ecs.pool.StackEntityPool;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.render.window.WindowProperty;
import com.greentree.engine.moon.signals.MouseButton;
import com.greentree.engine.moon.signals.ui.Click;

public final class ButtonSystem implements InitSystem, UpdateSystem, DestroySystem {
	
	private static final BasicFont FONT = new BasicFont(new java.awt.Font(null, 0, 12), false);
	
	private static final FilterBuilder BUTTONS = new FilterBuilder().required(Button.class);
	private static final FilterBuilder CLICKS = new FilterBuilder().required(Click.class);
	
	private Filter buttons, clicks;
	
	private EntityPool pool;
	
	private World world;
	
	@Override
	public void destroy() {
		buttons.close();
		clicks.close();
		world = null;
		pool.close();
	}
	
	@Override
	public void init(World world) {
		this.world = world;
		pool = new StackEntityPool(world, new EmptyEntityStrategy());
		buttons = BUTTONS.build(world);
		clicks = CLICKS.build(world);
	}
	
	@ReadComponent({Click.class})
	@Override
	public void update() {
		glClearColor(.6f, .6f, .6f, 1);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		for(var button : buttons) {
			final var t = button.get(Transform.class).getModelMatrix();
			
			glPushMatrix();
			glLoadIdentity();
			try(final var stack = MemoryStack.create().push()) {
				final var buf = stack.mallocFloat(16);
				t.get(buf);
				glMultMatrixf(buf);
			}
			
			final var temp = new Matrix4f();
			t.invert(temp);
			final var vec = new Vector3f();
			for(var e : clicks) {
				final var c = e.get(Click.class);
				if(c.button() != MouseButton.MOUSE_BUTTON_LEFT)
					continue;
				
				vec.set(c.x(), c.y(), 0);
				
				vec.mul(temp);
			}
			
			glColor3f(1, 1, 1);
			glBegin(GL_QUADS);
			glVertex2f(1, 1);
			glVertex2f(1, -1);
			glVertex2f(-1, -1);
			glVertex2f(-1, 1);
			glEnd();
			
			glColor3f(0, 0, 0);
			
			FONT.drawString(0, 0, "Hello");
			
			glPopMatrix();
		}
		
		world.get(EnginePropertiesWorldComponent.class).properties().get(WindowProperty.class)
				.window().swapBuffer();
	}
	
	
	
}
