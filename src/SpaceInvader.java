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
        /**
         Main cycle that handles the initial menu of the application.
         **/
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
        /**
         Handles key presses in-game.
         **/
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
        /**
         Remove the first char different from ' ' and removes if it matches with method parameter enemy.
         After removing increase score by a specific amount and call drawScoreLine and drawEnemies.
         **/
        for (int i = 2; i < enemyState.length; i++) {
            if (enemyState[i] != ' ') {
                if (enemyState[i] == enemy) {
                    enemyState[i] = ' ';
                    score += 1 + (enemy - '0');
                    Interface.drawScoreLine(score);
                    Interface.drawEnemies(String.valueOf(enemyState));
                }
                break;
            }
        }
    }

    public static void saveScore() {
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
        Interface.drawSaveScoreInput();
        Interface.drawSaveScoreName(name.toString());
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
                        Interface.drawSaveScoreName(name.toString());
                    }
                    break;
                case '8':
                    if (name.charAt(cursorPosition) > 'A') {
                        name.setCharAt(cursorPosition, (char) (name.charAt(cursorPosition) - 1));
                        Interface.drawSaveScoreName(name.toString());
                    }
                    break;
                case '4':
                    if (cursorPosition > 0)
                        TUI.moveCursor(TUI.TOPLINE, Interface.savePrefix.length() + --cursorPosition);
                    break;
                case '6':
                    if (cursorPosition < 7) {
                        TUI.moveCursor(TUI.TOPLINE, Interface.savePrefix.length() + ++cursorPosition);
                        if (cursorPosition > name.length() - 1) {
                            name.append('A');
                            Interface.drawSaveScoreName(name.toString());
                        }
                    }
                    break;
                case '*':
                    if (cursorPosition == name.length() - 1 && cursorPosition > 0) {
                        name.deleteCharAt(cursorPosition--);
                        Interface.drawSaveScoreName(name.toString());
                    }
                    break;
            }
            Time.sleep(100);
        }
        TUI.hideCursor();
        leaderboard.add(name.toString(), score);
    }


    public static void showScore() {
        /**
        Handle showing the score in main menu.
         **/
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

    public static void clearEnemyState() {
        /**
        Clear enemyState array.
         **/
        score = 0;
        for (int i = 0; i < enemyState.length; i++) {
            enemyState[i] = ' ';
        }
        Time.sleep(1000);
    }

    public static void startGame() {
        /**
         Handles key presses in game.
         **/
        long time = Time.getTimeInMillis();
        Interface.drawGame(cannonChar, shipChar);
        clearEnemyState();
        boolean gameOver = false;
        char key;
        while (!gameOver) {
            key = TUI.readInput(100);
            if (key != 0) {
                prepareShot(key);
                Time.sleep(100);
            }
            if (Time.getTimeInMillis() - time >= 1000) {
                gameOver = checkGameOver();
                time = Time.getTimeInMillis();
                shiftGameState();
                if (Math.random() < 0.5)
                    addEnemy();
                Interface.drawEnemies(String.valueOf(enemyState));
            }
        }
        TUI.clearLine(TUI.TOPLINE);
        TUI.alignCenter(TUI.TOPLINE, "GAME OVER!");
        Time.sleep(1500);
    }

    private static boolean checkGameOver() {
        /**
         Checks if game is over by validating if the first position is diferent ' '.
         **/
        return enemyState[0] != ' ';
    }

    private static void addEnemy() {
        /**
         Add a random number between 0-9 to the end of the enemyState array.
         **/
        char enemy = (char) ('0' + (Math.random() * 10));
        enemyState[enemyState.length - 1] = enemy;
    }

    private static void shiftGameState() {
        /**
         Shifts to the left all chars in enemyState array, discarding the first position.
         **/
        for (int i = 0; i < enemyState.length - 1; i++) {
            enemyState[i] = enemyState[i + 1];
            enemyState[i + 1] = ' ';
        }
    }
}
