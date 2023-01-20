package com.greentree.engine.moon.replication;

import java.util.function.IntConsumer;

import com.greentree.common.ecs.WorldComponent;
import com.greentree.commons.action.ListenerCloser;
import com.greentree.commons.action.observer.integer.IntAction;


public class Clients implements WorldComponent {
	private static final long serialVersionUID = 1L;

	private final IntAction action = new IntAction();
	
	void event(int owner) {
		action.event(owner);
	}
	
	public ListenerCloser addListener(IntConsumer listener) {
		return action.addListener(listener);
	}
	
}
