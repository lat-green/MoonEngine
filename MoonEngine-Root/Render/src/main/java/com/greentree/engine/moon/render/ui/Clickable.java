package com.greentree.engine.moon.render.ui;

import com.greentree.common.ecs.annotation.RequiredComponent;
import com.greentree.common.ecs.component.ConstComponent;
import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.action.observer.run.RunAction;
import com.greentree.engine.moon.base.transform.Transform;

@RequiredComponent({Transform.class})
public final class Clickable implements ConstComponent {
	
	private static final long serialVersionUID = 1L;
	
	private transient final RunAction action = new RunAction();
	
	public ListenerCloser addListener(Runnable listener) {
		return action.addListener(listener);
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Clickable;
	}
	
	@Override
	public int hashCode() {
		return 0;
	}
	
}
