package com.greentree.engine.moon.signals.device.value;

import com.greentree.engine.moon.signals.device.Device;
import com.greentree.engine.moon.signals.device.Devices;

import java.io.Serializable;
import java.util.Objects;

public interface DeviceValue extends Serializable {

    default boolean isConst() {
        return false;
    }

    default boolean isDefault() {
        return false;
    }

    interface Boolean extends DeviceValue {

        default Float toFloat() {
            return new ButtonToAxesDeviceValue(this);
        }

        default Boolean toLazy() {
            return new Lazy(this);
        }

        boolean value();

        final class Lazy implements Boolean {

            private static final long serialVersionUID = 1L;
            private final Boolean bool;
            private transient boolean old;

            public Lazy(Boolean bool) {
                this.bool = bool;
            }

            @Override
            public int hashCode() {
                return Objects.hash(bool);
            }

            @Override
            public boolean equals(Object obj) {
                if (this == obj)
                    return true;
                if ((obj == null) || (getClass() != obj.getClass()))
                    return false;
                var other = (Lazy) obj;
                return Objects.equals(bool, other.bool);
            }

            @Override
            public String toString() {
                return "Lazy [" + bool + "]";
            }

            @Override
            public boolean value() {
                final var result = old;
                old = bool.value();
                return result;
            }

            @Override
            public boolean isConst() {
                return bool.isConst();
            }

        }

        record Link(Devices signals, Device<? extends Boolean> device) implements Boolean {

            @Override
            public boolean value() {
                return signals.getValue(device).value();
            }

        }

        record Default(boolean value) implements Boolean {

            @Override
            public boolean isConst() {
                return true;
            }

            @Override
            public boolean isDefault() {
                return true;
            }

        }

        record Const(boolean value) implements Boolean {

            @Override
            public String toString() {
                return "const [" + value + "]";
            }

            @Override
            public boolean isConst() {
                return true;
            }

        }

        record Or(Boolean a, Boolean b) implements Boolean {

            @Override
            public boolean isConst() {
                return a.isConst() && b.isConst();
            }

            @Override
            public boolean isDefault() {
                return a.isDefault() && a.isDefault();
            }

            @Override
            public boolean value() {
                return a.value() || b.value();
            }

        }

    }

    interface Float extends DeviceValue {

        default Float border(float border) {
            return new BorderValue(this, border);
        }

        default Float clamp(float border) {
            return new ClampValue(this, border);
        }

        default Float mult(float x) {
            return mult(new Const(x));
        }

        default Float mult(Float x) {
            return new MultDeviceValue(x, this);
        }

        default Float sum(float x) {
            return sum(new Const(x));
        }

        default Float sum(Float x) {
            return new SumDeviceValue(x, this);
        }

        float value();

        record Link(Devices signals, Device<? extends Float> device) implements Float {

            @Override
            public float value() {
                return signals.get(device);
            }

        }

        record Default(float value) implements Float {

            @Override
            public boolean isConst() {
                return true;
            }

            @Override
            public boolean isDefault() {
                return true;
            }

        }

        record Const(float value) implements Float {

            @Override
            public String toString() {
                return "const [" + value + "]";
            }

            @Override
            public boolean isConst() {
                return true;
            }

        }

    }

}
