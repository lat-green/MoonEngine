package com.greentree.engine.moon.signals;

import com.greentree.engine.moon.signals.device.BooleanDevice;

public enum MouseButton implements BooleanDevice {
	
	MOUSE_BUTTON_RIGHT(),
	MOUSE_BUTTON_MIDDLE(),
	MOUSE_BUTTON_LEFT(),
	;

	MouseButton() {
	}

}
