package com.greentree.engine.moon.base.assets.number;

import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.asset.Value1Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;

public class ShortAssetSerializator implements AssetSerializator<Short> {

    @Override
    public boolean canLoad(AssetManager manager, AssetKey key) {
        return manager.canLoad(String.class, key);
    }

    @Override
    public Asset<Short> load(AssetManager manager, AssetKey ckey) {
        {
            final var str = manager.load(String.class, ckey);
            if (str != null)
                return AssetKt.map(str, new StringToShort());
        }
        return null;
    }

    private static final class StringToShort implements Value1Function<String, Short> {

        private static final long serialVersionUID = 1L;

        @Override
        public Short apply(String str) {
            return Short.parseShort(str);
        }

    }

}
