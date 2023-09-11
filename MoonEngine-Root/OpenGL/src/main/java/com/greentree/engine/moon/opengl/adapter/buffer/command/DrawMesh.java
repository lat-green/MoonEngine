package com.greentree.engine.moon.opengl.adapter.buffer.command;

import com.greentree.engine.moon.opengl.adapter.RenderMesh;
import com.greentree.engine.moon.opengl.adapter.Shader;
import com.greentree.engine.moon.render.material.MaterialProperties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public record DrawMesh(Shader shader, RenderMesh mesh, Iterable<MaterialProperties> properties)
        implements TargetCommand {

    public DrawMesh(Shader shader, RenderMesh mesh, MaterialProperties properties) {
        this(shader, mesh, List.of(properties));
    }

    public DrawMesh {
        Objects.requireNonNull(shader);
        Objects.requireNonNull(mesh);
        Objects.requireNonNull(properties);
    }

    @Override
    public void run() {
        shader.bind();
        mesh.bind();
        final var iter = properties.iterator();
        MaterialProperties last = null;
        if (iter.hasNext()) {
            final var p = iter.next();
            shader.set(p);
            mesh.render();
            last = p;
        }
        while (iter.hasNext()) {
            final var p = iter.next();
            shader.set(p, last);
            mesh.render();
            last = p;
        }
        mesh.unbind();
        shader.unbind();
    }

    @Override
    public TargetCommand merge(TargetCommand command) {
        if (command == this)
            return this;
        if (command instanceof DrawMesh c && c.shader.equals(shader) && c.mesh.equals(mesh))
            return new DrawMesh(shader, mesh, merge(properties, c.properties));
        return null;
    }

    private static <T> Collection<T> merge(Iterable<? extends T> a, Iterable<? extends T> b) {
        final ArrayList<T> result;
        if (a instanceof Collection ca)
            result = new ArrayList<>(ca);
        else {
            result = new ArrayList<>();
            for (var e : a)
                result.add(e);
        }
        for (var e : b)
            result.add(e);
        result.trimToSize();
        return result;
    }

    @Override
    public String toString() {
        return "DrawMesh [" + shader + ", " + properties + ", " + mesh + "]";
    }

}
