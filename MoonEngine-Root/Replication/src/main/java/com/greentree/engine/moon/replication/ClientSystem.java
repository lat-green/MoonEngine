package com.greentree.engine.moon.replication;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

import com.greentree.commons.util.exception.WrappedException;
import com.greentree.commons.web.protocol.StreamableConnection;
import com.greentree.commons.web.protocol.tcp.TCP;
import com.greentree.engine.moon.base.systems.CreateWorldComponent;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.filter.Filter;
import com.greentree.engine.moon.ecs.filter.FilterBuilder;
import com.greentree.engine.moon.ecs.system.DestroySystem;
import com.greentree.engine.moon.ecs.system.InitSystem;
import com.greentree.engine.moon.ecs.system.UpdateSystem;
import com.greentree.engine.moon.replication.event.EntityEvent;
import com.greentree.engine.moon.replication.id.OwnerID;
import com.greentree.engine.moon.replication.id.OwnerIDWorldComponent;

public class ClientSystem implements InitSystem, UpdateSystem, DestroySystem {
	
	
	public class SeverInfo extends ConnectionInfo {
		
		private final int id;
		
		public SeverInfo(StreamableConnection connection, Consumer<? super Iterable<? extends EntityEvent>> events) {
			super(connection);
			try {
				this.id = in.readInt();
			}catch(IOException e) {
				throw new WrappedException(e);
			}
			initReadThream(events);
		}
		
		@Override
		protected boolean getWriteRuleChange(int owner, ReplicationType type) {
			return switch(type) {
				case PUBLIC_OWNER -> true;
				case PRIVATE_OWNER -> true;
				default -> false;
			};
		}
		
		@Override
		protected boolean getWriteRuleExistence(int owner, ReplicationType type) {
			return false;
		}
		
	}
	
	private static final FilterBuilder REPLICATION = new FilterBuilder().required(ReplicationComponent.class).required(OwnerID.class);
	private final InetAddress address;
	private Filter replication;
	private final int port;
	private SeverInfo connection;
	private World world;
	private final Collection<EntityEvent> evs = new ArrayList<>();
	
	public ClientSystem(InetAddress address, int port) {
		this.address = address;
		this.port = port;
	}
	
	@Override
	public void destroy() {
		world = null;
		replication.close();
		replication = null;
		evs.clear();
	}
	
	@CreateWorldComponent({OwnerIDWorldComponent.class})
	@Override
	public void init(World world) {
		replication = REPLICATION.build(world);
		this.world = world;
		try {
			connection = new SeverInfo(TCP.INSTANCE.open(address, port), evs-> {
				synchronized(this.evs) {
					for(var e : evs)
						this.evs.add(e);
				}
			});
			world.add(new OwnerIDWorldComponent(connection.id));
		}catch(IOException e) {
			throw new WrappedException(e);
		}
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
	
	@Override
	public void update() {
		synchronized(evs) {
			for(var ee : evs)
				ee.accept(world);
			evs.clear();
		}
		connection.write(replication);
	}
	
}
