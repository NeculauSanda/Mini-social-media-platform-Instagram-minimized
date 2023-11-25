package TemaTest;

import java.io.*;

public class Postare {
    private String textPostare;

    public boolean like;

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

    // facem un fisier cu likeruri unde se tine id posterii + valoarea de LIKE = TRUE
    Likeable likeable = new Likeable() {
        @Override
        public void fisierLikeId(String id) {
            try (FileWriter fw = new FileWriter("src/main/java/TemaTest/like.csv", true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.print(id + "," + "true,");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public int verLike(String idlike) {
            String line;
            String likes = "true";
            try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/TemaTest/like.csv"))) {
                String splitBy = ",";
                while ((line = br.readLine()) != null) {
                    String[] likeda = line.split(splitBy);
                    //daca  exista returnam mesajul si valoarea 1
                    for (int i = 0; i < likeda.length; i += 2)
                        if (idlike.equals(likeda[i]) && likes.equals(likeda[i + 1])) {
                            return 1; // i a fost deja dat like
                        }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  0; // nu are like
        }
    };
//    public void fisierLikeId(Utilizator user, String id) {
//        try (FileWriter fw = new FileWriter("src/main/java/TemaTest/post.csv", true);
//             BufferedWriter bw = new BufferedWriter(fw);
//             PrintWriter out = new PrintWriter(bw)) {
//             out.print(id + "," + user.postari.like);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
