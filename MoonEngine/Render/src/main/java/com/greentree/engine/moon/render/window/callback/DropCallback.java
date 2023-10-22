package com.greentree.engine.moon.render.window.callback;

@FunctionalInterface
public interface DropCallback {
	
	static DropCallback create(Iterable<? extends DropCallback> callbacks) {
		return (names)-> {
			for(var c : callbacks)
				c.invoke(names);
		};
	}
	
	void invoke(String[] names);
	
}
