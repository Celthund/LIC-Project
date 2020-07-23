package TUI;

import KBD.KBD;
import LCD.LCD;
import SoundGenerator.SoundGenerator;
import Statistics.Statistics;
import isel.leic.utils.Time;

public class TUI {
    public static final int TOPLINE = 1, BOTTOMLINE = 2;
    public static final int COLUMNS = LCD.MAX_COL;
    public static int curLine = LCD.MIN_LINE, curCol = LCD.MIN_COL;
    public static final String savePrefix = "Name:";
    public static final String scorePrefix = "Score:";
    private static final int MAX_SIZE_USERNAME = 8;
    private static final char NEXT_LETTER_KEY = '2', PREVIOUS_LETTER_KEY = '8',
    SHIFT_RIGHT_KEY ='6', SHIFT_LEFT_KEY = '4', ACCEPT_KEY = '5', DELETE_CHAR_KEY = '*';


    public static void main(String[] args) {
        init();
//        drawSaveScoreInput();
        clearLine(curLine);
        moveCursor(1,1);
        write("123456789ABCDEFDDDDD");
        moveCursor(2,1);
        write("ZZZZZZ");
    }

    public static void init() {
        /**
         Initialize Keyboard and LCD classes.
         **/
        KBD.init();
        LCD.init();
    }

    private static void write(char data){
        write("" + data);
    }

    private static void write(String data){
        LCD.write(data);
        curCol++;
        if (curCol > LCD.MAX_COL) {
            switch (curLine) {
                case 1:
                    curLine++;
                    curCol = LCD.MIN_COL;
                    LCD.cursor(curLine, curCol);
                    break;
                case 2:
                    curLine = LCD.MIN_LINE;
                    curCol = LCD.MIN_COL;
                    LCD.cursor(curLine, curCol);
                    break;
            }
        }
    }

    public static String readUsername(){
        /**
         Handles key presses in save score menu.
         The keys follow the following actions:
         // 2 change letter to the next one
         // 8 change letter to the previous one
         // 6 shifts cursor right
         // 4 shifts cursor left
         // 5 accepts the word
         // * deletes if cursor is position in last letter
         // 8 chars max
         **/
        StringBuilder name = new StringBuilder().append('A');
        drawSaveScoreInput();/*
        |Name:_______________|
        |____________________|*/
        paddingLeft(TOPLINE, name.toString(), savePrefix.length());
        int cursorPosition = savePrefix.length() + name.length();
        int currChar = name.length() - 1;
        moveCursor(TOPLINE, cursorPosition);
        char key;
        while (true){
            key = readUserInput(100);
            if (key == ACCEPT_KEY)
                break;
            switch (key) {
                case NEXT_LETTER_KEY:
                    if (name.charAt(currChar) < 'Z') {
                        name.setCharAt(currChar, (char) (name.charAt(currChar) + 1));
                        writeCharAndBack(TOPLINE, cursorPosition, name.charAt(currChar));
                    }
                    break;
                case PREVIOUS_LETTER_KEY:
                    if (name.charAt(currChar) > 'A') {
                        name.setCharAt(currChar, (char) (name.charAt(currChar) - 1));
                        writeCharAndBack(TOPLINE, cursorPosition, name.charAt(currChar));
                    }
                    break;
                case SHIFT_LEFT_KEY:
                    if (currChar > 0){
                        currChar--;
                        cursorPosition--;
                        moveCursor(TOPLINE, cursorPosition);
                    }
                    break;
                case SHIFT_RIGHT_KEY:
                    if (currChar < MAX_SIZE_USERNAME - 1){
                        currChar++;
                        cursorPosition++;
                        if (currChar == name.length()){
                            name.append('A');
                            writeCharAndBack(TOPLINE, cursorPosition, name.charAt(currChar));
                        } else {
                            moveCursor(TOPLINE, cursorPosition);
                        }
                    }
                    break;
                case DELETE_CHAR_KEY:
                    if (currChar > 0){
                        cursorPosition = savePrefix.length() + name.length();
                        writeCharAndBack(TOPLINE, cursorPosition--, ' ');
                        moveCursor(TOPLINE, cursorPosition);

                        name.deleteCharAt(currChar);
                        currChar = name.length() - 1;
                    }
                    break;
            }
            Time.sleep(100);
        }
        TUI.hideCursor();
        return name.toString();
    }

    private static void writeCharAndBack(int line, int col, char letter){
        moveCursor(line, col);
        write(letter);
        moveCursor(line, col);
    }

    public static char readUserInput(long timeout) {
        /**
         Reads input of Keyboard.
         **/
        return KBD.waitKey(timeout);
    }

    public static void alignCenter(int line, String data) {
        /**
         Writes the String aligned to the Center in the LCD.
         **/
        moveCursor(line, (COLUMNS - data.length()) / 2 + 1);
        write(data);
    }

    public static void alignCenter(int line, char data) {
        /**
         Writes the char aligned to the Center in the LCD.
         **/
        moveCursor(line, (COLUMNS - 1) / 2 + 1);
        write(data);
    }

    public static void alignRight(int line, String data) {
        /**
         Writes the String aligned to the Right in the LCD.
         **/
        moveCursor(line, COLUMNS - data.length() + 1);
        write(data);
    }

    public static void alignRight(int line, char data) {
        /**
         Writes the char aligned to the Right in the LCD.
         **/
        moveCursor(line, COLUMNS);
        write(data);
    }

    public static void alignLeft(int line, String data) {
        /**
         Writes the String aligned to the Left in the LCD.
         **/
        moveCursor(line, 1);
        write(data);
    }

    public static void alignLeft(int line, char data) {
        /**
         Writes the char aligned to the Left in the LCD.
         **/
        moveCursor(line, 1);
        write(data);
    }

    public static void paddingLeft(int line, String data, int padding) {
        /**
         Writes the String with a certain distance for the Left margin of the LCD.
         **/
        moveCursor(line, padding + 1);
        write(data);
    }

    public static void paddingLeft(int line, char data, int padding) {
        /**
         Writes the char with a certain distance for the Left margin of the LCD.
         **/
        moveCursor(line, padding + 1);
        write(data);
    }

    public static void paddingRight(int line, String data, int padding) {
        /**
         Writes the String with a certain distance for the Right margin of the LCD.
         **/
        moveCursor(line, COLUMNS - padding - 1);
        write(data);
    }

    public static void paddingRight(int line, char data, int padding) {
        /**
         Writes the char with a certain distance for the Right margin of the LCD.
         **/
        moveCursor(line, COLUMNS - padding - 1);
        write(data);
    }

    public static void clearLine(int line) {
        /**
         Clear a given line of the LCD.
         **/
        moveCursor(line, 1);
        write(" ".repeat(COLUMNS));
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
        if (line < LCD.MIN_LINE || line > LCD.MAX_LINE) {
            return;
        }
        if (col < LCD.MIN_COL || col > LCD.MAX_COL) {
            return;
        }
        curCol = col;
        curLine = line;
        LCD.cursor(curLine, curCol);
    }

    public static void drawLeaderBoard(String name, String score){
        /**
         Draw leaderboard line. Used in main menu.
         Example in bottom line:
         ______________________
         |____________________|
         |01-ASD___________123|
         **/
        clearLine(TUI.BOTTOMLINE);
        Time.sleep(100);
        alignLeft(TUI.BOTTOMLINE, name);
        alignRight(TUI.BOTTOMLINE, score);
    }

    public static void drawEnemies(String enemies){
        /**
         Draw enemy line.
         Example in top line number part:
         ______________________
         |____2315901710410928|
         |____________________|
         **/
        alignRight(TUI.TOPLINE, enemies);
    }
    //DESENHA CHAR NA POSIÇAO CANHAO
    public static void drawCannon(char key){
        /**
         Draw cannon char
         Example:
         ______________________
         |]___________________|
         |____________________|
         **/
        alignLeft(TUI.TOPLINE, key);
    }
    //DESENHA CHAR NA POSIÇAO SHIP
    public static void drawShip(char key){
        /**
         Draw ship char
         Example:
         ______________________
         |_}__________________|
         |____________________|
         **/
        paddingLeft(TUI.TOPLINE, key, 1);
    }

    public static void drawMainMenu(){
        /**
         Draw leaderboard line. Used in main menu.
         Example:
         ______________________
         |____Space_Invaders__|
         |____________________|
         **/
        clear();
        alignCenter(TUI.TOPLINE, "Space Invaders");
    }

    public static void drawGame(char Cannon, char Ship){
        /**
         Draw initial game line.
         Example:
         ______________________
         |]}__________________|
         |Score:0____________|
         **/
        clear();
        drawCannon(Cannon);
        drawShip(Ship);
        alignLeft(TUI.BOTTOMLINE, scorePrefix);
        drawScoreLine(0);
    }

    public static void drawScoreLine(int score){
        /**
         Draw score line.
         Example:
         ______________________
         |____________________|
         |Score:32____________|
         **/
        paddingLeft(TUI.BOTTOMLINE, "" + score, scorePrefix.length());
    }

    public static void drawSaveScoreName(String name) {
        /**
         Draw save score input name.
         Example:
         ______________________
         |_____AAA____________|
         |____________________|
         **/
        paddingLeft(TUI.TOPLINE, name, savePrefix.length());
        moveCursor(TUI.TOPLINE, savePrefix.length() + name.length());
    }

    public static void drawSaveScoreInput(){
        /**
         Draw save score input prefix.
         Example:
         ______________________
         |Name:_______________|
         |____________________|
         **/
        clearLine(TUI.TOPLINE);
        showCursor();
        alignLeft(TUI.TOPLINE, savePrefix);
    }

    public static void drawGameOver(){
        clearLine(TUI.TOPLINE);
        alignCenter(TUI.TOPLINE, "GAME OVER!");
    }

    public static void drawMaintenance() {
        clear();
        alignCenter(TUI.TOPLINE, "On Maintenance");
        alignLeft(TUI.BOTTOMLINE,"*-Count #-shutD");
        
    }

    public static void drawStatistics(Statistics statistics) {
        clear();
        alignLeft(TUI.TOPLINE, "Games:" + statistics.games);
        alignLeft(TUI.BOTTOMLINE,"Coins:" + statistics.coins);
    }

    public static void drawShutdown() {
        clear();
        alignCenter(TUI.TOPLINE, "Shutdown");
        alignLeft(TUI.BOTTOMLINE,"5-Yes other-No");
    }

    public static void drawClearCounters() {
        clear();
        alignCenter(TUI.TOPLINE, "Clear Counters");
        alignLeft(TUI.BOTTOMLINE,"5-Yes other-No");
    }

    public static void drawAvailableCredits(int credits) {
        clearLine(BOTTOMLINE);
        alignCenter(BOTTOMLINE, "Credits:"+credits+"$");
    }

    public static void shutdown() {
        LCD.shutdown();
    }
}
