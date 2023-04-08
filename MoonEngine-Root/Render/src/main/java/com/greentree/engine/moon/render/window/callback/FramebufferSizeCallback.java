package com.greentree.engine.moon.render.window.callback;


@FunctionalInterface
public interface FramebufferSizeCallback {

	static FramebufferSizeCallback create(Iterable<? extends FramebufferSizeCallback> callbacks) {
		return (width, height)-> {
			for(var c : callbacks)
				c.invoke(width, height);
		};
	}
	
	void invoke(int width, int height);

}
