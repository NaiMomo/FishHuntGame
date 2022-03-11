import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class HighScores {

    private ArrayList<String> tabu;
    private String path = "scores.txt";
    private String [][] tab;

    public HighScores(){
        this.tabu = new ArrayList<String>();
        this.path= path;
        this.tab= new String[10][2];
        //System.out.println(path);

    };


    public ArrayList<String> read(){

        try {
            FileReader reader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] t = line.split("-");
                tabu = (ArrayList<String>) Arrays.asList(t);
                for(String s: tabu){
                    System.out.println(s);
                }

            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Erreur à l’ouverture du fichier");
        }
        return triage();
    }

    public int getSize(){
        System.out.print(tabu.size());
        return tabu.size();
    }

    public ArrayList<String> triage(){
        Collections.sort(this.tabu);
        return tabu;
    }

    public void write(int score){

        try {
            FileWriter fw = new FileWriter(path);
            BufferedWriter writer = new BufferedWriter(fw);
            String s = Integer.toString(score);
            writer.append(s);
            writer.close();
        } catch (IOException ex) {
            System.out.println("Erreur à l’écriture du fichier");
        }
    }


}
