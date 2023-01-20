package com.greentree.common.ecs.system;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.greentree.common.ecs.IterAssertions;
import com.greentree.common.ecs.annotation.AnnotationUtil;
import com.greentree.commons.util.iterator.IteratorUtil;

public class SortSystemTest {
	
	@Test
	void getInitCreate() {
		final var system = new ASystem();
		IterAssertions.assertEqualsAsSet(AnnotationUtil.getInitCreate(system), IteratorUtil.iterable(TestComponent1.class));
		IterAssertions.assertEqualsAsSet(AnnotationUtil.getInitRead(system), IteratorUtil.iterable());
		IterAssertions.assertEqualsAsSet(AnnotationUtil.getInitWrite(system), IteratorUtil.iterable());
		IterAssertions.assertEqualsAsSet(AnnotationUtil.getInitDestroy(system), IteratorUtil.iterable());
	}
	
	@Test
	void getInitRead() {
		final var system = new BSystem();
		IterAssertions.assertEqualsAsSet(AnnotationUtil.getInitCreate(system), IteratorUtil.iterable());
		IterAssertions.assertEqualsAsSet(AnnotationUtil.getInitWrite(system), IteratorUtil.iterable(TestComponent1.class));
		IterAssertions.assertEqualsAsSet(AnnotationUtil.getInitRead(system), IteratorUtil.iterable());
		IterAssertions.assertEqualsAsSet(AnnotationUtil.getInitDestroy(system), IteratorUtil.iterable());
	}
	
	@Test
	void getInitWrite() {
		final var system = new CSystem();
		IterAssertions.assertEqualsAsSet(AnnotationUtil.getInitCreate(system), IteratorUtil.iterable());
		IterAssertions.assertEqualsAsSet(AnnotationUtil.getInitWrite(system), IteratorUtil.iterable());
		IterAssertions.assertEqualsAsSet(AnnotationUtil.getInitRead(system), IteratorUtil.iterable(TestComponent1.class));
		IterAssertions.assertEqualsAsSet(AnnotationUtil.getInitDestroy(system), IteratorUtil.iterable());
	}
	
	@Test
	void getInitDestroy() {
		final var system = new DSystem();
		IterAssertions.assertEqualsAsSet(AnnotationUtil.getInitCreate(system), IteratorUtil.iterable());
		IterAssertions.assertEqualsAsSet(AnnotationUtil.getInitRead(system), IteratorUtil.iterable());
		IterAssertions.assertEqualsAsSet(AnnotationUtil.getInitWrite(system), IteratorUtil.iterable());
		IterAssertions.assertEqualsAsSet(AnnotationUtil.getInitDestroy(system), IteratorUtil.iterable(TestComponent1.class));
	}
	
	@Test
	void getInit_empty() {
		final var system = new ESystem();
		IterAssertions.assertEqualsAsSet(AnnotationUtil.getInitCreate(system), IteratorUtil.iterable());
		IterAssertions.assertEqualsAsSet(AnnotationUtil.getInitRead(system), IteratorUtil.iterable());
		IterAssertions.assertEqualsAsSet(AnnotationUtil.getInitWrite(system), IteratorUtil.iterable());
		IterAssertions.assertEqualsAsSet(AnnotationUtil.getInitDestroy(system), IteratorUtil.iterable());
	}
	
	@Test
	void test1() {
		final var a = new ASystem();
		final var b = new BSystem();
		final var c = new CSystem();
		final var d = new DSystem();
		
		final var list = new ArrayList<ECSSystem>();
		
		list.add(a);
		list.add(c);
		list.add(b);
		list.add(d);
		
		AnnotationUtil.sort(list, AnnotationUtil.INIT);
		
		IterAssertions.assertEqualsAsList(list, List.of(a, b, c, d));
	}
	
}
