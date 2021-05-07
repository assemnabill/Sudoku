import GameEngine.GameEngine;

public class GameCore implements GameEngine {
    private final GameBoard board;

    public GameCore() {
        this.board = new GameBoard();
    }

    @Override
    public boolean isGameOver() {
        return board.isGameOver();
    }

    @Override
    public char getWinner() {
        return board.getWinner();
    }

    @Override
    public char getSlotValue(int index) {
        return board.getSquare(index);
    }

    @Override
    public void makeMove(int index) {
        try {
            board.setSquare(index);
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void printBoard() {
        board.print();
    }

}
