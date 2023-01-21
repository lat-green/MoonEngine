package com.greentree.engine.moon.replication;

import java.util.ArrayList;
import java.util.Collection;

import com.greentree.engine.moon.ecs.Entity;

public class ConnectionInfos implements AutoCloseable {
	
	private final Collection<ConnectionInfo> connections = new ArrayList<>();
	
	public ConnectionInfos() {
	}

	public void add(ConnectionInfo connection) {
		connections.add(connection);
	}
	
	@Override
	public void close() {
		for(var c : connections)
			c.close();
		connections.clear();
	}
	
	public void write(Iterable<Entity> replication) {
		connections.removeIf(ConnectionInfo::isClosed);
		
		for(var c : connections)
			c.write(replication);
	}
	
}
