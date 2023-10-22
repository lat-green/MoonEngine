package com.greentree.engine.moon.render.window.callback;

import java.util.function.Supplier;

import com.greentree.engine.moon.signals.Key;

@FunctionalInterface
public interface KeyCallback {

	static KeyCallback create(Iterable<? extends KeyCallback> callbacks) {
		return (key, scancode, action, mods)-> {
			for(var c : callbacks)
				c.invoke(key, scancode, action, mods);
		};
	}
	
	void invoke(Key key, int scancode, ButtonAction action, Supplier<KeyMode[]> mods);

}
