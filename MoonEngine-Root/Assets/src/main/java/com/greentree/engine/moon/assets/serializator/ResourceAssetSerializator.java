package com.greentree.engine.moon.assets.serializator;

import com.greentree.commons.data.resource.Resource;
import com.greentree.commons.data.resource.location.ResourceLocation;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.ResourceAssetKt;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.key.ResourceAssetKey;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;

public class ResourceAssetSerializator implements AssetSerializator<Resource> {

    private final ResourceLocation resources;

    public ResourceAssetSerializator(ResourceLocation resources) {
        this.resources = resources;
    }

    @Override
    public boolean canLoad(AssetManager manager, AssetKey key) {
        if (key instanceof ResourceAssetKey k)
            return manager.canLoad(String.class, k.resourceName());
        return false;
    }

    @Override
    public Asset<Resource> load(AssetManager manager, AssetKey ckey) {
        if (ckey instanceof ResourceAssetKey key) {
            final var name = manager.load(String.class, key.resourceName());
            return ResourceAssetKt.newResourceAsset(resources, name);
        }
        return null;
    }

}
