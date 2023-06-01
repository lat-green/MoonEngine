package test.com.greentree.engine.moon.ecs;

import com.greentree.engine.moon.ecs.component.Component;

import java.util.Objects;

public final class ACompnent implements Component {

    private static final long serialVersionUID = 1L;

    public int value;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if ((obj == null) || (getClass() != obj.getClass())) return false;
        ACompnent other = (ACompnent) obj;
        return value == other.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "ACompnent [" + value + "]";
    }

    @Override
    public ACompnent copy() {
        final var a = new ACompnent();
        a.value = value;
        return a;
    }

    @Override
    public boolean copyTo(Component other) {
        final var a = (ACompnent) other;
        a.value = value;
        return true;
    }
//	@Override
//	public void writeExternal(ObjectOutput out) throws IOException {
//		out.writeInt(value);
//	}
//
//	@Override
//	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
//		value = in.readInt();
//	}

}
