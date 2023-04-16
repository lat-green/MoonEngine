package com.greentree.engine.moon.base.assets;

import com.greentree.engine.moon.base.AssetManagerProperty;
import com.greentree.engine.moon.base.assets.json.JSONAssetSerializator;
import com.greentree.engine.moon.base.assets.number.BooleanAssetSerializator;
import com.greentree.engine.moon.base.assets.number.ByteAssetSerializator;
import com.greentree.engine.moon.base.assets.number.DoubleAssetSerializator;
import com.greentree.engine.moon.base.assets.number.FloatAssetSerializator;
import com.greentree.engine.moon.base.assets.number.IntAssetSerializator;
import com.greentree.engine.moon.base.assets.number.LongAssetSerializator;
import com.greentree.engine.moon.base.assets.number.ShortAssetSerializator;
import com.greentree.engine.moon.base.assets.properties.PropertiesAssetSerializator;
import com.greentree.engine.moon.base.assets.scene.XMLSceneAssetSerializator;
import com.greentree.engine.moon.base.assets.text.PropertyStringAssetSerializor;
import com.greentree.engine.moon.base.assets.text.StringAssetSerializor;
import com.greentree.engine.moon.base.assets.xml.XMLAssetSerializator;
import com.greentree.engine.moon.base.scene.SceneManagerProperty;
import com.greentree.engine.moon.modules.EngineProperties;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.WriteProperty;

public class BaseAssetSerializatorModule implements LaunchModule {
	
	@WriteProperty({AssetManagerProperty.class,SceneManagerProperty.class})
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
		
		manager.addSerializator(new StringAssetSerializor());
		manager.addSerializator(new PropertyStringAssetSerializor());
		manager.addSerializator(new PropertiesAssetSerializator());
		
		manager.addSerializator(new XMLAssetSerializator());
		manager.addSerializator(new JSONAssetSerializator());
		
		manager.addSerializator(new XMLSceneAssetSerializator());
	}
	
}
