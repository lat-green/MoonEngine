package com.greentree.engine.moon.base.options;

import com.greentree.engine.moon.assets.key.ResourceAssetKey;
import com.greentree.engine.moon.base.AssetManagerProperty;
import com.greentree.engine.moon.base.property.modules.CreateProperty;
import com.greentree.engine.moon.base.property.modules.ReadProperty;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.property.EngineProperties;

import java.util.Properties;

import static com.greentree.commons.reflection.info.TypeInfoBuilder.getTypeInfo;

public class OptionsModule implements LaunchModule {

    @ReadProperty(AssetManagerProperty.class)
    @CreateProperty(OptionsProperty.class)
    @Override
    public void launch(EngineProperties properties) {
        var manager = properties.get(AssetManagerProperty.class).manager;
        var ps = manager.load(getTypeInfo(Properties.class), new ResourceAssetKey("options.properties")).getValue();
        properties.add(new OptionsProperty(new PropertiesOptionsProvider(ps)));
    }

    private record PropertiesOptionsProvider(Properties properties) implements OptionsProvider {

        @Override
        public String get(String name) {
            return properties.getProperty(name);
        }

    }

}
