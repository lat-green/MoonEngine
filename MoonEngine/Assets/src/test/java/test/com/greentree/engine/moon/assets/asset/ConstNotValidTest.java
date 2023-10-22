package test.com.greentree.engine.moon.assets.asset;

import com.greentree.engine.moon.assets.asset.ConstAsset;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConstNotValidTest {

    @Test
    void createNull() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            new ConstAsset<>(null);
        });
    }

}
