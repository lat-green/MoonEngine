package com.greentree.common.ecs;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.greentree.engine.moon.ecs.Entity;

public class TestUtil {

	public static void assertComponentEquals(Entity a, Entity b) {
		for(var ac : a) {
			final var bc = b.get(ac.getClass());
			assertEquals(ac, bc);
		}
		for(var bc : b) {
			final var ac = a.get(bc.getClass());
			assertEquals(ac, bc);
		}
	}


	@SuppressWarnings("unchecked")
	public static <T> T deser(byte[] ser) throws IOException, ClassNotFoundException {
		try(final var bout = new ByteArrayInputStream(ser)) {
			try(final var oout = new ObjectInputStream(bout)) {
				return (T) oout.readObject();
			}
		}
	}

	public static byte[] ser(Object obj) throws IOException {
		try(final var bout = new ByteArrayOutputStream()) {
			try(final var oout = new ObjectOutputStream(bout)) {
				oout.writeObject(obj);
			}
			return bout.toByteArray();
		}
	}
	
}
