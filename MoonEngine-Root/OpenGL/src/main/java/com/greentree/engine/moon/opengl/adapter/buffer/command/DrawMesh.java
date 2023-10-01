package com.greentree.engine.moon.opengl.adapter.buffer.command;

import com.greentree.engine.moon.opengl.adapter.OpenGLMaterial;
import com.greentree.engine.moon.opengl.adapter.RenderMesh;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public record DrawMesh(RenderMesh mesh, Iterable<OpenGLMaterial> properties)
        implements TargetCommand {

    public DrawMesh(RenderMesh mesh, OpenGLMaterial properties) {
        this(mesh, List.of(properties));
    }

    public DrawMesh {
        Objects.requireNonNull(mesh);
        Objects.requireNonNull(properties);
    }

    @Override
    public void run() {
        mesh.bind();
        final var iter = properties.iterator();
        if (iter.hasNext()) {
            final var p = iter.next();
            p.bind();
            mesh.render();
            p.unbind();
        }
        mesh.unbind();
    }

    @Override
    public TargetCommand merge(TargetCommand command) {
        if (command == this)
            return this;
        if (command instanceof DrawMesh c && c.mesh.equals(mesh))
            return new DrawMesh(mesh, merge(properties, c.properties));
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
        return "DrawMesh [" + properties + ", " + mesh + "]";
    }

}
