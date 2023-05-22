package test.com.greentree.engine.moon.base.systems;

import com.greentree.engine.moon.base.component.DestroyComponent;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.system.InitSystem;

public class DSystem implements InitSystem {
	
	@DestroyComponent({TestComponent1.class})
	@Override
	public void init(World world) {
		
	}
	
	@Override
	public String toString() {
		return "D";
	}
	
}
