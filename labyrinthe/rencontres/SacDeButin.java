package labyrinthe.rencontres;

/***
 * classe sac de butin
 */
public class SacDeButin extends Tresor {

    /***
     * Suit le format du message Tresor
     * Ici type de tresor = "sac de butin"
     *
     * @return String, le but etant:
     * [type de tresor]! Quelle chance!
     */
    @Override
    public String rencontrer() {
        return "Sac de butin" + msgFin;
    }
}
