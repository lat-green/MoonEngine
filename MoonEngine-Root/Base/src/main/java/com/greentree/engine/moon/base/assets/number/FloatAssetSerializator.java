package com.greentree.engine.moon.base.assets.number;

import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.asset.Value1Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;

public class FloatAssetSerializator implements AssetSerializator<Float> {

    @Override
    public Asset<Float> load(AssetManager manager, AssetKey ckey) {
        final var str = manager.load(String.class, ckey);
        return AssetKt.map(str, new StringToFloat());
    }

    private static final class StringToFloat implements Value1Function<String, Float> {

        private static final long serialVersionUID = 1L;

        @Override
        public Float apply(String value) {
            return Float.parseFloat(value);
        }

    }

}
