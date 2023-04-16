package test.com.greentree.engine.moon.kernel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.greentree.engine.moon.kernel.container.ConfigurableBeanContainerBase;

public class Test1 {
	
	public static final class A {
		
		B b;
	}
	
	public static final class B {
		
		A a;
	}
	
	@Test
	void test1() {
		var context = new ConfigurableBeanContainerBase();
		context.addBean(A.class);
		context.addBean(B.class);
		
		var a = context.get(A.class).get();
		var b = context.get(B.class).get();
		
		assertNotNull(b);
		assertNotNull(b.a);
		assertNotNull(a);
		assertNotNull(a.b);
		
		assertEquals(a, b.a);
		assertEquals(b, a.b);
		
	}
	
	@Test
	void getNotNull() {
		var context = new ConfigurableBeanContainerBase();
		context.addBean(A.class);
		
		var a = context.get(A.class).get();
		
		assertNotNull(a);
		
	}
	
}
