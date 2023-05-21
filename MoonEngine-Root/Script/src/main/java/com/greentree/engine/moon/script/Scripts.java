package com.greentree.engine.moon.script;

import java.util.Objects;

import com.greentree.commons.util.iterator.IteratorUtil;
import com.greentree.engine.moon.assets.value.provider.ValueProvider;
import com.greentree.engine.moon.ecs.component.ConstComponent;

public record Scripts(Iterable<? extends ValueProvider<? extends Script>> scripts)
		implements ConstComponent {
	
	@SafeVarargs
	public Scripts(ValueProvider<? extends Script>... scriptsArray) {
		this(IteratorUtil.iterable(scriptsArray));
	}
	
	public Scripts() {
		this(IteratorUtil.empty());
	}
	
	public Scripts {
		Objects.requireNonNull(scripts);
	}
	
}
