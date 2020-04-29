package Interface;

import TUI.TUI;
import isel.leic.utils.Time;

public class Interface {

    public static final String savePrefix = "Name:";
    public static final String scorePrefix = "Score:";

    public static void drawLeaderBoard(String name, String score){
        /**
         Draw leaderboard line. Used in main menu.
         Example in bottom line:
         ______________________
         |____________________|
         |01-ASD___________123|
         **/
        TUI.clearLine(TUI.BOTTOMLINE);
        Time.sleep(100);
        TUI.alignLeft(TUI.BOTTOMLINE, name);
        TUI.alignRight(TUI.BOTTOMLINE, score);
    }

    public static void drawEnemies(String enemies){
        /**
         Draw enemy line.
         Example in top line number part:
         ______________________
         |____2315901710410928|
         |____________________|
         **/
        TUI.alignRight(TUI.TOPLINE, enemies);
    }

    public static void drawCannon(char key){
        /**
         Draw enemy line
         Example:
         ______________________
         |]___________________|
         |____________________|
         **/
        TUI.alignLeft(TUI.TOPLINE, key);
    }

    public static void drawShip(char key){
        /**
         Draw enemy line
         Example:
         ______________________
         |_}__________________|
         |____________________|
         **/
        TUI.paddingLeft(TUI.TOPLINE, key, 1);
    }

    public static void drawMainMenu(){
        /**
         Draw leaderboard line. Used in main menu.
         Example:
         ______________________
         |____Space_Invaders__|
         |____________________|
         **/
        TUI.clear();
        TUI.alignCenter(TUI.TOPLINE, "Space Invaders");
    }

    public static void drawGame(char Cannon, char Ship){
        /**
         Draw initial game line.
         Example:
         ______________________
         |]}__________________|
         |Score:0____________|
         **/
        TUI.clear();
        drawCannon(Cannon);
        drawShip(Ship);
        TUI.alignLeft(TUI.BOTTOMLINE, scorePrefix);
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
        TUI.paddingLeft(TUI.BOTTOMLINE, "" + score, scorePrefix.length());
    }

    public static void drawSaveScoreName(String name) {
        /**
         Draw save score input name.
         Example:
         ______________________
         |_____AAA____________|
         |____________________|
         **/
        TUI.paddingLeft(TUI.TOPLINE, name, savePrefix.length());
        TUI.moveCursor(TUI.TOPLINE, savePrefix.length() + name.length());
    }

    public static void drawSaveScoreInput(){
        /**
         Draw save score input prefix.
         Example:
         ______________________
         |Name:_______________|
         |____________________|
         **/
        TUI.clearLine(TUI.TOPLINE);
        TUI.showCursor();
        TUI.alignLeft(TUI.TOPLINE, savePrefix);
    }

}
