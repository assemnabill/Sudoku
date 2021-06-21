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
        Assertions.assertEquals(value, result); // it should be written
    }

    /**
     * Test setting a cell value with invalid row and col indexes.
     * An IndexOutOfBoundsException should be thrown.
     */
    @Test
    public void whenSettingSquareOutOfBounds() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> _gameBoard.setCell(9,9, 8));
        assertTrue(exception.getMessage().contains("Index 9 out of bounds for length 9"));
    }

    /**
     * Test getting a cell value with valid row and col indexes.
     */
    @Test
    void whenGettingCell() {
        int result = _gameBoard.getCell(0,4);
        Assertions.assertEquals(_puzzle[0][4], result);
    }

    /**
     * Test getting a cell value with invalid row and col indexes.
     * An IndexOutOfBoundsException should be thrown.
     */
    @Test
    public void whenGettingSquareOutOfBounds() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> _gameBoard.getCell(9,9));
        assertTrue(exception.getMessage().contains("Index 9 out of bounds for length 9"));
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
        assertNotNull(result); // shouldn't be null
        assertNotEquals(result, new int[9][9]); // shouldn't be empty
        Assertions.assertArrayEquals(_solution, result); // must be correct
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
//        System.out.println(_gameBoard.toString());
        assertNotNull(result);  // shouldn't be null
        assertNotEquals(result, new int[9][9]); // shouldn't be empty
        assertArrayEquals(result, _gameBoard.getSolution()); // should be solvable
    }

    /**
     * Test solution testing with a correct solution.
     * When testing a correct solution it should be identical with the saved solution (accepted).
     */
    @Test
    void whenTestingCorrectSolution() {
        // copy given solution values into board
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) { _gameBoard.setCell(i,j, _solution[i][j]); }
        }
        _gameBoard.getSolution(); // test solution
        assertTrue(_gameBoard.testSolution()); // it should be accepted
    }

    /**
     * Test solution testing with a wrong solution.
     * When testing a wrong solution it should be rejected.
     */
    @Test
    void whenTestingWrongSolution() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) { _gameBoard.setCell(i,j, _solution[i][j]); }
        }
        _gameBoard.getSolution();
        assertFalse(_gameBoard.testSolution()); // it should be rejected
    }
}