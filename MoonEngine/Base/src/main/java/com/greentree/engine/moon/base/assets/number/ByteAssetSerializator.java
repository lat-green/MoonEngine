package com.greentree.engine.moon.base.assets.number;

import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.asset.Value1Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;

public class ByteAssetSerializator implements AssetSerializator<Byte> {

    @Override
    public Asset<Byte> load(AssetManager manager, AssetKey ckey) {
        final var str = manager.load(String.class, ckey);
        return AssetKt.map(str, new StringToByte());
    }

    private static final class StringToByte implements Value1Function<String, Byte> {

        private static final long serialVersionUID = 1L;

        @Override
        public Byte apply(String str) {
            return Byte.parseByte(str);
        }

    }

}
