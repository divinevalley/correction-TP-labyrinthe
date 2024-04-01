package labyrinthe.rencontres;

/***
 * classe Garougille
 */
public class Gargouille extends Monstre {
    /***
     * suit le format du message Monstre
     * @return String, le but etant:
     * Un [type de monstre] affreux!
     */
    @Override
    public String rencontrer() {
        return msgDebut + "gargouille" + msgFin;
    }
}
