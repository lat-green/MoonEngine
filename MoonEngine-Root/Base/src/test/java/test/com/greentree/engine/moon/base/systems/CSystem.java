package test.com.greentree.engine.moon.base.systems;

import com.greentree.engine.moon.base.systems.ReadComponent;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.system.InitSystem;

public class CSystem implements InitSystem {
	
	@ReadComponent({TestComponent1.class})
	@Override
	public void init(World world) {
		
	}
	
	@Override
	public String toString() {
		return "C";
	}
}
