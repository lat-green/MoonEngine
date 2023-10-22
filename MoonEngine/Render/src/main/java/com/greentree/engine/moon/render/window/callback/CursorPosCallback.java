package com.greentree.engine.moon.render.window.callback;


@FunctionalInterface
public interface CursorPosCallback {

	static CursorPosCallback create(Iterable<? extends CursorPosCallback> callbacks) {
		return (x, y)-> {
			for(var c : callbacks)
				c.invoke(x, y);
		};
	}
	
	void invoke(double x, double y);

}
