package com.greentree.engine.moon.render.window.callback;


@FunctionalInterface
public interface CharCallback {

	static CharCallback create(Iterable<? extends CharCallback> callbacks) {
		return (chars)-> {
			for(var c : callbacks)
				c.invoke(chars);
		};
	}
	
	void invoke(int chars);

}
