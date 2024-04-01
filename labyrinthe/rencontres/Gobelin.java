package labyrinthe.rencontres;

/***
 * classe Gobelin
 */
public class Gobelin extends Monstre{
    /***
     * suit le format du message Monstre
     * @return String, le but etant:
     * Un [type de monstre] affreux!
     */
    @Override
    public String rencontrer() {
        return msgDebut + "gobelin" + msgFin;
    }
}
