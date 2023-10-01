package com.greentree.engine.moon.opengl.adapter;

import com.greentree.commons.graphics.smart.mesh.Mesh;

public interface RenderMesh extends Mesh {

    void render();

    void bind();

    void unbind();

}
