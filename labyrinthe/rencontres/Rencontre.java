package labyrinthe.rencontres;

/***
 * classe mere Rencontre pour tout type de rencontres (Monstre, Tresor, etc)
 */
public abstract class Rencontre {
    /***
     * Permet de retourner le String msg desire pour le scenario
     * genere a la fin du programme, en fonction du type d'objet
     *
     * Pour les monstres : “Un [type de monstre] affreux!”
     * Pour les trésors : “[type de trésor]! Quelle chance!”
     * Pour le type de rencontre Rien : “Un moment pacifique.”
     * Pour le boss : “La bataille finale!"
     *
     * @return msg en fonction du type
     */
    public abstract String rencontrer();
}
