import GameEngine.GameEngine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameCoreTest {
    GameEngine _core;
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
        this._core = new GameCore(this._gameBoard);
    }

    /**
     * Test setting a cell value with valid value.
     * Value should be set correctly.
     */
    @Test
    void whenWritingCellWithValidValue() {
        int value = 3;
        _core.writeCell(0,1, value);
        int result = _gameBoard.getCell(0,1);
        Assertions.assertEquals(value, result);
    }

    /**
     * Test setting a cell value with faulty value (not suitable in context of solution).
     * IllegalArgumentException should be thrown.
     */
    @Test
    public void whenWritingCellWithFaultyValue() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    _core.writeCell(0,1, 8);
                });
        String expectedMessage = "Cannot set square to 8 at (0, 1)";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test setting a cell value with forbidden value (not a digit between 1-9).
     * IllegalArgumentException should be thrown.
     */
    @Test
    public void whenWritingCellWithForbiddenValue() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    _core.writeCell(0,1, 11);
                });

        String expectedMessage = "Cannot set square to 11 at (0, 1)";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    /**
     * Test setting a cell value with invalid indexes.
     * IllegalArgumentException should be thrown.
     */
    @Test
    public void whenWritingCellWithInvalidIndex() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    _core.writeCell(0,10, 11);
                });
        String expectedMessage = "Cannot set square to 11 at (0, 10)";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}