package TemaTest;

import java.io.*;

public class Postare {
    private String textPostare;

    public Comentariu comentariu;

    Postare(){
    }
    Postare(String text) {
        setTextPostare(text);
    }

    public void setComentariu(Comentariu comentariu) {
        this.comentariu = comentariu;
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
             out.print(user.userName +","+ user.postari.getTextPostare() + ",");
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
                            return 1; // exista like
                        }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  0; // nu are like
        }

        @Override
        public int numberlike(String idlike) {
            String line;
            String likes = "true";
            int contorlike = 0;
            try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/TemaTest/like.csv"))) {
                String splitBy = ",";
                while ((line = br.readLine()) != null) {
                    String[] likeda = line.split(splitBy);
                    //daca  exista returnam mesajul si valoarea 1
                    for (int i = 0; i < likeda.length; i += 2)
                        if (idlike.equals(likeda[i]) && likes.equals(likeda[i + 1])) {
                            contorlike++;
                        }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  contorlike;
        }
    };

    // varificam daca exista postare cu id-ul at
    public int verPost(String idpost) {
        String line;
        int contor = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/TemaTest/post.csv"))) {
            String splitBy = ",";
            while ((line = br.readLine()) != null) {
                contor++;
                if (contor == Integer.parseInt(idpost)) {
                    return 1; // exista postarea cu id- ul respectiv
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  0; // nu are like
    }
}
