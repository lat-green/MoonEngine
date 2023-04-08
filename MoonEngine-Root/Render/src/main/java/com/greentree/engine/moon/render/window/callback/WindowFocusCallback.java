package com.greentree.engine.moon.render.window.callback;


@FunctionalInterface
public interface WindowFocusCallback {

	static WindowFocusCallback create(Iterable<? extends WindowFocusCallback> callbacks) {
		return (focused)-> {
			for(var c : callbacks)
				c.invoke(focused);
		};
	}
	
	void invoke(boolean focused);

}
