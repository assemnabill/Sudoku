import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameBoard {
    // Constants with package visibility
    final int EMPTY_CELL = 0;
    final int GRID_SIZE = 9;
    final int SIDE_SIZE = 3;
    /* Sudoku fanatics have claimed that the smallest number
     * of starting clues a Sudoku puzzle can contain is 17. */
    final int CLUES_COUNT = 19;
    private int[][] initialPuzzle = new int[9][9];
    private int[][] gameBoard = new int[9][9];
    private int[][] solution = null;
    ArrayList<Integer> possibilities = new ArrayList<>();
    private char[] line;

    protected GameBoard() {
        generatePuzzle();
    }

    protected GameBoard(int[][] gameBoard) {
        this.initialPuzzle = gameBoard;
        this.gameBoard = gameBoard;
    }

    protected int[][] generatePuzzle() {
        int[][] generated = new int[9][9];
        boolean isSolvable = false;
        while (!isSolvable) {
            generated = initPuzzle();
            this.initialPuzzle = deepCopy(generated);
            this.gameBoard = deepCopy(this.initialPuzzle);
            isSolvable = solve(0, 0, generated);
            this.solution = deepCopy(generated);
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
                while (!isValidValue(row, col, value, cells)) { value = randomValue(); }
                cells[row][col] = value;
                i++;
            }
        }
        return cells;
    }

    private int randomValue() {
        return (int) Math.ceil(Math.random() * GRID_SIZE);
    }

    private boolean isEmpty(int row, int col, int[][] board) {
        if (board == null) { board = this.gameBoard; }
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
        // skip filled cells, go a row down
        if (cells[row][col] != EMPTY_CELL) {return solve(row + 1, col, cells); }
        //  Try all possibilities
        for (int val = 1; val <= GRID_SIZE; ++val) {
            if (isValidValue(row, col, val, cells)) {
                cells[row][col] = val;
                if (solve(row + 1, col, cells)) {
                    return true;
                }
            }
        }
        cells[row][col] = EMPTY_CELL; // reset cells on backtrack
        return false;
    }

    private boolean isValidValue(int row, int col, int val, int[][] board) {
        // check columns/rows
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == val) return false;
            if (board[i][col] == val) return false;
        }

        // check regions
        int regionRowOffset = row - row % SIDE_SIZE;
        int regionColOffset = col - col % SIDE_SIZE;
        for (int k = 0; k < SIDE_SIZE; ++k) {
            for (int m = 0; m < SIDE_SIZE; ++m) {
                if (val == board[regionRowOffset + k][regionColOffset + m]) { return false; }
            }
        }
        return true; // no violations
    }

    protected boolean setCell(int row, int col, int digit) throws IndexOutOfBoundsException {
        if (row > gameBoard.length || col > gameBoard[0].length) {
            throw new IndexOutOfBoundsException("Cannot set square at (" + row + ", " + col + ")");
        }

        if (gameBoard[row][col] == EMPTY_CELL || !isGiven(row, col)) {
            if (isValidValue(row, col, digit, gameBoard)) {
                gameBoard[row][col] = digit;
                return true;
            }
        }
        return false;
    }

    protected int getCell(int row, int col) throws IndexOutOfBoundsException {
        if (row > gameBoard.length || col > gameBoard[0].length) {
            throw new IndexOutOfBoundsException("Cannot get square at (" + row + ", " + col + ")");
        }
        return gameBoard[row][col];
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
            int[] row = gameBoard[i];
            if (i % SIDE_SIZE == 0) { appendLine(buffer); }
            for (int j = 0; j < GRID_SIZE; j++) {
                int value = row[j];
                if (j % SIDE_SIZE == 0) { buffer.append(verticalSpace); }
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
        return Arrays.deepEquals(gameBoard, solution);
    }

    protected void resetBoard() {
        this.gameBoard = deepCopy(initialPuzzle);
    }

    protected void solve() {
        this.gameBoard = getSolution();
    }

    protected int[][] getSolution() {
        if (solution == null) {
            int[][] tmp = deepCopy(initialPuzzle);
            if (solve(0, 0, tmp)) { solution = tmp; }
            else { System.out.println("Failed to solve"); }
        }
        return deepCopy(solution);
    }

    protected int[] calcPossibilities(int row, int col) {
        int[][] tmp = deepCopy(this.gameBoard);
        int regionRowOffset = row - row % SIDE_SIZE;
        int regionColOffset = col - col % SIDE_SIZE;
        int[] currRow = tmp[row];
        int[] currCol = new int[GRID_SIZE];
        int[] currReg = new int[GRID_SIZE];
        // init column array
        for (int i = 0; i < GRID_SIZE; i++) currCol[i] = tmp[i][col];

        int i = 0; // init region array
        while (i < GRID_SIZE)
            for (int k = 0; k < SIDE_SIZE; k++) {
                for (int m = 0; m < SIDE_SIZE; m++) {
                    currReg[i] = tmp[regionRowOffset + k][regionColOffset + m];
                    i++;
                }
            }

        possibilities = IntStream.range(1, GRID_SIZE + 1).filter(x -> IntStream.of(currRow).distinct()
                .noneMatch(p -> p == x) && IntStream.of(currCol).distinct().noneMatch(p -> p == x)
                && IntStream.of(currReg).distinct().noneMatch(p -> p == x))
                .boxed().collect(Collectors.toCollection(ArrayList::new));
        return possibilities.stream().mapToInt(x -> x).toArray();
    }

    private int[][] deepCopy(int[][] matrix) {
        return java.util.Arrays.stream(matrix).map(el -> el.clone()).toArray($ -> matrix.clone());
    }

    protected boolean isGiven(int row, int col) {
        return initialPuzzle[row][col] != EMPTY_CELL;
    }
}