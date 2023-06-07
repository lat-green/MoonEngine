package com.greentree.engine.moon.render.material;

import com.greentree.engine.moon.render.shader.ShaderProgramData;

import java.io.Serializable;

public record Material(ShaderProgramData shader, MaterialProperties properties) implements Serializable {

}
