package com.greentree.engine.moon.render.material;

public record float4Property(float x, float y, float z, float w) implements Property {

    @Override
    public void bind(PropertyLocation property) {
        property.setFloat(x, y, z, w);
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + z + "," + w + "]";
    }

}
