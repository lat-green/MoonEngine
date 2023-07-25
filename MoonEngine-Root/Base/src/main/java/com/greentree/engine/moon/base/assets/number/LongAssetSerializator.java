package com.greentree.engine.moon.base.assets.number;

import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.asset.Value1Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;

public class LongAssetSerializator implements AssetSerializator<Long> {

    @Override
    public boolean canLoad(AssetManager manager, AssetKey key) {
        return manager.canLoad(String.class, key);
    }

    @Override
    public Asset<Long> load(AssetManager manager, AssetKey ckey) {
        {
            final var str = manager.load(String.class, ckey);
            if (str != null)
                return AssetKt.map(str, new StringToLong());
        }
        return null;
    }

    private static final class StringToLong implements Value1Function<String, Long> {

        private static final long serialVersionUID = 1L;

        @Override
        public Long apply(String str) {
            return Long.parseLong(str);
        }

    }

}
