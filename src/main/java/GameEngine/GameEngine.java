package GameEngine;

public interface GameEngine {

    int readCellValue(int row, int col);
    boolean writeCell(int row, int col, int value);
    int[][] getSolution();
    boolean checkSolution();
    void startOver();
    void generateNewPuzzle();
    void printBoard();
}
