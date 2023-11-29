package TemaTest;

import java.io.*;

public class Comentariu {

    public String textComentariu;

    Comentariu() {
    }

    public void setTextComentariu(String comentariu) {
        this.textComentariu = comentariu;
    }

    public String getTextComentariu() {
        return textComentariu;
    }

    public boolean verficareLungime() {
        return textComentariu.length() < 300;
    }

    public void introducInFisier(Utilizator user, String id) {
        try (FileWriter fw = new FileWriter("src/main/java/TemaTest/comentarii.csv", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.print(user.userName + "," + id + "," + user.postari.comentariu.getTextComentariu() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // val == 1 ->verifica comentariu si dupa user si id
    // val == 2 -> verifica comentariu doar dupa id
    public int verComentariu(String idcoment, Utilizator user, int val) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/TemaTest/comentarii.csv"))) {
            String splitBy = ",";
            while ((line = br.readLine()) != null) {
                String[] likeda = line.split(splitBy);
                //daca  exista returnam mesajul si valoarea 1
                for (int i = 0; i < likeda.length; i += 3)
                    if (val == 1) {
                        if (user.userName.equals(likeda[i]) && idcoment.equals(likeda[i + 1])) {
                            return 3; // exista comentariu
                        }
                    } else if(val == 2) {
                        // verifica doar dupa id
                        if (idcoment.equals(likeda[i + 1])) {
                            return 3; // exista comentariu
                        }
                    }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  5; // nu are comentariu
    }

    Likeable likeable = new Likeable() {
        @Override
        public void fisierLikeId(String id, Utilizator user) {
            try (FileWriter fw = new FileWriter("src/main/java/TemaTest/likeComentariu.csv", true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.print(id + "," + "true,");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public int verLike(String idcoment, Utilizator user) {
            String line;
            String likes = "true";
            try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/TemaTest/likeComentariu.csv"))) {
                String splitBy = ",";
                while ((line = br.readLine()) != null) {
                    String[] likeda = line.split(splitBy);
                    //daca  exista returnam mesajul si valoarea 1
                    for (int i = 0; i < likeda.length; i += 2)
                        if (idcoment.equals(likeda[i]) && likes.equals(likeda[i + 1])) {
                            return 3; // exista like
                        }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  5; // nu are like
        }

        @Override
        public int numberlike(String idlike) {
            String line;
            String likes = "true";
            int contorlike = 0;
            try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/TemaTest/likeComentariu.csv"))) {
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
}
