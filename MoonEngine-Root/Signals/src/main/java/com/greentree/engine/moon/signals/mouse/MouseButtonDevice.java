package com.greentree.engine.moon.signals.mouse;

import com.greentree.engine.moon.signals.MouseButton;
import com.greentree.engine.moon.signals.device.BooleanDevice;

public record MouseButtonDevice(MouseButton button) implements BooleanDevice {
	
}
