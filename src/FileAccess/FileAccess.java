package FileAccess;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileAccess {
    private String file;

    public void main(String[] args) {
        ArrayList data = read();
        write(data);
    }

    public FileAccess(String file) {
        this.file = file;
    } //construtor com parametro do nome do ficheiro

    public void write(ArrayList<String> lines) { // escreve tudo o que tiver no arraylist para o file
        /**
         Write each String of a given ArrayList to a this.file separate by a newline.
         **/
        PrintWriter out = null;
        try {
            out = new PrintWriter(new FileWriter(file));
        } catch (
                IOException e) {
            e.printStackTrace();
            System.out.println("Unable to save: " + file);
            System.exit(-2);
        }

        //ITERA ARRAYLIST E COLOCA CADA lines NUMA LINHA NOVA DO FILE
        for (int i = 0; i < lines.size(); i++){
            if (i < lines.size() - 1){
                out.println(lines.get(i));
            } else {
                out.print(lines.get(i));
            }
        }
        out.flush();
        out.close();
    }

    public ArrayList<String> read() {
        /**
         Read and append each line of this.file to a ArrayList of strings.
         **/
        Scanner in = null;
        ArrayList<String> result = new ArrayList<>(); //Inicializa Arraylist
        try {
            in = new Scanner(new FileReader(file));
        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File missing: " + file);
            System.exit(-1);
        }
        while (in.hasNextLine()) {          //enquanto houver linha no ficheiro
            result.add(in.nextLine());      //adiciona ao ArrayList criado
        }
        in.close();
        return result;                      // retorna ArrayList criado
    }
}
