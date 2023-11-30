package TemaTest;

import java.io.*;

public class Utilizator {
    public String userName;
    private String parola;
    public Postare postari;
    private String follow;

    //constructor
    Utilizator() {
    }

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

    public static int creatUser(String user, String parola, Utilizator utilizator){
        // verificare user
        if (parola == null && user != null) {
            return 3; // parola nu a fost data
        } else if (user == null) {
            return 4; //userul nu a fost dat
        } else {
            // verificam daca numele si parola utilizatorului exista
            int result = utilizator.verificareUtilizatorExistent(user, parola);
            // val == 2 --> nu exista user, il introducem
            if (result == 2) {
                utilizator.setParametriUser(user, parola);
                utilizator.introducInFisier(utilizator);
                return 2;
            } else if(result == 1) {
                return 1;
            }
        }
        return 0;
    }

    public int verificareUtilizatorExistent(String name, String parola) {
        //verificam daca exista deja in baza de date
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/TemaTest/user.csv"))) {
            String line;
            String splitBy =",";
            while ((line = br.readLine()) != null) {
                String[] usernume = line.split(splitBy);
                for(int i = 0; i < usernume.length; i+=2)
                    if(name.equals(usernume[i]) && parola.equals(usernume[i+1]))
                        return 1; // exista user
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return 2; //nu exista user
    }

    public int verificaUserPost(Utilizator utilizator) {
        if(utilizator.userName == null || (utilizator.getParola() == null && utilizator.userName != null)) {
            return 1; // nu exista user sau parola
        } else if(utilizator.userName != null && utilizator.getParola() != null){
            //verificam daca userul e in baza de date
            int rezultat = verificareUtilizatorExistent(utilizator.userName, utilizator.getParola());
            if(rezultat == 1) {
                return 3; //inseamna ca e bun si poate sa continue actiunile in continuare
            } else if(rezultat == 2) {
                return 2;
            }
        }
        return 2; // utilizatorul nu exista are parola sau userul gresit
    }

    // scriem in fisier user-ul
    public void introducInFisier ( Utilizator user) {
        try (FileWriter fw = new FileWriter("src/main/java/TemaTest/user.csv", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
             out.print(user.userName + "," + user.getParola()  + ",");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // scriem in fisier userul + persoana pe care o urmareste
    public void introducInFisierFolloweri(String user, Utilizator userparinte) {
        try (FileWriter fw = new FileWriter("src/main/java/TemaTest/follow.csv", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
             out.print(userparinte.userName + "," + user + ",");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
