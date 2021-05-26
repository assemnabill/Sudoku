import java.util.Arrays;

public class GameBoard {
    // Constants with package visibility
    static final int EMPTY_CELL = 0;
    static final int GRID_SIZE = 9;
    static final int SIDE_SIZE = 3;
    // Sudoku fanatics have claimed that the smallest number
    // of starting clues a Sudoku puzzle can contain is 17.
    static final int CLUES_COUNT = 19;
    private int[][] puzzle = new int[9][9];
    private int[][] board = new int[9][9];
    private int[][] solution = null;
    private char[] line;

    protected GameBoard() {
        generatePuzzle();
    }

    // constructor is for testing
    protected GameBoard(int[][] board) {
        this.board = board;
    }

    protected int[][] generatePuzzle() {
        int[][] generated = new int[9][9];
        boolean isSolvable = false;
        // for analysis purpose
//        int permutations = 0;

        while (!isSolvable) {
            generated = initPuzzle();
            this.board = deepCopy(generated);
            this.puzzle = deepCopy(generated);
            isSolvable = solve(0, 0, generated);
//            permutations++;
        }
        return generated;
    }

    private int[][] initPuzzle() {
        int[][] cells = new int[9][9];
        int i = 0;
        int row, col;
        int value = randomValue();

        while (i < CLUES_COUNT) {
            int[] primes = new int[]{1, 2, 3, 5, 7};
            row = (int) (randomValue() * (Math.random() * primes[Math.min(randomValue(), randomValue()) % 5])) % 9;
            col = (int) (randomValue() * (Math.random() * primes[Math.min(randomValue(), randomValue()) % 5])) % 9;

            if (isEmpty(row, col, cells)) {
                // look for a random valid digit
                while (!isValidValue(row, col, value, cells)) {
                    value = randomValue();
                }
                cells[row][col] = value;
                i++;
            }
        }
        return cells;
    }

    private int randomValue() {
        return (int) Math.ceil(Math.random() * 9);
    }

    private boolean isEmpty(int row, int col, int[][] board) {
        if (board == null) { board = this.board; }
        return board[row][col] == EMPTY_CELL;
    }

    /***
     * Method solves a sudoku puzzle recursively in place
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

    //    private static void printSolution(int[][] result) {
//        int N = result.length;
//        for (int i = 0; i < N; i++) {
//            String ret = "";
//            for (int j = 0; j < N; j++) {
//                ret += result[i][j] + " ";
//            }
//            System.out.println(ret);
//        }
//        System.out.println();
//    }
    private boolean isValidValue(int row, int col, int val, int[][] board) {
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

    protected boolean setCell(int row, int col, int digit) throws IndexOutOfBoundsException {
        if (row > board.length || col > board[0].length) {
            throw new IndexOutOfBoundsException("Cannot set square at (" + row + ", " + col + ")");
        }

        if (board[row][col] == 0) {
            if (isValidValue(row, col, digit, board)) {
                board[row][col] = digit;
                return true;
            }
        }
        return false;
    }

    protected int getCell(int row, int col) throws IndexOutOfBoundsException {
        if (row > board.length || col > board[0].length) {
            throw new IndexOutOfBoundsException("Cannot get square at (" + row + ", " + col + ")");
        }
        return board[row][col];
    }

    /**
     * Return a string containing the sudoku puzzle with region separators
     */
    @Override
    public String toString() {
        // Exact size of the generated string for the buffer (values + spacers)
        final int size = (GRID_SIZE * 2 + 1 + ((SIDE_SIZE + 1) * 2)) * (GRID_SIZE + SIDE_SIZE + 1);
        final String verticalSpace = " |";
        final StringBuilder buffer = new StringBuilder(size);

        // Row/Column traversal
        for (int i = 0; i < GRID_SIZE; i++) {
            int[] row = board[i];
            if (i % SIDE_SIZE == 0) {
                appendLine(buffer);
            }
            for (int j = 0; j < GRID_SIZE; j++) {
                int value = row[j];
                if (j % SIDE_SIZE == 0) {
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

    private void appendValue(StringBuilder buffer, int value) {
        buffer.append(' ');
        buffer.append((value != EMPTY_CELL) ? value : ".");
    }

    private void appendLine(StringBuilder buffer) {
        if (line == null) {
            line = new char[GRID_SIZE * 2 + ((SIDE_SIZE + 1) * 2)];
            Arrays.fill(line, '-');
            line[0] = ' ';     //first char as space
        }
        buffer.append(line);
        buffer.append('\n');
    }

    protected boolean testSolution() {
        return Arrays.deepEquals(board, solution);
    }

    protected void resetBoard() {
        this.board = deepCopy(puzzle);
    }

    protected void solve() {
        this.board = getSolution();
    }

    protected int[][] getSolution() {
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

    private int[][] getBoard() {
        return deepCopy(board);
    }

    private int[][] deepCopy(int[][] matrix) {
        return java.util.Arrays.stream(matrix).map(el -> el.clone()).toArray($ -> matrix.clone());
    }
}
