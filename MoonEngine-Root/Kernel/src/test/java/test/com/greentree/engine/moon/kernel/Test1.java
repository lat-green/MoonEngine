package test.com.greentree.engine.moon.kernel;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.greentree.commons.injector.InjectionContainer;
import com.greentree.commons.injector.Injector;
import com.greentree.engine.moon.kernel.AutowiredFieldDependencyScanner;
import com.greentree.engine.moon.kernel.annotation.Autowired;
import com.greentree.engine.moon.kernel.container.ArraySetBeanContainer;

public class Test1 {
	
	@Test
	void getNotNull() {
		var context = new ArraySetBeanContainer();
		context.addBean(new A());
		
		var a = context.getBean(A.class).get();
		
		assertNotNull(a);
		
	}
	
	@Test
	void injectDepndency() {
		var context = new ArraySetBeanContainer();
		
		context.action().addListener(bean -> {
			var injector = new Injector(new InjectionContainer() {
				
				@Override
				public <T> Optional<T> get(Class<T> cls) {
					return context.getBean(cls);
				}
				
			}, new AutowiredFieldDependencyScanner());
			injector.inject(bean);
		});
		
		context.addBean(new A());
		context.addBean(new B());
		
		var a = context.getBean(A.class).get();
		var b = context.getBean(B.class).get();
		
		assertNotNull(a);
		assertNull(a.b);
		
		assertNotNull(b);
		assertNotNull(b.a);
	}
	
	public static final class A {
		
		@Override
		public String toString() {
			return "A [" + b + "]";
		}
		
		@Autowired(required = false)
		B b;
	}
	
	public static final class B {
		
		@Override
		public String toString() {
			return "B [" + a + "]";
		}
		
		@Autowired(required = false)
		A a;
	}
	
}
