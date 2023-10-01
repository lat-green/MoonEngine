package com.greentree.engine.moon.opengl.adapter.buffer;

import com.greentree.commons.graphics.smart.mesh.Mesh;
import com.greentree.commons.image.Color;
import com.greentree.engine.moon.opengl.adapter.GLRenderLibrary;
import com.greentree.engine.moon.opengl.adapter.RenderMesh;
import com.greentree.engine.moon.opengl.adapter.Shader;
import com.greentree.engine.moon.opengl.adapter.buffer.command.*;
import com.greentree.engine.moon.render.material.MaterialProperties;
import com.greentree.engine.moon.render.pipeline.target.buffer.TargetCommandBuffer;
import com.greentree.engine.moon.render.shader.ShaderProgramData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PushCommandBuffer implements TargetCommandBuffer {

    private static final int INITIAL_CAPACITY = 10;
    private final GLRenderLibrary library;
    private final List<TargetCommand> commands;
    private boolean depthTest;
    private boolean cullFace;
    private MaterialProperties bindedProperties;
    private RenderMesh mesh;
    private Shader bindedShader;

    public PushCommandBuffer(GLRenderLibrary library) {
        this(library, INITIAL_CAPACITY);
    }

    public PushCommandBuffer(GLRenderLibrary library, int initialCapacity) {
        this.library = library;
        commands = new ArrayList<>(initialCapacity);
    }

    @Override
    public void clear() {
        commands.clear();
        cullFace = false;
        depthTest = false;
    }

    @Override
    public void clearColor(Color color) {
        push(new ClearRenderTargetColor(color));
    }

    @Override
    public void clearDepth(float depth) {
        push(new ClearRenderTargetDepth(depth));
    }

    @Override
    public void disableCullFace() {
        cullFace = false;
    }

    @Override
    public void disableDepthTest() {
        depthTest = false;
    }

    @Override
    public void bindMesh(Mesh mesh) {
        this.mesh = (RenderMesh) mesh;
    }

    @Override
    public void bindMaterial(MaterialProperties properties) {
        this.bindedProperties = properties.copy();
    }

    @Override
    public void bindShader(ShaderProgramData shader) {
        this.bindedShader = library.build(shader);
    }

    @Override
    public void draw() {
        TargetCommand command = new DrawMesh(bindedShader, mesh, bindedProperties);
        if (cullFace)
            command = new CullFace(command);
        if (depthTest)
            command = new DepthTest(command);
        push(command);
    }

    @Override
    public void enableCullFace() {
        cullFace = true;
    }

    @Override
    public void enableDepthTest() {
        depthTest = true;
    }

    @Override
    public void execute() {
        for (var c : commands)
            c.run();
    }

    public void push(TargetCommand command) {
        Objects.requireNonNull(command);
        while (commands.size() > 1) {
            final var c = commands.get(commands.size() - 1);
            final var m = c.merge(command);
            if (m == null)
                break;
            command = m;
            commands.remove(commands.size() - 1);
        }
        commands.add(command);
    }

}
