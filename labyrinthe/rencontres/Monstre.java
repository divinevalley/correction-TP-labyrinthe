package labyrinthe.rencontres;

/***
 * class Monstre
 */
public abstract class Monstre extends Rencontre{
    protected String msgDebut = "Un ";
    protected String msgFin = " affreux!";
    /**
     * Ne devrait pas etre appelee ici mais plutot
     * au niveau specifique (type de monstre)
     *
     * @return String, le but etant:
     * Un [type de monstre] affreux!
     */
    @Override
    public String rencontrer() {
        return null;
    }
}
