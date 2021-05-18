import java.util.Arrays;

public class GameBoard {
    // Constants with package visibility
    static final int EMPTY_CELL = 0;
    static final int GRID_SIZE = 9;
    static final int SIDE_SIZE = 3;
    // Sudoku fanatics have claimed that the smallest number of starting clues a Sudoku puzzle can contain is 17.
    static final int CLUES_COUNT = 30; // algo needs 31 clues
    private char[] line;


    private int[][] board = new int[9][9]; // default values are 0
    private int[][] solution = null; // default values are 0


    public GameBoard() {
        // TODO
//        generatePuzzle();
        board = new int[][]{
                {8, 0, 0, 0, 1, 0, 9, 0, 7},
                {0, 0, 6, 0, 0, 7, 0, 0, 0},
                {4, 0, 0, 0, 9, 0, 0, 5, 0},
                {0, 0, 9, 0, 0, 0, 0, 0, 2},
                {1, 0, 0, 6, 0, 8, 0, 4, 0},
                {0, 0, 8, 0, 2, 0, 1, 0, 3},
                {6, 0, 0, 7, 0, 0, 0, 9, 0},
                {0, 0, 1, 0, 0, 0, 2, 0, 6},
                {0, 7, 0, 9, 0, 2, 0, 0, 5}
        };
    }

    // constructor is for testing
    public GameBoard(int[][] board) {
        this.board = board;
    }

    protected void generatePuzzle() {
        // TODO
        board = new int[9][9];
        int i = 0;

        // init with clues
        // solve
        // remove clues till u can't solve
        ////////////////////////////
        // init grid with random values
        // fill every row with 1 value
        // fill every col with 1 value

        while (i < CLUES_COUNT) {
            int row = (int) (Math.floor(Math.random() * 9) * (Math.random() * 3)) % 9;
            int col = (int) (Math.floor(Math.random() * 9) * (Math.random() * 7)) % 9;
            if (isEmpty(row, col)) {
                int value = (int) Math.ceil(Math.random() * 9);
                // look for a valid digit
                while (!isValidValue(row, col, value, board)) {
                    value = (int) Math.ceil(Math.random() * 9);
                }
                setSquare(row, col, value);
                i++;
            }
        }
    }

    private boolean isEmpty(int row, int col) {
        return board[row][col] == EMPTY_CELL;
    }

    /***
     * Method solves a sudoku puzzle recursively
     * @return a truth value weather the puzzle solved or not
     */
    private boolean solve(int row, int col, int[][] cells) {
        if (row == GRID_SIZE) {
            row = 0;
            if (++col == GRID_SIZE) { // reached the end
                return true;
            }
        }
        if (cells[row][col] != EMPTY_CELL) { // skip filled cells
            return solve(row + 1, col, cells); // go a row down
        }
        //  Try all possibilities
        for (int val = 1; val <= GRID_SIZE; ++val) {
            if (isValidValue(row, col, val, cells)) {
                cells[row][col] = val;
                if (solve(row + 1, col, cells)) {
                    return true;
                }
            }
        }
        cells[row][col] = EMPTY_CELL; // reset on backtrack
        return false;
    }

    public static void printSolution(int[][] result) {
        int N = result.length;
        for (int i = 0; i < N; i++) {
            String ret = "";
            for (int j = 0; j < N; j++) {
                ret += result[i][j] + " ";
            }
            System.out.println(ret);
        }
        System.out.println();
    }

    /***
     * Method solves a sudoku puzzle recursively
     * @param row value row index
     * @param col value column index
     * @return a truth value weather the value is valid or not
     */
    private boolean isValidValue(int row, int col, int val, int[][]board) {
        // check columns/rows
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == val) return false;
            if (board[i][col] == val) return false;
        }

        // check regions
        int regionRowOffset = row - row % SIDE_SIZE;
        int regionColOffset = col - col % SIDE_SIZE;
        for (int k = 0; k < 3; ++k) {
            for (int m = 0; m < 3; ++m) {
                if (val == board[regionRowOffset + k][regionColOffset + m]) {
                    return false;
                }
            }
        }
        return true; // no violations
    }


    /***
     *         // TODO
     * @param row
     * @param col
     * @param digit
     * @throws IndexOutOfBoundsException
     */
    public void setSquare(int row, int col, int digit) throws IndexOutOfBoundsException {
        if (row > board.length || col > board[0].length) {
            throw new IndexOutOfBoundsException("Cannot set square at (" + row + ", " + col + ")");
        }

        if (board[row][col] == 0) {
            if (isValidValue(row,col,digit,board)) {
                board[row][col] = digit;
            } else {
                throw new IllegalArgumentException("Cannot set square to " + digit + " at (" + row + ", " + col + ")");
            }
//            System.out.println("WRITE " + digit + " at (" + row + ", " + col + ")");
        }
    }

    /***
     * // TODO
     * @param row
     * @param col
     * @return
     */
    public int getSquare(int row, int col) {
        if (row > board.length || col > board[0].length) {
            throw new IndexOutOfBoundsException("Cannot get square at (" + row + ", " + col + ")");
        }
        return board[row][col];
    }

    /**
     * Return a string containing the sudoku with region separators
     */
    @Override
    public String toString() {
        // Exact size of the generated string for the buffer (values + spacers)
        final int size = (GRID_SIZE * 2 + 1 + ((CLUES_COUNT + 1) * 2)) * (GRID_SIZE + CLUES_COUNT + 1);
        final String verticalSpace = " |";

        final StringBuilder buffer = new StringBuilder(size);
        // Row/Column traversal
        for (int i = 0; i < GRID_SIZE; i++) {
            int[] row = board[i];
            if (i % CLUES_COUNT == 0) {
                appendLine(buffer);
            }
            for (int j = 0; j < GRID_SIZE; j++) {
                int value = row[j];
                if (j % CLUES_COUNT == 0) {
                    buffer.append(verticalSpace);
                }
                appendValue(buffer, value);
            }
            buffer.append(verticalSpace);
            buffer.append('\n');
        }
        appendLine(buffer);
        return buffer.toString();
    }

    /**
     * Appends the value, or a _ if empty
     */
    private void appendValue(StringBuilder buffer, int value) {
        buffer.append(' ');
        if (value != EMPTY_CELL) {
            buffer.append(value);
        } else {
            buffer.append('_');
        }
    }

    /**
     * Append a line (separator line between region)
     */
    private void appendLine(StringBuilder buffer) {
        if (line == null) {
            line = new char[GRID_SIZE * 2 + ((CLUES_COUNT + 1) * 2)];
            Arrays.fill(line, '-');
            line[0] = ' ';     //first char as space
        }
        buffer.append(line);
        buffer.append('\n');
    }

    public boolean testSolution() {
        // TODO : implementation only works for unique puzzles
        return Arrays.deepEquals(board, solution);
    }

    public void reset() {
        // TODO
    }



    public int[][] getSolution() {
        if (solution == null) {
            int[][] tmp = deepCopy(board);
            if (solve(0, 0, tmp)) {
                solution = tmp;
            } else {
                System.out.println("Failed to solve");
            }
        }
        return deepCopy(solution);
    }

    public int[][] getBoard() {
        return deepCopy(board);
    }


    private int[][] deepCopy(int[][] matrix) {
        return java.util.Arrays.stream(matrix).map(el -> el.clone()).toArray($ -> matrix.clone());
    }
}
