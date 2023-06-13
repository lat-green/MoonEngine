package com.greentree.engine.moon.signals.device;

import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.math.vector.Vector2f;
import com.greentree.commons.math.vector.Vector3f;
import com.greentree.engine.moon.signals.device.value.DeviceValue;
import com.greentree.engine.moon.signals.device.value.DeviceValue.Boolean;
import com.greentree.engine.moon.signals.device.value.DeviceValue.Float;

import java.io.Serializable;
import java.util.function.Consumer;

public interface Devices extends Iterable<Device<?>>, Serializable {

    default boolean get(BooleanDevice device) {
        return getValue(device).value();
    }

    <V extends DeviceValue> V getValue(Device<V> device);

    default Vector2f get(Device<? extends Float> x, Device<? extends Float> y) {
        return new Vector2f(get(x), get(y));
    }

    default float get(Device<? extends Float> device) {
        return getValue(device).value();
    }

    default Vector3f get(Device<? extends Float> x, Device<? extends Float> y, Device<? extends Float> z) {
        return new Vector3f(get(x), get(y), get(z));
    }

    default <V extends DeviceValue.Boolean> void add(Device<V> device, V value) {
        var d = getValue(device);
        if (d.isDefault())
            set(device, value);
        set(device, new DeviceValue.Boolean.Or(d, value));
    }

    <V extends DeviceValue> void set(Device<? extends V> device, V value);

    default <V extends DeviceValue.Float> void add(Device<V> device, V value) {
        var d = getValue(device);
        if (d.isDefault())
            set(device, value);
        set(device, d.sum(value));
    }

    default void set(Device<? super DeviceValue.Boolean> device, boolean value) {
        set(device, new Boolean.Const(value));
    }

    default void set(Device<? super DeviceValue.Float> device, float value) {
        set(device, new Float.Const(value));
    }

    default ListenerCloser onPress(Device<? extends Boolean> button, Runnable listener) {
        return onChange(button, v -> {
            if (v.value())
                listener.run();
        });
    }

    <V extends DeviceValue> ListenerCloser onChange(Device<? extends V> button, Consumer<? super V> listener);

}
