/*
 * This Java source file was generated by the Gradle 'init' task.
 */


package TemaTest;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class App {

    public App() {/* compiled code */
    }

    private static String extragerePN(String rowstring, int lungime) {
            String s = rowstring.replaceAll("'", "");
            return  s.substring(lungime);
    }

    public int creatUser(String user, String parola, Utilizator utilizator){
        // verificare user
        if (user != null && parola == null) {
            return 3; // parola nu a fost data
        } else if (user == null) {
            return 4; //userul nu a fost dat
        } else {
            // verificam daca numele si parola utilizatorului exista
            int result = utilizator.verificareUtilizatorExistent(user, parola);
            // daca nu exista il introducem
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

    public int verificaUserPost(Utilizator utilizator) {
        if(utilizator.userName == null) {
            return 1;
        } else if(utilizator.getParola() == null && utilizator.userName != null) {
            return 1;
        } else if(utilizator.userName != null && utilizator.getParola() != null){
            //verificam daca userul e bun
            int rezultat = utilizator.verificareUtilizatorExistent(utilizator.userName, utilizator.getParola());
            if(rezultat == 1) {
                return 3; //inseamna ca e bun si poate sa inceapa verificarea textului
            } else if(rezultat == 2) {
                return 2;
            }
        }
        return 2; // utilizatorul nu exista are parola sau userul gresit
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

    public int creatComentPostare(String text, Utilizator utilizator, String id) {
        if(text == null) {
            //nu s-a primit niciun text
            return 4;
        }

        if(text.length() < 300) {
            //postarea e buna
            utilizator.postari.comentariu.textComentariu = text;
            utilizator.postari.comentariu.introducInFisier(utilizator, id);
            return 3;
        }
        return 5; //postarea are mai mult de 300 de caractere
    }

    // verificam userul postarii (necesitate: Like post)
    public boolean verUserPost (Utilizator user) {
        String path = null;
        path = "src/main/java/TemaTest/post.csv";

        // citim continut fisier
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String linie;

            while ((linie = reader.readLine()) != null) {
                if (linie.startsWith(user.userName + ",")) {
                    return true;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    public int verificareid(String id, List<String> lines) {
        if(!lines.isEmpty() && Integer.parseInt(id) <= lines.size()) {
            return 3; // SUCCESC
        } else {
            return 5; // THE IDENTIFIER WAS NOT VALID / NU EXISTA
        }
    }

    public  int verificaidpost(Utilizator utilizator, String id){
        int ver = utilizator.postari.verPost(id);
        int rezultatSec = 0;
        if(ver == 1){
            rezultatSec = 3;
        } else {
            rezultatSec =5;
        }
        return  rezultatSec;
    }

    //citeste toate liniile din fisier si le pune intr-o lista de stringuri
    public static List<String> citirefisier(String path) throws IOException{
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    public int veruserfollowpre(Utilizator user, String usefollow) {
        if (usefollow == null) {
            return 4; //No username to list posts was provided
        }

        int exist = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/TemaTest/user.csv"))) {
            String line;
            String splitBy = ",";

            while ((line = br.readLine()) != null) {
                String[] usersecond = line.split(splitBy);

                for (int i = 0; i < usersecond.length; i++) {
                    // daca user-ul cerut exista in baza de date verificam daca era deja in followurile userUL nostru
                    if (usefollow.equals(usersecond[i]) && (usefollow != user.userName)) {
                        exist = 1;
                        return 3; //  exista in baza de date
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (exist == 0) {
            return 5; // nu exista in baza de date, The username to list posts was not valid
        }
        return 0;
    }

    public int verUserFollow(Utilizator utilizator, String follow, int val) {
        if (follow == null) {
            return 4; // No username to follow was provided
        }
            int exist = 0;
            try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/TemaTest/user.csv"))) {
                String line;
                String splitBy = ",";

                while ((line = br.readLine()) != null) {
                    String[] user = line.split(splitBy);

                    for (int i = 0; i < user.length; i++) {
                        // daca user-ul cerut exista in baza de date verificam daca era deja in followurile userUL nostru
                        if (follow.equals(user[i]) && (follow != utilizator.userName)) {
                            exist = 1;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (exist == 0) {
                return 5; // nu exista in baza de date, The username to follow was not valid
            }

            /* val = 1 --> pentru executia la follow cand fisierul e gol
            *  val = 2 --> pentru executia la unfollow cand fisierul e gol*/
            if(val == 1) {
                // daca userul nu are niciun follower atunci il punem
                if (!verfisier()) {
                    utilizator.setFollow(follow);
                    utilizator.introducInFisierFolloweri(follow, utilizator);
                    return 3; //“Operation execute successfully”
                }
            } else if( val == 2){
                // daca userul nu are niciun follower atunci nu il mai punem punem
                if(!verfisier()) {
                    return 5; //The username to unfollow was not valid
                }
            }

            int ok = 0;
            if(verfisier()) {
                try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/TemaTest/follow.csv"))) {
                    String lines;
                    String splitBys = ",";
                    while ((lines = br.readLine()) != null){
                        String[] linie = lines.split(splitBys);
                        for (int i = 0; i < linie.length; i+=2) {
                            if (Objects.equals(linie[i+1], follow) && Objects.equals(linie[i], utilizator.userName)) {
                                ok = 1;
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if( val == 1 ) {
                if (ok == 1) {
                    return 5; //exista deja in followeri, “The username to follow was not valid
                } else {
                    // inseamna ca utilizatorul nu exista in lista userului de folooweri
                    // si trebuie pus
                    utilizator.setFollow(follow);
                    utilizator.introducInFisierFolloweri(follow, utilizator);
                    return 3; //“Operation execute successfully”
                }
            }
            if(val == 2) {
                if (ok == 1) {
                    // stergem din fisierul de followeri userul
                    stergeFollowOrLike(follow,1, utilizator);
                    return 3; //exista deja in followeri, “Operation executed successfully
                } else {
                    return 5; //nu se gaseste in lista user - ului inseamna ca e deja unfollow
                }
            }
        return 0;
    }

    // val == 1 --> sterge follow
    // val == 2 --> sterge like
    // val == 3 --: sterge like comentariu
    public void stergeFollowOrLike(String followOrId, int val, Utilizator user) {
        String path = null;
        if(val == 1) {
            path = "src/main/java/TemaTest/follow.csv";
        } else if(val == 2) {
            path = "src/main/java/TemaTest/like.csv";
        } else if (val == 3) {
            path = "src/main/java/TemaTest/likeComentariu.csv";
        }
        // citim continut fisier
        List<String> lin = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String linie;
            while ((linie = reader.readLine()) != null) {
                lin.add(linie);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            // Citirea conținutului actual
            List<String> linii = lin;

            // Verificam daca linia de șters exista
            boolean linieGasita = false;
            if(val == 1) {
                linieGasita = linii.remove(user.userName + "," + followOrId + ",");   /// adauga user name
            } else if(val == 2 || val == 3) {
                linieGasita = linii.remove(user.userName + "," + followOrId + "," + "true,");
            }

            if (linieGasita) {
                // Rescrierea conținutului actualizat înapoi în fișier
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
                    for (String liniesec : linii) {
                        writer.write(liniesec);
                        writer.newLine();
                    }
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stergePost(Utilizator user, Postare text) {
        String path = null;
        path = "src/main/java/TemaTest/post.csv";

        // citim continut fisier
        List<String> lin = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String linie;
            while ((linie = reader.readLine()) != null) {
                lin.add(linie);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            // Citirea conținutului actual
            List<String> linii = lin;

            // Verificam daca linia de șters exista
            boolean linieGasita = false;
            linieGasita = linii.remove(user.userName +"," + text.getTextPostare() + ",");

            if (linieGasita) {
                // Rescrierea conținutului actualizat înapoi în fișier
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
                    for (String liniesec : linii) {
                        writer.write(liniesec);
                        writer.newLine();
                    }
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    // vedem daca fisierul este gol sau are scris ceva in el
    public boolean verfisier() {
        try (BufferedReader brs = new BufferedReader(new FileReader("src/main/java/TemaTest/follow.csv"))) {
            String lines;
            String splitBys = ",";
            if ((lines = brs.readLine()) == null){
                return false;
            }
            while ((lines = brs.readLine()) != null){
                String[] user = lines.split(splitBys);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void main(java.lang.String[] strings) {
        //citirea comenzi primite terminal
        String user = null;
        String parola = null;
        Postare text = new Postare();
        String comentariu = null;
        String action = null;
        String id = null;
        String follow = null;
        String postId = null;
        String comentid = null;

        if(strings == null) {
            System.out.print("Hello world!");
        } else {
            for (int i = 0; i < strings.length; i++) {
                //stergem din fisier tot pentru urmatorul test
                if (strings[i].startsWith("-cleanup-all")) {
                    FunctiiAjutatoare.cleanFile();
                }
                action = strings[0];
                if (strings[i].startsWith("-u")) {
                    if (strings[i].startsWith("-username")) {
                        follow = FunctiiAjutatoare.extragerePN(strings[i], 10);
                    } else {
                        user = FunctiiAjutatoare.extragerePN(strings[i], 3);
                    }
                } else if (strings[i].startsWith("-p")) {
                    if (strings[i].startsWith("-post-id '")) {
                        postId = FunctiiAjutatoare.extragerePN(strings[i], 9);
                    } else {
                        parola = FunctiiAjutatoare.extragerePN(strings[i], 3);
                    }
                } else if (strings[i].startsWith("-text")) {
                    text.setTextPostare(FunctiiAjutatoare.extragerePN(strings[i], 6));
                    comentariu = FunctiiAjutatoare.extragerePN(strings[i], 6);
                } else if (strings[i].startsWith("-id ")) {
                    id = FunctiiAjutatoare.extragerePN(strings[i], 4);
                } else if (strings[i].startsWith("-comment-id")) {
                    comentid = FunctiiAjutatoare.extragerePN(strings[i], 12);
                }
            }

            Utilizator utilizator = new Utilizator(); // o instanta a utilizatorului
            App aplicatie = new App(); // aplicatia noastra
            Outputuri answer = new Outputuri();

            if (action.startsWith("-create-user")) {
                int valoare;
                valoare = aplicatie.creatUser(user, parola, utilizator);
                answer.answerUser(valoare);
            }

            /* verificam daca a primit toti parametrii
             * pe urma verifica daca exista user-ul si pe urma textul*/
            if (action.startsWith("-create-post")) {
                // am setat din nou userul deoarece nu va mai intra in sectiunea "-creat-user"
                utilizator.setParametriUser(user, parola);

                //verifica daca utilizatorul e in baza de date
                int rezultat = aplicatie.verificaUserPost(utilizator);

                //daca utilizatorul exista
                if (rezultat == 3) {
                    int rezultatSecund = aplicatie.creatPostareUser(text, utilizator);
                    answer.answerPost(rezultatSecund);
                } else {
                    answer.answerPost(rezultat);
                }

            }

            if (action.startsWith("-delete-post-by-id")) {
                utilizator.setParametriUser(user, parola);

                int rezultat = aplicatie.verificaUserPost(utilizator);
                if (rezultat == 3) {
                    // verificam id
                    if (id != null) {
                        int rezultatSec = aplicatie.verificaidpost(utilizator,id);
                        if (rezultatSec == 3) {
                            aplicatie.stergePost(utilizator, text);
                            answer.answerDeletePost(rezultatSec);
                        } else {
                            answer.answerDeletePost(rezultatSec);
                        }
                    } else {
                        answer.answerDeletePost(4); //No identifier was provided
                    }
                } else {
                    answer.answerDeletePost(rezultat);
                }
            }


            if (action.startsWith("-follow-user-by-username")) {
                utilizator.setParametriUser(user, parola);

                // verificam sa vedem daca exista utilizatorul/daca e logat
                int rezultat = aplicatie.verificaUserPost(utilizator);
                if (rezultat == 3) {
                    int rezultatsec = aplicatie.verUserFollow(utilizator, follow, 1);
                    answer.answerFollow(rezultatsec);
                } else {
                    answer.answerFollow(rezultat);
                }
            }

            if (action.startsWith("-unfollow")) {
                utilizator.setParametriUser(user, parola);

                // verificam sa vedem daca exista utilizatorul/daca e logat
                int rezultat = aplicatie.verificaUserPost(utilizator);
                if (rezultat == 3) {
                    int rezultatsec = aplicatie.verUserFollow(utilizator, follow, 2);
                    answer.answerUnfollow(rezultatsec);
                } else {
                    answer.answerUnfollow(rezultat);
                }
            }

            if (action.startsWith("-like-post")) {
                utilizator.setParametriUser(user, parola);
                // verificam sa vedem daca exista utilizatorul/daca e logat
                int rezultat = aplicatie.verificaUserPost(utilizator);
                if (rezultat == 3) {
                    if (postId != null) {
                        // verificam daca user-ul isi da singur like la postare
                        if (aplicatie.verUserPost(utilizator) == false) {
                            // verificam daca exista o postare cu id-ul dat
                            int rezultatSec = aplicatie.verificaidpost(utilizator, postId);
                            // inseamna ca exista o postare careia ii putem da like
                            if (rezultatSec == 3) {
                                // verificam daca postarea are deja like
                                int likeGood = utilizator.postari.likeable.verLike(postId,utilizator);
                                if (likeGood == 0) {
                                    utilizator.postari.likeable.fisierLikeId(postId,utilizator);
                                    answer.answerLike(rezultatSec);
                                } else if (likeGood == 1) {
                                    answer.answerLike(5);
                                }

                            } else {
                                answer.answerLike(rezultatSec);  // nu esxista postarea cu id-ul
                            }
                        } else {
                            answer.answerLike(5);
                        }
                    } else {
                        answer.answerLike(4); // nu a fost dat id
                    }
                } else {
                    answer.answerLike(rezultat);
                }
            }

            if (action.startsWith("-unlike-post")) {
                utilizator.setParametriUser(user, parola);

                int rezultat = aplicatie.verificaUserPost(utilizator);
                if (rezultat == 3) {
                    // verificam id
                    if (postId != null) {
                        // verificam daca exista like cu id-ul dat
                        int rezultatSec = utilizator.postari.likeable.verLike(postId, utilizator);
                        if (rezultatSec == 1) {
                            // stergem like-ul deoarece el exista
                            aplicatie.stergeFollowOrLike(postId, 2, utilizator);
                            answer.answerUnlike(3);
                        } else {
                            answer.answerUnlike(5);  // returneaza 5 nu esxista postarea cu id-ul
                        }
                    } else {
                        answer.answerUnlike(4); //No identifier was provided
                    }
                } else {
                    answer.answerUnlike(rezultat);
                }
            }

            if (action.startsWith("-comment-post")) {
                // am setat din nou userul deoarece nu va mai intra in sectiunea "-creat-user"
                utilizator.setParametriUser(user, parola);

                //verifica daca utilizatorul e in baza de date
                int rezultat = aplicatie.verificaUserPost(utilizator);
                //daca utilizatorul exista
                if (rezultat == 3) {
                    // verificam daca exista o postare cu id-ul dat
                    int rezultatSec = aplicatie.verificaidpost(utilizator, postId);
                    // daca am gasit postarea atunci cream comentariu pentru ea
                    if (rezultatSec == 3) {
                        int val = aplicatie.creatComentPostare(comentariu, utilizator, postId);
                        answer.answerComPost(val);
                    } else {
                        answer.answerComPost(4);
                    }
                } else {
                    answer.answerComPost(rezultat);
                }

            }

            if (action.startsWith("-delete-comment-by-id")) {
                utilizator.setParametriUser(user, parola);

                int rezultat = aplicatie.verificaUserPost(utilizator);
                if (rezultat == 3) {
                    // verificam id
                    if (id != null) {
                        int rezultatSec = utilizator.postari.comentariu.verComentariu(id, utilizator, 1);
                        answer.answerDeleteComPost(rezultatSec);
                    } else {
                        answer.answerDeleteComPost(4); //No identifier was provided
                    }
                } else {
                    answer.answerDeleteComPost(rezultat);
                }
            }

            if (action.startsWith("-like-comment")) {
                utilizator.setParametriUser(user, parola);
                // verificam sa vedem daca exista utilizatorul/daca e logat
                int rezultat = aplicatie.verificaUserPost(utilizator);
                if (rezultat == 3) {
                    if (comentid != null) {
                        // verificam daca exista comentariu cu id-ul dat
                        int rezultatSec = utilizator.postari.comentariu.verComentariu(comentid, utilizator,2);
                        // inseamna ca exista o postare careia ii putem da like
                        if (rezultatSec == 3) {
                            // verificam daca postarea are deja like
                            int likeGood = utilizator.postari.comentariu.likeable.verLike(comentid,utilizator);
                            if (likeGood == 5) {
                                //adaugam likeul la comentariu
                                utilizator.postari.comentariu.likeable.fisierLikeId(comentid, utilizator);
                                answer.answerLikeComPost(3);
                            } else {
                                answer.answerLikeComPost(5);
                            }
                        } else {
                            answer.answerLikeComPost(rezultatSec);  // nu esxista postarea cu id-ul
                        }
                    } else {
                        answer.answerLikeComPost(4); // nu a fost dat id
                    }
                } else {
                    answer.answerLikeComPost(rezultat);
                }
            }


            if (action.startsWith("-unlike-comment")) {
                utilizator.setParametriUser(user, parola);

                int rezultat = aplicatie.verificaUserPost(utilizator);
                if (rezultat == 3) {
                    // verificam id
                    if (comentid != null) {
                        // verificam daca exista like cu id-ul dat
                        int rezultatSec = utilizator.postari.comentariu.likeable.verLike(comentid, utilizator);
                        if (rezultatSec == 3) {
                            // stergem like-ul deoarece el exista
                            aplicatie.stergeFollowOrLike(comentid, 3, utilizator);
                            answer.answerUnlikeComPost(rezultatSec);
                        } else {
                            answer.answerUnlikeComPost(rezultatSec);  // returneaza 5 nu esxista postarea cu id-ul
                        }
                    } else {
                        answer.answerUnlikeComPost(4); //No identifier was provided
                    }
                } else {
                    answer.answerUnlikeComPost(rezultat);
                }
            }


            if (action.startsWith("-get-followings-posts")) {
                utilizator.setParametriUser(user, parola);

                int rezultat = aplicatie.verificaUserPost(utilizator);
                if (rezultat == 3) {
                   FunctiiAjutatoare.afisFolowPost(utilizator, follow, 1);
                } else {
                    answer.answerfolowing(rezultat);
                }
            }

            if (action.startsWith("-get-user-posts")) {
                utilizator.setParametriUser(user, parola);

                int rezultat = aplicatie.verificaUserPost(utilizator);
                if (rezultat == 3) {
                    int rez = aplicatie.veruserfollowpre(utilizator, follow);
                    if(rez == 3) {
                        FunctiiAjutatoare.afisFolowPost(utilizator, follow, 2);
                    } else {
                        answer.answerfolowingpost(rez);
                    }
                } else {
                    answer.answerfolowingpost(rezultat);
                }
            }

            if (action.startsWith("-get-post-details")) {
                utilizator.setParametriUser(user, parola);

                int rezultat = aplicatie.verificaUserPost(utilizator);
                if (rezultat == 3) {
                    // verificam id
                    if (postId != null) {
                        // verificam daca exita postarea cu id-ul respectiv pe urma afisam detaliile ei
                         FunctiiAjutatoare.verificaAfiseazaComLike(utilizator, postId);
                    } else {
                        answer.answerpostdetails(4); //No identifier was provided
                    }
                } else {
                    answer.answerpostdetails(rezultat);
                }
            }

            // afisam persoanele care pe care le urmareste region 15
            if(action.equals("-get-following")) {
                utilizator.setParametriUser(user, parola);

                int rezultat = aplicatie.verificaUserPost(utilizator);
                if (rezultat == 3) {
                    // verificam id
                    FunctiiAjutatoare.afisarefollowing(utilizator, 1, "0");
                } else {
                    answer.answerfolowing(rezultat);
                }
            }

            // region 16
            if(action.equals("-get-followers")) {
                utilizator.setParametriUser(user, parola);

                int rezultat = aplicatie.verificaUserPost(utilizator);
                if (rezultat == 3) {
                    int rezultatSecund = aplicatie.veruserfollowpre(utilizator, follow);
                    if(rezultatSecund == 3) {
                        FunctiiAjutatoare.afisarefollowing(utilizator, 2, follow);
                    } else {
                        answer.answeregion16(rezultatSecund);
                    }
                } else {
                    answer.answeregion16(rezultat);
                }
            }

            if(action.equals("-get-most-liked-posts")) {
                utilizator.setParametriUser(user, parola);

                int rezultat = aplicatie.verificaUserPost(utilizator);
                if (rezultat == 3) {
                    FunctiiAjutatoare.afisaremostlikepost(utilizator);
                } else {
                    answer.answeregion16(rezultat);
                }
            }
        }

    }
}