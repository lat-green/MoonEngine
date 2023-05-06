package test.com.greentree.engine.moon.kernel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.greentree.engine.moon.kernel.annotation.ArgumentsInjectorImpl;
import com.greentree.engine.moon.kernel.annotation.Autowired;
import com.greentree.engine.moon.kernel.container.ArraySetBeanContainer;
import com.greentree.engine.moon.kernel.processor.EngineBeanFactoryProcessor;
import com.greentree.engine.moon.kernel.processor.EngineConfigurationProcessor;
import com.greentree.engine.moon.kernel.processor.ImportClassBeanFactoryProcessor;
import com.greentree.engine.moon.kernel.processor.InjectBeanProcessor;
import com.greentree.engine.moon.kernel.processor.PackageScanEngineBeanProcessor;
import com.greentree.engine.moon.kernel.processor.PostConstructProcessor;

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
		
		//		System.out.println(context.getBeans().toList());
		
		assertNotNull(a);
		assertNotNull(b);
		
		assertNotNull(a.b);
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
	}
	
}
