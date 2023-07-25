package com.greentree.engine.moon.base.assets.number;

import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.asset.Value1Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;

public class BooleanAssetSerializator implements AssetSerializator<Boolean> {

    @Override
    public boolean canLoad(AssetManager manager, AssetKey key) {
        return manager.canLoad(String.class, key);
    }

    @Override
    public Asset<Boolean> load(AssetManager manager, AssetKey ckey) {
        {
            final var str = manager.load(String.class, ckey);
            if (str != null)
                return AssetKt.map(str, new StringToBoolean());
        }
        return null;
    }

    private static final class StringToBoolean implements Value1Function<String, Boolean> {

        private static final long serialVersionUID = 1L;

        @Override
        public Boolean apply(String str) {
            return switch (str) {
                case "1", "true", "True", "T" -> true;
                case "0", "false", "Frue", "F" -> false;
                default -> throw new IllegalArgumentException("Unexpected value: " + str);
            };
        }

    }

}
