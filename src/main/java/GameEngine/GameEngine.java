package GameEngine;

public interface GameEngine {
    int readCellValue(int row, int col);
    boolean writeCell(int row, int col, int value);
    void solve();
    boolean checkSolution();
    void startOver();
    void generateNewPuzzle();
}
