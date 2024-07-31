import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Solver {
    Board beginning;
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        beginning = initial;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        int[] flattendPuzzle = new int[beginning.width*beginning.width-1];
        int inversions = 0;
        for (int i = 0; i < flattendPuzzle.length; i++) {
            if (flattendPuzzle[i] != 0) {
                for (int j = i + 1; j < flattendPuzzle.length; i++) {
                    if (flattendPuzzle[i] > flattendPuzzle[j] && flattendPuzzle[j] != 0) {
                        inversions++;
                    }
                }
            }
        }
        return inversions % 2 == 0;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (!isSolvable()) return -1;

    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        PriorityQueue<Object[]> open_set = new PriorityQueue<>();
        List<Object> closed_set = new ArrayList<>();
        Node start = new Node(beginning, false, 0, beginning.manhattan());
        open_set.add(new Object[]{start.f, start});
        while (open_set) {
            Object currentObject = open_set.poll()[1];
            if (currentObject.getClass() != Node.class) {
                throw new IllegalArgumentException("Illegals");
            }
            Node currentNode = (Node) currentObject;
            closed_set.add(currentNode.state);

            if (currentNode.state.isGoal()) {
                List<Board> path = new ArrayList<>();
                while (true) {
                    path.add(currentNode.state);
                    if (currentNode.parent == null) break;
                }
                Collections.reverse(path);
                return path;
            }
            Iterable<Board> neighbors = currentNode.state.neighbors();
            for (Board child_state : neighbors) {
                if (!closed_set.contains(child_state)) {

                }
            }
        }
    }



    // test client (see below)
    public static void main(String[] args) {
        Board tiles  = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {8, 7, 0}});
        Solver solver = new Solver(tiles);
        System.out.println(solver.isSolvable());
    }

}

class Node {
    Board state;
    Object parent;
    int g;
    int h;
    int f;

    public Node(Board state, Object parent, int g, int h) {
        this.state = state;
        this.parent = parent;
        this.g = g;
        this.h = h;
        f = g + h;
    }
}
