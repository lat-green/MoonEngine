package com.greentree.engine.moon.render.window.callback;


@FunctionalInterface
public interface WindowPosCallback {
	
	static WindowPosCallback create(Iterable<? extends WindowPosCallback> callbacks) {
		return (xpos, ypos)-> {
			for(var c : callbacks)
				c.invoke(xpos, ypos);
		};
	}
	
	void invoke(int xpos, int ypos);
	
}
