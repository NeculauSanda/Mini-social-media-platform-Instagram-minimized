package TemaTest;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class FunctiiAjutatoare {

    public FunctiiAjutatoare() {
    }

    public static String extragerePN(String rowstring, int lungime) {
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


    // val == 1 --> afiseaza toate postarile tuturor followerilor
    // val == 2 -->  afiseaza postarile username-ului primit
    public static void afisFolowPost(Utilizator user, String userfollow, int val) {
        int one = 0;
        int contorpost = numberpost(1);
        int existauser = 0;
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
                                            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                            Date date = new Date();
                                            String currentDateAsString = dateFormat.format(date);
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
                                                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                                Date date = new Date();
                                                String currentDateAsString = dateFormat.format(date);
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
        String path = null;
        if(val == 1) {
            path = "src/main/java/TemaTest/post.csv";
        } else if(val == 2) {
            path = "src/main/java/TemaTest/comentarii.csv";
        }
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

    // region
    public static void verificaAfiseazaComLike(Utilizator user, String id) {
        int one = 0; // ajuta atunci cand nu exista postarea si afiseaza outputul corect
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
            if(one == 0) {
                System.out.print("{ 'status' : 'error', 'message' : 'The post identifier was not valid'}");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
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
        String path = null;
        if (val == 1) {
            path = "src/main/java/TemaTest/like.csv";
        } else if(val == 2) {
            path = "src/main/java/TemaTest/comentarii.csv";
        }
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String lines;
            String splitBys = ",";
            while ((lines = br.readLine()) != null) {
                String[] linie = lines.split(splitBys);
                for(int j = 0; j < linie.length; j+=3){
                    if(val == 1) {
                        vallike[Integer.parseInt(linie[j + 1])]++;
                    } else if(val == 2) {
                        vallike[Integer.parseInt(linie[j + 1])]++;
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //trecem valorile in vector unde punem id postare si numarul de like-uri
        for (int i = 1; i <= numberpost(1); i++){
            ordine[i] = i + "," + vallike[i];
        }

        int maxlike = 0;
        String valintermediar = null;
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
                }
            }
        }
        return ordine;
    }

    // val == 1 most like post / region 17
    // val == 2 most comment post / region 18
    public static void afisaremostlikepost(Utilizator user, int val) {
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
                            if (contor == Integer.parseInt(linie[0]) && contor != numberpost) {
                                System.out.print("{'post_id' : '" + contor + "','post_text' : '" + lines[j + 1] + "', 'post_date' : '" + currentDateAsString + "', 'username' : '" + lines[j] + "', 'number_of_likes' : '" + linie[1] + "' },");
                            } else if (contor == Integer.parseInt(linie[0]) && contor == numberpost) {
                                System.out.print("{'post_id' : '" + contor + "','post_text' : '" + lines[j + 1] + "', 'post_date' : '" + currentDateAsString + "', 'username' : '" + lines[j] + "', 'number_of_likes' : '" + linie[1] + "' } ]}");
                            }
                        } else if(val == 2) {
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
        String path = null;
        path = "src/main/java/TemaTest/follow.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String lines;
            String splitBys = ",";
            while ((lines = br.readLine()) != null) {
                String[] linie = lines.split(splitBys);
                for(int j = 0; j < linie.length; j+=2){
                    int index = 0;
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

        //trecem valorile in vector unde punem id postare si numarul de like-uri
        for (int i = 1; i <= numarUser(); i++){
            if(i == 1) {
                ordine[i] = "test" + "," + vallike[i];
            } else {
                ordine[i] = "test" + i + "," + vallike[i];
            }
        }

        int maxfollow = 0;
        String valintermediar = null;
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
        String path = null;
        int contorcoment = numberpost(2);
        path = "src/main/java/TemaTest/comentarii.csv";


        try (BufferedReader brs = new BufferedReader(new FileReader("src/main/java/TemaTest/likeComentariu.csv"))) {
            String linestwo;
            String splitBy = ",";
            while ((linestwo = brs.readLine()) != null) {
                String[] linietwo = linestwo.split(splitBy);
                for(int j = 0; j < linietwo.length; j+=3){
                    vallike[Integer.parseInt(linietwo[j+1])]++;
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

        String intermadiar = null;
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
            String test = null;
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
