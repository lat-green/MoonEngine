package com.greentree.engine.moon.render.assets.material;

import java.io.Serializable;

public record PBRMaterial<T>(T albedo, T normal, T metallic, T roughness, T displacement, T ambientOcclusion) implements Serializable {
}
