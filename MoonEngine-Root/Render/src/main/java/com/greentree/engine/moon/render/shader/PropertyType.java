package com.greentree.engine.moon.render.shader;

import com.greentree.engine.moon.render.material.EmptyMaterialProperty;
import com.greentree.engine.moon.render.material.MaterialProperty;

@Deprecated
public enum PropertyType{
	
	bool1(),bool2(),bool3(),bool4(),int1(),int2(),int3(),int4(),uint1(),uint2(),uint3(),uint4(),
	float1(),float2(),float3(),float4(),double1(),doubl2(),double3(),double4(),mat4x4(),mat4x3(),
	mat4x2(),mat3x4(),mat3x3(),mat3x2(),mat2x4(),mat2x3(),mat2x2(),;
	
	public MaterialProperty createDefault() {
		return new EmptyMaterialProperty();
	}
	
	public MaterialProperty create(boolean b1) {
		throw new UnsupportedOperationException(this + " not support (bool)");
	}
	
	public MaterialProperty create(boolean b1, boolean b2) {
		throw new UnsupportedOperationException(this + " not support (bool, bool)");
	}
	
	public MaterialProperty create(boolean b1, boolean b2, boolean b3) {
		throw new UnsupportedOperationException(this + " not support (bool, bool, bool)");
	}
	
	public MaterialProperty create(boolean b1, boolean b2, boolean b3, boolean b4) {
		throw new UnsupportedOperationException(this + " not support (bool, bool, bool, bool)");
	}
	
	public MaterialProperty create(int b1) {
		throw new UnsupportedOperationException(this + " not support (int)");
	}
	
	public MaterialProperty create(int b1, int b2) {
		throw new UnsupportedOperationException(this + " not support (int, int)");
	}
	
	public MaterialProperty create(int b1, int b2, int b3) {
		throw new UnsupportedOperationException(this + " not support (int, int, int)");
	}
	
	public MaterialProperty create(int b1, int b2, int b3, int b4) {
		throw new UnsupportedOperationException(this + " not support (int, int, int, int)");
	}
	
	public MaterialProperty create(float b1) {
		throw new UnsupportedOperationException(this + " not support (float)");
	}
	
	public MaterialProperty create(float b1, float b2) {
		throw new UnsupportedOperationException(this + " not support (float, float)");
	}
	
	public MaterialProperty create(float b1, float b2, float b3) {
		throw new UnsupportedOperationException(this + " not support (float, float, float)");
	}
	
	public MaterialProperty create(float b1, float b2, float b3, float b4) {
		throw new UnsupportedOperationException(this + " not support (float, float, float, float)");
	}
	
	public MaterialProperty createUnsigned(int b1) {
		throw new UnsupportedOperationException(this + " not support (uint)");
	}
	
	public MaterialProperty createUnsigned(int b1, int b2) {
		throw new UnsupportedOperationException(this + " not support (uint, uint)");
	}
	
	public MaterialProperty createUnsigned(int b1, int b2, int b3) {
		throw new UnsupportedOperationException(this + " not support (uint, uint, uint)");
	}
	
	public MaterialProperty createUnsigned(int b1, int b2, int b3, int b4) {
		throw new UnsupportedOperationException(this + " not support (uint, uint, uint, uint)");
	}
	
}
