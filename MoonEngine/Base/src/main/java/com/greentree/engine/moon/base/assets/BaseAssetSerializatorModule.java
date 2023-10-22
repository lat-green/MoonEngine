package com.greentree.engine.moon.base.assets;

import com.greentree.engine.moon.base.AssetManagerProperty;
import com.greentree.engine.moon.base.assets.json.JSONAssetSerializator;
import com.greentree.engine.moon.base.assets.number.*;
import com.greentree.engine.moon.base.assets.properties.PropertiesAssetSerializator;
import com.greentree.engine.moon.base.assets.scene.XMLSceneAssetSerializator;
import com.greentree.engine.moon.base.assets.text.PropertyStringAssetSerializator;
import com.greentree.engine.moon.base.assets.text.ResourceToTextSerializator;
import com.greentree.engine.moon.base.assets.xml.XMLAssetSerializator;
import com.greentree.engine.moon.base.property.modules.WriteProperty;
import com.greentree.engine.moon.base.scene.SceneManagerProperty;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.property.EngineProperties;

public class BaseAssetSerializatorModule implements LaunchModule {

    @WriteProperty({AssetManagerProperty.class, SceneManagerProperty.class})
    @Override
    public void launch(EngineProperties context) {
        final var manager = context.get(AssetManagerProperty.class).manager();
        manager.addSerializator(new LongAssetSerializator());
        manager.addSerializator(new IntAssetSerializator());
        manager.addSerializator(new DoubleAssetSerializator());
        manager.addSerializator(new FloatAssetSerializator());
        manager.addSerializator(new ByteAssetSerializator());
        manager.addSerializator(new ShortAssetSerializator());
        manager.addSerializator(new BooleanAssetSerializator());
        manager.addSerializator(new ResourceToTextSerializator());
        manager.addSerializator(new PropertyStringAssetSerializator());
        manager.addSerializator(new PropertiesAssetSerializator());
        manager.addSerializator(new XMLAssetSerializator());
        manager.addSerializator(new JSONAssetSerializator());
        manager.addSerializator(new XMLSceneAssetSerializator());
    }

}
