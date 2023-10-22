package com.greentree.engine.moon.replication.id;

import com.greentree.engine.moon.ecs.scene.SceneProperty;

import java.util.Objects;

public class OwnerIDWorldComponent implements SceneProperty {

    private static final long serialVersionUID = 1L;

    public final int id;

    public OwnerIDWorldComponent(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        OwnerIDWorldComponent other = (OwnerIDWorldComponent) obj;
        return id == other.id;
    }

    @Override
    public String toString() {
        return "OwnerID [" + id + "]";
    }

}
