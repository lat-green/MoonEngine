package com.greentree.engine.moon.replication;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import com.greentree.common.ecs.component.Component;
import com.greentree.common.ecs.component.ConstComponent;
import com.greentree.engine.moon.replication.id.EntityID;
import com.greentree.engine.moon.replication.id.OwnerID;

public final class ReplicationComponent implements ConstComponent, Iterable<Class<? extends Component>> {
	private static final long serialVersionUID = 1L;

	private final Map<Class<? extends Component>, ReplicationType> replications;

	private ReplicationComponent(Map<Class<? extends Component>, ReplicationType> replications) {
		this.replications = new HashMap<>();
		for(var r : replications.entrySet())
			this.replications.put(r.getKey(), r.getValue());
	}

	public static Builder builder() {
		return new Builder();
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if((obj == null) || (getClass() != obj.getClass())) return false;
		ReplicationComponent other = (ReplicationComponent) obj;
		return Objects.equals(replications, other.replications);
	}

	public ReplicationType get(Class<? extends Component> cls) {
		return replications.get(cls);
	}

	@Override
	public int hashCode() {
		return Objects.hash(replications);
	}

	@Override
	public Iterator<Class<? extends Component>> iterator() {
		return replications.keySet().iterator();
	}

	public static final class Builder {

		private final Map<Class<? extends Component>, ReplicationType> map = new HashMap<>();

		{
			putServer(EntityID.class);
			putPrivate(ReplicationComponent.class);
			putPrivate(OwnerID.class);
		}

		public ReplicationComponent build() {
			return new ReplicationComponent(map);
		}

		public Builder put(Class<? extends Component> cls, ReplicationType type) {
			if(map.containsKey(cls))
				throw new IllegalArgumentException(cls + " already added");
			map.put(cls, type);
			return this;
		}
		public Builder putPublicOwner(Class<? extends Component> cls) {
			return put(cls, ReplicationType.PUBLIC_OWNER);
		}
		public Builder putOwner(Class<? extends Component> cls) {
			return put(cls, ReplicationType.PRIVATE_OWNER);
		}
		public Builder putServer(Class<? extends Component> cls) {
			return put(cls, ReplicationType.SERVER_ONLY);
		}
		public Builder putPrivate(Class<? extends Component> cls) {
			return put(cls, ReplicationType.PRIVATE);
		}
		public Builder putPublic(Class<? extends Component> cls) {
			return put(cls, ReplicationType.PUBLIC);
		}

	}



}
