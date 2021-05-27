package GameEngine;

public interface GameEngine {
    int readCellValue(int row, int col);

    boolean writeCell(int row, int col, int value);

    boolean isGiven(int row, int col);

    void solve();

    boolean checkSolution();

    void startOver();

    void generateNewPuzzle();

    int[] calculatePossibilities(int row, int col);
}
