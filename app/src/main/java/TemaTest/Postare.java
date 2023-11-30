package TemaTest;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Postare {
    private String textPostare;

    public Comentariu comentariu;

    Postare(){
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

    public int creatPostareUser(Postare text, Utilizator utilizator) {
        if(text.getTextPostare() == null) {
            //nu s-a primit niciun text
            return 4;
        }
        if(text.verficareLungime()) {
            //postarea e buna
            utilizator.postari.setTextPostare(text.getTextPostare());
            utilizator.postari.introducInFisier(utilizator);
            return 3;
        }
        return 5; //postarea are mai mult de 300 de caractere
    }

    public void stergePost(Utilizator user, Postare text) {

        // citim continut fisier intr-o lista
        List<String> lin = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/TemaTest/post.csv"))) {
            String linie;
            while ((linie = reader.readLine()) != null) {
                lin.add(linie);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            // Verificam daca linia de șters exista
            boolean linieGasita;
            linieGasita = lin.remove(user.userName +"," + text.getTextPostare() + ",");

            if (linieGasita) {
                // Rescrierea conținutului actualizat înapoi în fișier
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/TemaTest/post.csv"))) {
                    for (String liniesec : lin) {
                        writer.write(liniesec);
                        writer.newLine();
                    }
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    Likeable likeable = new Likeable() {

        // facem un fisier cu likeruri unde se tine user name + id postare + valoarea de LIKE = TRUE
        @Override
        public void fisierLikeId(String id, Utilizator user) {
            try (FileWriter fw = new FileWriter("src/main/java/TemaTest/like.csv", true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.print(user.userName + "," +id + "," + "true,");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public int verLike(String idlike, Utilizator user) {
            String line;
            String likes = "true";
            try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/TemaTest/like.csv"))) {
                String splitBy = ",";
                while ((line = br.readLine()) != null) {
                    String[] likeda = line.split(splitBy);
                    //daca  exista returnam mesajul si valoarea 1
                    for (int i = 0; i < likeda.length; i += 3) {
                        if (user.userName.equals(likeda[i]) && idlike.equals(likeda[i + 1]) && likes.equals(likeda[i + 2])) {
                            return 1; // exista like
                        }
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
                    for (int i = 0; i < likeda.length; i += 3)
                        if (idlike.equals(likeda[i+1]) && likes.equals(likeda[i + 2])) {
                            contorlike++;
                        }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  contorlike;
        }
    };

    // varificam daca exista postare cu id-ul dat
    public int verPost(String idpost) {
        String line;
        int contor = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/TemaTest/post.csv"))) {
            while ((line = br.readLine()) != null) {
                for(int i = 0; i < line.length(); i+=2) {
                    contor++;
                    if (contor == Integer.parseInt(idpost)) {
                        return 1; // exista postarea cu id- ul respectiv
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  0; // nu exista postare cu id-ul dat
    }
}
