package com.greentree.engine.moon.render.window.callback;

import java.util.function.Supplier;

import com.greentree.engine.moon.signals.MouseButton;

@FunctionalInterface
public interface MouseButtonCallback {

	static MouseButtonCallback create(Iterable<? extends MouseButtonCallback> callbacks) {
		return (button, action, mods)-> {
			for(var c : callbacks)
				c.invoke(button, action, mods);
		};
	}
	
	void invoke(MouseButton button, ButtonAction action, Supplier<KeyMode[]> mods);

}
