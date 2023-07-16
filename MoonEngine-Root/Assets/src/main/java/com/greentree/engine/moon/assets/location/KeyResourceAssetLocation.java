package com.greentree.engine.moon.assets.location;

import com.greentree.commons.data.resource.Resource;
import com.greentree.commons.data.resource.location.ResourceLocation;
import com.greentree.commons.util.exception.MultiException;
import com.greentree.commons.util.exception.WrappedException;
import com.greentree.engine.moon.assets.key.AssetKey;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Optional;

public class KeyResourceAssetLocation implements AssetLocation {

    private final ResourceLocation resources;

    public KeyResourceAssetLocation(ResourceLocation location) {
        this.resources = location;
    }

    @Override
    public Optional<AssetKey> getKeyOptional(String name) {
        AssetKey key = null;
        try {
            key = get0(name + ".key");
        } catch (Exception e1) {
            try {
                key = get0(name);
            } catch (Exception e2) {
                throw new MultiException(e1, e2);
            }
        }
        if (key == null)
            try {
                key = get0(name);
            } catch (Exception e) {
                throw new WrappedException(e);
            }
        return Optional.ofNullable(key);
    }

    private AssetKey get0(String name) throws ClassNotFoundException, IOException {
        final var res = resources.getResource(name);
        if (res == null)
            return null;
        return get(res);
    }

    private static AssetKey get(Resource res) throws IOException, ClassNotFoundException {
        try (final var in = res.open()) {
            try (final var oin = new ObjectInputStream(in)) {
                return (AssetKey) oin.readObject();
            }
        }
    }

    public ResourceLocation getResources() {
        return resources;
    }

    @Override
    public String toString() {
        String builder = "KeyResourceAssetLocation [resources=" +
                resources +
                "]";
        return builder;
    }

}
