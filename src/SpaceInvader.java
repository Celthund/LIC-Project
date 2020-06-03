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
        TUI.drawMainMenu();
        char key;
        while (true) {
            key = TUI.readUserInput(100);
            if (key == '*') {
                startGame();
                saveScore();
                TUI.drawMainMenu();
            }
            if (Time.getTimeInMillis() - time >= 2000) {
                showScore();
                time = Time.getTimeInMillis();
            }
        }
    }

    public static void parseKeyPress(char key) {
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
        TUI.drawCannon(cannon);
    }

    public static void seekAndDestroy(char enemy) {
        /**
         Remove the first char different from ' ' and removes if it matches with method parameter enemy.
         After removing increase score by a specific amount and call drawScoreLine and drawEnemies.
         **/
        for (int i = 0; i < enemyState.length; i++) {
            if (enemyState[i] != ' ') {
                if (enemyState[i] == enemy) {
                    enemyState[i] = ' ';
                    score += 1 + (enemy - '0');
                    TUI.drawScoreLine(score);
                    TUI.paddingLeft(TUI.TOPLINE, ' ', i + 2);
                }
                break;
            }
        }
    }

    public static void saveScore() {
        String name = TUI.readUsername();
        leaderboard.add(name, score);
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
        TUI.drawLeaderBoard(leftText, rightText);
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
        TUI.drawGame(cannonChar, shipChar);
        clearEnemyState();
        boolean gameOver = false;
        char key;
        while (!gameOver) {
            key = TUI.readUserInput(100);
            if (key != 0) {
                parseKeyPress(key);
                Time.sleep(100);
            }
            if (Time.getTimeInMillis() - time >= 1000) {
                gameOver = checkGameOver();
                time = Time.getTimeInMillis();
                shiftGameState();
                if (Math.random() < 0.5)
                    addEnemy();
                TUI.drawEnemies(String.valueOf(enemyState));
            }
        }
        TUI.drawGameOver();
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
