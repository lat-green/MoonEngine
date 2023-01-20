package com.greentree.engine.moon.replication.id;

import com.greentree.common.ecs.annotation.RequiredComponent;
import com.greentree.common.ecs.component.ConstComponent;
import com.greentree.engine.moon.replication.ReplicationComponent;

@RequiredComponent({ReplicationComponent.class})
public record OwnerID(int id) implements ConstComponent {
	
}
