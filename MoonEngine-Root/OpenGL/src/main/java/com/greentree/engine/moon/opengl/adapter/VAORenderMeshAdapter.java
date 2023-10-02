package com.greentree.engine.moon.opengl.adapter;

import com.greentree.common.graphics.sgl.vao.GLVertexArray;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;

public record VAORenderMeshAdapter(GLVertexArray vao) implements RenderMesh {

    @Override
    public void render() {
        vao.bind();
        glDrawArrays(GL_TRIANGLES, 0, vao.size());
        vao.unbind();
    }

}
