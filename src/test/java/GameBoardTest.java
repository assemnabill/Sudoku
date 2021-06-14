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

    @Test
    void whenSettingCell() {
        int value = 3;
        _gameBoard.setCell(0,1, value);
        int result = _gameBoard.getCell(0,1);
        // it should be written
        Assertions.assertEquals(value, result);
    }

    @Test
    public void whenSettingSquareOutOfBounds() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            _gameBoard.setCell(9,9, 8);
        });
        String expectedMessage = "Index 9 out of bounds for length 9";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenGettingCell() {
        int row = 0;
        int col = 4;
        int result = _gameBoard.getCell(row,col);
        Assertions.assertEquals(_puzzle[row][col], result);
    }

    @Test
    public void whenGettingSquareOutOfBounds() {
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            _gameBoard.getCell(9,9);
        });

        String expectedMessage = "Index 9 out of bounds for length 9";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenSolvingPuzzle() {
        int[][] result = _gameBoard.getSolution();
        // solution shouldn't be null or empty
        assertNotNull(result);
        assertNotEquals(result, new int[9][9]);
        // solution must be correct
        Assertions.assertArrayEquals(_solution, result);
    }

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

    @Test
    void whenTestingCorrectSolution() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                _gameBoard.setCell(i,j, _solution[i][j]);
            }
        }
        _gameBoard.getSolution();
        // it should be accepted
        assertTrue(_gameBoard.testSolution());
    }

    @Test
    void whenTestingWrongSolution() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                _gameBoard.setCell(i,j, _solution[i][j]);
            }
        }
        _gameBoard.getSolution();
        // it should be declined
        assertFalse(_gameBoard.testSolution());
    }

}