package TemaTest;

import java.io.*;

public class Utilizator {
    public String userName;
    private String parola;
    public Postare postari;
    private String follow;

    //constructori
    Utilizator() {
    }

//    Utilizator(String name, String parola) {
//        this.setParola(parola);
//        this.userName = name;
//        this.postari = new Postare();
//    }

    public void setParametriUser(String name, String parola) {
        this.setParola(parola);
        this.userName = name;
        this.postari = new Postare();
        this.postari.comentariu = new Comentariu();
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getParola() {
        return parola;
    }

    public void setFollow(String follow) {
        this.follow = follow;
    }
    public String getFollow(){
        return follow;
    }

    public int verificareUtilizatorExistent(String name, String parola) {
        //verificam daca exista deja in baza de date
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/TemaTest/user.csv"))) {
            String line;
            String splitBy =",";
            while ((line = br.readLine()) != null)
            {
                String[] usernume = line.split(splitBy);
                //daca  exista returnam mesajul si valoarea 1
                for(int i = 0; i < usernume.length; i+=2)
                if(name.equals(usernume[i]) && parola.equals(usernume[i+1])) {
                    return 1;
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return 2; //daca nu exista returnam 2
    }

    public void introducInFisier ( Utilizator user) {
        try (FileWriter fw = new FileWriter("src/main/java/TemaTest/user.csv", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
             out.print(user.userName + "," + user.getParola()  + ",");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void introducInFisierFolloweri(String user) {
        try (FileWriter fw = new FileWriter("src/main/java/TemaTest/follow.csv", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
             out.print(user + ",");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
