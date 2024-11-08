package com.greentree.engine.moon.replication;

import com.greentree.commons.util.exception.WrappedException;
import com.greentree.commons.web.protocol.StreamableConnection;
import com.greentree.commons.web.protocol.tcp.TCP;
import com.greentree.engine.moon.base.property.modules.CreateProperty;
import com.greentree.engine.moon.base.time.Time;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.builder.FilterBuilder;
import com.greentree.engine.moon.ecs.scene.SceneProperties;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.ecs.system.WorldInitSystem;
import com.greentree.engine.moon.replication.event.EntityEvent;
import com.greentree.engine.moon.replication.id.EntityID;
import com.greentree.engine.moon.replication.id.OwnerIDWorldComponent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

public class ServerSystem implements WorldInitSystem, UpdateSystem, DestroySystem {

    private static final FilterBuilder REPLICATION = new FilterBuilder().require(ReplicationComponent.class).require(EntityID.class);

    private final int port;
    private final Collection<EntityEvent> evs = new ArrayList<>();
    private final float DELTA;
    private final Collection<Runnable> commands = new ArrayList<>();
    private ConnectionInfos connections;
    private Filter<?> replication;
    private boolean[] running;
    private Clients cliens;
    private World world;
    private Time time;

    private float d;
    private SceneProperties sceneProperties;

    public ServerSystem(int port, float delta) {
        this.port = port;
        this.DELTA = delta;
    }

    @Override
    public void destroy() {
        connections.close();
        connections = null;
        replication = null;
        running[0] = false;
        running = null;
        evs.clear();
        world = null;
        time = null;
        d = 0;
        sceneProperties = null;
    }

    @CreateProperty({OwnerIDWorldComponent.class, Clients.class})
    @Override
    public void init(World world, SceneProperties sceneProperties) {
        this.world = world;
        this.sceneProperties = sceneProperties;
        this.time = sceneProperties.get(Time.class);
        cliens = new Clients();
        sceneProperties.add(cliens);
        replication = REPLICATION.build(world);
        sceneProperties.add(new OwnerIDWorldComponent(0));
        connections = new ConnectionInfos();
        initAcceptThread();
    }

    private void initAcceptThread() {
        final var running = new boolean[]{true};
        this.running = running;
        final var acceptThread = new Thread(() -> {
            try {
                while (running[0]) {
                    final var info = new ClientInfo(TCP.INSTANCE.open(port), evs -> {
                        synchronized (this.evs) {
                            for (var e : evs)
                                this.evs.add(e);
                        }
                    });
                    connections.add(info);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, "ServerSystem.acceptThread");
        acceptThread.setDaemon(true);
        acceptThread.start();
    }

    @Override
    public void update() {
        d += time.delta();
        if (d > DELTA) {
            d -= DELTA;
            if (d > DELTA)
                throw new RuntimeException("BIG DELTA" + d / DELTA);
            connections.write(replication);
        }
        synchronized (evs) {
            for (var e : evs)
                e.accept(world, sceneProperties);
            evs.clear();
        }
        synchronized (commands) {
            for (var c : commands)
                c.run();
            commands.clear();
        }
    }

    public class ClientInfo extends ConnectionInfo {

        private static int ID = 1;
        private final int id;

        public ClientInfo(StreamableConnection s, Consumer<? super Iterable<? extends EntityEvent>> events) {
            super(s);
            id = ID++;
            try {
                out.writeInt(id);
                out.flush();
            } catch (IOException e) {
                throw new WrappedException(e);
            }
            initReadThream(events);
            synchronized (commands) {
                commands.add(() -> {
                    cliens.event(id);
                });
            }
        }

        @Override
        protected boolean getWriteRuleExistence(int owner, ReplicationType type) {
            return switch (type) {
                case PRIVATE, PRIVATE_OWNER -> owner == id;
                case PUBLIC, PUBLIC_OWNER -> true;
                default -> false;
            };
        }

        @Override
        protected boolean getWriteRuleChange(int owner, ReplicationType type) {
            return switch (type) {
                case PRIVATE -> owner == id;
                case PUBLIC -> true;
                case PUBLIC_OWNER -> owner != id;
                default -> false;
            };
        }

    }

}
