import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solver {
    private final Board beginning;
    private final Iterable<Board> solution;
    private final int width;
    private boolean solvable = true;
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("Board is null");
        beginning = initial;
        width = beginning.dimension();
        solution = aStarSolver();
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;
        int size = -1;
        for (Board i : solution) {
            size++;
        }
        return size;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    private Iterable<Board> aStarSolver() {
        if (!isSolvable()) return null;

        MinPQ<Node> openSet = new MinPQ<>();
        List<List<Board>> closedSet = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            closedSet.add(i, new ArrayList<>());
        Node start = new Node(beginning, null, 0, beginning.manhattan());
        openSet.insert(start);

        MinPQ<Node> openSetTwin = new MinPQ<>();
        List<List<Board>> closedSetTwin = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            closedSetTwin.add(i, new ArrayList<>());
        Board beginningTwin = beginning.twin();
        Node startTwin = new Node(beginningTwin, null, 0, beginningTwin.manhattan());
        openSetTwin.insert(startTwin);

        while (true) {
            Node currentNode = openSet.delMin();
            closedSet.get(currentNode.g % 10).add(currentNode.state);

            if (currentNode.state.isGoal()) {
                List<Board> path = new ArrayList<>();
                while (currentNode.parent != null) {
                    path.add(currentNode.state);
                    currentNode = currentNode.parent;
                }
                path.add(currentNode.state);
                Collections.reverse(path);

                return path;
            }

            Iterable<Board> neighbors = currentNode.state.neighbors();
            for (Board childState : neighbors) {
                if (currentNode.parent == null || currentNode.parent.state != childState) {
                    if (!closedSet.get(childState.manhattan()%10).contains(childState)) {
                        int g = currentNode.g + 1;
                        int h = childState.manhattan();
                        Node childNode = new Node(childState, currentNode, g, h);
                        openSet.insert(childNode);
                        //System.out.println(openSet.size());
                        //System.out.println(closedSet.size());
                    }
                }
            }

            Node currentNodeTwin = openSetTwin.delMin();
            closedSetTwin.get(currentNodeTwin.g%10).add(currentNodeTwin.state);

            if (currentNodeTwin.state.isGoal()) {
                solvable = false;
                return null;
            }

            Iterable<Board> neighborsTwin = currentNodeTwin.state.neighbors();
            for (Board childState : neighborsTwin) {
                if (currentNodeTwin.parent == null || currentNodeTwin.parent.state != childState) {
                    if (!closedSetTwin.get(childState.manhattan()%10).contains(childState)) {
                        int g = currentNodeTwin.g + 1;
                        int h = childState.manhattan();
                        Node childNode = new Node(childState, currentNodeTwin, g, h);
                        openSetTwin.insert(childNode);
                    }
                }
            }
        }
    }

    // test client (see below)
    public static void main(String[] args) {
        int[][] tiles  = new int[][]{{7, 8, 5}, {4, 0, 2}, {3, 6, 1}};
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board.toString());
                //StdOut.println(board.getCalls());
            }
        }
    }

    private static class Node implements Comparable<Node> {
        Board state;
        Node parent;
        int g;
        int h;
        int f;

        public Node(Board state, Node parent, int g, int h) {
            this.state = state;
            this.parent = parent;
            this.g = g;
            this.h = h;
            f = g + h;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(f, other.f);
        }
    }
}


