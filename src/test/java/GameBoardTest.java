import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {

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
    }

    @Test
    void setSquare() {
        int value = 3;
        _gameBoard.setSquare(0,1, value);
        int result = _gameBoard.getSquare(0,1);
        Assertions.assertEquals(value, result);
    }

    @Test
    public void whenSettingSquareOutOfBounds() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            _gameBoard.setSquare(9,9, 8);
        });

        String expectedMessage = "Index 9 out of bounds for length 9";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void whenSettingSquareWithInvalidValue() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            _gameBoard.setSquare(0,1, 9);
        });

        String expectedMessage = "Cannot set square to 9 at (0, 1)";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void getSquare() {
        int result = _gameBoard.getSquare(8,8); // 5
        Assertions.assertEquals(5, result);
    }

    @Test
    public void whenGettingSquareOutOfBounds() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            _gameBoard.getSquare(9,9);
        });

        String expectedMessage = "Index 9 out of bounds for length 9";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void solve() {
        int[][] result = _gameBoard.getSolution();
//        System.out.println(_gameBoard);
        Assertions.assertNotNull(result);
        Assertions.assertNotEquals(_gameBoard.getBoard(),result);
        Assertions.assertArrayEquals(_solution, result);
    }

    @Test
    void generatePuzzle() {
        // TODO
        _gameBoard.generatePuzzle();
        System.out.println(_gameBoard.toString());
//        _gameBoard.solve();
//        System.out.println(Arrays.deepToString(_gameBoard.getSolution()));
//        Assertions.assertFalse(Arrays.deepEquals(_gameBoard.getSolution(), _gameBoard.getSnapshot()));
    }

    @Test
    void testSolution() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                _gameBoard.setSquare(row,col,_solution[row][col]);
            }
        }
        _gameBoard.getSolution();
        assertTrue(_gameBoard.testSolution());
    }

    @Test
    void reset() {
        // TODO

    }
}