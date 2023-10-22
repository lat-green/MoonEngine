package com.greentree.engine.moon.render.window.callback;


@FunctionalInterface
public interface WindowSizeCallback {

	static WindowSizeCallback create(Iterable<? extends WindowSizeCallback> callbacks) {
		return (width, height)-> {
			for(var c : callbacks)
				c.invoke(width, height);
		};
	}
	
	void invoke(int width, int height);

}
