package com.greentree.engine.moon.render.window.callback;


@FunctionalInterface
public interface WindowMaximizeCallback {

	static WindowMaximizeCallback create(Iterable<? extends WindowMaximizeCallback> callbacks) {
		return (maximized)-> {
			for(var c : callbacks)
				c.invoke(maximized);
		};
	}
	
	void invoke(boolean maximized);

}
