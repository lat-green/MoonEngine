package com.greentree.engine.moon.render.window.callback;


@FunctionalInterface
public interface WindowIconifyCallback {

	static WindowIconifyCallback create(Iterable<? extends WindowIconifyCallback> callbacks) {
		return (iconified)-> {
			for(var c : callbacks)
				c.invoke(iconified);
		};
	}
	
	void invoke(boolean iconified);

}
