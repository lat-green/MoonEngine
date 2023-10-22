package com.greentree.engine.moon.replication;

import com.greentree.commons.util.exception.WrappedException;
import com.greentree.commons.web.protocol.StreamableConnection;
import com.greentree.commons.web.protocol.tcp.TCP;
import com.greentree.engine.moon.base.property.modules.CreateProperty;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.builder.FilterBuilder;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.ecs.system.WorldInitSystem;
import com.greentree.engine.moon.replication.event.EntityEvent;
import com.greentree.engine.moon.replication.id.OwnerID;
import com.greentree.engine.moon.replication.id.OwnerIDWorldComponent;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

public class ClientSystem implements WorldInitSystem, UpdateSystem, DestroySystem {

    private static final FilterBuilder REPLICATION = new FilterBuilder().require(ReplicationComponent.class).require(OwnerID.class);
    private final InetAddress address;
    private final int port;
    private final Collection<EntityEvent> evs = new ArrayList<>();
    private Filter<?> replication;
    private SeverInfo connection;
    private World world;
    private SceneProperties sceneProperties;

    public ClientSystem(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }

    @Override
    public void destroy() {
        world = null;
        replication = null;
        evs.clear();
    }

    @CreateProperty({OwnerIDWorldComponent.class})
    @Override
    public void init(World world, SceneProperties sceneProperties) {
        this.world = world;
        this.sceneProperties = sceneProperties;
        replication = REPLICATION.build(world);
        try {
            connection = new SeverInfo(TCP.INSTANCE.open(address, port), evs -> {
                synchronized (this.evs) {
                    for (var e : evs)
                        this.evs.add(e);
                }
            });
            sceneProperties.add(new OwnerIDWorldComponent(connection.id));
        } catch (IOException e) {
            throw new WrappedException(e);
        }
    }

    @Override
    public void update() {
        synchronized (evs) {
            for (var ee : evs)
                ee.accept(world, sceneProperties);
            evs.clear();
        }
        connection.write(replication);
    }
    //	private void diff(byte[] a, byte[] b) {
    //		final var mnlen = Math.min(a.length, b.length);
    //		for(int i = 0; i < mnlen; i++) {
    //			final var ac = a[i];
    //			final var bc = b[i];
    //			if(ac != bc) {
    //				System.out.println(i);
    //				System.out.println(new String(sub(a, i, 50)));
    //				System.out.println(new String(sub(b, i, 50)));
    //				return;
    //			}
    //		}
    //	}
    //
    //	private byte[] sub(byte[] buf, int mn, int cnt) {
    //		if(mn < 0) mn = 0;
    //		cnt = Math.min(cnt, buf.length - mn);
    //		final var res = new byte[cnt];
    //		for(int i = 0; i < res.length; i++)
    //			res[i] = buf[i + mn];
    //		return res;
    //	}

    public class SeverInfo extends ConnectionInfo {

        private final int id;

        public SeverInfo(StreamableConnection connection, Consumer<? super Iterable<? extends EntityEvent>> events) {
            super(connection);
            try {
                this.id = in.readInt();
            } catch (IOException e) {
                throw new WrappedException(e);
            }
            initReadThream(events);
        }

        @Override
        protected boolean getWriteRuleExistence(int owner, ReplicationType type) {
            return false;
        }

        @Override
        protected boolean getWriteRuleChange(int owner, ReplicationType type) {
            return switch (type) {
                case PUBLIC_OWNER -> true;
                case PRIVATE_OWNER -> true;
                default -> false;
            };
        }

    }

}
