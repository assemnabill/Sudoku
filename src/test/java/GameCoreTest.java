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

    @BeforeEach
    void setUp() {
        // A puzzle with one unique solution
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

    @Test
    void whenWritingCellWithValidValue() {
        int value = 3;
        _core.writeCell(0,1, value);
        int result = _gameBoard.getCell(0,1);
        Assertions.assertEquals(value, result);
    }

    @Test
    public void whenWritingCellWithInvalidValue() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> {
                    _core.writeCell(0,1, 8);
                });

        String expectedMessage = "Cannot set square to 8 at (0, 1)";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}