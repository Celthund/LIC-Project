package Statistics;

import FileAccess.FileAccess;

import java.util.ArrayList;

public class Statistics {
    private final FileAccess file = new FileAccess("statistics.txt");
    private int COIN_POS = 0, GAMES_POS = 1;
    public int coins = 0, games = 0;

    public Statistics(){
        ArrayList<String> lines = file.read();
        if (lines.size() == 2){
            coins = Integer.parseInt(lines.get(COIN_POS));
            games = Integer.parseInt(lines.get(GAMES_POS));
        }
    }

    public void save(){
        /**
         Saves coins and games counter to a file.
         **/
        ArrayList<String> text = new ArrayList();
        text.add(""+coins);
        text.add(""+games);
        file.write(text);
    }

    public void reset(){
        /**
         Resets coins and games counter to zero.
         **/
        coins = 0;
        games = 0;
    }

}
