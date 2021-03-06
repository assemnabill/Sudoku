public class GUISettings {
    final int OFFSET = 75;
    final int SIZE_CELL = 50;
    final int SIZE_REGION = SIZE_CELL * 3;
    final int SIZE_BOARD = SIZE_REGION * 3;
    final int BTN_HEIGHT = (int) (1.5 * SIZE_CELL);
    final int BTN_WIDTH = 200;
    final int BTN_OFFSET = (SIZE_BOARD - (4 * BTN_HEIGHT)) / 3;
    final int X_SIZE = (3 * OFFSET + SIZE_BOARD) + BTN_WIDTH;
    final int Y_SIZE = (3 * OFFSET + SIZE_BOARD);
    final int COLOR_BG = 0x590010;
    final String FONT_NAME = "DejaVu Sans Mono";
    final int FONT_SIZE = 20;
}
