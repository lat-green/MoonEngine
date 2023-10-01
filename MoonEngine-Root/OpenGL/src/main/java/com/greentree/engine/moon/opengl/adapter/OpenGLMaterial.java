package com.greentree.engine.moon.opengl.adapter;

import com.greentree.commons.graphics.smart.shader.material.Material;

public interface OpenGLMaterial extends Material {

    void bind();

    void unbind();

}
