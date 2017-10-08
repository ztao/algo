/**
 * Created by zt on 1/2/17.
 * Course work 1
 */
import java.lang.Math;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private int trials;
    private double mean;
    private double stddev;
    public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
    {
        if (n<=0 || trials <=0) {
            throw new IllegalArgumentException();
        }
        this.trials = trials;
        double[] trialsTimes = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while(! percolation.percolates()) {
                int r = StdRandom.uniform(n*n);
                // StdOut.println("r = " + r);
                int p = r / n + 1;
                int q = r % n + 1;
                percolation.open(p,q);
            }
            trialsTimes[i] = ((double)percolation.numberOfOpenSites())/n/n;
            // StdOut.println("No." + i + " Trails = " + trialsTimes[i]);
        }
        double totalTrialsTimes = 0.0;
        for (int i = 0; i < this.trials; i++) {
            totalTrialsTimes += trialsTimes[i];
        }
        // StdOut.println("totalTrialsTimes = " + totalTrialsTimes);
        this.mean = totalTrialsTimes / this.trials;
        // StdOut.println("mean = " + mean);
        double S = 0.0;
        for (int i = 0; i < this.trials; i++) {
            S += Math.pow(trialsTimes[i] - this.mean, 2);
        }
        S /= this.trials - 1;
        this.stddev = Math.sqrt(S);
    }

    public double mean()                          // sample mean of percolation threshold
    {
        return this.mean;
    }

    public double stddev()                        // sample standard deviation of percolation threshold
    {
        return this.stddev;
    }

    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        return this.mean - (1.96 * this.stddev / Math.sqrt(this.trials));
    }

    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        return this.mean + (1.96 * this.stddev / Math.sqrt(this.trials));
    }

    public static void main(String[] args)        // test client (described below)
    {
        // int n = StdIn.readInt();
        // int trials = StdIn.readInt();
        int[] N = {200, 200, 2, 2};
        int[] TRIALS = {100, 100, 10000, 100000};
        for (int i = 0; i < 4; i++) {
            PercolationStats stats = new PercolationStats(N[i], TRIALS[i]);
            StdOut.println("mean = " + stats.mean());
            StdOut.println("stddev = " + stats.stddev());
            StdOut.println("95% confidence interval = " + stats.confidenceLo() + ", " + stats.confidenceHi());
        }
    }
}
