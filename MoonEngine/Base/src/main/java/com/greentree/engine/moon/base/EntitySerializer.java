package com.greentree.engine.moon.base;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.greentree.commons.data.file.DataFileReader;
import com.greentree.commons.data.file.DataFileWriter;
import com.greentree.commons.data.file.serializer.AbstractSerializer;
import com.greentree.engine.moon.ecs.ClassSetEntity;
import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.ecs.component.Component;

public class EntitySerializer extends AbstractSerializer {

	public EntitySerializer() {
		super(Entity.class);
	}

	@Override
	public void write(Object obj, DataOutput out, DataFileWriter file) throws IOException {
		final var e = (Entity) obj;
		out.writeInt(e.size());
		for(var c : e)
			out.writeInt(file.append(c));
	}

	@Override
	public Object read(Class<?> cls, DataInput in, DataFileReader file) throws IOException {
		final var e = new ClassSetEntity();
		var t = in.readInt();
		try(final var lock = e.lock()) {
    		while(t-- > 0)
    			lock.add((Component) file.get(in.readInt()));
		}
		return e;
	}

}
