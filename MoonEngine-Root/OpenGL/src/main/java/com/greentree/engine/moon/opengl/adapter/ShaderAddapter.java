package com.greentree.engine.moon.opengl.adapter;

import com.greentree.common.graphics.sgl.shader.GLShaderProgram;
import com.greentree.engine.moon.render.material.MaterialProperties;

public record ShaderAddapter(GLShaderProgram shader) implements Shader {

    @Override
    public void bind() {
        shader.bind();
    }

    @Override
    public void unbind() {
        shader.unbind();
    }

    @Override
    public void set(MaterialProperties ps) {
        var context = new PropertyBindContext() {

            int slot = 1;

            @Override
            public int nextEmptyTextureSlot() {
                return slot++;
            }

        };
        for (var p : ps)
            ps.get(p).bind(new OpenGLPropertyLocation(shader.getUL(p), context));

    }

}
