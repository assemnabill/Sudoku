package GameEngine;

public interface GameEngine {
    char getSlotValue(int index);
    void makeMove(int index);

    boolean isGameOver();
    char getWinner();
    void printBoard();
}
