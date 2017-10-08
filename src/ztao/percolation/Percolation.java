/*
 * Created by zt on 1/1/17.
 * Course work 1
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


import java.lang.Math;

public class Percolation {

    private boolean[] sites;
    private int side;
    private WeightedQuickUnionUF perc_uf, full_uf;
    private int head;
    private int tail;
    private int numberOfOpenSites;

    public Percolation(int n)                // create n-by-n grid, with all sites blocked
    {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.head = 0;
        this.tail = n*n + 1;
        this.perc_uf = new WeightedQuickUnionUF(n*n+2);
        this.full_uf = new WeightedQuickUnionUF(n*n+1);
        this.sites = new boolean[n*n+2];
        this.sites[this.head] = true;
        this.sites[this.tail] = true;
        this.side = n;
    }

    public void open(int row, int col)    // open site (row, col) if it is not open already
    {
        // StdOut.println("(" + row + ", " + col + ")");
        if (row < 1 || row > this.side || col < 1 || col > this.side) {
            throw new IndexOutOfBoundsException();
        }
        if (!this.isOpen(row, col)) {
            row -= 1;
            int s = row * this.side + col;
            if (!this.sites[s]) {
                numberOfOpenSites++;
                this.sites[s] = true;
                int upper = s - side;
                upper = upper >= this.head ? upper : this.head;
                int lower = s + side;
                lower = lower <= this.tail ? lower : this.tail;
                int left = s - 1;
                int right = s + 1;
                if (this.sites[upper]) {
                    this.full_uf.union(s, upper);
                    this.perc_uf.union(s, upper);
                }
                if (this.sites[lower]) {
                    if (lower != this.tail) {
                        this.full_uf.union(lower, s);
                        this.perc_uf.union(lower, s);
                    } else {
                        this.perc_uf.union(lower, s);
                    }
                }
                if (col != 1 && this.sites[left]) {
                    this.full_uf.union(s, left);
                    this.perc_uf.union(s, left);
                }
                if (col != side && this.sites[right]) {
                    this.full_uf.union(right, s);
                    this.perc_uf.union(right, s);
                }
            }
        }
    }
    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        if (row < 1 || row > this.side || col < 1 || col > this.side) {
            throw new IndexOutOfBoundsException();
        }
        row -= 1;
        return this.sites[row * this.side + col ];
    }
    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        if (row < 1 || row > this.side || col < 1 || col > this.side) {
            throw new IndexOutOfBoundsException();
        }
        int s = (row - 1) * this.side + col;
        return this.full_uf.connected(this.head, s);
    }

    public int numberOfOpenSites()       // number of open sites
    {
        return numberOfOpenSites;
    }

    public boolean percolates()              // does the system percolate?
    {
        return this.perc_uf.connected(this.head, this.tail);
    }

    public static void main(String[] args)   // test client (optional)
    {
        int n = StdIn.readInt();
        Percolation percolation = new Percolation(n);
        while (!StdIn.isEmpty()){
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            percolation.open(p,q);
        }
    }
}
