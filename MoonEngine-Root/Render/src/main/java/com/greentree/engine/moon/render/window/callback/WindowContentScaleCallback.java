package com.greentree.engine.moon.render.window.callback;


@FunctionalInterface
public interface WindowContentScaleCallback {

	static WindowContentScaleCallback create(Iterable<? extends WindowContentScaleCallback> callbacks) {
		return (xscale, yscale)-> {
			for(var c : callbacks)
				c.invoke(xscale, yscale);
		};
	}
	
	void invoke(float xscale, float yscale);

}
