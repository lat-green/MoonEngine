package com.greentree.engine.moon.signals.device;

import com.greentree.engine.moon.signals.Key;
import com.greentree.engine.moon.signals.device.value.DeviceValue.Boolean;
import com.greentree.engine.moon.signals.device.value.DeviceValue.Float;
import com.greentree.engine.moon.signals.device.value.SumDeviceValue;
import com.greentree.engine.moon.signals.keyboard.KeyBoardButton;

public final class SignalMapper {
	
	private SignalMapper() {
	}
	
	public static void map(DeviceCollection signals, DeviceCollection dest, FloatDevice input,
			FloatDevice result) {
		dest.set(result, new Float.Link(signals, input));
	}
	
	public static void map(DeviceCollection signals, DeviceCollection dest, FloatDevice input,
			FloatDevice result, float border) {
		dest.set(result, new Float.Link(signals, input).border(border));
	}
	
	public static void map(DeviceCollection signals, DeviceCollection dest, BooleanDevice min,
			BooleanDevice max, FloatDevice result) {
		final var mn = new Boolean.Link(signals, min).toFloat().mult(-1);
		final var mx = new Boolean.Link(signals, max).toFloat();
		dest.set(result, new SumDeviceValue(mx, mn));
	}
	
	public static void map(DeviceCollection signals, DeviceCollection dest, Key min, Key max,
			FloatDevice result) {
		map(signals, dest, new KeyBoardButton(min), new KeyBoardButton(max), result);
	}
	
}
