import GameEngine.GameEngine;
import processing.core.PApplet;

public class TicTacToe extends PApplet {
    GameEngine gameEngine;
    ScreenOptions s;

    public static void main(String[] args) {
        PApplet.main(TicTacToe.class, args);
    }

    public void settings() {
        this.gameEngine = new GameCore();
        this.s = new ScreenOptions();
        size(s.X_SIZE, s.Y_SIZE);
    }

    public void setup() {
        textAlign(CENTER, CENTER);
        textSize(27);
        noStroke();
        background(color(179, 189, 214));
        gameEngine.printBoard();
    }

    public void mouseClicked() {
        final int row = ((mouseY - s.X_OFFSET - s.SIZE_BORDER) / (s.SIZE_TILE + s.SIZE_BORDER));
        final int col = ((mouseX - s.Y_OFFSET - s.SIZE_BORDER) / (s.SIZE_TILE + s.SIZE_BORDER));
        int index = getIndex(row, col);
        if (index >= 0) {
            if (!gameEngine.isGameOver()) {
                gameEngine.makeMove(index);
                gameEngine.printBoard();
            }
        }
    }

    private int getIndex(int row, int col) {
        switch (row) {
            case 0 -> {
                return col;
            }
            case 1 -> {
                return col + 3;
            }
            case 2 -> {
                return col + 6;
            }
        }
        return -1;
    }

    public void draw() {
        background(color(179, 189, 214));
        if (!gameEngine.isGameOver()) {
            int edgeLength = 3;
            int X, Y;
            int slotColor = color(0, 0, 0, 60);
            for (int row = 0; row < edgeLength; row++) {
                Y = s.Y_OFFSET + s.SIZE_BORDER + row * (s.SIZE_TILE + s.SIZE_BORDER);
                for (int col = 0; col < edgeLength; col++) {
                    X = s.X_OFFSET + s.SIZE_BORDER + col * (s.SIZE_TILE + s.SIZE_BORDER);
                    fill(slotColor);
                    rect(X, Y, s.SIZE_TILE, s.SIZE_TILE, 15);
                    fill(color(255));
                    textSize(50);
                    text(gameEngine.getSlotValue(getIndex(row, col)), X + s.SIZE_TILE / 2, Y + s.SIZE_TILE / 2);
                }
            }
        } else {
            fill(color(random(255)));
            textSize(50);
            char winner = gameEngine.getWinner();
            String result = winner != 0 ? winner + " WINS!!" : "It's a tie!";
            text(result, s.X_SIZE / 2, s.Y_SIZE / 2);
        }
    }
}