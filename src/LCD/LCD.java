package LCD;

import HAL.HAL;
import isel.leic.utils.Time;

public class LCD {
    private static final int MASK_RS = 0x10;
    private static final int MASK_ENABLE = 0x20;
    private static final int MASK_LCD = 0x3F;
    public static final int MIN_LINE = 1, MAX_LINE = 2, MIN_COL = 1, MAX_COL = 16;
    public static int curLine = MIN_LINE, curCol = MIN_COL;
    private static final boolean SERIAL_INTERFACE = false;

    // TODO
    // Escreve um nibble de comando/dados no LCD em paralelo
    // private static void writeNibbleParallel(boolean rs, int data) {}
    // TODO
    // Escreve um nibble de comando/dados no LCD em série
    // private static void writeNibbleSerial(boolean rs, int data) {}

    private static void writeNibble(boolean rs, int data) {
        /**
         Send 4 bits of data to the LCD.
         **/
        HAL.clrBits(MASK_LCD);
        if (rs)
            HAL.setBits(MASK_RS);
        Time.sleep(1);
        HAL.setBits(MASK_ENABLE);
        HAL.writeBits(0x0F, data);
        Time.sleep(1);
        HAL.clrBits(MASK_ENABLE);
    }

    private static void writeByte(boolean rs, int data) {
        /**
         Write a byte by using writeNibble to send 4 bits at time.
         **/
        writeNibble(rs, data >> 4);
        writeNibble(rs, data);
        Time.sleep(2);
    }

    private static void writeCMD(int data) {
        /**
         Send a byte as command to the LCD.
         **/
        writeByte(false, data);
    }

    private static void writeDATA(int data) {
        /**
         Sends a byte of data and shifts the position of the cursor.
         If it reaches the end, resets the cursor position.
         **/
        writeByte(true, data);
        curCol++;
        if (curCol > MAX_COL) {
            switch (curLine) {
                case 1:
                    curLine++;
                    curCol = MIN_COL;
                    cursor(curLine, curCol);
                    break;
                case 2:
                    curLine = MIN_LINE;
                    curCol = MIN_COL;
                    cursor(curLine, curCol);
                    break;
            }
        }
    }

    public static void init() {
        /**
         Initialize LCD with a 4 bit communication, with display on, display cursor off and blinking of cursor off.
         **/
        HAL.clrBits(0xFF);
        Time.sleep(40);
        writeNibble(false, 0x03); // Sets to 4-bit operation
        Time.sleep(5);
        writeNibble(false, 0x03); // Sets to 4-bit operation
        Time.sleep(1);
        writeCMD(0x32);
        writeCMD(0x28);
        writeCMD(0x0C); // Display ON/OFF
        writeCMD(0x01);
        writeCMD(0x06);
        HAL.clrBits(0xFF);
        createShip();
    }

    public static void write(char c) {
        /**
         Writes a given Char to the LCD, in the current cursor position.
         **/
        writeDATA(c);
    }

    public static void write(String txt) {
        /**
         Writes a given String to the LCD, in the current cursor position.
         **/
        for (int i = 0; i < txt.length(); i++) {
            writeDATA(txt.charAt(i));
        }
    }

    public static void cursor(int lin, int col) {
        /**
         Changes the position of the cursor in the LCD.
         **/
        if (lin < MIN_LINE || lin > MAX_LINE)
            return;
        if (col < MIN_COL || col > MAX_COL)
            return;
        int cmd = 0x80 | ((lin - 1) << 6) | (col - 1);
        writeCMD(cmd);
        curLine = lin;
        curCol = col;
    }

    public static void clear() {
        /**
         Clears LCD and resets cursor to the first position of the first line.
         **/
        writeCMD(0x01);
        writeCMD(0x02);
    }

    private static void easterEgg() {
        /**
         Easter egg to test the LCD. Try it at your own risk.
         **/
        clear();
        for (int i = 0; i < 26; i++) {
            write((char) ('A' + i));
            Time.sleep(200);
        }
        Time.sleep(500);
        clear();
        write('(');
        cursor(1, 3);
        write('.');
        cursor(1, 5);
        write(')');
        write('(');
        cursor(1, 8);
        write('.');
        cursor(1, 10);
        write(')');
    }

    private static void createShip() {
        /**
         Creates a custom Char at first position of CGRAM.
         **/
        writeCMD(0x40);
        writeDATA(0x1E);
        writeDATA(0x18);
        writeDATA(0x1C);
        writeDATA(0x1F);
        writeDATA(0x1C);
        writeDATA(0x18);
        writeDATA(0x1E);
    }

    public static void enableCursor(boolean enable) {
        /**
         Enable/Disable display of the cursor.
         **/
        if (enable)
            writeCMD(0x0F);
        else
            writeCMD(0x0C);
    }
}