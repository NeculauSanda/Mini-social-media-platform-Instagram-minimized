/*
 * This Java source file was generated by the Gradle 'init' task.
 */


package TemaTest;

import java.io.*;

public class App {

    public App() {/* compiled code */
    }

    // verificam userul postarii (necesitate: Like post)
    public boolean verUserPost (Utilizator user) {
        String path = "src/main/java/TemaTest/post.csv";

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

    public  int verificaidpost(Utilizator utilizator, String id){
        int ver = utilizator.postari.verPost(id);
        int rezultatSec;
        if(ver == 1){
            rezultatSec = 3; // exista postare cu id dat
        } else {
            rezultatSec = 5; // nu exista postare cu id-ul dat
        }
        return  rezultatSec;
    }

    public static void main(java.lang.String[] strings) {
        //citirea comenzilor primite in terminal
        String user = null; // nume user
        String parola = null; // parola
        Postare text = new Postare(); // text postare
        String comentariu = null;  // text comentariu
        String action;  //actiunea primita
        String id = null;
        String follow = null;  // nume follow
        String postId = null;  // id postare
        String comentid = null; // id comentariu

        if(strings == null) {
            System.out.print("Hello world!");
        } else {
            for (String string : strings) {
                //stergem din fisier tot pentru urmatorul test
                if (string.startsWith("-cleanup-all")) {
                    FunctiiAjutatoare.cleanFile();
                }
                if (string.startsWith("-u")) {
                    if (string.startsWith("-username")) {
                        follow = FunctiiAjutatoare.extragere(string, 10);
                    } else {
                        user = FunctiiAjutatoare.extragere(string, 3);
                    }
                } else if (string.startsWith("-p")) {
                    if (string.startsWith("-post-id '")) {
                        postId = FunctiiAjutatoare.extragere(string, 9);
                    } else {
                        parola = FunctiiAjutatoare.extragere(string, 3);
                    }
                } else if (string.startsWith("-text")) {
                    text.setTextPostare(FunctiiAjutatoare.extragere(string, 6));
                    comentariu = FunctiiAjutatoare.extragere(string, 6);
                } else if (string.startsWith("-id ")) {
                    id = FunctiiAjutatoare.extragere(string, 4);
                } else if (string.startsWith("-comment-id ")) {
                    comentid = FunctiiAjutatoare.extragere(string, 12);
                }
            }

            Utilizator utilizator = new Utilizator(); // o instanta a utilizatorului
            App aplicatie = new App(); // aplicatia noastra
            Outputuri answer = new Outputuri(); // outputuri
            action = strings[0]; // actiunea

            // region 1 creare user
            if (action.equals("-create-user")) {
                int valoare = Utilizator.creatUser(user, parola, utilizator);
                answer.answerUser(valoare);
            }

            // region 2
            if (action.equals("-create-post")) {
                // am setat din nou userul deoarece nu va mai intra in sectiunea "-creat-user"
                utilizator.setParametriUser(user, parola);

                //verifica daca utilizatorul e in baza de date
                int rezultat = utilizator.verificaUserPost(utilizator);

                //daca utilizatorul exista
                if (rezultat == 3) {
                    int rezultatSecund = utilizator.postari.creatPostareUser(text, utilizator);
                    answer.answerPost(rezultatSecund);
                } else {
                    answer.answerPost(rezultat);
                }

            }

            if (action.equals("-delete-post-by-id")) {
                utilizator.setParametriUser(user, parola);

                int rezultat = utilizator.verificaUserPost(utilizator);
                if (rezultat == 3) {
                    // verificam id
                    if (id != null) {
                        int rezultatSec = aplicatie.verificaidpost(utilizator,id);
                        if (rezultatSec == 3) {
                            utilizator.postari.stergePost(utilizator, text);
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


            if (action.equals("-follow-user-by-username")) {
                utilizator.setParametriUser(user, parola);

                // verificam sa vedem daca exista utilizatorul/daca e logat
                int rezultat = utilizator.verificaUserPost(utilizator);
                if (rezultat == 3) {
                    int rezultatsec = FunctiiAjutatoare.verUserFollow(utilizator, follow, 1);
                    answer.answerFollow(rezultatsec);
                } else {
                    answer.answerFollow(rezultat);
                }
            }

            if (action.equals("-unfollow-user-by-username")) {
                utilizator.setParametriUser(user, parola);

                // verificam sa vedem daca exista utilizatorul/daca e logat
                int rezultat = utilizator.verificaUserPost(utilizator);
                if (rezultat == 3) {
                    int rezultatsec = FunctiiAjutatoare.verUserFollow(utilizator, follow, 2);
                    answer.answerUnfollow(rezultatsec);
                } else {
                    answer.answerUnfollow(rezultat);
                }
            }

            if (action.equals("-like-post")) {
                utilizator.setParametriUser(user, parola);
                // verificam daca exista utilizatorul
                int rezultat = utilizator.verificaUserPost(utilizator);

                if (rezultat == 3) {
                    if (postId != null) {
                        // verificam daca user-ul isi da singur like la postare
                        if (!aplicatie.verUserPost(utilizator)) {
                            // verificam daca exista o postare cu id-ul dat
                            int rezultatSec = aplicatie.verificaidpost(utilizator, postId);
                            // inseamna ca exista o postare careia ii putem da like
                            if (rezultatSec == 3) {
                                // verificam daca postarea are deja like
                                int likeGood = utilizator.postari.likeable.verLike(postId,utilizator);
                                if (likeGood == 0) {
                                    // nu are dat like il introducem in fisier
                                    utilizator.postari.likeable.fisierLikeId(postId,utilizator);
                                    answer.answerLike(rezultatSec);
                                } else if (likeGood == 1) {
                                    answer.answerLike(5);
                                }

                            } else {
                                answer.answerLike(rezultatSec);  // nu esxista postarea cu id-ul dat
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

            if (action.equals("-unlike-post")) {
                utilizator.setParametriUser(user, parola);

                int rezultat = utilizator.verificaUserPost(utilizator);
                if (rezultat == 3) {
                    // verificam id
                    if (postId != null) {
                        // verificam daca exista like cu id-ul dat
                        int rezultatSec = utilizator.postari.likeable.verLike(postId, utilizator);
                        if (rezultatSec == 1) {
                            // stergem like-ul deoarece el exista
                            FunctiiAjutatoare.stergeFollowOrLike(postId, 2, utilizator);
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

            if (action.equals("-comment-post")) {
                utilizator.setParametriUser(user, parola);

                //verifica daca utilizatorul e in baza de date
                int rezultat = utilizator.verificaUserPost(utilizator);
                //utilizatorul exista
                if (rezultat == 3) {
                    // verificam daca exista o postare cu id-ul dat
                    int rezultatSec = aplicatie.verificaidpost(utilizator, postId);
                    // daca am gasit postarea atunci cream comentariu pentru ea
                    if (rezultatSec == 3) {
                        int val = utilizator.postari.comentariu.creatComentPostare(comentariu, utilizator, postId);
                        answer.answerComPost(val);
                    } else {
                        answer.answerComPost(4);
                    }
                } else {
                    answer.answerComPost(rezultat);
                }
            }

            if (action.equals("-delete-comment-by-id")) {
                utilizator.setParametriUser(user, parola);

                int rezultat = utilizator.verificaUserPost(utilizator);
                if (rezultat == 3) {
                    // verificam id
                    if (id != null) {
                        int rezultatSec = utilizator.postari.comentariu.verComentariu(id, utilizator, 1);
                        if(rezultatSec == 3){
                            utilizator.postari.comentariu.stergeCom(utilizator,id);
                        }
                        answer.answerDeleteComPost(rezultatSec);
                    } else {
                        answer.answerDeleteComPost(4); //No identifier was provided
                    }
                } else {
                    answer.answerDeleteComPost(rezultat);
                }
            }

            if (action.equals("-like-comment")) {
                utilizator.setParametriUser(user, parola);
                // verificam sa vedem daca exista utilizatorul/daca e logat
                int rezultat = utilizator.verificaUserPost(utilizator);
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


            if (action.equals("-unlike-comment")) {
                utilizator.setParametriUser(user, parola);

                int rezultat = utilizator.verificaUserPost(utilizator);
                if (rezultat == 3) {
                    // verificam id
                    if (comentid != null) {
                        // verificam daca exista like cu id-ul dat
                        int rezultatSec = utilizator.postari.comentariu.likeable.verLike(comentid, utilizator);
                        if (rezultatSec == 3) {
                            // stergem like-ul deoarece el exista
                            FunctiiAjutatoare.stergeFollowOrLike(comentid, 3, utilizator);
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


            if (action.equals("-get-followings-posts")) {
                utilizator.setParametriUser(user, parola);

                int rezultat = utilizator.verificaUserPost(utilizator);
                if (rezultat == 3) {
                   FunctiiAjutatoare.afisFolowPost(utilizator, follow, 1);
                } else {
                    answer.answerfolowing(rezultat);
                }
            }

            if (action.equals("-get-user-posts")) {
                utilizator.setParametriUser(user, parola);

                int rezultat = utilizator.verificaUserPost(utilizator);
                if (rezultat == 3) {
                    int rez = FunctiiAjutatoare.veruserpre(follow);
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

                int rezultat = utilizator.verificaUserPost(utilizator);
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

                int rezultat = utilizator.verificaUserPost(utilizator);
                if (rezultat == 3) {
                    // verificam id
                    FunctiiAjutatoare.afisarefollowing(utilizator, 1, "0");
                } else {
                    answer.answerfolowing(rezultat);
                }
            }

            // region 16 afisam persoanele care-l urmaresc
            if(action.equals("-get-followers")) {
                utilizator.setParametriUser(user, parola);

                int rezultat = utilizator.verificaUserPost(utilizator);
                if (rezultat == 3) {
                    int rezultatSecund = FunctiiAjutatoare.veruserpre(follow);
                    if(rezultatSecund == 3) {
                        FunctiiAjutatoare.afisarefollowing(utilizator, 2, follow);
                    } else {
                        answer.answeregion16(rezultatSecund);
                    }
                } else {
                    answer.answeregion16(rezultat);
                }
            }

            //Region 17
            if(action.equals("-get-most-liked-posts")) {
                utilizator.setParametriUser(user, parola);

                int rezultat = utilizator.verificaUserPost(utilizator);
                if (rezultat == 3) {
                    FunctiiAjutatoare.afisaremostlikepost( 1);
                } else {
                    answer.answeregion16(rezultat);
                }
            }

            //Region 18
            if(action.equals("-get-most-commented-posts")) {
                utilizator.setParametriUser(user, parola);

                int rezultat = utilizator.verificaUserPost(utilizator);
                if (rezultat == 3) {
                    FunctiiAjutatoare.afisaremostlikepost( 2);
                } else {
                    answer.answeregion16(rezultat);
                }
            }

            //Region 19
            if(action.equals("-get-most-followed-users")) {
                utilizator.setParametriUser(user, parola);

                int rezultat = utilizator.verificaUserPost(utilizator);
                if (rezultat == 3) {
                    FunctiiAjutatoare.afisareMostFollowed();
                } else {
                    answer.answeregion16(rezultat);
                }
            }

            if(action.equals("-get-most-liked-users")) {
                utilizator.setParametriUser(user, parola);

                int rezultat = utilizator.verificaUserPost(utilizator);
                if (rezultat == 3) {
                     FunctiiAjutatoare.afisareRegion20();
                } else {
                    answer.answeregion16(rezultat);
                }
            }
        }

    }
}