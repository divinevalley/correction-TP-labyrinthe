package labyrinthe.rencontres;

/***
 * Boss est une gargouille
 */
public class Boss extends Gargouille{ // un boss est une gargouille
    /**
     * renvoie texte pour le scenario genere en fin de programme:
     *
     * @return toujours "La bataille finale!" pour un boss
     */
    @Override
    public String rencontrer() {
        return "La bataille finale!";
    }
}
