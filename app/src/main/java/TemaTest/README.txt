Neculau Sanda-Elena seria CB, grupa 323


La aceasta tema m-am folosit doar de fisierele CSV, pentru a retine informatia si pentru a extrage-o.

!!! OUTPUT-URI !!!
(acestea se schimba la fiecare test, insa semnificatia lor ramane aceeasi)

1 --> eroare de autentificare
2 --> eroare de logare
3 --> Succes
4 --> identificatorul nu a fost dat
5 --> identificatorul nu a fost valid

La inceput citim parametrii primiti in terminal.
Fiecare actiune la inceput inafara de crearea userului(region 1), creeaza mai intai instanta userului,
pe urma verifica daca se afla in baza de date. Apoi face urmatorii pasi.

Region 1:
Verifica daca a primit toti parametrii, daca i a primit pe toti verifica pe urma daca exista deja in
baza de date, daca nu exista il introducem si returnam o val cu care afisam output-ul. Daca exista deja procedem la fel returnam o val si afisam output-ul.

Region 2:
Verifica daca a primit textul pentru postare, pe urma verifica lungimea si apoi il inntroduce in fisier

Region 3:
Verifica daca a primit id, daca a primit se verifica mai intai daca exista postarea cu id-ul dat. Daca exista se sterge. Si se afiseaza rezultatul specific in functie de rezultat.

Region 4:
Se verifica mai intai daca s-a primit numele useru-lui pe care vrem sa-l urmarim.
Se verifica daca exista user-ul respectiv in baza de date.
Daca exista si --fisierul este gol-- il punem. Daca --fisierul nu este gol-- se verifica daca deja user-ul il urmareste. Daca il urmareste afisam mesajul specific. Daca nu-l urmareste il introducem in fisier.

Region 5:
Se verifica mai intai daca s-a primit numele useru-lui pe care vrem sa-l urmarim.
Se verifica daca exista user-ul respectiv in baza de date.
Daca exista si --fisierul este gol-- nu facem nimic.  Daca --fisierul nu este gol-- se cauta user-ul caruia i-a dat follow si il sterge.

Region 6:
Se verifica id ul daca a fost dat. Apoi se verifica daca user-ul isi da singur like la postare. Daca nu-
si da atunci se verifica daca exista o postare cu id ul dat. Daca exista verificam daca postarea are  like dat. Daca nu are il introducem in fisier(ii dam like).

Region 7:
Se verifica la fel id-ul daca e dat. Apoi se verifica daca exista like cu id-ul dat. Daca exista il stergem.

Region 13:
Se verifica mai intai daca exista postarea cu id-ul dat. Daca exista cream comentariu.

Region 14:
Se verifica daca a fost dat id pentru a sterge comentariul. Daca a fost dat stergem comentariu utilizand doar numele user ului si id-ul. Pentru a sterge linia cu comentariul din fisierul utilizam un fisier temporar.

Region 8:
Se verifica daca s-a primit id-ul comentariului. Se verifica apoi daca comentariul exista dupa id. Daca exista verificam daca are deja like. Daca nu are like il introducem in fisier(ii dam like).

Region 9:
Se verifica la fel ca mai sus, insa daca exista deja like il stergem.

Region 10(get followings post):
Afisam postarile tuturor persoanelor pe care user-ul le urmareste in ordine inversa. De aceea citim fisierul de followeri invers si daca gasim un urmaritor de a lui, pt fiecare in parte citim fisierul de ---postari-- in ordine inversa.

Region 11(get users post):
La fel ca mai sus insa se verifica si daca exista user-ul, caruia vrem sa-i afisam postarile, in baza de date. Daca user-ul nu exista la urmaritorii user-ului sa afiseaza mesajul cerut . In cod un parametru("existauseer") v a lua valoarea '0', daca nu exista.

Region 12(post details):
Verificam daca a fost dat id-ul postarii. Apoi verificam daca exista postarea cu id-ul respectiv, in prealabil afisam si postare daca exista. Pe urma verificam daca aceasta are comentarii, daca da, le afisam impreuna cu nr like-urile pe comentariu daca acestea exista.

Region 15:
Afisam persoanele pe care le urmareste.

Region 16:
Verificam daca exista user-ul respectiv si afisam persoanele care-l urmaresc.La fel ca region 15. 

Region 17:
Pentru a afisa postarile in functie de like-uri o sa utiliza un vector de stringuri care v a retine id-postari si numarul de like-uri intr-un string. Pentru asta calculam intr-un vector de tipul int(cu un numar de elemente = numarul de postari + 1) numarul de like-uri pe postare (indexul blocului v a fi id-ul postarii). Apoi trecem in vectorul de stringuri id si nr de like-uri. Pe urma vectorul de stringuri il ordonam descrescator in functie de numarul de like-uri. Pe urma se face afisarea primelor 5.

Region 18:
Pentru a afisa postarea cu cele mai multe comentarii la fel ca mai sus utilizam un vector de stringuri. Aceeasi implementare, doar output-ul diferit.

Region 19:
Pentru a afisa primii 5 cei mai urmariti useri. Vom utiliza din nou un vector de stringuri in care de data aceasta o sa trecem numele user + numarul de like-uri. Pentru asta calculam intr-un vector de tipul int(cu un numar de elemente = numarul de useri + 1) numarul de followeri (indexul blocului v a fi numarul user-ului).Apoi trecem in vectorul de stringuri userul si nr de followeri. Pe urma vectorul de stringuri il ordonam descrescator in functie de numarul de followeri. Pe urma se face afisarea primilor 5.

Region 20:
Pentru a afisa primii 5 useri apreciati folosim aceasi implementare cu vectorul de string-uri. Avem deja calculat intr-un vector de string-uri numarul de like-uri pe postare la care mai adugam nr de like-uri pe comentarii cu acelasi id. Pentru asta facem un vector de tip int(cu un numar de elemente = nr de postari + 1), unde o sa calculam numarul de like-uri pe comentariu (indexul blocului reprezinta id-ul).
Pe urma in vectorul de tip int (unde sunt like-urile pe comentarii) adaugam din vectorul de stringuri nr de like-uri pe postare si pe urma le trecem inapoi in vectorul de stringuri sub forma id + nr like. Pe urma se face aranjarea lui descrescator, dar si in functie de id(daca 2 s-au mai multe sunt egale se alege ala mai mic). La sfarsit se afiseaza primele 5.