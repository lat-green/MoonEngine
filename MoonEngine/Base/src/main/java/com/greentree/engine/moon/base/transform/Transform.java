package com.greentree.engine.moon.base.transform;

import com.greentree.commons.math.quaternion.AbstractQuaternion;
import com.greentree.commons.math.quaternion.AbstractQuaternionKt;
import com.greentree.commons.math.quaternion.Quaternion;
import com.greentree.commons.math.vector.AbstractVector3f;
import com.greentree.commons.math.vector.AbstractVector4f;
import com.greentree.commons.math.vector.FinalVector3f;
import com.greentree.commons.math.vector.Vector3f;
import com.greentree.engine.moon.ecs.component.Component;
import org.joml.Matrix4f;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

public final class Transform implements Component, Externalizable {

    public final static AbstractVector3f RIGHT = new FinalVector3f(1, 0, 0);
    public final static AbstractVector3f UP = new FinalVector3f(0, 1, 0);
    public final static AbstractVector3f DIRECTION = new FinalVector3f(0, 0, 1);
    private static final long serialVersionUID = 1L;
    public final Vector3f position = new Vector3f();
    public final Quaternion rotation = new Quaternion();
    public final Vector3f scale = new Vector3f(1);

    @Override
    public Transform copy() {
        final var c = new Transform();
        c.position.set(position);
        c.rotation.set(rotation);
        c.scale.set(scale);
        return c;
    }

    @Override
    public boolean copyTo(Component other) {
        final var c = (Transform) other;
        c.position.set(position);
        c.rotation.set(rotation);
        c.scale.set(scale);
        return true;
    }

    public AbstractVector3f direction() {
        return direction(rotation);
    }

    public static AbstractVector3f direction(AbstractQuaternion rotation) {
        return rotation.times(DIRECTION);
    }

    public Matrix4f getModelMatrix() {
        return getModelMatrix(new Matrix4f());
    }

    public Matrix4f getModelMatrix(Matrix4f dest) {
        dest.identity();
        dest.translate(position.toJoml());
        dest.rotate(AbstractQuaternionKt.jomlQuaternion(rotation));
        dest.scale(scale.toJoml());
        return dest;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, rotation, scale);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        var other = (Transform) obj;
        return Objects.equals(position, other.position) && Objects.equals(rotation, other.rotation)
                && Objects.equals(scale, other.scale);
    }

    @Override
    public String toString() {
        return "Transform [position=" + position + ", rotation=" + rotation + ", scale=" + scale + "]";
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(position);
        out.writeObject(rotation);
        out.writeObject(scale);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        position.set((AbstractVector3f) in.readObject());
        rotation.set((AbstractVector4f) in.readObject());
        scale.set((AbstractVector3f) in.readObject());
    }

}
