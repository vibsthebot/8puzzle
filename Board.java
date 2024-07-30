import static java.lang.Math.abs;

public class Board {

    int[][] board;
    int width;

    public Board(int[][] tiles) {
        board = tiles;
        width = board[0].length;
    }
    // string representation of this board
    public String toString() {
        String boardAsString = "";
        boardAsString = boardAsString + width + "\n";
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                boardAsString = boardAsString + " " + board[i][j];
            }
            boardAsString = boardAsString + "\n";
        }


        return boardAsString;
    }

    // board dimension n
    public int dimension() {
        return width;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        int dimension = dimension();
        for (int i = 0; i < dimension; i++){
            for (int j = 0; j < dimension; j++){
                int cur = i*dimension + j + 1;
                if (board[i][j] != cur && board[i][j] != 0){
                    hamming++;
                }
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        int dimension = dimension();
        for (int i = 0; i < dimension; i++){
            for (int j = 0; j < dimension; j++){
                int curElement = board[i][j];
                if (curElement != 0) {
                    int intendedX = (curElement - 1) / dimension;
                    int intendedY = (curElement - 1) - intendedX * dimension;
                    manhattan = manhattan + abs(intendedX - i) + abs(intendedY - j);
                }
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int dimension = dimension();
        int[][] goalMatrix = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++){
            for (int j = 0; j < dimension; j++){
                int numToSet = i*dimension + j + 1;
                if (numToSet == dimension*dimension){
                    numToSet = 0;
                }
                goalMatrix[i][j] = numToSet;
            }
        }
        Board goalBoard = new Board(goalMatrix);
        return equals(goalBoard);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (that.dimension() != this.dimension())
            return false;
        for (int i = 0; i < board.length; i ++) {
            if (this.board[i] != that.board[i])
                return false;
        }
        return true;
    }

    /*
    // all neighboring boards
    public Iterable<Board> neighbors() {

    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

    }
    */
    public static void main(String[] args) {
        Board tiles  = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
        Board tiles2  = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}});
        System.out.println(tiles);
    }

}
