package com.greentree.engine.moon.base.assets.number;

import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.asset.Value1Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;

public class DoubleAssetSerializator implements AssetSerializator<Double> {

    @Override
    public Asset<Double> load(AssetManager manager, AssetKey ckey) {
        final var str = manager.load(String.class, ckey);
        return AssetKt.map(str, new StringToDouble());
    }

    private static final class StringToDouble implements Value1Function<String, Double> {

        private static final long serialVersionUID = 1L;

        @Override
        public Double apply(String value) {
            return Double.parseDouble(value);
        }

    }

}
