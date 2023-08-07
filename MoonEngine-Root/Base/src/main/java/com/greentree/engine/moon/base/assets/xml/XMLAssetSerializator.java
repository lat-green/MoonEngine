package com.greentree.engine.moon.base.assets.xml;

import com.greentree.commons.xml.SAXXMLParser;
import com.greentree.commons.xml.XMLElement;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.asset.Value1Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;

import java.io.ByteArrayInputStream;

public class XMLAssetSerializator implements AssetSerializator<XMLElement> {

    @Override
    public Asset<XMLElement> load(AssetManager context, AssetKey key) {
        final var text = context.load(String.class, key);
        return AssetKt.map(text, XMLTextFunction.INSTANCE);
    }

    private static final class XMLTextFunction implements Value1Function<String, XMLElement> {

        public static final XMLTextFunction INSTANCE = new XMLTextFunction();
        private static final long serialVersionUID = 1L;

        @SuppressWarnings("deprecation")
        @Override
        public XMLElement apply(String text) {
            try (final var bout = new ByteArrayInputStream(text.getBytes())) {
                return SAXXMLParser.parse(bout);
            } catch (Exception e) {
                throw new IllegalArgumentException(text, e);
            }
        }

    }

}
