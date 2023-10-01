package com.greentree.engine.moon.opengl.adapter;

import com.greentree.common.graphics.sgl.buffer.FloatStaticDrawArrayBuffer;
import com.greentree.common.graphics.sgl.enums.gl.GLType;
import com.greentree.common.graphics.sgl.shader.GLSLShader;
import com.greentree.common.graphics.sgl.shader.GLShaderProgram;
import com.greentree.common.graphics.sgl.vao.GLVertexArray;
import com.greentree.common.graphics.sgl.vao.GLVertexArray.AttributeGroup;
import com.greentree.commons.graphics.smart.shader.Shader;
import com.greentree.commons.graphics.smart.target.FrameBuffer;
import com.greentree.commons.graphics.smart.target.RenderCommandBuffer;
import com.greentree.commons.graphics.smart.target.RenderTarget;
import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.mesh.AttributeData;
import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.mesh.compoent.StaticMeshFaceComponent;
import com.greentree.engine.moon.opengl.GLEnums;
import com.greentree.engine.moon.opengl.adapter.buffer.PushCommandBuffer;
import com.greentree.engine.moon.render.pipeline.RenderLibrary;
import com.greentree.engine.moon.render.shader.ShaderData;
import com.greentree.engine.moon.render.shader.ShaderProgramData;
import org.lwjgl.system.MemoryStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.greentree.engine.moon.mesh.compoent.MeshComponent.*;

public record GLRenderLibrary() implements RenderLibrary, RenderTarget {

    public static final StaticMeshFaceComponent[] COMPONENTS = {
            VERTEX, NORMAL, TEXTURE_COORDINAT, TANGENT
    };
    private static final RenderMeshBuilder MESH_BUILDER = new RenderMeshBuilder();
    private static final GLSLShaderBuilder SHADER_BUILDER = new GLSLShaderBuilder();

    @Override
    public RenderCommandBuffer buffer() {
        return new PushCommandBuffer(this);
    }

    @Override
    public FrameBuffer.Builder createRenderTarget() {
        return new GLRenderTargetTextuteBuilder(this);
    }

    public RenderMesh build(StaticMesh mesh) {
        return build(mesh.getAttributeGroup(COMPONENTS));
    }

    public RenderMesh build(AttributeData mesh) {
        final var vao = getVAO(mesh);
        return new VAORenderMeshAdapter(vao);
    }

    public GLVertexArray getVAO(AttributeData mesh) {
        return MESH_BUILDER.getVAO(mesh);
    }

    public Shader build(ShaderProgramData program) {
        return SHADER_BUILDER.build(program);
    }

    public record TArray<T>(T[] array) {

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (!(obj instanceof TArray<?> other))
                return false;
            return Arrays.deepEquals(array, other.array);
        }

        @Override
        public int hashCode() {
            final var prime = 31;
            var result = 1;
            result = prime * result + Arrays.deepHashCode(array);
            return result;
        }

    }

    private static final class RenderMeshBuilder {

        private final Map<AttributeData, GLVertexArray> vaos = new HashMap<>();

        public GLVertexArray getVAO(AttributeData attribute) {
            if (vaos.containsKey(attribute))
                return vaos.get(attribute);
            final var vbo = getVBO(attribute.vertex());
            final var vao = new GLVertexArray(AttributeGroup.of(vbo, attribute.sizes()));
            vaos.put(attribute, vao);
            return vao;
        }

        public static FloatStaticDrawArrayBuffer getVBO(float[] VERTEXS) {
            final var vbo = new FloatStaticDrawArrayBuffer();
            vbo.bind();
            try (final var stack = MemoryStack.create(VERTEXS.length * GLType.FLOAT.size).push()) {
                vbo.setData(stack.floats(VERTEXS));
            }
            vbo.unbind();
            return vbo;
        }

    }

    private static final class GLSLShaderBuilder {

        private final Map<ShaderProgramData, Shader> programs = new HashMap<>();

        public Shader build(ShaderProgramData program) {
            if (programs.containsKey(program))
                return programs.get(program);
            final Shader shader;
            final var vert = build(program.vert());
            final var frag = build(program.frag());
            if (program.geom() != null) {
                final var geom = build(program.geom());
                final var p = new GLShaderProgram(IteratorUtil.iterable(vert, frag, geom));
                shader = new OpenGLShader(p);
            } else {
                final var p = new GLShaderProgram(IteratorUtil.iterable(vert, frag));
                shader = new OpenGLShader(p);
            }
            programs.put(program, shader);
            return shader;
        }

        private GLSLShader build(ShaderData shader) {
            final var s = new GLSLShader(shader.text(), GLEnums.get(shader.type()));
            return s;
        }

    }

}
