public class GameBoard {
    private final char[] grid = new char[9]; // default values are 0
    private final int[][] winConditions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // horizontal
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // vertical
            {0, 4, 8}, {2, 4, 6}             // diagonal
    };
    private char winner;
    private int currentPlayerMark;

    public GameBoard() {
        this.currentPlayerMark = 1; // player 1 (X)
        this.winner = 0;
    }

    public char getWinner() {
        return winner;
    }

    private boolean isBoardFull() {
        for (char c : grid) {
            if (c == 0) {
                return false;
            }
        }
        return true;
    }

    private boolean threeInRow() {
        for (int i = 0; i < 8; i++) {
            if ((grid[winConditions[i][0]] == grid[winConditions[i][1]])
                    && (grid[winConditions[i][1]] == grid[winConditions[i][2]])
                    && (grid[winConditions[i][0]] != 0)) {
                winner = grid[winConditions[i][0]];
                return true;
            }
        }
        return false;
    }

    public boolean isGameOver() {
        boolean tie = winner == 0 && isBoardFull();
        if (tie || threeInRow()) {
            String result = tie ? "It's a tie!" : "GAME OVER. Winner is => " + getWinner();
            System.out.println(result);
            return true;
        }
        return false;
    }

    public void setSquare(int index) throws IndexOutOfBoundsException {
        if (index > grid.length) {
            throw new IndexOutOfBoundsException("Cannot set square at index " + index);
        }

        final char playerX = 'X';
        final char playerO = 'O';
        char currentPlayer = currentPlayerMark == 1 ? playerX : playerO;
        if ((int) grid[index] == 0) {
            grid[index] = currentPlayer;
            currentPlayerMark = -currentPlayerMark;
            System.out.println("WRITE " + currentPlayer + " at (" + index + ")");
        }
    }

    public char getSquare(int index) {
        return grid[index];
    }

    public void print() {
        StringBuilder builder = new StringBuilder();
        builder.append("-------------\n");
        int index;
        for (int row = 0; row < 3; row++) {
            switch (row) {
                case 1: {
                    index = 3;
                }
                case 2: {
                    index = 6;
                }
                default: {
                    index = 0;
                }
            }
            builder.append("| ").append(grid[index]).append(" | ")
                    .append(grid[index + 1]).append(" | ")
                    .append(grid[index + 2]).append(" | ")
                    .append("\n-------------\n");
        }
        System.out.println(builder);
    }
}
