package com.greentree.engine.moon.replication;

import com.greentree.engine.moon.ecs.Entity;

import java.util.ArrayList;
import java.util.Collection;

public class ConnectionInfos implements AutoCloseable {

    private final Collection<ConnectionInfo> connections = new ArrayList<>();

    public ConnectionInfos() {
    }

    public void add(ConnectionInfo connection) {
        connections.add(connection);
    }

    @Override
    public void close() {
        for (var c : connections)
            c.close();
        connections.clear();
    }

    public void write(Iterable<? extends Entity> replication) {
        connections.removeIf(ConnectionInfo::isClosed);
        for (var c : connections)
            c.write(replication);
    }

}
