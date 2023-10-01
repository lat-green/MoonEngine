package com.greentree.engine.moon.render.pipeline.target.buffer;

import com.greentree.commons.graphics.smart.mesh.Mesh;
import com.greentree.commons.image.Color;
import com.greentree.engine.moon.mesh.compoent.StaticMeshFaceComponent;
import com.greentree.engine.moon.render.material.MaterialProperties;
import com.greentree.engine.moon.render.shader.ShaderProgramData;

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

    void bindMaterial(MaterialProperties properties);

    void bindShader(ShaderProgramData shader);

    void draw();

    void enableCullFace();

    void enableDepthTest();

    void execute();

}
