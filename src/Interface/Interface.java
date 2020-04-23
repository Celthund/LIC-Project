package Interface;

import TUI.TUI;
import isel.leic.utils.Time;

public class Interface {

    public static void drawStatistics(){

    }

    public static void drawLeaderBoard(String name, String score){
        TUI.clearLine(TUI.BOTTOMLINE);
        Time.sleep(100);
        TUI.alignLeft(TUI.BOTTOMLINE, name);
        TUI.alignRight(TUI.BOTTOMLINE, score);
    }

    public static void drawEnemies(String enemies){
        TUI.alignRight(TUI.TOPLINE, enemies);
    }

    public static void drawCannon(char key){
        TUI.alignLeft(TUI.TOPLINE, key);
    }

    public static void drawShip(char key){
        TUI.paddingLeft(TUI.TOPLINE, key, 1);
    }

    public static void drawMainMenu(){
        TUI.clear();
        TUI.alignCenter(TUI.TOPLINE, "Space Invaders");
    }

    public static void drawGame(char Cannon, char Ship){
        TUI.clear();
        drawCannon(Cannon);
        drawShip(Ship);
        TUI.alignLeft(TUI.BOTTOMLINE,"Score:");
        drawScore(0);
    }

    public static void drawScore(int score){
        TUI.paddingLeft(TUI.BOTTOMLINE, "" + score, 6);
    }

    public static void drawSaveScore(){

    }
}
