import GameEngine.GameEngine;

public class GameCore implements GameEngine {
    private GameBoard board;

    public GameCore() {
        this.board = new GameBoard();
    }

    @Override
    public int readCellValue(int row, int col) {
        return board.getSquare(row, col);
    }

    @Override
    public boolean writeCell(int row, int col, int digit) throws IllegalArgumentException{
//        try {
            board.setSquare(row, col, digit);
//        }
//        catch (Exception ex) {
//            ex.printStackTrace();
//        }
        return board.getSquare(row,col) == digit;
    }

    @Override
    public int[][] getSolution() {
        return board.getSolution();
    }

    @Override
    public boolean checkSolution() {
        return board.testSolution();
    }

    @Override
    public void startOver() {
        board.reset();
    }

    @Override
    public void generateNewPuzzle() {
        board.generatePuzzle();
    }

    @Override
    public void printBoard() {
        System.out.println(board);
    }

}
