package labyrinthe;

import labyrinthe.code_squelette.*;
import labyrinthe.soumission.*;

//import code_squelette.*;
//import soumission.*;

import org.junit.*;
import java.util.Arrays;

public class Autograder {
    private MonLabyrinthe monLabyrinthe;
    private MonAventure monAventure;

    private Exterieur exter = Exterieur.getExterieur();
    private Piece piece1 = new Piece(1, RencontreType.RIEN);
    private Piece piece2 = new Piece(2, RencontreType.RIEN);
    private Piece piece3 = new Piece(3, RencontreType.BOSS);
    private Piece piece4 = new Piece(4, RencontreType.BOSS);
    private Piece piece5 = new Piece(5, RencontreType.RIEN);
    private Piece piece6 = new Piece(6, RencontreType.TRESOR);
    private Piece piece7 = new Piece(7, RencontreType.TRESOR);
    private Piece piece8 = new Piece(8, RencontreType.RIEN);
    private Piece piece9 = new Piece(9, RencontreType.RIEN);
    private Piece piece10 = new Piece(10, RencontreType.RIEN);
    private Piece piece11 = new Piece(11, RencontreType.RIEN);


    @Before
    public void reset() {
        monLabyrinthe = new MonLabyrinthe();
        monAventure = new MonAventure(monLabyrinthe);
    }

    // initialise listPieces si besoin
    public Piece[] initialiserListPieces(int n){
        Piece[] listPieces = monLabyrinthe.getPieces();
        if(listPieces==null){ // initialiser si besoin
            listPieces = new Piece[50];
        } else if (listPieces.length<n) { // retailler si besoin
            listPieces = new Piece[n];
        }
        return listPieces;
    }

    // 1. tester getPieces(). 5 pts.
    @Test
    public void testA_getPieces() {
        System.out.println("length of Pieces[] : " + monLabyrinthe.getPieces().length);
        Assert.assertTrue(monLabyrinthe.getPieces() instanceof Piece[]);
    }

    // 2. tester nombreDePieces(), (expected = 0 or not initialized)
    @Test
    public void testA_nombreDePieces_1() {
        Piece[] listPieces = monLabyrinthe.getPieces();
        // don't wnat to initialize it for them, bc may mess up count.
        if(listPieces!=null){ // null check
            Assert.assertEquals(0, monLabyrinthe.nombreDePieces()); // 2.5 pts
        } else{
            System.out.println("testA_nombreDePieces_1: Piece[] not initialized. ok.");
        }
    }

    // (2. suite) nombreDePieces() : ajouter 2 pieces, compter nb pieces (expected=2)
    @Test
    public void testA_nombreDePieces_2() {
        Piece[] listPieces = initialiserListPieces(2);
        listPieces[0] = exter;
        listPieces[1] = piece1;
        monLabyrinthe.ajouteCorridor(exter, piece1);
        Assert.assertEquals(2, monLabyrinthe.nombreDePieces()); // 2.5 pts
    }

    public void setupAjouteEntreeExterPiece1(){
        initialiserListPieces(2);
        monLabyrinthe.ajouteEntree(exter, piece1);
    }

    // 3. tester ajouteEntree(), 1.5 pts. expected: Piece 1 sur la liste (tableau) de Piece[].
    @Test
    public void testA_ajouteEntree_1() {
        setupAjouteEntreeExterPiece1();

        System.out.println("testA_ajouteEntree_1(): on ajoute une piece 1. check si <1- est sur tableau?: ");
        System.out.println("pieces[]: " + Arrays.toString(monLabyrinthe.getPieces()));

        // check: méthode a ajouté les deux sur la liste des Pieces du labyr
        // piece <1-...> sur la liste
        Assert.assertTrue(Arrays.toString(monLabyrinthe.getPieces()).contains("<1-")); // 1.5 pt
    }

    // (suite) 1.5 pts. Piece 0 sur la liste aussi.
    @Test
    public void testA_ajouteEntree_2() {
        setupAjouteEntreeExterPiece1();
        System.out.println("testA_ajouteEntree_2(). ajoute 0. check piece <0- sur la liste aussi ");
        // check: exterieur <0-...> sur la liste
        Assert.assertTrue(Arrays.toString(monLabyrinthe.getPieces()).contains("<0-")); // 1.5 pt
    }

    // (suite) creer lien/corridor entre. 2 pts.
    @Test
    public void testA_ajouteEntree_3() {
        setupAjouteEntreeExterPiece1();

        // check: méthode a crée un lien (corridor) entre Exterieur et une Piece:
        Assert.assertTrue(monLabyrinthe.existeCorridorEntre(exter, piece1)); // 2 pt

        // si fail (existeCorridorEntre() ne fonctionne pas), on teste avec 3bis
    }

    // (suite) pour points partiels (1 pt): seulement si test precedent fail, tester "manuellement"
    @Test
    public void testA_ajouteEntree_3bis() {
        setupAjouteEntreeExterPiece1();

        // en 2e recours, parcourir manuellement les Pieces connectees:
        boolean resultat = false;
        for (Piece piece: monLabyrinthe.getPiecesConnectees(piece1)) {
            if (exter == piece) { // si  match
                resultat = true;
                break;
            }
        }
        Assert.assertTrue(resultat);
    }

    // setup 5. setup labyrinthe 0123 for existeCorridorEntre() :
    public void setupExisteCorridorEntre(){
        Piece[] listPieces = initialiserListPieces(4);
        if(!Arrays.asList(listPieces).containsAll(Arrays.asList(piece1, piece2, piece3))){
            // si besoin, ajouter les pieces explicitement
            listPieces[0] = exter;
            listPieces[1] = piece1;
            listPieces[2] = piece2;
            listPieces[3] = piece3;
        }
    }

    // 5. existeCorridorEntre()
    @Test
    public void testA_existeCorridorEntre_1() {
        setupExisteCorridorEntre();
        monLabyrinthe.ajouteCorridor(piece1, piece2); // 1-2 lien
        monLabyrinthe.ajouteCorridor(piece1, piece3); // 1-3 lien

        Assert.assertTrue(monLabyrinthe.existeCorridorEntre(piece1, piece2));// 1-2 true
    }

    // (suite) existeCorridorEntre()
    @Test
    public void testA_existeCorridorEntre_2() {
        setupExisteCorridorEntre();
        // tester avec labyrinthe avec pieces 123, corridors entre 1-2 et 1-3.

        monLabyrinthe.ajouteCorridor(piece1, piece2); // 1-2 lien
        monLabyrinthe.ajouteCorridor(piece1, piece3); // 1-3 lien

        Assert.assertTrue(monLabyrinthe.existeCorridorEntre(piece2, piece1));// 2-1 true aussi

    }

    // (suite) existeCorridorEntre()
    @Test
    public void testA_existeCorridorEntre_3() {
        setupExisteCorridorEntre();
        monLabyrinthe.ajouteCorridor(piece1, piece2); // 1-2 lien
        monLabyrinthe.ajouteCorridor(piece1, piece3); // 1-3 lien
        Assert.assertTrue(monLabyrinthe.existeCorridorEntre(piece1, piece3)); // 1-3 true
    }

    // (suite) existeCorridorEntre()
    @Test
    public void testA_existeCorridorEntre_4() {
        setupExisteCorridorEntre();
        monLabyrinthe.ajouteCorridor(piece1, piece2); // 1-2 lien
        monLabyrinthe.ajouteCorridor(piece1, piece3); // 1-3 lien

        Assert.assertFalse(monLabyrinthe.existeCorridorEntre(piece2, piece3)); // 2-3 false
    }

    // (suite) existeCorridorEntre()
    @Test
    public void testA_existeCorridorEntre_5() {
        setupExisteCorridorEntre();
        monLabyrinthe.ajouteCorridor(piece1, piece2); // 1-2 lien
        monLabyrinthe.ajouteCorridor(piece1, piece3); // 1-3 lien

        // Imaginons qu'on essaie d'interroger si 1-4 existe, ou qqchose comme ça, alors que piece 4 n'existe pas (null).
        Assert.assertFalse(monLabyrinthe.existeCorridorEntre(piece1, null)); // ne plante pas. false.
    }

    public void setupAjouteCorridor(int n){
        try{
            monLabyrinthe.ajouteCorridor(piece3, piece4);
        } catch (IndexOutOfBoundsException e) {
            initialiserListPieces(n);
            monLabyrinthe.ajouteCorridor(piece3, piece4);
        }
    }

    // 4.  ajouteCorridor() devrait ajouter la piece a la liste de pieces Piece[]
    @Test
    public void testA_ajouteCorridor_1() {
        // after ajouteCorridor(): (modifie le tableau de Piece[])
        setupAjouteCorridor(2);
        Assert.assertTrue(Arrays.toString(monLabyrinthe.getPieces()).contains("<4-")); // 1 pt. nouvelle piece ajoutee

    }
    // (suite) tester ajouteCorridor(). augmente le nombre de pieces au total +2
    @Test
    public void testA_ajouteCorridor_2() {
        // before ajouteCorridor():
        int beforeAdd = monLabyrinthe.nombreDePieces();
        setupAjouteCorridor(2);
        Assert.assertEquals(beforeAdd + 2, monLabyrinthe.nombreDePieces()); // 1 pt. nb pieces augmenté de 2
    }

    // (suite) tester ajouteCorridor() si full (max corridors 8)
    @Test
    public void testA_ajouteCorridor_3() {
        setupAjouteCorridor(10);
        monLabyrinthe.ajouteCorridor(piece1, exter);
        monLabyrinthe.ajouteCorridor(piece1, piece2);
        monLabyrinthe.ajouteCorridor(piece1, piece3);
        monLabyrinthe.ajouteCorridor(piece1, piece4);
        monLabyrinthe.ajouteCorridor(piece1, piece7);
        monLabyrinthe.ajouteCorridor(piece1, piece8);
        monLabyrinthe.ajouteCorridor(piece1, piece9);
        monLabyrinthe.ajouteCorridor(piece1, piece10); // 8e piece
        monLabyrinthe.ajouteCorridor(piece1, piece11); // essaie. un corridor de trop.

        // idéalement 9e piece devrait ne pas etre ajoutee. mais au moins size doit être 8.
        Assert.assertEquals(8, monLabyrinthe.getPiecesConnectees(piece1).length);

    }

    // (suite) tester ajouteCorridor() ajout deux sens
    @Test
    public void testA_ajouteCorridor_4() {
        setupAjouteCorridor(2);
        monLabyrinthe.ajouteCorridor(piece1, piece7);

        // ajout des 2 côtés : piecesConnectees de piece7 contient piece1
        System.out.println(Arrays.toString(monLabyrinthe.getPiecesConnectees(piece7)).contains("<1-"));
    }


    // (suite) tester ajouteCorridor()  ajout deux sens
    @Test
    public void testA_ajouteCorridor_5() {
        setupAjouteCorridor(2);
        monLabyrinthe.ajouteCorridor(piece1, piece7);

        // ... et piece1 contient piece7
        System.out.println(Arrays.toString(monLabyrinthe.getPiecesConnectees(piece1)).contains("<7-"));
    }


    public MonLabyrinthe setupLabyrintheSimple(){
        initialiserListPieces(4);
        // labyrinthe simple 0123, liens 0-1, 1-2, 2-3.
        monLabyrinthe.ajouteCorridor(exter, piece2);
        monLabyrinthe.ajouteCorridor(piece1, piece2);
        monLabyrinthe.ajouteCorridor(piece2, piece3);
        return monLabyrinthe;
    }

    // 6. tester avec labyrinthes simples (0123, peu de liens), et complexe (012345678..., beaucoup de liens)
    @Test
    public void testA_getPiecesConnectees_1() {
        monLabyrinthe = setupLabyrintheSimple();
        Assert.assertTrue(Arrays.toString(monLabyrinthe.getPiecesConnectees(piece1)).contains("<2-"));
        // 1 reliee a 2. 1 pt.

    }

    // labyrinthe simple encore
    @Test
    public void testA_getPiecesConnectees_2() {
        monLabyrinthe = setupLabyrintheSimple();
        Assert.assertTrue(Arrays.toString(monLabyrinthe.getPiecesConnectees(piece2)).contains("<1-"));
        // 2 reliee a 1. 1 pt.
    }

    public MonLabyrinthe setupLabyrintheComplexe(){
        initialiserListPieces(9);
        // labyrinthe complexe 012345678, liens 0-1, 1-2, 1-3, 1-4, 1-5, 1-6, 2-4, 3-4, 3-5, 3-6, 3-7, 3-8
        monLabyrinthe.ajouteCorridor(exter, piece1);
        monLabyrinthe.ajouteCorridor(piece1, piece2);
        monLabyrinthe.ajouteCorridor(piece1, piece3);
        monLabyrinthe.ajouteCorridor(piece1, piece4);
        monLabyrinthe.ajouteCorridor(piece1, piece5);
        monLabyrinthe.ajouteCorridor(piece1, piece6);
        monLabyrinthe.ajouteCorridor(piece2, piece4);
        monLabyrinthe.ajouteCorridor(piece3, piece4);
        monLabyrinthe.ajouteCorridor(piece3, piece5);
        monLabyrinthe.ajouteCorridor(piece3, piece6);
        monLabyrinthe.ajouteCorridor(piece3, piece7);
        monLabyrinthe.ajouteCorridor(piece3, piece8);
        return monLabyrinthe;
    }

    @Test
    public void testA_getPiecesConnectees_3(){
        monLabyrinthe = setupLabyrintheComplexe();

        String piecesConnecteesPiece1 = Arrays.toString(monLabyrinthe.getPiecesConnectees(piece1));
        Assert.assertTrue(piecesConnecteesPiece1.contains("<0-")
                && piecesConnecteesPiece1.contains("<2-")
                && piecesConnecteesPiece1.contains("<3-")
                && piecesConnecteesPiece1.contains("<4-")
                && piecesConnecteesPiece1.contains("<5-")
                && piecesConnecteesPiece1.contains("<6-")); // contient 0, 2, 3, 4, 5, 6.  1 pt.
    }

    @Test
    public void testA_getPiecesConnectees_4(){
        monLabyrinthe = setupLabyrintheComplexe();
        Assert.assertTrue(Arrays.toString(monLabyrinthe.getPiecesConnectees(piece8)).contains("<3-")); // contient 3. 1 pt.
    }

    @Test
    public void testA_getPiecesConnectees_5(){
        monLabyrinthe = setupLabyrintheComplexe();
        Assert.assertTrue(Arrays.toString(monLabyrinthe.getPiecesConnectees(piece5)).contains("<1-")); // contient 1 et 3. 1 pt.
    }

    // ==============B===================

    @Test
    public void testB_estPacifique_true(){
        initialiserListPieces(3);
        monLabyrinthe.ajouteEntree(exter, piece1);
        monLabyrinthe.ajouteCorridor(piece1, piece2);
        // on va ajouter pieces manuellement au cas ou le pgm ne le fait pas
        monLabyrinthe.getPieces()[0] = exter;
        monLabyrinthe.getPieces()[1] = piece1; //rien
        monLabyrinthe.getPieces()[2] = piece2; //rien
        Assert.assertTrue(monAventure.estPacifique()); // true
    }

    @Test
    public void testB_estPacifique_false(){
        initialiserListPieces(3);
        // on va ajouter pieces manuellement
        monLabyrinthe.getPieces()[0] = exter;
        monLabyrinthe.getPieces()[1] = piece1;
        monLabyrinthe.getPieces()[3] = piece3; //boss
        Assert.assertFalse(monAventure.estPacifique()); // false
    }

    @Test
    public void testB_contientDuTresor_true(){
        initialiserListPieces(3);
        monLabyrinthe.getPieces()[0] = exter; //rien
        monLabyrinthe.getPieces()[3] = piece3; //boss
        monLabyrinthe.getPieces()[6] = piece6; //tresor
        Assert.assertTrue(monAventure.contientDuTresor()); // true
    }

    @Test
    public void testB_contientDuTresor_false(){
        initialiserListPieces(2);
        monLabyrinthe.getPieces()[0] = exter; //rien
        monLabyrinthe.getPieces()[3] = piece3; //boss
        Assert.assertFalse(monAventure.contientDuTresor()); // false
    }

    @Test
    public void testB_contientBoss_true(){
        initialiserListPieces(3);
        monLabyrinthe.getPieces()[0] = exter; //rien
        monLabyrinthe.getPieces()[1] = piece1; //rien
        monLabyrinthe.getPieces()[3] = piece3; //boss
        Assert.assertTrue(monAventure.contientBoss()); // true
    }

    @Test
    public void testB_contientBoss_false(){
        initialiserListPieces(1);
        monLabyrinthe.getPieces()[0] = exter; //rien
        Assert.assertFalse(monAventure.contientBoss()); // false
    }

    //==================C===================
/*
    labyrinthe simple 0123boss
    labyrinthe 012345 sans boss, renvoie tab vide
    labyrinthe sans chemin 0145boss, renvoie tab vide
    labyrinthe 012345boss mais pas de lien jusqu'au bout, renvoie tab vide
    labyrinthe avec boss au milieu 0123boss456, s'arrête au boss */
    public void setupLabyrinthe0123boss(){
        initialiserListPieces(4);
        monLabyrinthe.getPieces()[0] = exter; //rien
        monLabyrinthe.getPieces()[1] = piece1; //rien
        monLabyrinthe.getPieces()[2] = piece2; //rien
        monLabyrinthe.getPieces()[3] = piece3; //boss
        //corridors
        monLabyrinthe.ajouteCorridor(exter,piece1); //0-1
        monLabyrinthe.ajouteCorridor(piece1,piece2); //1-2
        monLabyrinthe.ajouteCorridor(piece2,piece3); //2-3
    }

    @Test
    public void testC_cheminJusquauBoss_1_labyr0123boss(){
        setupLabyrinthe0123boss();

        // a few possibilities are ok:
        // expected: [<0-RIEN>, <1-RIEN>, <2-RIEN>, <3-BOSS>, null, ...]
        // OR [<0-RIEN>, <1-RIEN>, <2-RIEN>, <3-BOSS>]

        Piece[] cheminActual = monAventure.cheminJusquAuBoss();
        Piece[] cheminExpectedSubset = {exter, piece1, piece2, piece3};

        Assert.assertTrue(Arrays.asList(cheminActual).containsAll(Arrays.asList(cheminExpectedSubset)));

    }

    public boolean estTableauVide(Piece[] tableauAEvaluer){
        return Arrays.toString(tableauAEvaluer).contains("[]") || Arrays.toString(tableauAEvaluer).contains("[null");
    }

    @Test
    public void testC_cheminJusquauBoss_2_labyr012sansBoss(){

        initialiserListPieces(3);
        monLabyrinthe.getPieces()[0] = exter; //rien
        monLabyrinthe.getPieces()[1] = piece1; //rien
        monLabyrinthe.getPieces()[2] = piece2; //rien
        //corridors
        monLabyrinthe.ajouteCorridor(exter,piece1); //0-1
        monLabyrinthe.ajouteCorridor(piece1,piece2); //1-2

        Piece[] cheminActual = monAventure.cheminJusquAuBoss();
        Assert.assertTrue(estTableauVide(cheminActual));
    }

    @Test
    public void testC_cheminJusquauBoss_3_labyr014sanschemin(){
        initialiserListPieces(3);
        monLabyrinthe.getPieces()[0] = exter; //rien
        monLabyrinthe.getPieces()[1] = piece1; //rien
        monLabyrinthe.getPieces()[4] = piece4; //boss
        //corridors
        monLabyrinthe.ajouteCorridor(exter,piece1); //0-1
        monLabyrinthe.ajouteCorridor(piece1,piece4); //1-4

        Piece[] cheminActual = monAventure.cheminJusquAuBoss();
        Assert.assertTrue(estTableauVide(cheminActual));


    }

    @Test
    public void testC_cheminJusquauBoss_4_labyrSansLiens(){
        initialiserListPieces(4);
        monLabyrinthe.getPieces()[0] = exter; //rien
        monLabyrinthe.getPieces()[1] = piece1; //rien
        monLabyrinthe.getPieces()[2] = piece2; //rien
        monLabyrinthe.getPieces()[3] = piece3; //boss
        //corridors
        monLabyrinthe.ajouteCorridor(exter,piece1); //0-1
        monLabyrinthe.ajouteCorridor(piece2,piece3); //2-3. il manque le lien 1-2.

        Piece[] cheminActual = monAventure.cheminJusquAuBoss();
        Assert.assertTrue(estTableauVide(cheminActual));
    }

    @Test
    public void testC_cheminJusquauBoss_5_labyrBossMilieu(){
        initialiserListPieces(5);
        monLabyrinthe.getPieces()[0] = exter; //rien
        monLabyrinthe.getPieces()[1] = piece1; //rien
        monLabyrinthe.getPieces()[2] = piece2; //rien
        monLabyrinthe.getPieces()[3] = piece3; //boss .. s'arrete ici!
        monLabyrinthe.getPieces()[4] = piece4; //boss  <--- ne devrait pas etre present!
        //corridors
        monLabyrinthe.ajouteCorridor(exter,piece1); //0-1
        monLabyrinthe.ajouteCorridor(piece1,piece2); //2-3
        monLabyrinthe.ajouteCorridor(piece2,piece3); //2-3
        monLabyrinthe.ajouteCorridor(piece3,piece4); //3-4

        Piece[] cheminActual = monAventure.cheminJusquAuBoss();

        System.out.println("Expected: 0123, no 4. cheminActual: " + Arrays.toString(cheminActual));
        // piece 4 ne devrait PAS etre presente.
        Assert.assertFalse(Arrays.toString(cheminActual).contains("<4-"));
    }

    // parties D-E manuel
    // Scanner: manuel
    // Rencontre: visual inspection

    @Test
    public void testF_genererScenario(){
        setupLabyrinthe0123boss();
        /*Un moment pacifique.
        Un moment pacifique.
        Un moment pacifique.
        La bataille finale!*/
        Assert.assertTrue(DIROgue.genererScenario(monAventure).contains("moment pacifique"));

    }




}
