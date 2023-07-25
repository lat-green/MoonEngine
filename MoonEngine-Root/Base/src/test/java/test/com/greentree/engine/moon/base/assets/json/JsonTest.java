package test.com.greentree.engine.moon.base.assets.json;

import com.google.gson.JsonElement;
import com.greentree.engine.moon.base.AssetManagerProperty;
import com.greentree.engine.moon.base.assets.json.JSONToObjectGenerator;
import com.greentree.engine.moon.base.assets.json.ObjectToJsonKey;
import com.greentree.engine.moon.base.property.modules.ReadProperty;
import com.greentree.engine.moon.modules.LaunchModule;
import com.greentree.engine.moon.modules.property.EngineProperties;
import com.greentree.engine.moon.modules.property.ExitManagerProperty;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    @Test
    void test1() {
        class Module implements LaunchModule {

            @ReadProperty({AssetManagerProperty.class})
            @Override
            public void launch(EngineProperties properties) {
                final var manager = properties.get(AssetManagerProperty.class).manager();
                manager.addGenerator(new JSONToObjectGenerator());
                final var arr = new ArrayList<>();
                arr.add(null);
                arr.add("5");
                arr.add("4");
                final var key = new ObjectToJsonKey(arr);
                final var json = manager.load(JsonElement.class, key);
                final var res = manager.load(arr.getClass(), json.getValue());
                assertEquals(arr, res.getValue());
                properties.get(ExitManagerProperty.class).manager().exit();
            }

        }
        //		Engine.launch(new String[]{}, new Module(), new BaseAssetSerializatorModule());
    }

}
