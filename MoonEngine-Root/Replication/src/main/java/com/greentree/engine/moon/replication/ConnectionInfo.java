package com.greentree.engine.moon.replication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.greentree.commons.data.file.DataSerialization;
import com.greentree.commons.util.exception.WrappedException;
import com.greentree.commons.web.protocol.ConnectionInputStream;
import com.greentree.commons.web.protocol.ConnectionOutputStream;
import com.greentree.commons.web.protocol.StreamableConnection;
import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.replication.event.AddComponentEvent;
import com.greentree.engine.moon.replication.event.AddEntityEvent;
import com.greentree.engine.moon.replication.event.ChangeComponentEvent;
import com.greentree.engine.moon.replication.event.ComponentEntityEvent;
import com.greentree.engine.moon.replication.event.ComponentEvent;
import com.greentree.engine.moon.replication.event.EntityEvent;
import com.greentree.engine.moon.replication.event.RemoveComponentEvent;
import com.greentree.engine.moon.replication.event.RemoveEntityEvent;
import com.greentree.engine.moon.replication.id.EntityID;
import com.greentree.engine.moon.replication.id.OwnerID;

public class ConnectionInfo implements AutoCloseable {

	private static final int COMPRESSED = 0;
	private static final int NOT_COMPRESSED = 1;

	private byte[] old_write;
	private byte[] old_read;

	public final DataOutputStream out;
	public final DataInputStream in;
	public final StreamableConnection connection;

	private ArrayList<Entity> old_es = new ArrayList<>();
	private boolean[] running;


	private static Object deser(byte[] obj) throws IOException, ClassNotFoundException {
		try(final var bin = new ByteArrayInputStream(obj)) {
			try(final var oin = new ObjectInputStream(bin)) {
				return oin.readObject();
			}
		}
	}
	
	public ConnectionInfo(StreamableConnection connection) {
		this.connection = connection;
		old_read = new byte[0];
		old_write = new byte[0];
		try {
			in = new DataInputStream(new ConnectionInputStream(connection));
			out = new DataOutputStream(new ConnectionOutputStream(connection));
			out.flush();
		}catch(IOException e) {
			throw new WrappedException(e);
		}
	}

	protected void initReadThream(Consumer<? super Iterable<? extends EntityEvent>> events) {
		final var running = new boolean[] { true };
		this.running = running;
		final var getThread = new Thread(() -> {
			try {
				while(running[0]) {
					final var bs = read();
					@SuppressWarnings("unchecked")
					final var evs = (Iterable<? extends EntityEvent>) deser(bs);
					events.accept(evs);
				}
			}catch (SocketException e) {
				if(!"Connection reset".equals(e.getMessage()))
					e.printStackTrace();
			}catch(Exception e) {
				e.printStackTrace();
			}
			close();
		}, "ConnectionInfo.read " + this);
		getThread.setDaemon(true);
		getThread.start();
	}

	private static Map<Integer, Entity> getIDs(Iterable<Entity> es) {
		final var res = new HashMap<Integer, Entity>();
		for(var e : es) {
			final var id = e.get(EntityID.class).id();
			res.put(id, e);
		}
		return res;
	}

	private static int getOwner(Entity e) {
		if(e.contains(OwnerID.class))
			return e.get(OwnerID.class).id();
		return 0;
	}

	@Override
	public final void close() {
		try {
			connection.close();
		}catch(IOException e) {
		}
		if(running != null)
			running[0] = false;
		running = null;
	}

	public final boolean isClosed() {
		try {
			return connection.isClosed();
		} catch(IOException e) {
			throw new WrappedException(e);
		}
	}


	public final byte[] read() throws IOException {
		final var type = in.readByte();
		return switch(type) {
			case NOT_COMPRESSED -> {
				final var len = in.readInt();
				final var bs = new byte[len];
				in.read(bs);
//				System.out.println("rc  "+ bs.length);
				old_read = bs;
				yield bs;
			}
			case COMPRESSED -> {
				final var len = in.readInt();
				var fs = new byte[len];
				in.read(fs);
//				System.out.println("rnc "+ fs.length);
				fs = DataSerialization.zeroDereduce(fs);
				final var bs = new byte[fs.length];
				for(var i = 0; i < fs.length; i++)
					bs[i] = (byte) (old_read[i] + fs[i]);
				old_read = bs;
				yield bs;
			}
			default ->
			throw new IllegalArgumentException("Unexpected value: " + type);
		};
	}
	public final void write(byte[] obj) throws IOException {
		if(obj.length == old_write.length) {
			var fs = new byte[obj.length];

			for(var i = 0; i < fs.length; i++)
				fs[i] = (byte) (obj[i] - old_write[i]);

			fs = DataSerialization.zeroReduce(fs);

			writeCompressed(fs);
		}else
			writeNotCompressed(obj);
		old_write = obj;
	}

	public final void write(Iterable<Entity> replication) {
		final var es = new ArrayList<Entity>();
		for(var r : replication) {
			final var rep = r.get(ReplicationComponent.class);
			final var e = new Entity();
			es.add(e);
			try(final var lock = e.lock()) {
				for(var cls : rep)
					if(r.contains(cls))
						lock.add(r.get(cls).copy());
			}
		}
		final var evs = getEvents(old_es, es);
		old_es = es;
		
		if(evs.isEmpty()) return;
		
		final byte[] bs;
		try {
			bs = ser(evs);
		}catch (IOException e) {
			throw new WrappedException(e);
		}
		try {
			write(bs);
		}catch(IOException e) {
			throw new WrappedException(e);
		}
	}

	private Collection<? extends EntityEvent> getEvents(Iterable<Entity> old_es, Iterable<Entity> es) {
		final var old_ids = getIDs(old_es);
		final var new_ids = getIDs(es);

		final var res = new ArrayList<EntityEvent>();

		for(var id : old_ids.keySet())
			if(!new_ids.containsKey(id))
				res.add(new RemoveEntityEvent(id));

		for(var id : new_ids.keySet())
			if(!old_ids.containsKey(id)) {
				final var ce = new ArrayList<ComponentEvent>();
				final var r = new_ids.get(id);
				final var owner = getOwner(r);
				final var rep = r.get(ReplicationComponent.class);
				for(var c : new_ids.get(id))
					if(getWriteRuleExistence(owner, rep.get(c.getClass())))
						ce.add(new AddComponentEvent(c));
				res.add(new AddEntityEvent(id));
				if(!ce.isEmpty())
					res.add(new ComponentEntityEvent(id, ce));
			}

		for(var id : new_ids.keySet()) {
			final var oe = old_ids.get(id);
			if(oe == null) continue;
			final var ne = new_ids.get(id);

			final var ce = new ArrayList<ComponentEvent>();

			final var owner = getOwner(ne);
			final var rep = ne.get(ReplicationComponent.class);
			
			for(var c : ne) {
				final var cls = c.getClass();
    				if(!oe.contains(cls)) {
    					if(getWriteRuleExistence(owner, rep.get(cls)))
    						ce.add(new AddComponentEvent(c));
    				}else {
    					if(getWriteRuleChange(owner, rep.get(cls))) {
        					final var oc = oe.get(cls);
        					if(!c.equals(oc))
        						ce.add(new ChangeComponentEvent(c));
    					}
    				}
			}
			
			for(var c : oe) {
				final var cls = c.getClass();
				if(getWriteRuleExistence(owner, rep.get(cls)) && !ne.contains(cls))
					ce.add(new RemoveComponentEvent(cls));
			}

			if(!ce.isEmpty())
				res.add(new ComponentEntityEvent(id, ce));
		}

		return res;
	}

	private byte[] ser(Object obj) throws IOException {
		try(final var bout = new ByteArrayOutputStream()) {
			try(final var oout = new ObjectOutputStream(bout)) {
				oout.writeObject(obj);
			}
			final var bs = bout.toByteArray();
			return bs;
		}
	}

	private void writeCompressed(byte[] obj) throws IOException {
//		System.out.println("wc  "+obj.length);
		out.writeByte(COMPRESSED);
		out.writeInt(obj.length);
		out.write(obj);
		out.flush();
	}

	private void writeNotCompressed(byte[] obj) throws IOException {
//		System.out.println("wnc "+obj.length);
		out.writeByte(NOT_COMPRESSED);
		out.writeInt(obj.length);
		out.write(obj);
		out.flush();
	}

	protected boolean getWriteRuleChange(int owner, ReplicationType type) {
		return type == ReplicationType.PUBLIC;
	}

	protected boolean getWriteRuleExistence(int owner, ReplicationType type) {
		return type == ReplicationType.PUBLIC;
	}

}
