package com.greentree.engine.moon.signals.keyboard;

import com.greentree.engine.moon.signals.Key;
import com.greentree.engine.moon.signals.device.BooleanDevice;

public record KeyBoardButton(Key key) implements BooleanDevice {
	
}
