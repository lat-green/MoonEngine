package com.greentree.engine.moon.signals.device;

import com.greentree.common.renderer.window.Key;
import com.greentree.engine.moon.signals.device.value.DeviceValue;
import com.greentree.engine.moon.signals.device.value.SumDeviceValue;
import com.greentree.engine.moon.signals.keyboard.KeyBoardButton;

public final class SignalMapper {
	
	private SignalMapper() {
	}
	
	public static void map(DeviceCollection signals, DeviceCollection dest, FloatDevice input, FloatDevice result) {
		dest.set(result, new DeviceValue.Float.Link(signals, input));
	}
	
	public static void map(DeviceCollection signals, DeviceCollection dest, FloatDevice input, FloatDevice result, float border) {
		dest.set(result, new DeviceValue.Float.Link(signals, input).border(border));
	}
	
	public static void map(DeviceCollection signals, DeviceCollection dest, BooleanDevice min, BooleanDevice max, FloatDevice result) {
		final var mn = new DeviceValue.Boolean.Link(signals, min).toFloat().mult(-1);
		final var mx = new DeviceValue.Boolean.Link(signals, max).toFloat();
		dest.set(result, new SumDeviceValue(mx, mn));
	}
	
	public static void map(DeviceCollection signals, DeviceCollection dest, Key min, Key max, FloatDevice result) {
		map(signals, dest, new KeyBoardButton(min), new KeyBoardButton(max), result);
	}
	
}
