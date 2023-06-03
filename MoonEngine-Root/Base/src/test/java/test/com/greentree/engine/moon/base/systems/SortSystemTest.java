package test.com.greentree.engine.moon.base.systems;

import com.greentree.engine.moon.base.component.info.AnnotatedCWRDMethodComponentInfo;
import com.greentree.engine.moon.base.sorter.MergeSorter;
import com.greentree.engine.moon.base.sorter.OnCWRDMethodSorter;
import com.greentree.engine.moon.ecs.system.ECSSystem;
import org.junit.jupiter.api.Test;
import test.com.greentree.engine.moon.base.IterAssertions;

import java.util.ArrayList;
import java.util.List;

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
        var sorter = new MergeSorter(new OnCWRDMethodSorter(new AnnotatedCWRDMethodComponentInfo()));
        sorter.sort(list, "init");
        IterAssertions.assertEqualsAsList(list, List.of(a, b, c, d));
    }

    @Test
    void ASystem_getCreate() {
        var info = new AnnotatedCWRDMethodComponentInfo();
        var creates = info.getCreate(new ASystem(), "init");
        IterAssertions.assertEqualsAsList(creates.toList(), List.of(TestComponent1.class));
    }

    @Test
    void DSystem_getDestroy() {
        var info = new AnnotatedCWRDMethodComponentInfo();
        var creates = info.getDestroy(new DSystem(), "init");
        IterAssertions.assertEqualsAsList(creates.toList(), List.of(TestComponent1.class));
    }

}
