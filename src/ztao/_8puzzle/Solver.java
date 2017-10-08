/**
 * Created by zt on 06/10/2017.
 */
package ztao._8puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;


public class Solver {

    private MinPQ<Board> openSet = new MinPQ<>();
    private MinPQ<Board> twinOpenSet = new MinPQ<>();
    private boolean solvable;
    private Board b, tb;
    private int moves = 0;

    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        if (initial == null)
            throw new IllegalArgumentException();
        Board twin = initial.twin();
        int dim = initial.dimension();
        openSet.insert(initial);
        twinOpenSet.insert(initial);
        while (true) {
            b = openSet.delMin();
            tb = twinOpenSet.delMin();
            if(b.isGoal()){
                this.solvable = true;
                break;
            }
            if(tb.isGoal()) {
                this.solvable = false;
                break;
            }
            for (Board neighbor : b.neighbors()) {
                System.out.println(neighbor);
                if (neighbor.equals(b.previousBoard))
                    continue;
                openSet.insert(neighbor);
            }
            for (Board neighbor : tb.neighbors()) {
                if (neighbor.equals(tb.previousBoard))
                    continue;
                twinOpenSet.insert(neighbor);
            }
            moves++;
        }
    }

    public boolean isSolvable()            // is the initial board solvable?
    {
        return solvable;
    }
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        return moves;
    }
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        if (this.isSolvable()) {
            Stack<Board> solutionBoards = new Stack<>();
            Board sb = b;
            while (sb.previousBoard != null) {
                solutionBoards.push(sb);
                sb = sb.previousBoard;
            }
            return solutionBoards;
        } else {
            return null;
        }
    }

    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
//        System.out.println(initial);
        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            System.out.println("No solution possible");
        else {
            System.out.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                System.out.println(board);
        }
    }
}
