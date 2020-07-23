import CoinAcceptor.CoinAcceptor;
import Scores.Scores;
import KBD.KBD;
import Statistics.Statistics;
import TUI.TUI;
import SoundGenerator.SoundGenerator;
import M.M;
import isel.leic.utils.Time;

public class SpaceInvader{
    private static final int MENU_TIMEOUT = 3000, SCORE_CHANGE = 3000;
    private static int score = 0;
    private static char[] enemyState = new char[TUI.COLUMNS - 2];
    private static final char cannonChar = ']';
    private static final char shipChar = 0;
    private static char cannon = cannonChar;
    private static Scores leaderboard = new Scores();
    private static Statistics statistics = new Statistics();
    private static int scoreCounter = 1, credits = 0;

    private enum Menus {
        MAIN_MENU, MAINTENANCE, STATISTIC, CLEAR, SHUTDOWN
    }

    public static void main(String[] args) {
        /**
         Main cycle that handles the initial menu of the application.
         **/
        TUI.init(); // inicializa TUI
        SoundGenerator.init(); // inicializa SOUNDGENERATOR
        SoundGenerator.setVolume(3);
        Menus currMenu = Menus.MAIN_MENU;
        long timeScore = Time.getTimeInMillis();
        long timeReturn = Integer.MIN_VALUE;
        boolean showScore = true;
        char key;
        TUI.drawMainMenu();
       //   |_Space_Invaders_|
       //   |________________|

        while (true) {
            key = TUI.readUserInput(100); // LE TECLA

            //
            if (timeReturn != Integer.MIN_VALUE){

                //QUAL O MENU A DESENHAR M OR MAIN MENU APOS O TIMEOUT INICIAL DO SPACEINVADERS 3SEC
                if (Time.getTimeInMillis() - timeReturn >= MENU_TIMEOUT) {
                    timeReturn = Integer.MIN_VALUE;
                    if (M.checkMButton()){
                        currMenu = Menus.MAINTENANCE;
                        TUI.drawMaintenance();
                         //  |__On Maintenance __|
                         //  |__*-Count #-shutD__|
                    }
                    else {
                        currMenu = Menus.MAIN_MENU;
                        TUI.drawMainMenu();
                        //   |__Space_Invaders__|
                        //   |__________________|
                    }
                }
                //SE O MENU FOR CLEAR
                if (currMenu == Menus.CLEAR){
                    if (key == '5') {
                        statistics.reset();
                        timeReturn -= MENU_TIMEOUT;
                    } else if (key != KBD.NONE) {
                        timeReturn -= MENU_TIMEOUT;
                    }
                }
                //SE O MENU FOR STATISTICAS
                if (currMenu == Menus.STATISTIC){
                    if (key == '#') {
                        TUI.drawClearCounters();
                        //  |__Clear Counters__|
                        //  |__5-Yes other-No__|
                        currMenu = Menus.CLEAR;
                        timeReturn = Time.getTimeInMillis();
                    } else if (key != KBD.NONE) {
                        timeReturn -= MENU_TIMEOUT;
                    }
                }
                //SE O MENU FOR SHUTDOWN
                if (currMenu == Menus.SHUTDOWN){
                    if (key == '5') {
                        statistics.save();
                        leaderboard.save();
                        TUI.shutdown();
                        System.exit(0);
                        // |____Shutdown______|
                        // |__5-Yes_other-No__|
                    } else if (key != KBD.NONE) {
                        timeReturn -= MENU_TIMEOUT;
                    }
                }
            } else {
                //M SWITCH ESTA ACTIVO
                if (M.checkMButton()){
                    if (currMenu == Menus.MAIN_MENU){
                        currMenu = Menus.MAINTENANCE;
                        TUI.drawMaintenance();
                        //  |__On Maintenance __|
                        //  |__*-Count #-shutD__|
                    }
                    // *-Count
                    if (key == '*') {
                        currMenu = Menus.STATISTIC;
                        TUI.drawStatistics(statistics);
                        //  |Games: _______|
                        //  |Coins:________|
                        timeReturn = Time.getTimeInMillis();

                        //#-shutD
                    } else if (key == '#') {
                        currMenu = Menus.SHUTDOWN;
                        TUI.drawShutdown();
                        //  |_____Shutdown_____|
                        //  |__5-Yes_other-No _|
                        timeReturn = Time.getTimeInMillis();

                        //Outra tecla faz iniciar um JOGO GRATUITO
                    } else if (key != KBD.NONE){
                        startGame();
                        if (M.checkMButton()){
                            TUI.drawMaintenance();
                        } else {
                            TUI.drawMainMenu();
                        }
                    }
                    //M SWITCH NAO ESTA ACTIVO
                } else {
                    // SE DURANTE 100ms caiu moeda ACTUALIZA CREDITOS
                    if (CoinAcceptor.waitCoin(100)){
                        statistics.coins++;
                        credits += 2;
                        TUI.drawAvailableCredits(credits);
                        // |________________|
                        // |__Credits:X$":__|
                        showScore = true;
                    }
                    // SE AINDA TIVER MAINTENANCE PASSA PARA MAIN MENU
                    if (currMenu == Menus.MAINTENANCE){
                        currMenu = Menus.MAIN_MENU;
                        TUI.drawMainMenu();
                    }
                    // INICIA O JOGO USANDO 1 CREDITO E CONTABILIZA NUMERO DE JOGOS
                    if (key == '*') {
                        if (credits > 0){
                            startGame(); //INICIALIZA JOGO
                            saveScore();
                            statistics.games++;
                            credits--;
                        }
                    }
                    // ALTERNA ENTRE MOSTRAR OS SCORES E OS CREDITOS DISPONIVEIS
                    if (Time.getTimeInMillis() - timeScore >= SCORE_CHANGE) {
                        if (showScore){
                            showScore();
                            showScore = false;
                        } else {
                            TUI.drawAvailableCredits(credits);
                            showScore = true;
                        }
                        timeScore = Time.getTimeInMillis();
                    }
                }

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
        for (int i = 0; i < enemyState.length; i++) { //Para cada pos de enemy da esquerda para a direita
            if (enemyState[i] != ' ') {               // Se a pos tiver um numero
                if (enemyState[i] == enemy) {         // Se essa pos tiver o mesmo nome que a tecla
                    enemyState[i] = ' ';              // Limpa esse enemy
                    score += 1 + (enemy - '0');       // Aumenta o Score
                    TUI.drawScoreLine(score);         // Actualiza Score
                    TUI.paddingLeft(TUI.TOPLINE, ' ', i + 2); //Mete ' ' apos a SHIP
                }
                break;
            }
        }
    }

    public static void saveScore() {
        String name = TUI.readUsername();
        leaderboard.add(name, score);
        TUI.drawMainMenu();
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
        //  |]}_________________|
        //  |Score:0____________|
        clearEnemyState();
        boolean gameOver = false;
        char key;
        while (!gameOver) {
            key = TUI.readUserInput(100);
            if (key != 0) {
                parseKeyPress(key); //Actualiza num da tecla pressionada, faz seek and destroy
                //Seek and destroy - Remove the first char different from ' ' and removes if it matches with method parameter enemy.
                //After removing increase score by a specific amount and call drawScoreLine and drawEnemies.
                Time.sleep(100);
            }
            if (Time.getTimeInMillis() - time >= 1000) {
                gameOver = checkGameOver();
                time = Time.getTimeInMillis();
                shiftGameState(); // Shifts to the left all chars in enemyState array, discarding the first position.
                if (Math.random() < 0.5)
                    addEnemy();  //Add a random number between 0-9 to the end of the enemyState array.
                TUI.drawEnemies(String.valueOf(enemyState));
                //  |____2315901710410928|
                //  |____________________|
            }
        }
        TUI.drawGameOver();
        //  |_____GAME OVER! ____|
        //  |____________________|
        SoundGenerator.play(1);
        Time.sleep(1000);
        SoundGenerator.stop();
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
