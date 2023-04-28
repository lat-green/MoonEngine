package test.com.greentree.engine.moon.kernel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.greentree.engine.moon.kernel.annotation.ArgumentsInjectorImpl;
import com.greentree.engine.moon.kernel.annotation.Autowired;
import com.greentree.engine.moon.kernel.annotation.EngineBeanFactoryProcessor;
import com.greentree.engine.moon.kernel.annotation.EngineConfigurationProcessor;
import com.greentree.engine.moon.kernel.annotation.ImportClassBeanFactoryProcessor;
import com.greentree.engine.moon.kernel.annotation.InjectBeanProcessor;
import com.greentree.engine.moon.kernel.annotation.PackageScanEngineBeanProcessor;
import com.greentree.engine.moon.kernel.annotation.PostConstructProcessor;
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
		
		context.addBean(new InjectBeanProcessor(context));
		context.addBean(new ArgumentsInjectorImpl());
		
		context.addBean(new EngineConfigurationProcessor());
		context.addBean(new ImportClassBeanFactoryProcessor());
		context.addBean(new PackageScanEngineBeanProcessor());
		
		context.addBean(new EngineBeanFactoryProcessor());
		context.addBean(new ImportClassBeanFactoryProcessor());
		context.addBean(new PostConstructProcessor());
		
		context.addBeans(new A(), new B());
		
		var a = context.getBean(A.class).get();
		var b = context.getBean(B.class).get();
		
		System.out.println(context.getBeans().toList());
		
		assertNotNull(a);
		assertNotNull(b);
		
		assertNull(a.b);
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
