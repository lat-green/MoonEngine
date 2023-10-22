package com.greentree.engine.moon.render.window.callback;


@FunctionalInterface
public interface WindowRefreshCallback {

	static WindowRefreshCallback create(Iterable<? extends WindowRefreshCallback> callbacks) {
		return ()-> {
			for(var c : callbacks)
				c.invoke();
		};
	}
	
	void invoke();

}
