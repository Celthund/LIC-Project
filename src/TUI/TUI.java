package TUI;

import KBD.KBD;
import LCD.LCD;

public class TUI {
    public static final int TOPLINE = 1, BOTTOMLINE = 2;
    public static final int COLUMNS = LCD.MAX_COL;

    public static void init() {
        /**
         Initialize Keyboard and LCD classes.
         **/
        KBD.init();
        LCD.init();
    }

    public static char readInput(long timeout) {
        /**
         Reads input of Keyboard.
         **/
        return KBD.waitKey(timeout);
    }

    public static void alignCenter(int line, String data) {
        /**
         Writes the String aligned to the Center in the LCD.
         **/
        LCD.cursor(line, (COLUMNS - data.length()) / 2 + 1);
        LCD.write(data);
    }
    public static void alignCenter(int line, char data) {
        /**
         Writes the char aligned to the Center in the LCD.
         **/
        LCD.cursor(line, (COLUMNS - 1) / 2 + 1);
        LCD.write(data);
    }

    public static void alignRight(int line, String data) {
        /**
         Writes the String aligned to the Right in the LCD.
         **/
        LCD.cursor(line, COLUMNS - data.length() + 1);
        LCD.write(data);
    }

    public static void alignRight(int line, char data) {
        /**
         Writes the char aligned to the Right in the LCD.
         **/
        LCD.cursor(line, COLUMNS);
        LCD.write(data);
    }

    public static void alignLeft(int line, String data) {
        /**
         Writes the String aligned to the Left in the LCD.
         **/
        LCD.cursor(line, 1);
        LCD.write(data);
    }

    public static void alignLeft(int line, char data) {
        /**
         Writes the char aligned to the Left in the LCD.
         **/
        LCD.cursor(line, 1);
        LCD.write(data);
    }

    public static void paddingLeft(int line, String data, int padding) {
        /**
         Writes the String with a certain distance for the Left margin of the LCD.
         **/
        LCD.cursor(line, padding);
        LCD.write(data);
    }

    public static void paddingLeft(int line, char data, int padding) {
        /**
         Writes the char with a certain distance for the Left margin of the LCD.
         **/
        LCD.cursor(line, padding);
        LCD.write(data);
    }

    public static void paddingRight(int line, String data, int padding) {
        /**
         Writes the String with a certain distance for the Right margin of the LCD.
         **/
        LCD.cursor(line, COLUMNS - padding);
        LCD.write(data);
    }

    public static void paddingRight(int line, char data, int padding) {
        /**
         Writes the char with a certain distance for the Right margin of the LCD.
         **/
        LCD.cursor(line, COLUMNS - padding);
        LCD.write(data);
    }

    public static void clearLine(int line) {
        /**
         Clear a given line of the LCD.
         **/
        LCD.cursor(line, 1);
        LCD.write(" ".repeat(COLUMNS));
    }

    public static void clear() {
        /**
         Clear the whole LCD.
         **/
        LCD.clear();
    }

    public static void showCursor() {
        /**
         Show the cursor on the LCD.
         **/
        LCD.enableCursor(true);
    }

    public static void hideCursor() {
        /**
         Hide the cursor on the LCD.
         **/
        LCD.enableCursor(false);
    }

    public static void moveCursor(int line, int col) {
        /**
         Move the cursor to a given position on the LCD.
         **/
        LCD.cursor(line, col);
    }

}
