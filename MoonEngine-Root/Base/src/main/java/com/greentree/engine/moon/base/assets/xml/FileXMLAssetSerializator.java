package com.greentree.engine.moon.base.assets.xml;

import com.greentree.commons.data.resource.Resource;
import com.greentree.commons.xml.SAXXMLParser;
import com.greentree.commons.xml.XMLElement;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.asset.Value1Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;

public class FileXMLAssetSerializator implements AssetSerializator<XMLElement> {

    @Override
    public Asset<XMLElement> load(AssetManager context, AssetKey key) {
        final var res = context.load(Resource.class, key);
        return AssetKt.map(res, XMLFunction.INSTANCE);
    }

    private static final class XMLFunction implements Value1Function<Resource, XMLElement> {

        public static final XMLFunction INSTANCE = new XMLFunction();
        private static final long serialVersionUID = 1L;

        @SuppressWarnings("deprecation")
        @Override
        public XMLElement apply(Resource res) {
            try (final var in = res.open()) {
                return SAXXMLParser.parse(in);
            } catch (Exception e) {
                throw new IllegalArgumentException(res.toString(), e);
            }
        }

    }

}
