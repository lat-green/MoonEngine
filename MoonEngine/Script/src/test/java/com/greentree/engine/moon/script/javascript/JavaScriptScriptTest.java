package com.greentree.engine.moon.script.javascript;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;
import org.mozilla.javascript.UniqueTag;

import com.greentree.engine.moon.script.Console;

public class JavaScriptScriptTest {
	
	@Test
	void bese_test() throws IOException {
		try(final var script = new JavaScriptScript("""

c = a + b
console.log(c)

""");) {
			script.set("a", 5);
			script.set("b", 4);
			
			try(final var w = new ByteArrayOutputStream();final var out = new PrintStream(w)) {
				script.set("console", new Console(out));
				var c = script.get("c");
				assertEquals(c, UniqueTag.NOT_FOUND);
				script.run();
				c = script.get("c");
				assertEquals(c, 9.0);
				assertEquals(new String(w.toByteArray()).trim(), "9.0");
			}
		}
	}
	
}
