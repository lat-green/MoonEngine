package com.greentree.engine.moon.signals.keyboard;

import com.greentree.common.renderer.window.Key;
import com.greentree.engine.moon.signals.device.BooleanDevice;

public record KeyBoardButton(Key key) implements BooleanDevice {
	
}
