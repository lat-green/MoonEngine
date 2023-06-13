package com.greentree.engine.moon.render.mesh;

import com.greentree.commons.math.vector.FinalVector3f;
import com.greentree.commons.math.vector.Vector2f;
import com.greentree.commons.math.vector.Vector3f;
import com.greentree.engine.moon.mesh.GraphicsMesh;
import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.mesh.StaticMesh.ValueVertexIndex;

import java.util.function.Consumer;

public final class MeshUtil {

    public static final StaticMesh BOX, QUAD, QUAD_SPRITE;

    static {
        final var builder = GraphicsMesh.builder();
        final var v000 = new Vector3f(0, 0, 0);
        final var v001 = new Vector3f(0, 0, 1);
        final var v010 = new Vector3f(0, 1, 0);
        final var v011 = new Vector3f(0, 1, 1);
        final var v100 = new Vector3f(1, 0, 0);
        final var v101 = new Vector3f(1, 0, 1);
        final var v110 = new Vector3f(1, 1, 0);
        final var v111 = new Vector3f(1, 1, 1);
        map(v -> {
            v.plusAssign(-.5f);
        }, v000, v001, v010, v011, v100, v101, v110, v111);
        builder.addFaces(v101, v001, v000);
        builder.addFaces(v010, v011, v111);
        builder.addFaces(v110, v111, v101);
        builder.addFaces(v111, v011, v001);
        builder.addFaces(v001, v011, v010);
        builder.addFaces(v100, v000, v010);
        builder.addFaces(v100, v101, v000);
        builder.addFaces(v110, v010, v111);
        builder.addFaces(v100, v110, v101);
        builder.addFaces(v101, v111, v001);
        builder.addFaces(v000, v001, v010);
        builder.addFaces(v110, v100, v010);
        BOX = builder.build();
    }

    static {
        final var builder = GraphicsMesh.builder();
        final var v01 = new Vector3f(0, 1, 0);
        final var v00 = new Vector3f(0, 0, 0);
        final var v10 = new Vector3f(1, 0, 0);
        final var v11 = new Vector3f(1, 1, 0);
        map(v -> {
            v.plusAssign(new FinalVector3f(-.5f, -.5f, 0));
            v.timesAssign(2);
        }, v01, v00, v10, v11);
        builder.addFaces(v00, v11, v01);
        builder.addFaces(v00, v10, v11);
        QUAD = builder.build();
    }

    static {
        final var builder = GraphicsMesh.builder();
        final var v01 = new Vector3f(0, 1, 0);
        final var v00 = new Vector3f(0, 0, 0);
        final var v10 = new Vector3f(1, 0, 0);
        final var v11 = new Vector3f(1, 1, 0);
        final var t01 = new Vector2f(0, 1);
        final var t00 = new Vector2f(0, 0);
        final var t10 = new Vector2f(1, 0);
        final var t11 = new Vector2f(1, 1);
        map(v -> {
            v.plusAssign(new FinalVector3f(-.5f, -.5f, 0));
            v.timesAssign(2);
        }, v01, v00, v10, v11);
        final var i01 = new ValueVertexIndex(v01, null, t00);
        final var i00 = new ValueVertexIndex(v00, null, t01);
        final var i10 = new ValueVertexIndex(v10, null, t11);
        final var i11 = new ValueVertexIndex(v11, null, t10);
        builder.addFaces(i00, i11, i01);
        builder.addFaces(i00, i10, i11);
        QUAD_SPRITE = builder.build();
    }

    private MeshUtil() {
    }

    @SafeVarargs
    private static <T> void map(Consumer<? super T> consumer, T... ts) {
        for (var t : ts)
            consumer.accept(t);
    }

}
