package com.greentree.engine.moon.signals.device;

import com.greentree.engine.moon.signals.device.value.DeviceValue.Boolean;
import com.greentree.engine.moon.signals.device.value.DeviceValue.Float;
import com.greentree.engine.moon.signals.device.value.SumDeviceValue;

public final class SignalMapper {

    private SignalMapper() {
    }

    public static void map(Devices signals, Devices dest, FloatDevice input,
                           FloatDevice result) {
        dest.add(result, new Float.Link(signals, input));
    }

    public static void map(Devices signals, Devices dest, BooleanDevice input, BooleanDevice result) {
        dest.add(result, new Boolean.Link(signals, input));
    }

    public static void map(Devices signals, Devices dest, FloatDevice input,
                           FloatDevice result, float border) {
        dest.add(result, new Float.Link(signals, input).border(border));
    }

    public static void map(Devices signals, Devices dest, BooleanDevice min,
                           BooleanDevice max, FloatDevice result) {
        final var mn = new Boolean.Link(signals, min).toFloat().mult(-1);
        final var mx = new Boolean.Link(signals, max).toFloat();
        dest.add(result, new SumDeviceValue(mx, mn));
    }

}
