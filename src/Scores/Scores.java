package Scores;

import FileAccess.FileAccess;
import java.util.ArrayList;

public class Scores {

    private int maxEntries = 20;

    class Entry {
        /**
         Class used for arraylist to keep each entry of the leaderboard.
         **/
        int score;
        String name;
        public Entry(String name, int score){ //construtor
            this.name = name;
            this.score = score;
        }
    }

    private final FileAccess file = new FileAccess("SIG_scores.txt");
    private ArrayList<Entry> leaderBoard= new ArrayList<Entry>(); //Arraylist de Entrys - leaderboard actual

    public Scores(){
        /**
         Read leaderboard from file and create a ArrayList with each entry.
         **/
        ArrayList<String> text = file.read(); //Arraylist temporario para ler o file scores.txt
        for (String entry: text){               // por cada string ex. "499;SEAE"
            leaderBoard.add(parseEntry(entry)); //adiciona uma entry nova a leaderboard ex. parseEntry("499;SEAE")
        }
    }

    private Entry parseEntry(String text){
        /**
         Parse a line with the following structure "499;SEAE" to a Entry.
         **/
        String[] arrString = text.split(";"); //faz a separaçao em duas strings {"499", "SEAE"}
        return new Entry(arrString[1], Integer.parseInt(arrString[0])); //retorna uma entry nova Entry("SEAE","499") - Entry(String name, int score)
    }

    public void save(){
        /**
            Saves the current leaderboard to a file.
         **/
        ArrayList<String> text = new ArrayList();
        for(Entry entry: leaderBoard){
            text.add(entry.score + ";" + entry.name); //adiciona no Arraylist temp as entrys de leaderboard para Arraylist de elems "499;SEAE"
        }
        file.write(text);
    }

    public void add(String name, int score){
        /**
            Add a given name and score to the leaderboard, this will be saved in order of the score.
         **/
        if (leaderBoard.size() == maxEntries){          //se ainda houver espaço na leaderboard
            if (leaderBoard.get(maxEntries - 1).score > score) //se o novo score a adicionar for menor que o ultimo elemento - PARAR NO ADD
                return;
            else
                leaderBoard.remove(maxEntries - 1); //ha um novo score! Remover o ultimo score de leaderboard!
        }
        for(Entry entry: leaderBoard){      //varrer leaderboard
            if (score > entry.score) {      //se novo score for maior que o curr
                leaderBoard.add(leaderBoard.indexOf(entry), new Entry(name, score)); // coloca nova entry no index do curr que é menor que o score novo
                                                                                    // NOTA: curr e anteriores a si sao empurrados para as posiçoes abaixo!
                return;
            }
        }
        leaderBoard.add(new Entry(name, score)); // caso onde o novo score fica na ultima posição
    }

    public String getName(int pos){
        /**
            Return a name from a given position.
         **/
        return leaderBoard.get(pos - 1).name;
    }

    public int getScore(int pos){
        /**
         Return a score from a given position.
         **/
        return leaderBoard.get(pos - 1).score;
    }

    public int size(){
        /**
         Return the current size of the leaderboard.
         **/
        return leaderBoard.size();
    }

}
