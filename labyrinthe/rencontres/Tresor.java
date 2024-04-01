package labyrinthe.rencontres;

/***
 * class Tresor
 */
public abstract class Tresor extends Rencontre{
    String msgFin = "! Quelle chance!";
    /**
     * Ne devrait pas etre appelee ici mais plutot
     * au niveau specifique (type de tresor)
     *
     * @return le but etant
     * [type de tresor]! Quelle chance!
     */
    @Override
    public String rencontrer() {
        return null;
    }
}
