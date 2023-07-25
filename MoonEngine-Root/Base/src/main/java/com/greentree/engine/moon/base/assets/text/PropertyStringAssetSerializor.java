package com.greentree.engine.moon.base.assets.text;

import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.asset.Value2Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;

import java.util.Properties;

public class PropertyStringAssetSerializor implements AssetSerializator<String> {

    @Override
    public boolean canLoad(AssetManager manager, AssetKey key) {
        if (key instanceof PropertyAssetKey k)
            return manager.canLoad(Properties.class, k.properties())
                    && manager.canLoad(String.class, k.name());
        return false;
    }

    @Override
    public Asset<String> load(AssetManager manager, AssetKey ckey) {
        if (ckey instanceof PropertyAssetKey key) {
            final var prop = manager.load(Properties.class, key.properties());
            final var name = manager.load(String.class, key.name());
            return AssetKt.map(prop, name, new PropertyAssetFunction());
        }
        return null;
    }

    private static final class PropertyAssetFunction
            implements Value2Function<Properties, String, String> {

        private static final long serialVersionUID = 1L;

        @Override
        public String apply(Properties prop, String name) {
            return prop.getProperty(name);
        }

    }

}
