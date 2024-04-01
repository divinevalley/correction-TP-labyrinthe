package labyrinthe.rencontres;

/***
 * classe Rien, represente "rien" (pas de monstre, etc) dans la piece
 */
public class Rien extends Rencontre{
    /**
     * renvoie texte pour le scenario genere en fin de programme:
     *
     * @return toujours "Un moment pacifique." pour un type "rien"
     */
    @Override
    public String rencontrer() {
        return "Un moment pacifique.";
    }
}
