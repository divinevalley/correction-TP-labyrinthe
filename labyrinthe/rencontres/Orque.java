package labyrinthe.rencontres;

/***
 * classe Orque
 */
public class Orque extends Monstre{
    /***
     * suit le format du message Monstre
     * @return String, le but etant:
     * Un [type de monstre] affreux!
     */
    @Override
    public String rencontrer() {
        return msgDebut + "orque" + msgFin;
    }
}
