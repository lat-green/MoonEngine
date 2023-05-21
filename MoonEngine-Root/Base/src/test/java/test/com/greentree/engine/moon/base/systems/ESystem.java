package test.com.greentree.engine.moon.base.systems;

import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.system.InitSystem;

public class ESystem implements InitSystem {
	
	@Override
	public void init(World world) {
		
	}
	
	@Override
	public String toString() {
		return "E";
	}
}
