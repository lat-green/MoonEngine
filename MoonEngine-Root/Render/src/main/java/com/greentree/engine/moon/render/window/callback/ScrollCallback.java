package com.greentree.engine.moon.render.window.callback;


@FunctionalInterface
public interface ScrollCallback {

	static ScrollCallback create(Iterable<? extends ScrollCallback> callbacks) {
		return (xoffset, yoffset)-> {
			for(var c : callbacks)
				c.invoke(xoffset, yoffset);
		};
	}
	
	void invoke(double xoffset, double yoffset);

}
