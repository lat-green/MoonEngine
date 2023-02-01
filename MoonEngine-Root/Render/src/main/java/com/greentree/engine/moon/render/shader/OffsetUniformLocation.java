package com.greentree.engine.moon.render.shader;


public record OffsetUniformLocation(UniformLocation base, int offset) implements UniformLocation {
	
	@Override
	public void setOffsetFloat(int offset, float x) {
		base.setOffsetFloat(this.offset + offset, x);
	}
	
	@Override
	public void setOffsetFloat(int offset, float x, float y) {
		base.setOffsetFloat(this.offset + offset, x, y);
	}
	
	@Override
	public void setOffsetFloat(int offset, float x, float y, float z) {
		base.setOffsetFloat(this.offset + offset, x, y, z);
	}
	
	@Override
	public void setOffsetFloat(int offset, float x, float y, float z, float w) {
		base.setOffsetFloat(this.offset + offset, x, y, z, w);
	}
	
	@Override
	public void setOffsetInt(int offset, int x) {
		base.setOffsetInt(this.offset + offset, x);
	}
	
	@Override
	public void setOffsetInt(int offset, int x, int y) {
		base.setOffsetInt(this.offset + offset, x, y);
	}
	
	@Override
	public void setOffsetInt(int offset, int x, int y, int z) {
		base.setOffsetInt(this.offset + offset, x, y, z);
	}
	
	@Override
	public void setOffsetInt(int offset, int x, int y, int z, int w) {
		base.setOffsetInt(this.offset + offset, x, y, z, w);
	}
	
	@Override
	public void setOffsetMatrix4x4f(int offset, float m00, float m01, float m02, float m03,
			float m10, float m11, float m12, float m13, float m20, float m21, float m22, float m23,
			float m30, float m31, float m32, float m33) {
		base.setOffsetMatrix4x4f(this.offset + offset, m00, m01, m02, m03, m10, m11, m12, m13, m20,
				m21, m22, m23, m30, m31, m32, m33);
	}
	
	
}
