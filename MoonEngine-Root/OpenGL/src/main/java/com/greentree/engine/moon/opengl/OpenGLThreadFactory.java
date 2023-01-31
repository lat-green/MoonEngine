package com.greentree.engine.moon.opengl;

import java.util.concurrent.ThreadFactory;

import com.greentree.common.graphics.sgl.window.GLFWContext;

public final class OpenGLThreadFactory implements ThreadFactory {
	
	private final ThreadFactory factory;
	private final GLFWContext context;
	
	public OpenGLThreadFactory(GLFWContext context, ThreadFactory factory) {
		this.factory = factory;
		this.context = context;
	}
	
	@Override
	public Thread newThread(Runnable r) {
		r = modifyRunnable(r);
		return factory.newThread(r);
	}
	
	private Runnable modifyRunnable(Runnable r) {
		return modifyRunnable(context, r);
	}
	
	private static Runnable modifyRunnable(GLFWContext mainContext, Runnable r) {
		return new Runnable() {
			
			final GLFWContext context = GLFWContext.createShared(mainContext);
			
			@Override
			public void run() {
				r.run();
				context.close();
			}
		};
	}
	
}
