package com.greentree.engine.moon.base.assets.number;

import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.asset.Value1Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;

public class IntAssetSerializator implements AssetSerializator<Integer> {

    @Override
    public Asset<Integer> load(AssetManager manager, AssetKey ckey) {
        final var str = manager.load(String.class, ckey);
        return AssetKt.map(str, new StringToInteger());
    }

    private static final class StringToInteger implements Value1Function<String, Integer> {

        private static final long serialVersionUID = 1L;

        @Override
        public Integer apply(String str) {
            return Integer.parseInt(str);
        }

    }

}
