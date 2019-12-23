package bearmaps;

import java.util.List;

public class NaivePointSet implements PointSet {

    List<Point> p;

    public NaivePointSet(List<Point> points) {
        p = points;
    }

    @Override
    public Point nearest(double x, double y) {
        Point ret = p.get(0);
        Point target = new Point(x, y);
        double min = Point.distance(ret, target);
        for (Point a : p) {
            if (min > Point.distance(a, target)) {
                ret = a;
                min = Point.distance(a, target);
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        Point p1 = new Point(1.1, 2.2);
        Point p2 = new Point(3.3, 4.4);
        Point p3 = new Point(-2.9, 4.2);

        NaivePointSet nn = new NaivePointSet(List.of(p1, p2, p3));
        Point ret = nn.nearest(3.0, 4.0);
        System.out.println(ret.getX()+" "+ret.getY());
    }
}
