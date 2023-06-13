package com.greentree.engine.moon.signals.device.value;

import com.greentree.commons.util.iterator.IteratorUtil;

import java.util.Objects;

public record SumDeviceValue(Iterable<? extends DeviceValue.Float> devices)
        implements DeviceValue.Float {

    @SafeVarargs
    public SumDeviceValue(DeviceValue.Float... devices) {
        this(IteratorUtil.iterable(devices));
    }

    public SumDeviceValue {
        Objects.requireNonNull(devices);
    }

    @Override
    public float value() {
        var sum = 0F;
        for (var v : devices)
            sum += v.value();
        return sum;
    }

    @Override
    public boolean isConst() {
        for (var v : devices)
            if (!v.isConst())
                return false;
        return true;
    }

    @Override
    public boolean isDefault() {
        for (var v : devices)
            if (!v.isDefault())
                return false;
        return true;
    }

}

