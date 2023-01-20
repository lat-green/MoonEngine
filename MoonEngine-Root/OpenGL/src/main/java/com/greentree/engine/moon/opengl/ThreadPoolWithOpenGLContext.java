package com.greentree.engine.moon.opengl;

import java.util.Stack;
import java.util.concurrent.Executor;

import com.greentree.common.graphics.sgl.window.GLFWContext;
import com.greentree.commons.util.concurent.MultiTask;

public final class ThreadPoolWithOpenGLContext implements Executor {
	
	private final Stack<GLFWContext> pool = new Stack<>();
	private final GLFWContext main;
	
	public ThreadPoolWithOpenGLContext(GLFWContext main) {
		super();
		this.main = main;
	}
	
	private GLFWContext getContext() {
		if(pool.isEmpty())
			return newContext();
		return pool.pop();
	}
	
	private GLFWContext newContext() {
		return new GLFWContext("", 1, 1, false, false, false, false, main);
	}
	
	private void back(GLFWContext context) {
		pool.add(context);
	}
	
	@Override
	public void execute(Runnable command) {
		MultiTask.task(()-> {
			try {
				final var context = getContext();
				context.makeCurrent();
				command.run();
				GLFWContext.unmakeCurrent();
				back(context);
			}catch(RuntimeException e) {
				e.printStackTrace();
			}
		});
	}
	
}
