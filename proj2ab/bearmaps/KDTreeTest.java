package bearmaps;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.SortedSet;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {

    @Test
    public void simpleTest() {
        Point p1 = new Point(2, 3);
        Point p2 = new Point(4, 2);
        Point p3 = new Point(4, 5);
        Point p4 = new Point(3, 3);
        Point p5 = new Point(1, 5);
        Point p6 = new Point(4, 4);

        KDTree nn = new KDTree(List.of(p1, p2, p3, p4, p5, p6));
        assertEquals(p5, nn.nearest(0, 7));
    }

    @Test
    public void testKDTree() {
        Random rd = new Random(7);
        List<Point> p = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            p.add(new Point(rd.nextDouble(), rd.nextDouble()));
        }
        NaivePointSet nps = new NaivePointSet(p);
        KDTree kdt = new KDTree(p);
        for (int i = 0; i < 100; i++) {
            Point target = new Point(rd.nextDouble(), rd.nextDouble());
            assertEquals(nps.nearest(target.getX(),target.getY()), kdt.nearest(target.getX(),target.getY()));
        }
    }

    @Test
    public void testTime() {
        Random rd = new Random(7);
        List<Point> p = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            p.add(new Point(rd.nextDouble(), rd.nextDouble()));
        }
        NaivePointSet nps = new NaivePointSet(p);
        KDTree kdt = new KDTree(p);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            Point target = new Point(rd.nextDouble(), rd.nextDouble());
            nps.nearest(target.getX(),target.getY());
        }
        long end = System.currentTimeMillis();
        long time = end - start;
        System.out.println("NaivePointSet takes " + time + " ms to finish the task");
        start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            Point target = new Point(rd.nextDouble(), rd.nextDouble());
            kdt.nearest(target.getX(),target.getY());
        }
        end = System.currentTimeMillis();
        time = end - start;
        System.out.println("KDTree takes " + time + " ms to finish the task");
    }
}
