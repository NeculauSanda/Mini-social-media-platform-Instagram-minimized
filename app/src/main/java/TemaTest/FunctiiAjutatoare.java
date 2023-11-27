package TemaTest;

import java.io.IOException;
import java.io.PrintWriter;

public class FunctiiAjutatoare {
    public FunctiiAjutatoare() {
    }

    public static String extragerePN(String rowstring, int lungime) {
        String s = rowstring.replaceAll("'", "");
        return  s.substring(lungime);
    }

    public static void cleanFile(){
        try {
            PrintWriter writer = new PrintWriter("src/main/java/TemaTest/user.csv");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            PrintWriter writer = new PrintWriter("src/main/java/TemaTest/post.csv");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            PrintWriter writer = new PrintWriter("src/main/java/TemaTest/follow.csv");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            PrintWriter writer = new PrintWriter("src/main/java/TemaTest/like.csv");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            PrintWriter writer = new PrintWriter("src/main/java/TemaTest/comentarii.csv");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            PrintWriter writer = new PrintWriter("src/main/java/TemaTest/likeComentariu.csv");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
