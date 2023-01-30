package com.greentree.commons.assets.serializator.context;

import com.greentree.commons.assets.serializator.manager.AssetManagerBase;
import com.greentree.commons.assets.serializator.manager.ParallelAssetManger;
import com.greentree.commons.assets.value.Value;
import com.greentree.commons.assets.value.Values;
import com.greentree.commons.assets.value.function.Value1Function;
import com.greentree.commons.assets.value.merge.Group2;
import com.greentree.commons.assets.value.merge.Group3;
import com.greentree.commons.assets.value.merge.Group4;
import com.greentree.commons.assets.value.merge.Group5;
import com.greentree.commons.assets.value.merge.Group6;
import com.greentree.commons.util.iterator.IteratorUtil;

public interface LoadContext extends DefaultLoadContext, AssetManagerBase {
	
	default <T, R> Value<R> map(Value<T> Value, Value1Function<? super T, R> mapFunction) {
		return Values.map(Value, mapFunction);
	}
	
	default <T1, T2, R> Value<R> map(Value<T1> Value1, Value<T2> Value2,
			Value1Function<? super Group2<? extends T1, ? extends T2>, R> mapFunction) {
		final var m = Values.merge(Value1, Value2);
		return map(m, mapFunction);
	}
	
	default <T1, T2, T3, R> Value<R> map(Value<T1> Value1, Value<T2> Value2, Value<T3> Value3,
			Value1Function<? super Group3<? extends T1, ? extends T2, ? extends T3>, R> mapFunction) {
		final var m = Values.merge(Value1, Value2, Value3);
		return map(m, mapFunction);
	}
	
	default <T1, T2, T3, T4, R> Value<R> map(Value<T1> Value1, Value<T2> Value2, Value<T3> Value3,
			Value<T4> Value4,
			Value1Function<? super Group4<? extends T1, ? extends T2, ? extends T3, ? extends T4>, R> mapFunction) {
		final var m = Values.merge(Value1, Value2, Value3, Value4);
		return map(m, mapFunction);
	}
	
	default <T1, T2, T3, T4, T5, R> Value<R> map(Value<T1> Value1, Value<T2> Value2,
			Value<T3> Value3, Value<T4> Value4, Value<T5> Value5,
			Value1Function<? super Group5<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5>, R> mapFunction) {
		final var m = Values.merge(Value1, Value2, Value3, Value4, Value5);
		return map(m, mapFunction);
	}
	
	default <T1, T2, T3, T4, T5, T6, R> Value<R> map(Value<T1> Value1, Value<T2> Value2,
			Value<T3> Value3, Value<T4> Value4, Value<T5> Value5, Value<T6> Value6,
			Value1Function<? super Group6<? extends T1, ? extends T2, ? extends T3, ? extends T4, ? extends T5, ? extends T6>, R> mapFunction) {
		final var m = Values.merge(Value1, Value2, Value3, Value4, Value5, Value6);
		return map(m, mapFunction);
	}
	
	default <T> Value<? extends Iterable<T>> merge(Iterable<? extends Value<? extends T>> values) {
		return Values.merge(values);
	}
	
	@SuppressWarnings("unchecked")
	default <T> Value<? extends Iterable<T>> merge(Value<? extends T>... Values) {
		return merge(IteratorUtil.iterable(Values));
	}
	
	ParallelAssetManger parallel();
	
}
