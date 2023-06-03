package test.com.greentree.engine.moon.modules.property;

import com.greentree.engine.moon.modules.property.EnginePropertiesBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EnginePropertiesTest {

    @Test
    void throw_ExampleEngineProperty() {
        var properties = new EnginePropertiesBase();
        Assertions.assertThrows(Exception.class, () -> {
            properties.get(ExampleEngineProperty.class);
        });
    }

    @Test
    void throw_KotlinExampleEngineProperty() {
        var properties = new EnginePropertiesBase();
        Assertions.assertThrows(Exception.class, () -> {
            properties.get(KotlinExampleEngineProperty.class);
        });
    }

}
