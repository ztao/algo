/**
 * Created by zt on 06/10/2017.
 */
package ztao._8puzzle;

import edu.princeton.cs.algs4.*;
import edu.princeton.cs.algs4.StdRandom;

public class Board{
    private int n;
    private int[][] goalBlock;
    private int[] currentBlocks;
    private int _hamming = 0;
    private int _manhattan = 0;
    private int empty_ptr;
    public Board previousBoard = null;

    public Board(int[][] blocks)            // construct a board from an n-by-n array of blocks
    {                                       // (where blocks[i][j] = block in row i, column j)
        n = blocks.length;
        currentBlocks = new int[n*n];
        int c = 1;
        int cur, loc;
        for (int i=0; i < n; i++)
            for (int j=0; j < n; j++) {

                cur = blocks[i][j];
                loc = i * n + j;
                if (cur == 0)
                    empty_ptr = loc;
                    currentBlocks[loc] = cur;
                if (loc != cur)
                    this._manhattan += abs(cur/n-i) + abs(cur%n-j);
                    this._hamming += 1;
            }
    }

    private Board(int[] blocks, int dim, int manhantan, int hamming, Board previousBoard) {
        n = dim;
        currentBlocks = blocks;
        this._manhattan = manhantan;
        this._hamming = hamming;
        this.previousBoard = previousBoard;
    }
    private int abs(int x)
    {
        if (x < 0) {
            return -x;
        } else {
            return x;
        }
    }
    private int deltManhattan(int x0, int y0, int x1, int y1, int x2, int y2) { // x0,y0 为该值最终坐标
        return (abs(x0-x2) + abs(y0-y2)) - (abs(x0-x1) + abs(y0-y1));
    }
    public int dimension()                 // board dimension n
    {
        return this.n;
    }
    public int hamming()                   // number of blocks out of place
    {
        return this._hamming;
    }
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        return this._manhattan;
    }
    public boolean isGoal()                // is this board the goal board?
    {
        return this.hamming() == 0;
    }
    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
        int x, y;
        switch (empty_ptr) {
            case 0:
            case 1:
                x = 2;
                y = 3;
                break;
            default:
                x = 0;
                y = 1;
        }
        int [][] twinBlocks = new int[n][n];
        int cur;
        for (int i=0; i<n; i++)
            for (int j=0; j<n; j++) {
                cur = i * n + j;
                if (cur == x)
                    cur = y;
                if (cur == y)
                    cur = x;
                twinBlocks[i][j] = currentBlocks[cur];
            }
        return new Board(twinBlocks);
    }
    @Override
    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == this) return true;
        if (y == null) return false;
        return this.hashCode() == y.hashCode();
    }
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        Queue<Integer> neighborsLocation = new Queue<Integer>();
        Queue<Board> neighborsQueue = new Queue<Board>();
        int x = empty_ptr / n;
        int y = empty_ptr % n;
        if (x != 0)
            neighborsLocation.enqueue((x-1)*n+y);
        if ( x != n-1)
            neighborsLocation.enqueue((x+1)*n+y);
        if (y != 0)
            neighborsLocation.enqueue(x*n+y-1);
        if ( y != n-1)
            neighborsLocation.enqueue(x*n+y+1);
        for (int neighbor : neighborsLocation) {
            int [] neighborBLocks = new int[currentBlocks.length];
            int cur, value, fX, fY, newManhanttan, newHamming;
            for (int i=0; i < n; i++)
                for (int j=0; j < n; j++) {
                    cur = i * n + j;
                    if (cur == neighbor)
                        neighborBLocks[cur] = 0;
                    else if (cur == empty_ptr) {
                        value = neighborBLocks[neighbor];
                        neighborBLocks[cur] = value;
                        fX = value / n;
                        fY = value % n;
                        newManhanttan = this.manhattan() + this.deltManhattan(fX, fY, i, j, neighbor / n, neighbor % n);
                        if (cur == value) { // 如果之前 hamming 距离相等，则距离必 +1
                            newHamming = hamming() + 1;
                        } else if(neighbor == value) {
                            newHamming = hamming() - 1; // 如果现在 hamming 距离相等，则距离必 -1
                        } else {
                            newHamming = hamming();
                        }
                        neighborsQueue.enqueue(new Board(neighborBLocks, n, newManhanttan, newHamming, this.previousBoard));
                    }
                }
        }
        return neighborsQueue;
    }
    @Override
    public String toString()               // string representation of this board (in the output format specified below)
    {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        int N = n*n;
        for (int i = 0; i < N; i++) {
            s.append(String.format("%2d ", currentBlocks[i]));
            if ((i+1)%n == 0)
                s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) // unit tests (not graded)
    {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        System.out.println(initial.toString());
    }
}
