package com.greentree.engine.moon.assets.asset

inline fun <T1, T2> group(t1: T1, t2: T2) = Group2(t1, t2)
inline fun <T1, T2, T3> group(t1: T1, t2: T2, t3: T3) = Group3(t1, t2, t3)
inline fun <T1, T2, T3, T4> group(t1: T1, t2: T2, t3: T3, t4: T4) = Group4(t1, t2, t3, t4)
inline fun <T1, T2, T3, T4, T5> group(t1: T1, t2: T2, t3: T3, t4: T4, t5: T5) = Group5(t1, t2, t3, t4, t5)
inline fun <T1, T2, T3, T4, T5, T6> group(t1: T1, t2: T2, t3: T3, t4: T4, t5: T5, t6: T6) =
	Group6(t1, t2, t3, t4, t5, t6)

data class Group2<T1, T2>(@JvmField val t1: T1, @JvmField val t2: T2)

data class Group3<T1, T2, T3>(@JvmField val t1: T1, @JvmField val t2: T2, @JvmField val t3: T3)

data class Group4<T1, T2, T3, T4>(
	@JvmField val t1: T1,
	@JvmField val t2: T2,
	@JvmField val t3: T3,
	@JvmField val t4: T4,
)

data class Group5<T1, T2, T3, T4, T5>(
	@JvmField val t1: T1,
	@JvmField val t2: T2,
	@JvmField val t3: T3,
	@JvmField val t4: T4,
	@JvmField val t5: T5,
)

data class Group6<T1, T2, T3, T4, T5, T6>(
	@JvmField val t1: T1,
	@JvmField val t2: T2,
	@JvmField val t3: T3,
	@JvmField val t4: T4,
	@JvmField val t5: T5,
	@JvmField val t6: T6,
)
