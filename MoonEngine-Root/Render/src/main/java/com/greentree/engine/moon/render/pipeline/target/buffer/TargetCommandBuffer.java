package com.greentree.engine.moon.render.pipeline.target.buffer;

import com.greentree.commons.graphics.smart.mesh.Mesh;
import com.greentree.commons.graphics.smart.shader.material.Material;
import com.greentree.commons.image.Color;
import com.greentree.engine.moon.mesh.compoent.StaticMeshFaceComponent;

import static com.greentree.engine.moon.mesh.compoent.MeshComponent.*;

public interface TargetCommandBuffer {

    StaticMeshFaceComponent[] COMPONENTS = {VERTEX, NORMAL,
            TEXTURE_COORDINAT, TANGENT};

    void clear();

    default void clear(Color color, float depth) {
        clearColor(color);
        clearDepth(depth);
    }

    void clearColor(Color color);

    void clearDepth(float depth);

    void disableCullFace();

    void disableDepthTest();

    void bindMesh(Mesh mesh);

    void bindMaterial(Material material);

    void draw();

    void enableCullFace();

    void enableDepthTest();

    void execute();

}
