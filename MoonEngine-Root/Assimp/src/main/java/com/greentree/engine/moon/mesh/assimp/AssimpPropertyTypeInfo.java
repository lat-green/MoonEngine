package com.greentree.engine.moon.mesh.assimp;

import java.io.Serializable;


public enum AssimpPropertyTypeInfo implements Serializable {

	Float(1),
    Double(2),
    String(3),
    Integer(4),
    Buffer(5),
	;

	private final int id;

	AssimpPropertyTypeInfo(int id) {
		this.id = id;
	}
	
	public static AssimpPropertyTypeInfo get(int id) {
		for(var v : values())
			if(v.id == id)
				return v;
		return null;
	}
	
}
