package com.greentree.common.assets.file;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.greentree.common.assets.file.AssetTest.NullExecutorService;
import com.greentree.common.assets.file.AssetTest.StringToIntAssetSerializator;
import com.greentree.common.assets.value.CloseEventValue;
import com.greentree.commons.tests.ExecuteCounter;
import com.greentree.engine.moon.assets.key.AssetKey;
import com.greentree.engine.moon.assets.serializator.AssetSerializator;
import com.greentree.engine.moon.assets.serializator.context.LoadContext;
import com.greentree.engine.moon.assets.serializator.manager.AssetManager;
import com.greentree.engine.moon.assets.serializator.manager.CanLoadAssetManager;
import com.greentree.engine.moon.assets.value.MutableValue;
import com.greentree.engine.moon.assets.value.Value;

public class CloseTest {
	
	
	
	private static final String TEXT1 = "A";
	private static final String TEXT2 = "B";
	
	
	private AssetManager manager;
	
	@BeforeEach
	void setup() {
		manager = new AssetManager(new NullExecutorService());
	}
	
	@Test
	void test1() {
		try(final var t1 = new ExecuteCounter(1);) {
			final var v1 = new CloseEventValue<>(new MutableValue<>(TEXT1), t1).openProvider();
			v1.close();
		}
	}
	
	@Test
	void test2() {
		manager.addSerializator(new CloseEventAssetSerializator());
		
		try(final var close_counter = new ExecuteCounter(1);) {
			final var key = new CloseEventKey("Not delete", close_counter);
			final var v1 = manager.load(String.class, key);
			final var v2 = manager.load(String.class, key);
			assertTrue(v1 == v2);
			v1.openProvider().close();
			final var v3 = manager.load(String.class, key);
			assertFalse(v1 == v3);
		}
	}
	
	@Test
	void test3() {
		manager.addSerializator(new CloseEventAssetSerializator());
		
		try(final var close_counter = new ExecuteCounter(1);) {
			final var key = new CloseEventKey("delete 2", close_counter);
			final var v1 = manager.load(String.class, key).openProvider();
			final var v2 = manager.load(String.class, key).openProvider();
			v1.close();
			v2.close();
		}
	}
	
	@Test
	void test4() {
		manager.addSerializator(new CloseEventAssetSerializator());
		
		try(final var close_counter = new ExecuteCounter(1);) {
			final var key = new CloseEventKey("delete 1", close_counter);
			final var v1 = manager.load(String.class, key).openProvider();
			v1.close();
		}
	}
	
	@Test
	void test5() {
		manager.addSerializator(new StringToIntAssetSerializator());
		manager.addSerializator(new CloseEventAssetSerializator());
		
		try(final var close_counter = new ExecuteCounter(1);) {
			final var key = new CloseEventKey("1234", close_counter);
			final var v1 = manager.load(Integer.class, key).openProvider();
			v1.close();
		}
	}
	
	private static final class CloseEventAssetSerializator implements AssetSerializator<String> {
		
		@Override
		public boolean canLoad(CanLoadAssetManager manager, AssetKey key) {
			return key instanceof CloseEventKey;
		}
		
		@Override
		public Value<String> load(LoadContext context, AssetKey ckey) {
			if(ckey instanceof CloseEventKey key) {
				return new CloseEventValue<>(new MutableValue<>(key.value), key.run);
			}
			return null;
		}
	}
	
	public record CloseEventKey(String value, Runnable run) implements AssetKey {
		
	}
	
}
