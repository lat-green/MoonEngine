package com.greentree.common.ecs.system;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.greentree.common.ecs.IterAssertions;
import com.greentree.engine.moon.ecs.annotation.AnnotationUtil;
import com.greentree.engine.moon.ecs.system.ECSSystem;

public class SortSystemTest {
	
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
