package bearmaps.proj2ab;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


public class KDTree implements PointSet {

    private class Node {
        Point point;
        Comparator<Point> pointComparator;
        Node left, right;
        boolean flag;

        Node(Point p, boolean flag) {
            point = p;
            this.flag = flag;
            if (flag) {
                pointComparator = xComparator;
            } else {
                pointComparator = yComparator;
            }

        }
    }

    Node root;

    public KDTree(List<Point> points) {
        for (Point x : points) {
            insert(x);
        }
    }

    @Override
    public Point nearest(double x, double y) {
        if (this.root == null) {
            throw new IllegalArgumentException("The KDTree is empty");
        }
        Point target = new Point(x, y);
        Node ret;
        double min = Point.distance(target, root.point);
        if (root.pointComparator.compare(target, root.point) > 0) {
            ret = nearest(root.right, target, root, min);
            min = Point.distance(ret.point, target);
            ret = nearest(root.left, target, ret, min);
        } else {
            ret = nearest(root.left, target, root, min);
            min = Point.distance(ret.point, target);
            ret = nearest(root.right, target, ret, min);
        }
        return ret.point;
    }

    private Node nearest(Node branch, Point t, Node best, double min) {
        if (branch != null) {
            double dist = Point.distance(t, branch.point);
            if (dist < min) {
                min = dist;
                best = branch;
            }
            double least;
            if (branch.flag) {
                least = t.getX() - branch.point.getX();
            } else {
                least = t.getY() - branch.point.getY();
            }
            if (least > 0) {
                best = nearest(branch.right, t, best, min);
                if (min < Point.distance(best.point, t)) {
                    System.out.println("Error! " + best);
                    System.exit(1);
                }
                min = Point.distance(best.point, t);
                if (Math.pow(least, 2) < min) {
                    best = nearest(branch.left, t, best, min);
                }
            } else {
                best = nearest(branch.left, t, best, min);
                if (min < Point.distance(best.point, t)) {
                    System.out.println("Error! " + best);
                    System.exit(1);
                }
                min = Point.distance(best.point, t);
                if (Math.pow(least, 2) < min) {
                    best = nearest(branch.right, t, best, min);
                }
            }
        }
        return best;
    }


    private void insert(Point p) {
        if (this.root == null) {
            root = new Node(p, true);
            return;
        }
        if (root.pointComparator.compare(root.point, p) > 0) {
            root.left = insert(root.left, p, true);
        } else {
            root.right = insert(root.right, p, true);
        }
    }

    private Node insert(Node n, Point p, boolean f) {
        if (n == null) {
            return new Node(p, !f);
        }
        if (n.pointComparator.compare(n.point, p) > 0) {
            n.left = insert(n.left, p, n.flag);
        } else {
            n.right =  insert(n.right, p, n.flag);
        }
        return n;
    }

    Comparator<Point> xComparator = (i, j) -> {
        double diff = i.getX() - j.getX();
        if (diff > 0) {
            return 1;
        } else {
            return -1;
        }
    };

    Comparator<Point> yComparator = (i, j) -> {
        double diff = i.getY() - j.getY();
        if (diff > 0) {
            return 1;
        } else {
            return -1;
        }
    };

    public static void main(String[] args) {
        Random rd = new Random(7);
        List<Point> p = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            p.add(new Point(rd.nextDouble(), rd.nextDouble()));
        }
        KDTree kdt = new KDTree(p);
        System.out.println(kdt.nearest(rd.nextDouble(),rd.nextDouble()));
    }
}
