package Statistics;

import FileAccess.FileAccess;

import java.util.ArrayList;

public class Statistics {
    private final FileAccess file = new FileAccess("statistics.txt");
    private int COIN_POS = 0, GAMES_POS = 1;
    public int coins = 0, games = 0;


    public Statistics(){
        ArrayList<String> lines = file.read(); //le o ficheiro e coloca num Arraylist temporario
        if (lines.size() == 2){                 //se o ficheiro tiver as duas linhas
            coins = Integer.parseInt(lines.get(COIN_POS)); // coins = linha 0
            games = Integer.parseInt(lines.get(GAMES_POS));// games = linha 1
        }
    }

    public void save(){
        /**
         Saves coins and games counter to a file.
         **/
        ArrayList<String> text = new ArrayList(); //cria um Arraylist
        text.add(""+coins); //adiciona coins
        text.add(""+games); //adiciona games
        file.write(text);   // escreve no ficheiro - file.write escreve cada index em cada linha do ficheiro
                            // portanto no fim temos sempre 2 linhas no ficheiro statistics.txt
    }

    public void reset(){
        /**
         Resets coins and games counter to zero.
         **/
        coins = 0;
        games = 0;
    }

}
