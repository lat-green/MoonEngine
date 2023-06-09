package com.greentree.engine.moon.render.pipeline.target.buffer;

import com.greentree.commons.image.Color;
import com.greentree.engine.moon.mesh.AttributeData;
import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.mesh.compoent.StaticMeshFaceComponent;
import com.greentree.engine.moon.render.material.MaterialProperties;
import com.greentree.engine.moon.render.shader.ShaderProgramData;

public interface TargetCommandBuffer {

    StaticMeshFaceComponent[] COMPONENTS = {StaticMeshFaceComponent.VERTEX, StaticMeshFaceComponent.NORMAL,
            StaticMeshFaceComponent.TEXTURE_COORDINAT, StaticMeshFaceComponent.TANGENT};

    void clear();

    default void clear(Color color, float depth) {
        clearColor(color);
        clearDepth(depth);
    }

    void clearColor(Color color);

    void clearDepth(float depth);

    void disableCullFace();

    void disableDepthTest();

    default void bindMesh(StaticMesh mesh) {
        bindMesh(mesh.getAttributeGroup(COMPONENTS));
    }

    void bindMesh(AttributeData mesh);

    void bindMaterial(MaterialProperties properties);

    void bindShader(ShaderProgramData shader);

    void draw();

    void enableCullFace();

    void enableDepthTest();

    void execute();

}
