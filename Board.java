import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        StringBuilder boardAsString = new StringBuilder();
        boardAsString.append(width).append("\n");
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < width; j++) {
                boardAsString.append(" ").append(board[i][j]);
            }
            boardAsString.append("\n");
        }


        return boardAsString.toString();
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
        if (y == null || y.getClass() != getClass()) return false;
        Board other = (Board) y;
        if (other.dimension() != width) return false;
        for (int i = 0; i < width; i++){
            for (int j = 0; j < width; j++){
                if (other.board[i][j] != board[i][j]) return false;
            }
        }
        return true;
    }


    // all neighboring boards
    public Iterable<Board> neighbors() {
        boolean found = false;
        int i = 0, j = 0;
        while (i < width) {
            while (j < width) {
                if (board[i][j] == 0) {
                    found = true;
                    break;
                }
                j++;
            }
            if (found) break;
            i++;
        }

        List<Board> boardList = new ArrayList<Board>();

        if (i > 0) {
            boardList.add(elementSwap(i, j, i - 1, j));
        }
        if (i < width - 1) {
            boardList.add(elementSwap(i, j, i + 1, j));
        }
        if (j > 0) {
            boardList.add(elementSwap(i, j, i, j - 1));
        }
        if (j < width - 1) {
            boardList.add(elementSwap(i, j, i, j + 1));
        }

        return boardList;

    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {

        Random random = new Random();

        // Generate two random positions
        int row1 = random.nextInt(width);
        int col1 = random.nextInt(width);
        int row2 = random.nextInt(width);
        int col2 = random.nextInt(width);

        // Swap the elements

        return elementSwap(row1, col1, row2, col2);
    }

    public Board elementSwap(int row1, int col1, int row2, int col2) {
        int[][] returnMatrix = new int[width][width];
        Board returnBoard = new Board(board);
        returnBoard.board[row1][col1] = board[row2][col2];
        returnBoard.board[row2][col2] = board[row1][col1];
        return returnBoard;
    }

    public static void main(String[] args) {
        Board tiles  = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        Board tiles2  = new Board(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        System.out.println(tiles.equals(tiles2));
        System.out.println(tiles.twin());
        System.out.println(tiles);
    }

}
