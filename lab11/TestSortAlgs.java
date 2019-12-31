import edu.princeton.cs.algs4.Queue;

import edu.princeton.cs.algs4.Stopwatch;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

import java.util.Random;

public class TestSortAlgs {

    @Test
    public void testQuickSort() {
        Queue<Integer> test = new Queue<>();
        Random r = new Random();
        int ch;
        for (int i = 0; i < 100000; i++) {
            ch = r.nextInt(128);
            test.enqueue(ch);
        }
        Stopwatch sw = new Stopwatch();
        QuickSort.quickSort(test);
        System.out.println(sw.elapsedTime());
        int c1 = test.dequeue();
        int c2 = test.peek();
        while (!test.isEmpty()) {
            assertTrue(c1 <= c2);
            c1 = c2;
            c2 = test.dequeue();
        }
    }

    @Test
    public void testMergeSort() {
        Queue<Integer> test = new Queue<>();
        Random r = new Random();
        int ch;
        for (int i = 0; i < 100000; i++) {
            ch = r.nextInt(128);
            test.enqueue(ch);
        }
        Stopwatch sw = new Stopwatch();
        MergeSort.mergeSort(test);
        System.out.println(sw.elapsedTime());
        int c1 = test.dequeue();
        int c2 = test.peek();
        while (!test.isEmpty()) {
            assertTrue(c1 <= c2);
            c1 = c2;
            c2 = test.dequeue();
        }
    }

    /**
     * Returns whether a Queue is sorted or not.
     *
     * @param items  A Queue of items
     * @return       true/false - whether "items" is sorted
     */
    private <Item extends Comparable> boolean isSorted(Queue<Item> items) {
        if (items.size() <= 1) {
            return true;
        }
        Item curr = items.dequeue();
        Item prev = curr;
        while (!items.isEmpty()) {
            prev = curr;
            curr = items.dequeue();
            if (curr.compareTo(prev) < 0) {
                return false;
            }
        }
        return true;
    }
}
