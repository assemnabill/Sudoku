import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {
    GameBoard _gameBoard;
    int[][] _puzzle;
    int[][] _solution;

    /**
     * Before each test initialize game board with a given unique puzzle,
     * which has only one unique solution.
     */
    @BeforeEach
    void setUp() {
        // A puzzle with only one unique solution
        _puzzle = new int[][]{
                {0, 0, 0, 8, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 4, 3},
                {5, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 7, 0, 8, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 2, 0, 0, 3, 0, 0, 0, 0},
                {6, 0, 0, 0, 0, 0, 0, 7, 5},
                {0, 0, 3, 4, 0, 0, 0, 0, 0},
                {0, 0, 0, 2, 0, 0, 6, 0, 0}
        };
        _solution = new int[][]{
                {2, 3, 7, 8, 4, 1, 5, 6, 9},
                {1, 8, 6, 7, 9, 5, 2, 4, 3},
                {5, 9, 4, 3, 2, 6, 7, 1, 8},
                {3, 1, 5, 6, 7, 4, 8, 9, 2},
                {4, 6, 9, 5, 8, 2, 1, 3, 7},
                {7, 2, 8, 1, 3, 9, 4, 5, 6},
                {6, 4, 2, 9, 1, 8, 3, 7, 5},
                {8, 5, 3, 4, 6, 7, 9, 2, 1},
                {9, 7, 1, 2, 5, 3, 6, 8, 4}
        };
        this._gameBoard = new GameBoard(_puzzle);
    }

    /**
     * Test setting a cell value with a valid value and valid row and col indexes.
     */
    @Test
    void whenSettingCell() {
        int value = 3;
        _gameBoard.setCell(0,1, value);
        int result = _gameBoard.getCell(0,1);
        // it should be written
        Assertions.assertEquals(value, result);
    }

    /**
     * Test setting a cell value with invalid row and col indexes.
     * An IndexOutOfBoundsException should be thrown.
     */
    @Test
    public void whenSettingSquareOutOfBounds() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            _gameBoard.setCell(9,9, 8);
        });
        String expectedMessage = "Index 9 out of bounds for length 9";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test getting a cell value with valid row and col indexes.
     */
    @Test
    void whenGettingCell() {
        int row = 0;
        int col = 4;
        int result = _gameBoard.getCell(row,col);
        Assertions.assertEquals(_puzzle[row][col], result);
    }

    /**
     * Test getting a cell value with invalid row and col indexes.
     * An IndexOutOfBoundsException should be thrown.
     */
    @Test
    public void whenGettingSquareOutOfBounds() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            _gameBoard.getCell(9,9);
        });

        String expectedMessage = "Index 9 out of bounds for length 9";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test solving the unique puzzle provided in test setup.
     * The puzzle should be solved and the solution should:
     * 1. not be null or empty
     * 2. be identical with the given unique solution in test setup
     */
    @Test
    void whenSolvingPuzzle() {
        int[][] result = _gameBoard.getSolution();
        // solution shouldn't be null or empty
        assertNotNull(result);
        assertNotEquals(result, new int[9][9]);
        // solution must be correct
        Assertions.assertArrayEquals(_solution, result);
    }

    /**
     * Test generating sudoku puzzles.
     * The generated puzzle should:
     * 1. not be null or empty
     * 2. be solvable
     * 3. have it's solution saved within the board
     */
    @Test
    void whenGeneratingPuzzle() {
        int[][] result = _gameBoard.generatePuzzle();
        System.out.println(_gameBoard.toString());
        // it shouldn't be null or empty
        assertNotNull(result);
        assertNotEquals(result, new int[9][9]);
        // it should be solvable and has one solution
        assertArrayEquals(result, _gameBoard.getSolution());
    }

    /**
     * Test solution testing with a correct solution.
     * When testing a correct solution it should be identical with the saved solution (accepted).
     */
    @Test
    void whenTestingCorrectSolution() {
        // copy given solution values into board
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                _gameBoard.setCell(i,j, _solution[i][j]);
            }
        }
        _gameBoard.getSolution(); // test solution
        // it should be accepted
        assertTrue(_gameBoard.testSolution());
    }

    /**
     * Test solution testing with a wrong solution.
     * When testing a wrong solution it should be rejected.
     */
    @Test
    void whenTestingWrongSolution() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                _gameBoard.setCell(i,j, _solution[i][j]);
            }
        }
        _gameBoard.getSolution();
        // it should be rejected
        assertFalse(_gameBoard.testSolution());
    }

}