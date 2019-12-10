import java.util.Arrays;

public class UnionFind {

    // TODO - Add instance variables?
    int[] ds;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        // TODO
        ds = new int[n];
        Arrays.fill(ds,-1);
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        // TODO
        if (vertex >= ds.length) {
            throw new IllegalArgumentException("The vertex is not valid");
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        // TODO
        validate(v1);
        while (v1>0){
            v1 = ds[v1];
        }
        return -v1;
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        // TODO
        validate(v1);
        return ds[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        // TODO
        return find(v1)==find(v2);
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        // TODO
        //if (!connected(v1, v2)) {
            if (sizeOf(v1) < sizeOf(v2)) {
                connect(v1, v2);
            } else {
                connect(v2, v1);
            }
        //}
    }

    private void connect(int v1, int v2) {
        int o1 = v1, o2 = v2;
        v1 = find(v1);
        v2 = find(v2);
        if (v1 == v2) return;
        if (v1 != o1) {
            ds[o1] = v1;
        }
        if (v2 != o2) {
            ds[o2] = v2;
        }
        ds[v2] += ds[v1];
        ds[v1] = v2;
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        // TODO
        int o1 = vertex;
        while (ds[vertex] > 0) {
            vertex = parent(vertex);
        }
        if (vertex != o1){
            ds[o1] = vertex;
            return vertex;
        }
        return o1;
    }

    public void print() {
        for (int i = 0; i < ds.length; i++) {
            System.out.printf("%d : %d, ", i, ds[i]);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        UnionFind uF = new UnionFind(12);
        uF.connect(0, 1);
        uF.connect(0, 3);
        uF.connect(9, 7);
        uF.connect(9, 10);
        uF.connect(10, 11);
        uF.print();
        System.out.println(uF.connected(0,1));
    }

}
