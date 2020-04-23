import Interface.Interface;
import Scores.Scores;
import TUI.TUI;
import isel.leic.utils.Time;

public class SpaceInvader {
    private static int score = 0;
    private static char[] enemyState = new char[TUI.COLUMNS - 2];
    private static final char cannonChar = ']';
    private static final char shipChar = 0;
    private static char cannon = cannonChar;
    private static Scores leaderboard = new Scores();
    private static int scoreCounter = 1;


    public static void main(String[] args) {
        long time = Time.getTimeInMillis();
        TUI.init();
        Interface.drawMainMenu();
        char key;

        while (true) {
            key = TUI.readInput(100);
            if (key == '*') {
                startGame();
                saveScore();
                TUI.clear();
                TUI.alignCenter(TUI.TOPLINE, "Space Invaders");
            }
            if (Time.getTimeInMillis() - time >= 2000) {
                showScore();
                time = Time.getTimeInMillis();
            }
        }
    }

    public static void prepareShot(char key) {
        if (key == '*' && cannon >= '0' && cannon <= '9') {
            seekAndDestroy(cannon);
            cannon = cannonChar;
        } else if (key == '#' || key == '*') {
            cannon = cannonChar;
        } else if (key >= '0' && key <= '9') {
            cannon = key;
        }
        Interface.drawCannon(cannon);
    }

    public static void seekAndDestroy(char enemy) {
        for (int i = 2; i < enemyState.length; i++) {
            if (enemyState[i] != ' ') {
                if (enemyState[i] == enemy) {
                    enemyState[i] = ' ';
                    score += 1 + (enemy - '0');
                    Interface.drawScore(score);
                }
                break;
            }
        }
    }


    private static int initSaveScore(String initialName) {
        String initialWord = "Name:";
        TUI.clearLine(TUI.TOPLINE);
        TUI.showCursor();
        TUI.alignLeft(TUI.TOPLINE, initialWord);
        TUI.paddingLeft(TUI.TOPLINE, initialName, initialWord.length());
        TUI.moveCursor(TUI.TOPLINE, initialWord.length() + initialName.length());
        return initialWord.length() + initialName.length();
    }

    private static void teste(String name, int padding, int cursorPosition) {
        TUI.paddingLeft(TUI.TOPLINE, name, padding);
        TUI.moveCursor(TUI.TOPLINE, padding + cursorPosition);
    }


    public static void saveScore() {
        // 2 change letter to the next one
        // 8 change letter to the previous one
        // 6 shifts cursor right
        // 4 shifts cursor left
        // 5 accepts the word
        // * deletes if cursor is position in last letter
        // 8 chars max
        StringBuilder name = new StringBuilder().append('A');
        int padding = initSaveScore(name.toString());
        int cursorPosition = name.length() - 1;
        char key;
        while (true) {
            key = TUI.readInput(100);
            if (key == '5')
                break;
            switch (key) {
                case '2':
                    if (name.charAt(cursorPosition) < 'Z') {
                        name.setCharAt(cursorPosition, (char) (name.charAt(cursorPosition) + 1));
                        teste(name.toString(), padding, cursorPosition);
                    }
                    break;
                case '8':
                    if (name.charAt(cursorPosition) > 'A') {
                        name.setCharAt(cursorPosition, (char) (name.charAt(cursorPosition) - 1));
                        teste(name.toString(), padding, cursorPosition);
                    }
                    break;
                case '4':
                    if (cursorPosition > 0)
                        TUI.moveCursor(TUI.TOPLINE, padding + --cursorPosition);
                    break;
                case '6':
                    if (cursorPosition < 7) {
                        TUI.moveCursor(TUI.TOPLINE, padding + ++cursorPosition);
                        if (cursorPosition > name.length() - 1) {
                            name.append('A');
                            teste(name.toString(), padding, cursorPosition);
                        }
                    }
                    break;
                case '*':
                    if (cursorPosition == name.length() - 1 && cursorPosition > 0) {
                        name.deleteCharAt(cursorPosition--);
                        teste(name.toString() + " ", padding, cursorPosition);
                    }
                    break;
            }
            Time.sleep(100);
        }
        TUI.hideCursor();
        leaderboard.add(name.toString(), score);
    }


    public static void showScore() {
        String leftText = "";
        String rightText = Integer.toString(leaderboard.getScore(scoreCounter));
        if (scoreCounter < 10)
            leftText += "0";
        leftText += scoreCounter + "-" + leaderboard.getName(scoreCounter);
        Interface.drawLeaderBoard(leftText, rightText);
        if (scoreCounter == leaderboard.size())
            scoreCounter = 1;
        else
            scoreCounter++;
    }

    public static void resetGame() {
        score = 0;
        for (int i = 0; i < enemyState.length; i++) {
            enemyState[i] = ' ';
        }
        Time.sleep(1000);
    }

    public static void startGame() {
        long time = Time.getTimeInMillis();
        Interface.drawGame(cannonChar, shipChar);
        resetGame();
        char key;
        while (true) {
            key = TUI.readInput(100);
            if (key != 0) {
                prepareShot(key);
                Time.sleep(100);
            }
            if (Time.getTimeInMillis() - time >= 1000) {
                if (checkGameOver() == true)
                    break;
                shiftGameState();
                time = Time.getTimeInMillis();
                if (Math.random() < 0.5)
                    addEnemy();
            }
        }
        TUI.clearLine(TUI.TOPLINE);
        TUI.alignCenter(TUI.TOPLINE, "GAME OVER!");
        Time.sleep(1500);
    }

    private static boolean checkGameOver() {
        return enemyState[0] != ' ';
    }

    private static void addEnemy() {
        char enemy = (char) ('0' + (Math.random() * 10));
        enemyState[enemyState.length - 1] = enemy;
    }

    private static void shiftGameState() {
        System.out.printf(screenStateToString());
        for (int i = 0; i < enemyState.length - 1; i++) {
            enemyState[i] = enemyState[i + 1];
            enemyState[i + 1] = ' ';
        }
        Interface.drawEnemies(screenStateToString());
    }

    private static String screenStateToString() {
        String screeState = "";
        for (int i = 0; i < enemyState.length; i++) {
            screeState += enemyState[i];
        }
        return screeState;
    }
}
