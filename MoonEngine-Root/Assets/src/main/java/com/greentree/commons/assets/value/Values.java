package com.greentree.commons.assets.value;

import com.greentree.commons.assets.value.function.Value1Function;
import com.greentree.commons.assets.value.merge.Group2;
import com.greentree.commons.assets.value.merge.Group3;
import com.greentree.commons.assets.value.merge.Group4;
import com.greentree.commons.assets.value.merge.Group5;
import com.greentree.commons.assets.value.merge.Group6;
import com.greentree.commons.assets.value.merge.M2Value;
import com.greentree.commons.assets.value.merge.M3Value;
import com.greentree.commons.assets.value.merge.M4Value;
import com.greentree.commons.assets.value.merge.M5Value;
import com.greentree.commons.assets.value.merge.M6Value;
import com.greentree.commons.assets.value.merge.MIValue;

public class Values {
	
	private Values() {
	}
	
	public static <IN, OUT> Value<OUT> map(Value<IN> Value,
			Value1Function<? super IN, OUT> mapFunction) {
		return ValueFunctionMapValue.newValue(Value, mapFunction);
	}
	
	public static <T1, T2, OUT> Value<OUT> map(Value<T1> value1, Value<T2> value2,
			Value1Function<? super Group2<? extends T1, ? extends T2>, OUT> mapFunction) {
		final var m = merge(value1, value2);
		return ValueFunctionMapValue.newValue(m, mapFunction);
	}
	
	public static <T1, T2> Value<Group2<T1, T2>> merge(Value<T1> Value1, Value<T2> Value2) {
		return new M2Value<>(Value1, Value2);
	}
	
	public static <T1, T2, T3> Value<Group3<T1, T2, T3>> merge(Value<T1> Value1, Value<T2> Value2,
			Value<T3> Value3) {
		return new M3Value<>(Value1, Value2, Value3);
	}
	
	public static <T1, T2, T3, T4> Value<Group4<T1, T2, T3, T4>> merge(Value<T1> Value1,
			Value<T2> Value2, Value<T3> Value3, Value<T4> Value4) {
		return new M4Value<>(Value1, Value2, Value3, Value4);
	}
	
	public static <T1, T2, T3, T4, T5> Value<Group5<T1, T2, T3, T4, T5>> merge(Value<T1> Value1,
			Value<T2> Value2, Value<T3> Value3, Value<T4> Value4, Value<T5> Value5) {
		return new M5Value<>(Value1, Value2, Value3, Value4, Value5);
	}
	
	public static <T1, T2, T3, T4, T5, T6> Value<Group6<T1, T2, T3, T4, T5, T6>> merge(
			Value<T1> Value1, Value<T2> Value2, Value<T3> Value3, Value<T4> Value4,
			Value<T5> Value5, Value<T6> Value6) {
		return new M6Value<>(Value1, Value2, Value3, Value4, Value5, Value6);
	}
	
	public static <T> Value<Iterable<T>> merge(Iterable<? extends Value<? extends T>> Values) {
		return new MIValue<>(Values);
	}
	
	
	
}
