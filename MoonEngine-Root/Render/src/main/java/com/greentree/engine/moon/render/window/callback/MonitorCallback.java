package com.greentree.engine.moon.render.window.callback;

@FunctionalInterface
public interface MonitorCallback {

	static MonitorCallback create(Iterable<? extends MonitorCallback> callbacks) {
		return (monitor, event)-> {
			for(var c : callbacks)
				c.invoke(monitor, event);
		};
	}
	
	void invoke(long monitor, ConnectedEvent event);

}
