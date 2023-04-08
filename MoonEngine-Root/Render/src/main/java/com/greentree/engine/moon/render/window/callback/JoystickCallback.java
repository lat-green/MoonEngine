package com.greentree.engine.moon.render.window.callback;

@FunctionalInterface
public interface JoystickCallback {

	static JoystickCallback create(Iterable<? extends JoystickCallback> callbacks) {
		return (jid, event)-> {
			for(var c : callbacks)
				c.invoke(jid, event);
		};
	}
	
	void invoke(int jid, ConnectedEvent event);

}
