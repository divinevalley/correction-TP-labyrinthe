package labyrinthe.soumission;

import labyrinthe.code_squelette.Exterieur;
import labyrinthe.code_squelette.Labyrinthe;
import labyrinthe.code_squelette.Piece;
import labyrinthe.code_squelette.RencontreType;

import java.util.Arrays;

/***
 * classe MonLabyrinthe contient notre propre labyrinthe avec toutes
 * ces propriétés (pièces, etc)
 */
public class MonLabyrinthe implements Labyrinthe {
    /***
     * tableau de toutes les pieces dans le labyrinthe, un max de 50
     */
    private Piece[] piecesDansLabyrinthe = new Piece[50];
    /***
     * tab à 2D, représentant la liste de pieces avec leurs pièces adjacentes
     */
    private Piece[][] listeAdjacence = new Piece[50][8];

    /**
     * Retourne un tableau avec toutes les Pieces du Labyrinthe.
     *
     * @return - l'ensemble des Pieces.
     */
    @Override
    public Piece[] getPieces() {
        return piecesDansLabyrinthe;
    }

    /**
     * Retourne le nombre total de Pieces dans le Labyrinthe.
     *
     * @return - le nombre de Pieces.
     */
    @Override
    public int nombreDePieces() {
        int nbCasesRemplies = 0;
        for (Piece piece : piecesDansLabyrinthe) {
            if (piece != null){
                nbCasesRemplies++ ;
            }
        }
        return nbCasesRemplies;
    }

    /**
     * Crée un corridor (lien) entre l'Exterieur et une Piece. Si l'un ou les
     * deux paramètres ne font pas partie du Labyrinthe, il les ajoute. Cette
     * méthode doit être invoquée au minimum une fois.
     *
     * @param out - l'Exterieur (vous pouvez prendre une instance comme
     *            Exterieur.getExterieur())
     * @param e   - une autre Piece
     */
    @Override
    public void ajouteEntree(Exterieur out, Piece e) {
        // ajouter Exterieur au labyrinthe si pas encore sur la liste
        if (piecesDansLabyrinthe[0] != out){
            piecesDansLabyrinthe[0] = out;
        }
        // ajouter Piece si pas encore sur la liste
        if (piecesDansLabyrinthe[e.getID()] != e){
            piecesDansLabyrinthe[e.getID()] = e;
        }

        // si pas liens (c-a-d pieces connectees est un tableau vide)
        if (Arrays.equals(this.getPiecesConnectees(out),new Piece[8])){
            // faire le lien
            ajouteCorridor(out, e);
        }
    }

    public Piece choisirPiece(){
        for (Piece piece : this.piecesDansLabyrinthe){
            if (piece != null && piece.getID()!=0){ // on veut piece pas id=0
                return piece;
            }
        }
        // si vraiment aucune piece dans le labyrinthe? en creer une
        return new Piece(1, RencontreType.RIEN);
    }

    /**
     * Crée un corridor (lien) entre deux Pieces. Si l'une ou les deux
     * Pieces ne font pas partie du Labyrinthe, il les ajoute.
     *
     * @param e1 - une Piece
     * @param e2 - une autre Piece
     */
    @Override
    public void ajouteCorridor(Piece e1, Piece e2) {
        // ajouter les deux pieces au labyrinthe au cas ou
        // (si existe deja, ca ne va rien changer)
        piecesDansLabyrinthe[e1.getID()] = e1;
        piecesDansLabyrinthe[e2.getID()] = e2;

        // maintenant relier :
        // eg. si arguments (1,2), veut dire on veut lier pieces 1 et 2
        // liste d'adjac va etre: [[], [2], [1], [], [], ...]
        // c-a-d: mettre piece 2 dans l'idx 1, et piece 1 dans l'idx 2

        //trouver mes tableaux
        Piece[] piecesLieesAIdE1 = getPiecesConnectees(e1);
        Piece[] piecesLieesAIdE2 = getPiecesConnectees(e2);

        // verifier que mes tableaux pas full
        if (aAtteintMax(piecesLieesAIdE1) || aAtteintMax(piecesLieesAIdE2)){
            System.out.println(e1 + " ou " + e2 + " a deja atteint le maximum de corridors (soit 8)");
            return; // sortir car refuser l'ajout
        }

        // verifier lien n'existe pas deja
        if (existeCorridorEntre(e1, e2)){
            System.out.println("corridor existe deja!");
            return;
        }

        ajouterPieceAListeDeLautre(e1, e2);
        ajouterPieceAListeDeLautre(e2, e1);
    }

    /***
     * Ajoute p1 à la liste de pieces connectées de p2.
     * Cette fonction est conçue pour être appelée 2 fois,
     * dans les deux sens. (p1, p2), et (p2, p1)
     *
     * @param p1
     * @param p2
     */
    private void ajouterPieceAListeDeLautre(Piece p1, Piece p2){
        //trouver mes tableaux
        Piece[] piecesLieesAIdP1 = getPiecesConnectees(p1);
        Piece[] piecesLieesAIdP2 = getPiecesConnectees(p2);
        // iterer pour trouver une place vide (null) dans tableau pour p2
        for (int i = 0; i < piecesLieesAIdP2.length; i++) {
            if (piecesLieesAIdP1[i]==p1){ // si doublon (piece deja sur liste)
                break; // on n'ajoute pas
            }
            if (piecesLieesAIdP2[i] == null) { // si null, ajouter
                piecesLieesAIdP2[i] = p1; //ajouter p1 a p2
                break;
            } // sinon (si non null aka rempli, continuer a chercher)
        }
    }

    /***
     * Permet de tester voir si un tableau de pieces connectees est deja rempli ou pas,
     * ce qui permet de savoir si on peut en rajouter ou pas
     *
     * @param piecesConnectees (represente la liste de pieces connectees, d'une taille de 8)
     * @return true si le tableau est rempli de Pieces non null
     */
    private boolean aAtteintMax(Piece[] piecesConnectees){
        for (Piece piece : piecesConnectees) {
            if (piece==null){
                return false;
            }
        }
        return true;
    }

    /**
     * Retourne vrai si et seulement si deux Pieces font partie du Labyrinthe et
     * sont connectées via un corridor. Les corridors ne sont pas orientés.
     *
     * @param e1 - une Piece
     * @param e2 - une autre Piece
     * @return - true si e1 et e2 sont dans le Labyrinthe et il y a un corridor
     * entre eux; false sinon.
     */
    @Override
    public boolean existeCorridorEntre(Piece e1, Piece e2) {
        if (e1 == null || e2 == null){
            return false;
        }

        //parcourir toutes les pieces adjacentes de e2
        for (Piece piece: getPiecesConnectees(e2)) {
            if (e1 == piece){ // si match,
                return true;
            }
        }
        return false;
    }

    /**
     * Retourne l'ensemble des Pieces reliées (par des corridors) à la Piece donnée
     * <p>
     * Pour un Labyrinthe lab, l'appel
     * lab.PiecesConnectes(Exterieur.getExterieur()) doit nous donner
     * l'ensemble des entrées.
     *
     * @param e - une Piece
     * @return - un tableau de Pieces, reliées au e; null si e ne fait pas
     * partie du Labyrinthe
     */
    @Override
    public Piece[] getPiecesConnectees(Piece e) {
        // chercher la piece dans liste de toutes les pieces
        if (e != null){
            for (Piece piece : piecesDansLabyrinthe) {
                if (piece == e) { // si trouvee
                    int pieceId = e.getID();

                    // ensuite chercher sa liste de pieces connectees
                    return listeAdjacence[pieceId];
                }
            }
        }

        return null;
    }


}
