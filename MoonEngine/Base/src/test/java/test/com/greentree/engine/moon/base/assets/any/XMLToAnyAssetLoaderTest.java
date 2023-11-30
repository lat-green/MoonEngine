package test.com.greentree.engine.moon.base.assets.any;

import com.greentree.commons.reflection.info.TypeInfoBuilder;
import com.greentree.commons.xml.XMLElement;
import com.greentree.engine.moon.assets.asset.ConstAsset;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.loader.AssetLoader;
import com.greentree.engine.moon.base.assets.any.XMLToAnyAssetLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class XMLToAnyAssetLoaderTest {

    @Mock
    AssetLoader.Context context;

    @Mock
    AssetKey key;

    public record Person(int age) {

    }

    @Test
    void loadA() {
        var field = new XMLElement(List.of(), Map.of("name", "age"), "property", "10");
        var xml = new XMLElement(List.of(field), Map.of(), "component", "");
        Mockito.when(context.load(TypeInfoBuilder.getTypeInfo(XMLElement.class), key)).thenReturn(new ConstAsset<>(xml));
        var person = XMLToAnyAssetLoader.INSTANCE.load(context, TypeInfoBuilder.getTypeInfo(Person.class), key);
        assertEquals(person, new ConstAsset<>(new Person(10)));
    }

}
