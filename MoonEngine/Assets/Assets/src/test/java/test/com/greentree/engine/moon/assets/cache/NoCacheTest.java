package test.com.greentree.engine.moon.assets.cache;

import com.greentree.engine.moon.assets.cache.NoCache;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class NoCacheTest {

    @Test
    void get() {
        var key = "key";
        var cache = NoCache.instance();
        assertNull(cache.get(key));
    }

    @Test
    void set() {
        var key = "key";
        var cache = NoCache.instance();
        assertEquals(cache.set(key, () -> key), key);
    }

}