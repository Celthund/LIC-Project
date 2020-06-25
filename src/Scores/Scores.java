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
        public Entry(String name, int score){
            this.name = name;
            this.score = score;
        }
    }

    private final FileAccess file = new FileAccess("SIG_scores.txt");
    private ArrayList<Entry> leaderBoard= new ArrayList<Entry>();

    public Scores(){
        /**
         Read leaderboard from file and create a ArrayList with each entry.
         **/
        ArrayList<String> text = file.read();
        for (String entry: text){
            leaderBoard.add(parseEntry(entry));
        }
    }

    private Entry parseEntry(String text){
        /**
         Parse a line with the following structure "499;SEAE" to a Entry.
         **/
        String[] arrString = text.split(";");
        return new Entry(arrString[1], Integer.parseInt(arrString[0]));
    }

    public void save(){
        /**
            Saves the current leaderboard to a file.
         **/
        ArrayList<String> text = new ArrayList();
        for(Entry entry: leaderBoard){
            text.add(entry.score + ";" + entry.name);
        }
        file.write(text);
    }

    public void add(String name, int score){
        /**
            Add a given name and score to the leaderboard, this will be saved in order of the score.
         **/
        if (leaderBoard.size() == maxEntries){
            if (leaderBoard.get(maxEntries - 1).score > score)
                return;
            else
                leaderBoard.remove(maxEntries - 1);
        }
        for(Entry entry: leaderBoard){
            if (score > entry.score) {
                leaderBoard.add(leaderBoard.indexOf(entry), new Entry(name, score));
                return;
            }
        }
        leaderBoard.add(new Entry(name, score));
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
