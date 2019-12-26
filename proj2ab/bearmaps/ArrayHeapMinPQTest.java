package bearmaps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ArrayHeapMinPQTest {

    @Test
    public void testGetRemoveSmallest() {
        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        test.add(16, 1);
        test.add(18, 1);
        test.add(8, 5);
        test.add(289, 10);
        test.add(34, 5);
        assertEquals(Integer.valueOf(16), test.getSmallest());
        assertEquals(Integer.valueOf(16), test.removeSmallest());
        assertEquals(Integer.valueOf(18), test.getSmallest());
    }

    @Test
    public void testResize() {
        ArrayHeapMinPQ<Integer> test = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 60; i++) {
            test.add(i, i);
        }
        assertEquals(60, test.size());
        for (int i = 0; i < 50; i++) {
            test.removeSmallest();
        }
        assertEquals(10, test.size());
    }

    @Test
    public void testChangePriority() {
        ArrayHeapMinPQ<String> test = new ArrayHeapMinPQ<>();
        test.add("a", 1);
        test.add("b", 2);
        test.add("c", 3);
        test.add("d", 1.5);
        test.changePriority("d", 0.5);
        assertEquals("d", test.removeSmallest());
    }

}
