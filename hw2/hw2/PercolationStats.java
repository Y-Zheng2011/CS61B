package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private Percolation p;
    private int size, times;
    private double mean, stddev, cL, cH;
    private int[] count;
    
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        size = N;
        times = T;
        count = new int[T];
        simulate();
    }

    private void simulate() {
        for (int i = 0; i < times; i++) {
            p = new Percolation(size);
            while (!p.percolates()) {
                p.open(StdRandom.uniform(size),StdRandom.uniform(size));
            }
            count[i] = p.numberOfOpenSites();
        }
        mean = StdStats.mean(count);
        stddev = StdStats.stddev(count);
        cL = mean - 1.96 * stddev / Math.sqrt(times);
        cH = mean +1.96 * stddev / Math.sqrt(times);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLow() {
        return cL;
    }

    public double confidenceHigh() {
        return cH;
    }
}
