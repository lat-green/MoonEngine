package com.greentree.engine.moon.collision2d.assets;

import com.greentree.commons.geometry.geom2d.IMovableShape2D;
import com.greentree.commons.geometry.geom2d.shape.Circle2D;
import com.greentree.commons.xml.XMLElement;
import com.greentree.engine.moon.assets.asset.Asset;
import com.greentree.engine.moon.assets.asset.AssetKt;
import com.greentree.engine.moon.assets.asset.Value1Function;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;

public class XMLtoShape implements AssetSerializator<IMovableShape2D> {

    @Override
    public boolean canLoad(AssetManager manager, AssetKey key) {
        return manager.canLoad(XMLElement.class, key);
    }

    @Override
    public Asset<IMovableShape2D> load(AssetManager manager, AssetKey key) {
        if (manager.canLoad(XMLElement.class, key)) {
            var v = manager.load(XMLElement.class, key);
            return AssetKt.map(v, new XMLtoShapeFunction());
        }
        return null;
    }

    private static final class XMLtoShapeFunction implements Value1Function<XMLElement, IMovableShape2D> {

        @Override
        public IMovableShape2D apply(XMLElement value) {
            var type = value.getAttribute("type").toLowerCase();
            switch (type) {
                case "circle" -> {
                    var radius = Float.parseFloat(value.getChildren("radius").getContent());
                    return new Circle2D(radius);
                }
                case "capsule" -> {
                }
            }
            throw new IllegalArgumentException("unsupported type " + type);
        }

    }

}
