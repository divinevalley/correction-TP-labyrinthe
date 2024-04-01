package labyrinthe.rencontres;

/***
 * javadoc Artefact Magique ...
 */
public class ArtefactMagique extends Tresor{
    /***
     * Suit le format du message Tresor
     * Ici type de tresor = "artefacte magique"
     *
     * @return String, le but etant:
     * [type de tresor]! Quelle chance!
     */
    @Override
    public String rencontrer() {
        return "Artefact magique" + msgFin;
    }

}
