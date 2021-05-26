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
        _puzzle = new int[][]{
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
        _solution = new int[][]{
                {8, 3, 5, 4, 1, 6, 9, 2, 7},
                {2, 9, 6, 8, 5, 7, 4, 3, 1},
                {4, 1, 7, 2, 9, 3, 6, 5, 8},
                {5, 6, 9, 1, 3, 4, 7, 8, 2},
                {1, 2, 3, 6, 7, 8, 5, 4, 9},
                {7, 4, 8, 5, 2, 9, 1, 6, 3},
                {6, 5, 2, 7, 8, 1, 3, 9, 4},
                {9, 8, 1, 3, 4, 5, 2, 7, 6},
                {3, 7, 4, 9, 6, 2, 8, 1, 5}
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
                    _core.writeCell(0,1, 9);
                });

        String expectedMessage = "Cannot set square to 9 at (0, 1)";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}