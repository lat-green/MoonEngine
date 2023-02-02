package com.greentree.engine.moon.render.pipeline.material;


public abstract class ProxyMaterialProperty implements MaterialProperty {
	
	private final MaterialProperty base;
	
	public ProxyMaterialProperty(MaterialProperty base) {
		this.base = base;
	}
	
	@Override
	public void setFloat(float x) {
		base.setFloat(x);
		afterSet();
	}
	
	protected void afterSet() {
	}
	
	@Override
	public void setFloat(float x, float y) {
		base.setFloat(x, y);
		afterSet();
	}
	
	@Override
	public void setFloat(float x, float y, float z) {
		base.setFloat(x, y, z);
		afterSet();
	}
	
	@Override
	public void setFloat(float x, float y, float z, float w) {
		base.setFloat(x, y, z, w);
		afterSet();
	}
	
	@Override
	public void setInt(int x) {
		base.setInt(x);
		afterSet();
	}
	
	@Override
	public void setInt(int x, int y) {
		base.setInt(x, y);
		afterSet();
	}
	
	@Override
	public void setInt(int x, int y, int z) {
		base.setInt(x, y, z);
		afterSet();
	}
	
	@Override
	public void setInt(int x, int y, int z, int w) {
		base.setInt(x, y, z, w);
		afterSet();
	}
	
	@Override
	public void setMatrix4x4f(float m00, float m01, float m02, float m03, float m10, float m11,
			float m12, float m13, float m20, float m21, float m22, float m23, float m30, float m31,
			float m32, float m33) {
		base.setMatrix4x4f(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31,
				m32, m33);
		afterSet();
	}
	
}
