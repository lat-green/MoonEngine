package com.greentree.common.ecs.component;


public interface ConstComponent extends Component {
	
	@Override
	default ConstComponent copy() {
		return this;
	}
	
	@Override
	default boolean copyTo(Component other) {
		return equals(other);
	}
	
}
