package test.com.greentree.engine.moon.mesh.compoent;

import com.greentree.commons.math.vector.Vector3f;
import com.greentree.engine.moon.mesh.AttributeData;
import com.greentree.engine.moon.mesh.GraphicsMesh;
import com.greentree.engine.moon.mesh.StaticMesh;
import com.greentree.engine.moon.mesh.compoent.StaticMeshVertexComponent;
import com.greentree.engine.moon.mesh.compoent.VertexArrayBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VertexArrayBuilderTest {

    StaticMesh plane;

    @BeforeEach
    void init_plane() {
        final var builder = GraphicsMesh.builder();
        final var v1 = new Vector3f(1, 0, 0);
        final var v2 = new Vector3f(1, 1, 0);
        final var v3 = new Vector3f(0, 1, 0);
        final var v4 = new Vector3f(0, 0, 0);
        builder.addFaces(v1, v2, v3);
        builder.addFaces(v3, v4, v1);
        plane = builder.build();
    }

    @Test
    void getAttributeGroup() {
        final var builder = new VertexArrayBuilder();
        final var group = builder.getAttributeGroup(plane, StaticMeshVertexComponent.VERTEX);
        assertEqualsAnyOf(group, new AttributeData(new float[]{
                        1, 0, 0,
                        1, 1, 0,
                        0, 1, 0,
                        0, 1, 0,
                        0, 0, 0,
                        1, 0, 0,
                }, 3),
                new AttributeData(new float[]{
                        0, 1, 0,
                        0, 0, 0,
                        1, 0, 0,
                        1, 0, 0,
                        1, 1, 0,
                        0, 1, 0,
                }, 3));
    }

    private <T> void assertEqualsAnyOf(T expected, T... actual) {
        for (var b : actual) {
            if (expected.equals(b))
                return;
        }
        assertEquals(expected, actual);
    }

}
