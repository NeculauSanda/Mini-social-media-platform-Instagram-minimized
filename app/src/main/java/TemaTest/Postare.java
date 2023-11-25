package TemaTest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Postare {
    private String textPostare;

    Postare(){
    }
    Postare(String text) {
        setTextPostare(text);
    }

    public void setTextPostare(String text) {
        this.textPostare = text;
    }

    public String getTextPostare() {
        return textPostare;
    }

    public boolean verficareLungime() {
        return textPostare.length() < 300;
    }

    public void introducInFisier(Utilizator user) {
        try (FileWriter fw = new FileWriter("src/main/java/TemaTest/post.csv", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
             out.print(user.postari.getTextPostare() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
