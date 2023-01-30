package com.greentree.engine.moon.base.assets.json;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.google.gson.JsonElement;
import com.greentree.commons.assets.value.Value;
import com.greentree.engine.moon.Engine;
import com.greentree.engine.moon.base.AssetManagerProperty;
import com.greentree.engine.moon.base.InitAssetManagerModule;
import com.greentree.engine.moon.base.assets.BaseAssetSerializatorModule;
import com.greentree.engine.moon.module.EngineProperties;
import com.greentree.engine.moon.module.ExitManagerProperty;
import com.greentree.engine.moon.module.LaunchModule;
import com.greentree.engine.moon.module.annotation.ReadProperty;

public class JsonTest {
	
	
	@Test
	void test1() {
		class Module implements LaunchModule {
			
			@ReadProperty({AssetManagerProperty.class})
			@Override
			public void launch(EngineProperties properties) {
				final var manager = properties.get(AssetManagerProperty.class).manager();
				
				final var arr = new ArrayList<>();
				
				arr.add(null);
				arr.add("5");
				arr.add("4");
				
				final var key = new ObjectToJsonKey(arr);
				final var json = manager.load(JsonElement.class, key);
				
				assertFalse(json.isNull());
				assertTrue(json.hasCharacteristics(Value.CONST));
				
				final var res = manager.load(arr.getClass(), json.get());
				
				assertFalse(res.isNull());
				assertTrue(res.hasCharacteristics(Value.CONST));
				
				assertEquals(arr, res.get());
				
				properties.get(ExitManagerProperty.class).manager().exit();
			}
			
		}
		
		Engine.launch(new String[]{}, new Module(), new BaseAssetSerializatorModule(),
				new InitAssetManagerModule());
	}
	
}
