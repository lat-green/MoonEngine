package com.greentree.engine.moon.opengl.adapter;

import com.greentree.common.graphics.sgl.enums.gl.GLType;
import com.greentree.common.graphics.sgl.shader.GLUniformLocation;
import com.greentree.engine.moon.render.material.PropertyLocation;
import org.lwjgl.system.MemoryStack;

public record OpenGLPropertyLocation(GLUniformLocation location, PropertyBindContext ctx) implements PropertyLocation {

    @Override
    public void setFloat(float x) {
        location.setf(x);
    }

    @Override
    public void setFloat(float x, float y) {
        location.setf(x, y);
    }

    @Override
    public void setFloat(float x, float y, float z) {
        location.setf(x, y, z);
    }

    @Override
    public void setFloat(float x, float y, float z, float w) {
        location.setf(x, y, z, w);
    }

    @Override
    public void setInt(int x) {
        location.seti(x);
    }

    @Override
    public void setInt(int x, int y) {
        throw new UnsupportedOperationException();
        //		location.seti(x, y);
    }

    @Override
    public void setInt(int x, int y, int z) {
        throw new UnsupportedOperationException();
        //		location.seti(x, y, z);
    }

    @Override
    public void setInt(int x, int y, int z, int w) {
        throw new UnsupportedOperationException();
        //		location.seti(x, y, z, w);
    }

    @Override
    public void setMatrix4x4f(float m00, float m01, float m02, float m03, float m10, float m11,
                              float m12, float m13, float m20, float m21, float m22, float m23, float m30, float m31,
                              float m32, float m33) {
        try (final var stack = MemoryStack.create(16 * GLType.FLOAT.size).push()) {
            final var buffer = stack.floats(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22,
                    m23, m30, m31, m32, m33);
            location.set4fv(buffer);
        }
    }

}
