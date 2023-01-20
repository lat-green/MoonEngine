package com.greentree.engine.moon.script;

import java.util.Objects;

import com.greentree.common.ecs.component.ConstComponent;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.util.iterator.IteratorUtil;

public record Scripts(Iterable<? extends Value<? extends Script>> scripts)
		implements ConstComponent {
	
	@SafeVarargs
	public Scripts(Value<? extends Script>... scripts) {
		this(IteratorUtil.iterable(scripts));
	}
	
	public Scripts() {
		this(IteratorUtil.empty());
	}
	
	public Scripts {
		Objects.requireNonNull(scripts);
	}
	
}
