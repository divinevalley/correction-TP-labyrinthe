package labyrinthe.soumission;

import labyrinthe.code_squelette.Aventure;
import labyrinthe.code_squelette.Labyrinthe;
import labyrinthe.code_squelette.Piece;
import labyrinthe.code_squelette.RencontreType;

import java.util.Arrays;

/***
 * Mon Aventure permet d'interroger MonLabyrinthe et donne des caractérisques générales :
 * si le jeu est pacifique ou non, etc.
 */
public class MonAventure extends Aventure {

    /**
     * Initialize une Aventure avec un Labyrinthe
     *
     * @param c - la carte de l'Aventure
     */
    public MonAventure(Labyrinthe c) {
        super(c);
    }

    /**
     * Nous dit si l'aventure contient des méchants.
     *
     * @return true si et seulement si la carte de l'Aventure ne contient pas de
     * Pieces avec des RencontreType.MONSTRE ou RencontreType.BOSS.
     */
    @Override
    public boolean estPacifique() {
        for (Piece piece : carte.getPieces()) {
            if (piece != null){
                if (piece.getRencontreType() != RencontreType.RIEN){
                    return false;
                }
            }

        }
        return true;
    }

    /**
     * Nous dit si l'aventure est rentable, en d'autre termes si elle contient du
     * tresor du tout.
     *
     * @return true si et seulement si la carte de l'Aventure contient au minimum une
     * Piece avec RencontreType.TRESOR
     */
    @Override
    public boolean contientDuTresor() {
        for (Piece piece : carte.getPieces()) {
            if (piece != null){
                if (piece.getRencontreType() == RencontreType.TRESOR){
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * Nous dit combien de RencontreType.TRESOR existent dans l'Aventure.
     *
     * @return le nombre de RencontreType.TRESOR dans l'Aventure.
     */
    @Override
    public int getTresorTotal() {
        int count = 0;
        for (Piece piece : carte.getPieces()) {
            if (piece != null){
                if (piece.getRencontreType() == RencontreType.TRESOR){
                    count++;
                }
            }

        }
        return count;
    }

    /**
     * Nous dit si l'Aventure contient un boss (ou plusieurs).
     *
     * @return true si et seulement si l'Aventure contient une Rencontre.BOSS ou plus.
     */
    @Override
    public boolean contientBoss() {
        for (Piece piece : carte.getPieces()) {
            if (piece != null){
                if (piece.getRencontreType() == RencontreType.BOSS){
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * Une façon de determiner si le donjon est gagnable. Retourne un chemin en partant
     * de l'Exterieur jusqu'au Boss. Nous faisons l'hypothèse qu'un Labyrinthe sera
     * toujours construit de façon à ce qu'en commençant depuis l'Extérieur (ID=0) et en
     * suivant des Pieces avec des ID en ordre croissant, nous devrions trouver le Boss,
     * tomber dans une boucle, ou arreter à une Piece sans sortie.
     *
     * @return - une chaine de Pieces telle que chaine[0].getID()==0,
     * chaine[i+1].getID()=chaine[i].getID()+1 et
     * chaine[chaine.length-1].getRencontreType==Rencontre.BOSS; s'il n'est
     * pas possible de trouver le Boss, retourne un tableau vide.
     */
    @Override
    public Piece[] cheminJusquAuBoss() {
        Piece[] chemin = new Piece[50];
        if (!this.contientBoss()){ // si on sait deja que pas de boss,
            return chemin; // stop, renvoie tableau vide
        }
        Piece[] pieces = carte.getPieces();

        int i = 0;
        while(true){

            // transferer la piece i dans chemin
            chemin[i] = pieces[i];

            // plusieurs conditions a checker:
            boolean prochainIdNExistePas = (pieces[i+1] == null);
            boolean pieceProchainIdNonConnectee = !carte.existeCorridorEntre(pieces[i], pieces[i+1]);
            boolean onATrouveBoss = (pieces[i].getRencontreType() == RencontreType.BOSS);

            // si une de ces trois conditions true, on s'arrete
            if (prochainIdNExistePas || pieceProchainIdNonConnectee || onATrouveBoss){
                break;
            }

            i++;
        }

        // mnt on s'est arrete mais peut etre on n'a pas trouve de boss.
        // besoin de voir si possiblement on a pas de boss
        for (Piece piece : chemin) {
            if (piece != null) {
                if (piece.getRencontreType() == RencontreType.BOSS) {
                    // on sait qu'on a un boss
                    return chemin;
                }
            }
        }

        // si on est rendu ici, ca veut dire pas de boss
        return new Piece[50];
    }
}
