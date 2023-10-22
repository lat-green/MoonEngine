package com.greentree.engine.moon.render.window.callback;


@FunctionalInterface
public interface WindowCloseCallback {

	static WindowCloseCallback create(Iterable<? extends WindowCloseCallback> callbacks) {
		return ()-> {
			for(var c : callbacks)
				c.invoke();
		};
	}
	
	void invoke();

}
