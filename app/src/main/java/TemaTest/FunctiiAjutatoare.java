package TemaTest;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class FunctiiAjutatoare {

    public FunctiiAjutatoare() {
    }

    // extrage doar valoare dintre ' '
    public static String extragere(String rowstring, int lungime) {
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

    //necesitate in Region 4, 5
    public static int verUserFollow(Utilizator utilizator, String follow, int val) {
        if (follow == null) {
            return 4; // No username to follow was provided
        }
        // se verifica follow (doar dupa numele lui) daca este in baza de date si daca nu e user-ul nostru
        int exist = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/TemaTest/user.csv"))) {
            String line;
            String splitBy = ",";

            while ((line = br.readLine()) != null) {
                String[] user = line.split(splitBy);

                for (String s : user) {
                    // daca user-ul cerut exista in baza de date verificam daca era deja in followurile userUL nostru
                    if (follow.equals(s) && (!follow.equals(utilizator.userName))) {
                        exist = 1;
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (exist == 0) {
            return 5; // nu exista in baza de date, The username to follow was not valid
        }

        /* val = 1 --> pentru executia la follow cand fisierul e gol la inceput
         *  val = 2 --> pentru executia la unfollow cand fisierul e gol*/
        if(val == 1) {
            // daca userul nu are niciun follower atunci il punem
            if (!verfisier()) {
                utilizator.setFollow(follow);
                utilizator.introducInFisierFolloweri(follow, utilizator);
                return 3; //“Operation execute successfully”
            }
        } else if( val == 2){
            // daca userul nu are niciun follower atunci nu avem ce sa stergem
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
                        if (Objects.equals(linie[i + 1], follow) && Objects.equals(linie[i], utilizator.userName)) {
                            ok = 1;
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if( val == 1 ) {
            if (ok == 1) {
                return 5; //exista deja in followeri/The username to follow was not valid
            } else {
                // inseamna ca utilizatorul nu exista in lista userului de folooweri si trebuie pus
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

    public static void stergeFollowOrLike(String followOrId, int val, Utilizator user) {
        String path;
        if(val == 1) {
            path = "src/main/java/TemaTest/follow.csv";
        } else if(val == 2) {
            path = "src/main/java/TemaTest/like.csv";
        } else {
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
            // Verificam daca linia de șters exista
            boolean linieGasita = false;
            if(val == 1) {
                linieGasita = lin.remove(user.userName + "," + followOrId + ",");   /// adauga user name
            } else if(val == 2 || val == 3) {
                linieGasita = lin.remove(user.userName + "," + followOrId + "," + "true,");
            }

            if (linieGasita) {
                // Rescrierea conținutului actualizat înapoi în fișier
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
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

    // vedem daca fisierul este gol sau are scris ceva in el
    public static boolean verfisier() {
        try (BufferedReader brs = new BufferedReader(new FileReader("src/main/java/TemaTest/follow.csv"))) {
            String lines;
            if ((lines = brs.readLine()) == null){
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static int veruserpre(String usefollow) {
        if (usefollow == null) {
            return 4; //No username to list posts was provided
        }
        int exist = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/TemaTest/user.csv"))) {
            String line;
            String splitBy = ",";

            while ((line = br.readLine()) != null) {
                String[] usersecond = line.split(splitBy);

                for (String s : usersecond) {
                    // user-ul cerut exista in baza de date
                    if (usefollow.equals(s)) {
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


    // val == 1 --> afiseaza toate postarile tuturor persoanelor urmarite
    // val == 2 -->  afiseaza postarile username-ului primit
    public static void afisFolowPost(Utilizator user, String userfollow, int val) {
        int contorpost = numberpost(1);
        int one = 0;
        int existauser = 0;

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String currentDateAsString = dateFormat.format(date);

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/TemaTest/follow.csv"))) {
            String lines;
            String splitBys = ",";
            while ((lines = br.readLine()) != null) {
                String[] linie = lines.split(splitBys);
                for (int i = linie.length - 1; i >= 0; i -= 2) {
                    if (Objects.equals(linie[i-1], user.userName)) {
                        // daca exista followeri a userului pentru ei vom afisa in ordine inversa postarile lor
                        // de la cea mai recenta la cea mai veche
                        if(val == 1) {
                            try (BufferedReader brs = new BufferedReader(new FileReader("src/main/java/TemaTest/post.csv"))) {
                                String linestwo;
                                String splitBystwo = ",";
                                while ((linestwo = brs.readLine()) != null) {
                                    String[] linietwo = linestwo.split(splitBystwo);
                                    for (int j = linietwo.length - 1; j >= 0; j -= 2) {
                                        // daca am gasit o postare a followerului o afisam
                                        if (Objects.equals(linie[i], linietwo[j - 1])) {
                                            one++;
                                            if (one == 1) {
                                                System.out.print("{ 'status' : 'ok', 'message' : [");
                                            }
                                            if (contorpost == 1) {
                                                System.out.print("{'post_id' : '" + contorpost + "', 'post_text' : '" + linietwo[j] + "' , 'post_date':'" + currentDateAsString + "', 'username' : '" + linietwo[j - 1] + "'}]}");
                                            } else {
                                                System.out.print("{'post_id' : '" + contorpost + "', 'post_text' : '" + linietwo[j] + "' , 'post_date':'" + currentDateAsString + "', 'username' : '" + linietwo[j - 1] + "'},");
                                            }
                                            contorpost--;
                                        }
                                    }
                                }
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        } else if (val == 2) {
                            // daca userul are followerul dat atunci afisam postarile lui in ordina inversa
                            if(Objects.equals(linie[i], userfollow)) {
                                existauser = 1;
                                try (BufferedReader brs = new BufferedReader(new FileReader("src/main/java/TemaTest/post.csv"))) {
                                    String linestwo;
                                    String splitBystwo = ",";
                                    while ((linestwo = brs.readLine()) != null) {
                                        String[] linietwo = linestwo.split(splitBystwo);
                                        for (int j = linietwo.length - 1; j >= 0; j -= 2) {
                                            // daca am gasit o postare a followerului o afisam
                                            if (Objects.equals(linie[i], linietwo[j - 1])) {
                                                one++;
                                                if (one == 1) {
                                                    System.out.print("{ 'status' : 'ok', 'message' : [");
                                                }
                                                if (contorpost == 1) {
                                                    System.out.print("{'post_id' : '" + contorpost + "', 'post_text' : '" + linietwo[j] + "' , 'post_date':'" + currentDateAsString + "'}]}");
                                                } else {
                                                    System.out.print("{'post_id' : '" + contorpost + "', 'post_text' : '" + linietwo[j] + "' , 'post_date':'" + currentDateAsString + "'},");
                                                }
                                                contorpost--;
                                            }
                                        }
                                    }
                                } catch (FileNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                }
            }
            // daca avem val == 2 --->  se cere sa afisam postarile persoanei care il urmareste pe user, insa
            // daca el nu exista se va returana mesajul dat in cerinta
            if(existauser == 0 && val == 2) {
                System.out.println("{ 'status' : 'error', 'message' : 'The username to list posts was not valid'}");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // numarul de postari din fisier --> val = 1
    // numarul de comentari din fisier --> val = 2
     public static int numberpost(int val) {
        int contor = 0;
        try (BufferedReader brs = new BufferedReader(new FileReader("src/main/java/TemaTest/post.csv"))) {
            String linestwo;
            String splitBystwo = ",";
            while ((linestwo = brs.readLine()) != null) {
                String[] linietwo = linestwo.split(splitBystwo);
                if(val == 1) {
                    contor = linietwo.length / 2;
                } else if(val == 2) {
                    contor = linietwo.length / 3;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return contor;
    }

    public  static int numarUser(){
        int contor = 0;
        try (BufferedReader brs = new BufferedReader(new FileReader("src/main/java/TemaTest/user.csv"))) {
            String linestwo;
            String splitBystwo = ",";
            while ((linestwo = brs.readLine()) != null) {
                String[] linietwo = linestwo.split(splitBystwo);
                contor = linietwo.length / 2;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return contor;
    }

    // region 12
    public static void verificaAfiseazaComLike(Utilizator user, String id) {
        int one = 0; // ajuta atunci cand nu exista postarea sa afiseze outputul corect
        int contorid = 0;  // determina id-ul postarii

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String currentDateAsString = dateFormat.format(date);

        // afisam mai intai postarea daca este gasita
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/TemaTest/post.csv"))) {
            String lines;
            String split = ",";
            while ((lines = br.readLine()) != null) {
                contorid ++;
                String[] linie = lines.split(split);
                // am ajuns la postare pe care o vrem
                if(contorid == Integer.parseInt(id)) {
                    one++;
                    if(one == 1){
                        System.out.print("{ 'status' : 'ok', 'message' : [");
                    }
                        System.out.print("{'post_text' : '" + linie[contorid] + "', 'post_date' :'" + currentDateAsString + "', 'username' : '" + linie[contorid-1] + "', ");
                        System.out.print("'number_of_likes' : '" + user.postari.likeable.numberlike(id) + "', ");
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // verificam daca exista comentarii
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/TemaTest/comentarii.csv"))) {
            String lines;
            String split = ",";
            while ((lines = br.readLine()) != null) {
                one++; // nu este fisierul gol
                String[] linie = lines.split(split);
                for (int i = linie.length - 1; i >= 0; i -= 3) {
                    // afisam comentariile in ordine inversa si numarul de likeuri pe comentariul respecitv
                    System.out.print("'comments' : [{'comment_id' : '" + linie[i-1] + "' , 'comment_text' : '" + linie[i] + "' , 'comment_date':'" + currentDateAsString + "', 'username' : '" + linie[i-2] + "', 'number_of_likes' : '" + user.postari.comentariu.likeable.numberlike(linie[i-1]) + "'}] }] }");

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(one == 0) {
            System.out.print("{ 'status' : 'error', 'message' : 'The post identifier was not valid'}");
        }
    }

    // region 15 val == 1
    // region 16 val == 2
    public static void afisarefollowing(Utilizator user, int val, String follow) {
        int exista = 0;
        System.out.print("{'status' : 'ok', 'message' : [ ");
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/TemaTest/follow.csv"))) {
            String lines;
            String splitBys = ",";
            while ((lines = br.readLine()) != null) {
                String[] linie = lines.split(splitBys);
                for(int i = 0; i < linie.length; i+=2) {
                    if(val == 1) {
                        if (Objects.equals(linie[i], user.userName)) {
                            exista++;
                            if (exista == 1) {
                                System.out.print("'" + linie[i + 1] + "'");
                            }
                            if (exista > 0 && exista != 1) {
                                System.out.print(", '" + linie[i + 1] + "'");
                            }
                        }
                    }
                    if(val == 2) {
                        if (Objects.equals(linie[i+1], follow)) {
                            exista++;
                            if (exista == 1) {
                                System.out.print("'" + linie[i] + "'");
                            }
                            if (exista > 0 && exista != 1) {
                                System.out.print(", '" + linie[i] + "'");
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // nu are following pt val == 1
        if(exista == 0 && val == 1) {
            System.out.print("'" + 0 + "'");
        }
        System.out.print(" ]}");
    }



    // val == 1 -> face un vector de stringuri cu nr de like-uri pe postare / region 17
    // val == 2 -> face un vector de stringuri cu nr de comentarii pe postare / region 18
    public static String[] postariOrdineLikeOrComm(int val ) {
        String[] ordine = new String[numberpost(1) + 1];
        int[] vallike = new int[numberpost(1) + 1];
        String path;
        if (val == 1) {
            path = "src/main/java/TemaTest/like.csv";
        } else {
            path = "src/main/java/TemaTest/comentarii.csv";
        }
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String lines;
            String splitBys = ",";
            while ((lines = br.readLine()) != null) {
                String[] linie = lines.split(splitBys);
                for(int j = 0; j < linie.length; j+=3){
                        vallike[Integer.parseInt(linie[j + 1])]++;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //trecem valorile in vector unde punem id postare si numarul de like-uri
        for (int i = 1; i <= numberpost(1); i++){
            ordine[i] = i + "," + vallike[i];
        }

        int maxlike;
        String valintermediar;

        // ordonam vectorul de like-uri in functie de like-uri
        for(int i = 1; i <= numberpost(1); i++){
            String splitBy = ",";
            String[] like = ordine[i].split(splitBy);
            maxlike = Integer.parseInt(like[1]);
            for(int j = i + 1; j <= numberpost(1); j++){
                String split = ",";
                String[] liketwo = ordine[j].split(split);
                if(maxlike < Integer.parseInt(liketwo[1])) {
                    maxlike = Integer.parseInt(liketwo[1]);
                    valintermediar = ordine[i];
                    ordine[i] = ordine[j];
                    ordine[j] = valintermediar;
                } else if(maxlike == Integer.parseInt(liketwo[1]) && Integer.parseInt(like[0]) > Integer.parseInt(liketwo[0])) {
                    valintermediar = ordine[i];
                    ordine[i] = ordine[j];
                    ordine[j] = valintermediar;
                }
            }
        }
        return ordine;
    }

    // val == 1 most like post / region 17
    // val == 2 most comment post / region 18
    public static void afisaremostlikepost(int val) {
        int numberpost = numberpost(1);
        int ones = 0;
        String[] ordine = postariOrdineLikeOrComm(val);

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String currentDateAsString = dateFormat.format(date);

        for(int i = 1; i <= numberpost && i <= 5; i++){

            int contor = 0;
            String splitBy = ",";
            String[] linie = ordine[i].split(splitBy);

            try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/TemaTest/post.csv"))) {
                String splitBys = ",";
                String line;
                while ((line = br.readLine()) != null) {
                    String[] lines = line.split(splitBys);
                    for(int j = 0; j < lines.length; j+=2) {
                        contor++;
                        ones++;
                        if(ones == 1) {
                            System.out.print("{ 'status' : 'ok', 'message' : [");
                        }

                        if(val == 1) {
                            // daca postarea este ultima ce trebuie afisata se schimba la sfarsit putin output-ul(al doilea if)
                            if (contor == Integer.parseInt(linie[0]) && contor != numberpost) {
                                System.out.print("{'post_id' : '" + contor + "','post_text' : '" + lines[j + 1] + "', 'post_date' : '" + currentDateAsString + "', 'username' : '" + lines[j] + "', 'number_of_likes' : '" + linie[1] + "' },");
                            } else if (contor == Integer.parseInt(linie[0]) && contor == numberpost) {
                                System.out.print("{'post_id' : '" + contor + "','post_text' : '" + lines[j + 1] + "', 'post_date' : '" + currentDateAsString + "', 'username' : '" + lines[j] + "', 'number_of_likes' : '" + linie[1] + "' } ]}");
                            }
                        } else if(val == 2) {
                            // daca postarea este ultima ce trebuie afisata se schimba la sfarsit putin output-ul(al doilea if)
                            if (contor == Integer.parseInt(linie[0]) && contor != numberpost) {
                                System.out.print("{'post_id' : '" + contor + "','post_text' : '" + lines[j + 1] + "', 'post_date' : '" + currentDateAsString + "', 'username' : '" + lines[j] + "', 'number_of_comments' : '" + linie[1] + "' },");
                            } else if (contor == Integer.parseInt(linie[0]) && contor == numberpost) {
                                System.out.print("{'post_id' : '" + contor + "','post_text' : '" + lines[j + 1] + "', 'post_date' : '" + currentDateAsString + "', 'username' : '" + lines[j] + "', 'number_of_comments' : '" + linie[1] + "' }]}");
                            }
                        }
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // determinam nr followeri per user / region 19
    public static String[] postariOrdineFollow() {
        String[] ordine = new String[numarUser() + 1];
        int[] vallike = new int[numarUser() + 1];
        String path;
        path = "src/main/java/TemaTest/follow.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String lines;
            String splitBys = ",";
            while ((lines = br.readLine()) != null) {
                String[] linie = lines.split(splitBys);
                for(int j = 0; j < linie.length; j+=2){
                    int index;
                    //user-ul urmarit
                    String lastcaracter = String.valueOf(linie[j + 1].charAt(linie[j+1].length()-1));
                    if(lastcaracter.equals("t")) {
                        index = 1;
                    } else {
                        index = Integer.parseInt(lastcaracter);
                    }
                    vallike[index]++;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //trecem valorile in vector unde punem user si numarul de followeri
        for (int i = 1; i <= numarUser(); i++){
            if(i == 1) {
                ordine[i] = "test" + "," + vallike[i];
            } else {
                ordine[i] = "test" + i + "," + vallike[i];
            }
        }

        int maxfollow;
        String valintermediar;
        // ordonam vectorul de like-uri in functie de like-uri
        for(int i = 1; i <= numarUser(); i++){
            String splitBy = ",";
            String[] follow = ordine[i].split(splitBy);
            maxfollow = Integer.parseInt(follow[1]);
            for(int j = i + 1; j <= numarUser(); j++){
                String split = ",";
                String[] liketwo = ordine[j].split(split);
                if(maxfollow < Integer.parseInt(liketwo[1])) {
                    maxfollow = Integer.parseInt(liketwo[1]);
                    valintermediar = ordine[i];
                    ordine[i] = ordine[j];
                    ordine[j] = valintermediar;
                }
            }
        }
        return ordine;
    }

    // Region 19
    public static void afisareMostFollowed() {
        String[] ordine = postariOrdineFollow();
        int ones = 0;
        for (int i = 1; i <= numarUser() && i <= 5; i++) {
            ones++;
            String splitBy = ",";
            String[] linie = ordine[i].split(splitBy);

            if (ones == 1) {
                System.out.print("{ 'status' : 'ok', 'message' : [");
            }
            if (i == 5 || i == numarUser()) {
                System.out.print("{'username' : '" + linie[0] + "','number_of_followers' : '" + linie[1] + "' } ]}");
            } else {
                System.out.print("{'username' : '" + linie[0] + "','number_of_followers' : '" + linie[1] + "' },");
            }
        }
    }

    // determinare nr like-uri postare + nr.like comment postare / region 20
    public static String[] nrLikeComPost(){
        String[] ordine = postariOrdineLikeOrComm(1);
        int[] vallike = new int[numberpost(1) + 1];

        try (BufferedReader brs = new BufferedReader(new FileReader("src/main/java/TemaTest/likeComentariu.csv"))) {
            String linestwo;
            String splitBy = ",";
            while ((linestwo = brs.readLine()) != null) {
                String[] linietwo = linestwo.split(splitBy);
                for(int j = 0; j < linietwo.length; j+=3){

                    try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/TemaTest/comentarii.csv"))) {
                        String lin;
                        String split = ",";
                        while ((lin = br.readLine()) != null) {
                            String[] linie = lin.split(split);
                            int contor2 = 0;
                            for (int t = 0 ; t < numberpost(2); t+=3) {
                                int index = 0;
                                contor2++;
                                if(contor2  == Integer.parseInt(linietwo[j+1])){
                                //user-ul urmarit
                                    String lastcaracter = String.valueOf(linie[t].charAt(linie[t].length()-1));
                                    if(lastcaracter.equals("t")) {
                                        index = 1;
                                    } else {
                                        index = Integer.parseInt(lastcaracter);
                                    }
                                    vallike[index]++;
                                }

                            }
                        }
                    } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // actualizam vectorul cu like-uri ale postraii la care adugam numarul de like-uri ale comentariilor
        for (int i = 1; i < numberpost(1); i++) {
            String splitBy = ",";
            String[] like = ordine[i].split(splitBy);
            if(Objects.equals(Integer.parseInt(like[0]), i)) {
                vallike[i] = vallike[i] + Integer.parseInt(like[1]);
                ordine[i] = i + "," + vallike[i];
            }
        }

        String intermadiar;
        for(int i = 1; i <= numberpost(1); i++){
            String maxval = ordine[i];
            for(int j = i+1; j <= numberpost(1); j++) {
                String splitBy = ",";
                String[] like = maxval.split(splitBy);
                String[] liketwo = ordine[j].split(splitBy);
                if(Integer.parseInt(like[1]) < Integer.parseInt(liketwo[1])) {
                    intermadiar = ordine[i];
                    ordine[i] = ordine[j];
                    ordine[j] = intermadiar;
                } else if((Integer.parseInt(like[1]) == Integer.parseInt(liketwo[1])) && Integer.parseInt(like[0]) > Integer.parseInt(liketwo[0])) {
                    intermadiar = ordine[i];
                    ordine[i] = ordine[j];
                    ordine[j] = intermadiar;
                }
            }

        }

        return ordine;
    }

    // Afisarea primelor 5 Most liked Users
    public static void afisareRegion20() {
        String[] ordine = nrLikeComPost();
        int ones = 0;
        for (int i = 1; i <= numberpost(1) && i <= 5; i++) {
            ones++;
            String splitBy = ",";
            String[] like = ordine[i].split(splitBy);
            String test;
            if(Integer.parseInt(like[0]) == 1) {
                test = "test";
            } else {
                test = "test" + Integer.parseInt(like[0]);
            }
            if(ones == 1) {
                System.out.print("{ 'status' : 'ok', 'message' : [");
            }
            if (i == 5 || i == numarUser()) {
                System.out.print("{'username' : '" + test + "','number_of_likes' : '" + like[1] + "' }]}");
            } else {
                System.out.print("{'username' : '" + test + "','number_of_likes' : '" + like[1] + "' },");
            }
        }
    }

}
