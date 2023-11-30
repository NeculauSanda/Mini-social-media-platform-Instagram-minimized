package TemaTest;

import java.io.*;
import java.util.Objects;

public class Comentariu {

    public String textComentariu;

    Comentariu() {
    }

    public String getTextComentariu() {
        return textComentariu;
    }


    public void introducInFisier(Utilizator user, String id) {
        try (FileWriter fw = new FileWriter("src/main/java/TemaTest/comentarii.csv", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.print(user.userName + "," + id + "," + user.postari.comentariu.getTextComentariu() + ",");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int creatComentPostare(String text, Utilizator utilizator, String id) {
        if(text == null) {
            //nu s-a primit niciun text
            return 4;
        }
        if(text.length() < 300) {
            //postarea e buna
            utilizator.postari.comentariu.textComentariu = text;
            utilizator.postari.comentariu.introducInFisier(utilizator, id);
            return 3; // actiunea s-a realizat cu succes
        }
        return 5; //postarea are mai mult de 300 de caractere
    }


    // val == 1 ->verifica comentariu si dupa user si id
    // val == 2 -> verifica comentariu doar dupa id
    public int verComentariu(String idcoment, Utilizator user, int val) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/TemaTest/comentarii.csv"))) {
            String splitBy = ",";
            while ((line = br.readLine()) != null) {
                String[] likeda = line.split(splitBy);
                int contor = 0;
                //daca  exista returnam mesajul si valoarea 1
                for (int i = 0; i < likeda.length; i += 3) {
                    contor++;
                    if (val == 1) {
                        if (user.userName.equals(likeda[i]) && idcoment.equals(likeda[i + 1])) {
                            return 3; // exista comentariu
                        }
                    } else if (val == 2) {
                        // verifica doar dupa id
                        if (contor ==Integer.parseInt(idcoment)) {
                            return 3; // exista comentariu
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  5; // nu are comentariu
    }

    public void stergeCom(Utilizator user, String id) {
        try {
            BufferedReader com = new BufferedReader(new FileReader("src/main/java/TemaTest/comentarii.csv"));
            PrintWriter comtemp = new PrintWriter(new FileWriter("src/main/java/TemaTest/comentarii.csv" + ".temp"));
            String linie;
            while ((linie = com.readLine()) != null) {
                // Verifica daca linia începe cu cuvintele specificate
                if (!linie.startsWith(user.userName +"," + id + ",")) {
                    comtemp.println(linie);
                }
            }
            comtemp.close();
            com.close();

            // Stergem fisierul original
            if (!new File("src/main/java/TemaTest/comentarii.csv").delete()) {
                return;
            }

            // Redenumim fișierul temporar la numele original
            boolean b = new File("src/main/java/TemaTest/comentarii.csv" + ".temp").renameTo(new File("src/main/java/TemaTest/comentarii.csv"));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Likeable likeable = new Likeable() {
        @Override
        public void fisierLikeId(String id, Utilizator user) {
            try (FileWriter fw = new FileWriter("src/main/java/TemaTest/likeComentariu.csv", true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                out.print( user.userName + "," + id + "," + "true,");
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
                    for (int i = 0; i < likeda.length; i += 3)
                        if (Objects.equals(user.userName,likeda[i]) && Objects.equals(idcoment, likeda[i+1]) && Objects.equals(likeda[i+2], likes)) {
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
}
