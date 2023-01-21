package com.greentree.engine.moon.base.transform;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;

import com.greentree.commons.math.vector.AbstractVector3f;
import com.greentree.commons.math.vector.FinalVector3f;
import com.greentree.commons.math.vector.Vector3f;
import com.greentree.engine.moon.ecs.component.Component;

public final class Transform implements Component, Externalizable {
	
	private static final long serialVersionUID = 1L;
	
	public final static AbstractVector3f RIGHT = new FinalVector3f(1, 0, 0);
	public final static AbstractVector3f UP = new FinalVector3f(0, 1, 0);
	public final static AbstractVector3f DIRECTION = new FinalVector3f(0, 0, 1);
	
	public final Vector3f position = new Vector3f();
	public final Quaternionf rotation = new Quaternionf().identity();
	public final Vector3f scale = new Vector3f(1);
	
	public static AbstractVector3f direction(Quaternionfc rotation) {
		final var vec = rotation.transform(DIRECTION.toJoml(), new org.joml.Vector3f());
		return new Vector3f(vec.x, vec.y, vec.z);
	}
	
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
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null || getClass() != obj.getClass())
			return false;
		var other = (Transform) obj;
		return Objects.equals(position, other.position) && Objects.equals(rotation, other.rotation)
				&& Objects.equals(scale, other.scale);
	}
	
	public Matrix4f getModelMatrix() {
		final var modelView = new Matrix4f();
		modelView.translate(position.toJoml());
		modelView.rotate(rotation);
		modelView.scale(scale.toJoml());
		return modelView;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(position, rotation, scale);
	}
	
	@Override
	public String toString() {
		return "Transform [position=" + position + ", rotation=" + rotation + ", scale=" + scale
				+ "]";
	}
	
	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		position.set((AbstractVector3f) in.readObject());
		rotation.set((Quaternionf) in.readObject());
		scale.set((AbstractVector3f) in.readObject());
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeObject(position);
		out.writeObject(rotation);
		out.writeObject(scale);
	}
	
}
