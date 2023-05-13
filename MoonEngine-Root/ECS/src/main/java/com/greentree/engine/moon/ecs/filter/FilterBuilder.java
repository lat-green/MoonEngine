package com.greentree.engine.moon.ecs.filter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import com.greentree.engine.moon.ecs.Entity;
import com.greentree.engine.moon.ecs.World;
import com.greentree.engine.moon.ecs.component.Component;

public class FilterBuilder {
	
	private final List<Class<? extends Component>> required = new ArrayList<>(), ignore = new ArrayList<>();
	private final List<Comparator<? super Entity>> sort = new ArrayList<>();
	
	public FilterBuilder group(Class<? extends Component> cls) {
		return sort(e -> hash(e.get(cls)));
	}

	private static int hash(Object obj) {
		return obj == null ? 0 : obj.hashCode();
	}

	public <U extends Comparable<? super U>> FilterBuilder sort(Function<? super Entity, ? extends U> keyExtractor) {
		return sort(Comparator.comparing(keyExtractor));
	}
	
	public FilterBuilder sort(Comparator<? super Entity> comparator) {
		sort.add(comparator);
		return this;
	}
	
	public FilterBuilder ignore(Class<? extends Component> cls) {
		if(!ignore.contains(cls))
			ignore.add(cls);
		return this;
	}
	
	public FilterBuilder required(Class<? extends Component> cls) {
		if(!required.contains(cls))
			required.add(cls);
		return this;
	}
	
	public Filter build(World world) {
		var result = builder0(world);
		for(var s : sort)
			result = result.sort(s);
		return result;
	}
	
	private Filter builder0(World world) {
		if(ignore.isEmpty()) {
			if(required.isEmpty())
				return new AllEntityFilter(world);
			if(required.size() == 1)
				return new OneRequiredFilter(world, getFirst(required));
			if(required.size() == 2) {
				final var list = new ArrayList<>(required);
				return new TwoRequiredFilter(world, list.get(0), list.get(1));
			}
			return new OnlyRequiredFilter(world, required);
		}
		if(ignore.size() == 1) {
			if(required.isEmpty())
				return new OneIgnoreFilter(world, getFirst(ignore));
			if(required.size() == 1)
				return new OneRequiredOneIgnoreFilter(world, getFirst(required), getFirst(ignore));
			if(required.size() == 2) {
				final var list = new ArrayList<>(required);
				return new TwoRequiredOneIgnoreFilter(world, list.get(0), list.get(1), getFirst(ignore));
			}
//			return new MultiRequiredOneIgnoreFilter(world, required, getFirst(ignore));
		}
		if(ignore.size() == 2) {
			if(required.isEmpty()) {
    			final var list = new ArrayList<>(ignore);
    			return new TwoIgnoreFilter(world, list.get(0), list.get(1));
			}
//			if(required.size() == 1) {
//    			final var list = new ArrayList<>(ignore);
//				return new OneRequiredTwoIgnoreFilter(world, getFirst(required), list.get(0), list.get(1));
//			}
//			if(required.size() == 2) {
//    			final var ilist = new ArrayList<>(ignore);
//				final var rlist = new ArrayList<>(required);
//				return new TwoRequiredTwoIgnoreFilter(world, ilist.get(0), ilist.get(1), rlist.get(0), rlist.get(1));
//			}
//			final var list = new ArrayList<>(ignore);
//			return new MultiRequiredTwoIgnoreFilter(world, required, list.get(0), list.get(1));
		}
		{
    		if(required.isEmpty())
    			return new OnlyIgnoreFilter(world, ignore);
//    		if(required.size() == 1)
//    			return new OneRequiredMultiIgnoreFilter(world, getFirst(required), getFirst(ignore));
//    		if(required.size() == 2) {
//    			final var list = new ArrayList<>(required);
//    			return new TwoRequiredMultiIgnoreFilter(world, list.get(0), list.get(1), getFirst(ignore));
//    		}
    		return new MultiRequiredMultiIgnoreFilter(world, required, ignore);
		}
	}

	private <T> T getFirst(List<? extends T> list) {
		return list.get(0);
	}
	
}