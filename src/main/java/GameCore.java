import GameEngine.GameEngine;

public class GameCore implements GameEngine {
    private GameBoard board;

    public GameCore() {
        this.board = new GameBoard();
    }

    // for test
    public GameCore(GameBoard b) {
        this.board = b;
    }

    @Override
    public int readCellValue(int row, int col) {
        return board.getCell(row, col);
    }

    @Override
    public boolean writeCell(int row, int col, int digit) throws IllegalArgumentException {
        if (board.setCell(row, col, digit)) {
            return true;
        } else {
            throw new IllegalArgumentException("Invalid Value. Cannot set square to " + digit + " at (" + row + ", " + col + ")");
        }
    }

    @Override
    public void solve() {
        board.solve();
    }

    @Override
    public boolean checkSolution() {
        return board.testSolution();
    }

    @Override
    public void startOver() {
        board.resetBoard();
    }

    @Override
    public void generateNewPuzzle() {
        board.generatePuzzle();
    }

}
