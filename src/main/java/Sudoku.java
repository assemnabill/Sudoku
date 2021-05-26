import GameEngine.GameEngine;
import controlP5.*;
import processing.core.PApplet;

public class Sudoku extends PApplet {
    GameEngine gameEngine;
    GraphicOptions s;
    ControlP5 gui;
    Button checkBtn;
    Button solveBtn;
    Button resetBtn;
    Button newBtn;
    Textlabel hintArea;
    ControlGroup<Background> bg;


    public static void main(String[] args) {
        PApplet.main(Sudoku.class, args);
    }

    public void settings() {
        this.gameEngine = new GameCore();
        this.s = new GraphicOptions();
        size(s.X_SIZE, s.Y_SIZE);
    }

    public void setup() {
        initGUI();
    }

    private Button createButton(String name, int x, int y, int color) {
        ControlFont font = new ControlFont(createFont(s.FONT_NAME, s.FONT_SIZE));
        CColor cColor = new CColor();
        cColor.setBackground(color)
                .setForeground(color(0x78, 0x18, 0xcc)); // 7818CC
        Button b = gui.addButton(name)
                .setPosition(x, y)
                .setSize(s.BTN_WIDTH, s.BTN_HEIGHT)
                .setColor(cColor);
        b.getCaptionLabel().setFont(font);
        return b;
    }

    private void initGUI() {
        this.gui = new ControlP5(this);

        final int orange = color(0xCC, 0x99, 0x33);
        final int red = color(0xCC, 0, 0);
        final int green = color(0, 0x99, 0);
        final int blue = color(0x33, 0x99, 0x99);
        final int bgColor = color(0xFF, 0xFF, 0xCC);

        textAlign(CENTER, CENTER);
        textSize(27);
        noStroke();
        background(color(s.COLOR_BG));

        int btnXPos = 2 * s.OFFSET + s.SIZE_BOARD;
        int btnYPos = s.OFFSET + s.BTN_OFFSET + s.BTN_HEIGHT;
        checkBtn = createButton(s.BTN_CHECK, btnXPos, s.OFFSET, orange);
        solveBtn = createButton(s.BTN_SOLVE, btnXPos, btnYPos, red);
        btnYPos += s.BTN_OFFSET + s.BTN_HEIGHT;
        resetBtn = createButton(s.BTN_RESET, btnXPos, btnYPos, green);
        btnYPos += s.BTN_OFFSET + s.BTN_HEIGHT;
        newBtn = createButton(s.BTN_NEW, btnXPos, btnYPos, blue);

        hintArea = gui.addLabel("hintArea")
                .setColorLabel(0)
                .setWidth(s.SIZE_BOARD)
                .setHeight(s.SIZE_CELL)
                .setText("")
                .setFont(new ControlFont(createFont(s.FONT_NAME, s.FONT_SIZE)))
                .setPosition(s.OFFSET, s.SIZE_BOARD + s.OFFSET + s.BTN_OFFSET);

        bg = gui.addBackground("footer")
                .setSize(s.SIZE_BOARD, s.BTN_HEIGHT / 2)
                .setPosition(hintArea.getPosition()[0], hintArea.getPosition()[1]);
        bg.setBackgroundColor(s.COLOR_BG);

//        System.out.println("does bgColor have correct value? " + (bg.getColor().getBackground() == s.COLOR_BG));
        drawBoard();
        addListeners();
    }

    private void drawBoard() {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                fill(255);
                stroke(color(0xcc, 0x83, 0x56)); // CC8356
                strokeWeight(7);
                rect(s.OFFSET + (s.SIZE_CELL * x), s.OFFSET + (s.SIZE_CELL * y), s.SIZE_CELL, s.SIZE_CELL);
                textAlign(CENTER);
                if (gameEngine.readCellValue(y, x) != 0) {
                    fill(255, 0, 0);
                    textSize(25);
                    text(str(gameEngine.readCellValue(y, x)), s.OFFSET + (s.SIZE_CELL * x + s.SIZE_CELL / 2), s.OFFSET + (s.SIZE_CELL * y + s.SIZE_CELL / 3 * 2));
                }
            }
        }
        stroke(0);
        strokeWeight(3);
        // vertical separators
        line(s.OFFSET + (s.SIZE_CELL * 3), s.OFFSET, s.OFFSET + (s.SIZE_CELL * 3), s.OFFSET + s.SIZE_BOARD);
        line(s.OFFSET + (s.SIZE_CELL * 6), s.OFFSET, s.OFFSET + (s.SIZE_CELL * 6), s.OFFSET + s.SIZE_BOARD);
        // vertical boarders
        line(s.OFFSET, s.OFFSET, s.OFFSET, s.OFFSET + s.SIZE_BOARD);
        line(s.OFFSET + (s.SIZE_CELL * 9), s.OFFSET, s.OFFSET + (s.SIZE_CELL * 9), s.OFFSET + s.SIZE_BOARD);
        // horizontal boarders
        line(s.OFFSET, s.OFFSET, s.OFFSET + s.SIZE_BOARD, s.OFFSET);
        line(s.OFFSET, s.OFFSET + (s.SIZE_CELL * 9), s.OFFSET + s.SIZE_BOARD, s.OFFSET + (s.SIZE_CELL * 9));
        // horizontal separators
        line(s.OFFSET, s.OFFSET + (s.SIZE_CELL * 3), s.OFFSET + s.SIZE_BOARD, s.OFFSET + (s.SIZE_CELL * 3));
        line(s.OFFSET, s.OFFSET + (s.SIZE_CELL * 6), s.OFFSET + s.SIZE_BOARD, s.OFFSET + (s.SIZE_CELL * 6));

    }

    private void addListeners() {
        checkBtn.onClick(callbackEvent -> {
            boolean result = gameEngine.checkSolution();
            notify("This solution is " + result);
        });

        solveBtn.onClick(callbackEvent -> {
            gameEngine.solve();
            drawBoard();
            notify("That was it already!");
        });

        resetBtn.onClick(callbackEvent -> {
            gameEngine.startOver();
            drawBoard();
            notify("Puzzle ist reset!");
        });
        newBtn.onClick(callbackEvent -> {
            gameEngine.generateNewPuzzle();
            drawBoard();
            notify("Generated new puzzle!");
        });
//        hintArea.setUpdate(true);
//        hintArea.onClick(e -> hintArea.hide());
    }

    public void mouseClicked() {
        // identify position
        int col = floor(map(mouseX, s.OFFSET, s.SIZE_BOARD, 0, 8));
        int row = floor(map(mouseY, s.OFFSET, s.SIZE_BOARD, 0, 8));
//        System.out.println("row: " + row + "col: " + col);
        boolean boardIsClicked = (col >= 0 && col < 9)
                && (row >= 0 && row < 9);
        if (boardIsClicked) {
            System.out.println("CLICK ON BOARD");
            // TODO: show pop up with possible numbers
            int value = 5;
            setSquare(row, col, value);
        }
    }

    private void setSquare(int row, int col, int value) {
        try {
            if (gameEngine.writeCell(row, col, value)) {
                // set it graphically
                fill(0);
                stroke(200);
                strokeWeight(3);
                textAlign(CENTER);
                textSize(25);
                text(String.valueOf(value),
                        s.OFFSET + (s.SIZE_CELL * col + s.SIZE_CELL / 2),
                        s.OFFSET + (s.SIZE_CELL * row + s.SIZE_CELL / 3 * 2));
            }
        } catch (IllegalArgumentException ex) {
            flash(row, col);
            notify(ex.getMessage());
        }
    }

    private void flash(int row, int col) {
        // todo fix
//        frameRate(1);
        fill(color(0xff, 0xc0, 0xb8, 60)); //#FFC0B8
        // flash col
        rect(s.OFFSET + (s.SIZE_CELL * col), s.OFFSET, s.SIZE_CELL, s.SIZE_BOARD);
        // flash row
        rect(s.OFFSET, s.OFFSET + (s.SIZE_CELL * row), s.SIZE_BOARD, s.SIZE_CELL);
        drawBoard();
    }

    private void notify(String notification) {
        Controller<?> controller = gui.getController("hintArea");
        if (controller != null) {
            if (!controller.getStringValue().equals(notification)) {
                controller.setStringValue(notification);
            }
        }
    }

    public void draw() {
        frameRate(5);
        background(s.COLOR_BG);
        drawBoard();
    }
}