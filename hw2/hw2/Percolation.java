package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Percolation {

    private WeightedQuickUnionUF uF;
    private boolean[] open;
    private int size, count, top, bottom;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        uF = new WeightedQuickUnionUF(N * N + 2);
        open = new boolean[N * N];
        Arrays.fill(open, false);
        size = N;
        count = 0;
        top = N * N;
        bottom = top + 1;
    }

    private int calcIndex(int row, int col) {
        return row * size + col;
    }

    private void validate(int x, int y) {
        if (x < 0 || x > size || y < 0 || y > size) {
            throw new IndexOutOfBoundsException();
        }
    }

    public void open(int row, int col) {
        validate(row, col);
        count++;
        int here = calcIndex(row, col), neighbor;
        open[here] = true;
        if (row == 0) {
            uF.union(here, top);
        } else if (row == size - 1) {
            uF.union(here, bottom);
        }
        if (row > 0 && isOpen(row - 1, col)) {
            neighbor = calcIndex(row - 1, col);
            uF.union(here, neighbor);
        }
        if (row < size - 1 && isOpen(row + 1, col)) {
            neighbor = calcIndex(row + 1, col);
            uF.union(here, neighbor);
        }
        if (col > 0 && isOpen(row, col - 1)) {
            neighbor = calcIndex(row, col - 1);
            uF.union(here, neighbor);
        }
        if (col < size - 1 && isOpen(row, col + 1)) {
            neighbor = calcIndex(row, col + 1);
            uF.union(here, neighbor);
        }
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        return open[calcIndex(row,col)];
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        return uF.connected(calcIndex(row, col), top);
    }

    public int numberOfOpenSites() {
        return count;
    }

    public boolean percolates() {
        return uF.connected(top, bottom);
    }

    public static void main(String[] args) {

    }
}
