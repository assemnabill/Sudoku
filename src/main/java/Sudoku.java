import GameEngine.GameEngine;
import controlP5.*;
import processing.core.PApplet;
import java.util.Arrays;
import java.util.List;

public class Sudoku extends PApplet {
    GameEngine gameEngine;
    GraphicOptions s;
    ControlP5 gui;
    Button checkBtn;
    Button solveBtn;
    Button resetBtn;
    Button newBtn;
    Textlabel statusBar;
    ButtonBar buttonBar;
    private int selectedRow;
    private int selectedCol;


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

        statusBar = gui.addLabel(s.STATUS_BAR)
                .setColorLabel(0)
                .setWidth(s.SIZE_BOARD)
                .setHeight(s.SIZE_CELL)
                .setText("")
                .setFont(new ControlFont(createFont(s.FONT_NAME, s.FONT_SIZE)))
                .setPosition(s.OFFSET, s.SIZE_BOARD + s.OFFSET + s.BTN_OFFSET);

        buttonBar = gui.addButtonBar(s.BUTTON_BAR)
                .setColorActive(color(0xCC, 0x99, 0x33))
                .setColorValueLabel(color(30)) // text color
                .setColorBackground(color(255))
                .setVisible(false)
                .setColorForeground(color(0xCA, 0x99, 0x33)); // hover color
        drawBoard();
        addListeners();
    }

    private void drawBoard() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                fill(255);
                stroke(color(0xcc, 0x83, 0x56)); // CC8356
                strokeWeight(5);
                rect(s.OFFSET + (s.SIZE_CELL * col),
                        s.OFFSET + (s.SIZE_CELL * row), s.SIZE_CELL, s.SIZE_CELL);
                textAlign(CENTER);
                fill(gameEngine.isGiven(row, col) ? 0 : 255, 0, 0);
                if (gameEngine.readCellValue(row, col) != 0) {
                    textSize(25);
                    text(str(gameEngine.readCellValue(row, col)),
                            s.OFFSET + (s.SIZE_CELL * col + s.SIZE_CELL / 2),
                            s.OFFSET + (s.SIZE_CELL * row + s.SIZE_CELL / 3 * 2));
                }
            }
        }
        stroke(0);
        strokeWeight(3);
        // vertical separators
        line(s.OFFSET + (s.SIZE_CELL * 3), s.OFFSET,
                s.OFFSET + (s.SIZE_CELL * 3), s.OFFSET + s.SIZE_BOARD);
        line(s.OFFSET + (s.SIZE_CELL * 6), s.OFFSET,
                s.OFFSET + (s.SIZE_CELL * 6), s.OFFSET + s.SIZE_BOARD);
        // vertical boarders
        line(s.OFFSET, s.OFFSET, s.OFFSET, s.OFFSET + s.SIZE_BOARD);
        line(s.OFFSET + (s.SIZE_CELL * 9), s.OFFSET,
                s.OFFSET + (s.SIZE_CELL * 9), s.OFFSET + s.SIZE_BOARD);
        // horizontal boarders
        line(s.OFFSET, s.OFFSET, s.OFFSET + s.SIZE_BOARD, s.OFFSET);
        line(s.OFFSET, s.OFFSET + (s.SIZE_CELL * 9),
                s.OFFSET + s.SIZE_BOARD, s.OFFSET + (s.SIZE_CELL * 9));
        // horizontal separators
        line(s.OFFSET, s.OFFSET + (s.SIZE_CELL * 3),
                s.OFFSET + s.SIZE_BOARD, s.OFFSET + (s.SIZE_CELL * 3));
        line(s.OFFSET, s.OFFSET + (s.SIZE_CELL * 6),
                s.OFFSET + s.SIZE_BOARD, s.OFFSET + (s.SIZE_CELL * 6));

    }

    private void addListeners() {
        checkBtn.onClick(callbackEvent -> {
            boolean result = gameEngine.checkSolution();
            resetGUI();
            notify(s.CHK_BTN_NOTIFICATION + result);
        });

        solveBtn.onClick(callbackEvent -> {
            gameEngine.solve();
            resetGUI();
            notify(s.SLV_BTN_NOTIFICATION);
        });

        resetBtn.onClick(callbackEvent -> {
            gameEngine.startOver();
            resetGUI();
            notify(s.RST_BTN_NOTIFICATION);
        });
        newBtn.onClick(callbackEvent -> {
            gameEngine.generateNewPuzzle();
            resetGUI();
            notify(s.NEW_BTN_NOTIFICATION);
        });
    }

    public void mouseClicked() {
        // identify position
        int col = floor(map(mouseX, s.OFFSET, s.SIZE_BOARD + s.OFFSET, 0, 9));
        int row = floor(map(mouseY, s.OFFSET, s.SIZE_BOARD + s.OFFSET, 0, 9));
        System.out.println(!buttonBar.isVisible());
        boolean boardIsClicked = (col >= 0 && col < 9)
                && (row >= 0 && row < 9);

        if (boardIsClicked && !gameEngine.isGiven(row, col)) {
            System.out.println("CLICK ON BOARD " + "row: " + row + " col: " + col);
            if (!buttonBar.isVisible())  {
                showPossibilities(row, col);
//                if (selectedVal != null)
//                setSquare(row, col, selectedVal);
            } else {
                buttonBar.setVisible(false);
            }
        }
    }

    private void showPossibilities(int row, int col) {
        selectedRow = row;
        selectedCol = col;
        int[] possibilities = gameEngine.calculatePossibilities(row, col);
        String[] items = split(Arrays.toString(possibilities)
                .replaceAll("\\[|]|,|\\s", "\t"), "\t\t");

        buttonBar.setPosition(mouseX, mouseY).setVisible(true)
                .setSize((int) Math.ceil(possibilities.length * s.SIZE_CELL * 0.5),
                        (int) Math.ceil(s.SIZE_CELL * 0.5))
                .addItems(items);

        buttonBar.onChange(ev -> {
            int index = (int) buttonBar.getValue();
            if (index < possibilities.length) {
                int selectedVal = possibilities[index];
                setSquare(selectedRow, selectedCol, selectedVal);
                List<String> list = Arrays.stream(items).toList();
                buttonBar.removeItems(list);
                buttonBar.setSize(0,0);
            }
        });
    }

    private void setSquare(int row, int col, int value) {
        try {
            if (gameEngine.writeCell(row, col, value)) {
                // set it graphically
                resetGUI();
            }
        } catch (IllegalArgumentException ex) {
//            flash(row, col);
//            notify(ex.getMessage());
            //        resetGUI();
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
    }

    private void notify(String notification) {
        Controller<?> controller = gui.getController("statusBar");
        if (controller != null) {
            if (!controller.getStringValue().equals(notification)) {
                controller.setStringValue(notification);
            }
        }
    }

    public void draw() {
    }

    private void resetGUI() {
        background(s.COLOR_BG);
        drawBoard();
    }
}