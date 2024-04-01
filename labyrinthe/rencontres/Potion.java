package labyrinthe.rencontres;

/***
 * classe Potion
 */
public class Potion extends Tresor{
    /***
     * Suit le format du message Tresor
     * Ici type de tresor = "Potion"
     *
     * @return String, le but etant:
     * [type de tresor]! Quelle chance!
     */
    @Override
    public String rencontrer() {
        return "Potion" + msgFin;
    }


}
