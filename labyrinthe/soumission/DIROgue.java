package labyrinthe.soumission;

import labyrinthe.code_squelette.*;
import labyrinthe.rencontres.*;
import org.junit.Assert;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/***
 * classe main de TP1 pour IFT1025 Programmation 2, A2023
 * @author author
 */
public class DIROgue {

	public static void main(String[] args) {


		MonLabyrinthe monLabyrinthe = new MonLabyrinthe();
		MonAventure monAventure = new MonAventure(monLabyrinthe);

		// lancer le scanner
		Scanner sc = new Scanner(System.in);
		String input;
		boolean sortirBoucle = false; // <- sera declenche par "FIN" pour sortir de la boucle

		// lire inputs du scanner
		while((input = sc.nextLine()) != null){

			// parser mon input, split en selon les espaces, puis traiter texte
			String[] stringSplit = input.split(" ");

			String firstWord = stringSplit[0];

			switch (firstWord){
				case "piece":
					// si manque un mot
					if (stringSplit.length<3){
						System.out.println("il manque un mot");
						break;
					}

					// depuis input, parser id et rencontre type pour instancier une Piece
					int id = Integer.parseInt(stringSplit[1]);
					RencontreType rencontreType = parserEtInstancierRencontreType(stringSplit[2]);

					//une fois instanciee, ajouter au labyrinthe
					if (id != 0) { // Attn : différent type d'objet en fonction de id=0 ou pas...
						Piece newPiece = new Piece(id, rencontreType);
						monLabyrinthe.getPieces()[id] = newPiece;
					} else {
						Exterieur exterieur = Exterieur.getExterieur();
						monLabyrinthe.getPieces()[0] = exterieur;
					}

					break;

				case "corridor":

					// parser les deux id et creer corridor (lien) entre les deux
					int id1 = Integer.parseInt(stringSplit[1]); // je ne gere pas les mauvais input ici (non-int)
					int id2 = Integer.parseInt(stringSplit[2]);

					// rechercher la Piece (objet) qui correspond a l'ID. (id correspond a l'index)
					Piece pieceQuiCorrespondAId1 = monLabyrinthe.getPieces()[id1];
					Piece pieceQuiCorrespondAId2 = monLabyrinthe.getPieces()[id2];
					// si jamais ca n'existe pas...
					if (pieceQuiCorrespondAId1 == null || pieceQuiCorrespondAId2 == null){
						System.out.println("piece n'existe pas !");
						break;
					}

					// ensuite creer lien entre les deux
					monLabyrinthe.ajouteCorridor(pieceQuiCorrespondAId1, pieceQuiCorrespondAId2);
					break;

				case "CORRIDORS":
					// meme si l'utilisateur a cree une piece 0, ce ne sera pas du bon type (Exterieur)
					// donc on va l'ecraser :
					monLabyrinthe.getPieces()[0] = Exterieur.getExterieur(); // rappel: type rencontre = RIEN
					Exterieur exterieur = (Exterieur) monLabyrinthe.getPieces()[0]; // stocker en memoire

					// et si pas de liens, faut choisir une autre piece et relier
					if (Arrays.equals(monLabyrinthe.getPiecesConnectees(exterieur), new Piece[8])){
						Piece pieceARelier = monLabyrinthe.choisirPiece();
						monLabyrinthe.ajouteEntree(exterieur, pieceARelier);
					}
					break;

				case "FIN":
					System.out.println(genererRapport(monAventure));
					System.out.println(genererScenario(monAventure));
					sortirBoucle = true; //signaler sortir et arreter programme
					break;

				default:
					System.out.println("probleme input");
					break;
			}

			if (sortirBoucle){  // si utilisateur a mis "FIN"
				break;
			}
		}
		sc.close();
	}



	// ============================helper methods ==========================================

	/***
	 * Permet de prendre la partie rencontre type de la commande
	 * (ex. "monstre" dans "piece 2 monstre")
	 * et instancie le bon type RencontreType (eg. RencontreType.MONSTRE)
	 *
	 * @param inputRencontreType representant l'input de l'utilisateur eg. "monstre"
	 * @return RencontreType approprie
	 */
	private static RencontreType parserEtInstancierRencontreType(String inputRencontreType){
		RencontreType rencontreType = RencontreType.RIEN; // par default, rencontre type va etre rien
		switch (inputRencontreType){
			case "monstre":
				rencontreType = RencontreType.MONSTRE;
				break;
			case "tresor":
				rencontreType = RencontreType.TRESOR;
				break;
			case "boss":
				rencontreType = RencontreType.BOSS;
				break;
			case "rien":
				rencontreType = RencontreType.RIEN;
				break;
			default:
				System.out.println("probleme rencontre type... on va assumer type rien");
				break;
		}
		return rencontreType;
	}

	/***
	 * genere un rapport comme suit (par exemple) :
	 * Rapport:
	 * Donjon avec 9 pieces :
	 * <0-RIEN> : [<1-MONSTRE>, <5-BOSS>, <6-TRESOR>]
	 * <1-MONSTRE> : [<0-RIEN>, <2-RIEN>]
	 * <2-RIEN> : [<1-MONSTRE>, <3-TRESOR>]
	 * <3-TRESOR> : [<2-RIEN>, <4-MONSTRE>]
	 * <4-MONSTRE> : [<3-TRESOR>, <5-BOSS>, <8-RIEN>]
	 * @param a
	 * @return String a afficher
	 */
	public static String genererRapport(Aventure a) {

		// Construire string sortie
		StringBuilder stringToPrint = new StringBuilder();

		// chercher mes infos
		Labyrinthe monLabyrinthe = a.getLabyrinthe();
		int nbPieces = monLabyrinthe.nombreDePieces();

		// remplir le String
		stringToPrint.append("Rapport:\nDonjon avec " + nbPieces + " pieces :\n");
		for (Piece piece: monLabyrinthe.getPieces()) {
			if (piece != null){
				stringToPrint.append(piece + " : " +  piecesConnecteesSansNulls(monLabyrinthe.getPiecesConnectees(piece)) + "\n");
			}
		}

		// construire un autre string en fonction des caracteristiques du donjon
		// 1. pacifique ou non
		String msgPacifique = a.estPacifique() ? "Pacifique." : "Non pacifique.";

		// 2. contient boss ou non
		String msgBoss = a.contientBoss() ? "Contient un boss." : "Pas de boss.";

		// 3. contient n tresors (ou aucun)
		String msgTresors = "";
		if (a.contientDuTresor()){
			msgTresors = "Contient " + a.getTresorTotal() + " tresors.";
		} else {
			msgTresors = "Pas de tresor.";
		}

		// tout concatener ensemble et retourner
		stringToPrint.append(msgPacifique + "\n" + msgBoss + "\n" + msgTresors);
		return stringToPrint.toString();
	}

	/***
	 * Permet de ne pas afficher les valeurs "null", dans un print
	 * par exemple: [<0-RIEN>, <2-RIEN>, null, null, null, null ...]
	 * on veut plutot [<0-RIEN>, <2-RIEN>]
	 * (sans modifier le tableau en lui meme)
	 *
	 * @param piecesConnectees, un tableau avec une taille fixe, connue de 8
	 * @return String pour print
	 */
	public static String piecesConnecteesSansNulls(Piece[] piecesConnectees){
		if (Arrays.equals(piecesConnectees, new Piece[8])){
			return "[]";
		}
		StringBuilder toPrint = new StringBuilder();
		toPrint.append("[");
		// premier element
		toPrint.append(piecesConnectees[0].toString());

		int indexFirstNull = 0;
		for (int i = 1; i < piecesConnectees.length; i++) { // à partir du 2e élém
			if (piecesConnectees[i]==null){
				indexFirstNull = i;
				break;
			}
			toPrint.append(", " + piecesConnectees[i].toString());
		}

		// apres dernier element non null, fermer
		toPrint.append("]");

		return toPrint.toString();
	}

	/***
	 * generer le output du programme avec un chemin jusqu'au boss, comme suit:
	 *
	 * Chemin jusqu’au boss :
	 * <0-RIEN>
	 * <1-MONSTRE>
	 * <2-RIEN>
	 * <3-TRESOR>
	 * <4-MONSTRE>
	 * <5-BOSS>
	 *
	 * @param a representant l'objet Aventure
	 * @return string comme ci haut
	 */
	public static String genererScenario(Aventure a) {

		StringBuilder toPrint = new StringBuilder();
		toPrint.append("Scenario:\n");

		StringBuilder toPrintMessagesObjet = new StringBuilder();

		// chemin
		toPrint.append("chemin jusqu'au boss : \n");
		Piece[] chemin = a.cheminJusquAuBoss();

		// si aucun chemin au boss
		if(Arrays.equals(chemin,new Piece[50])){
			toPrint.append("-aucun chemin-");
		} else { // si chemin existe
			for (Piece piece : chemin) {
				if (piece != null){
					toPrint.append(piece + "\n");

					String msgMonstreOuTresor;
					switch (piece.getRencontreType()){
						case BOSS:
							Boss boss = new Boss();
							msgMonstreOuTresor = boss.rencontrer();
							break;
						case TRESOR:
							msgMonstreOuTresor = genererMsgTresor();
							break;
						case RIEN:
							Rien rien = new Rien();
							msgMonstreOuTresor = rien.rencontrer();
							break;
						default: // monstre
							msgMonstreOuTresor = genererMsgMonstre();
							break;
					}
					toPrintMessagesObjet.append(msgMonstreOuTresor + "\n");
				}

			}
		}

		return toPrint.toString() + toPrintMessagesObjet;
	}

	/***
	 * Instancie un monstre en choisissant le
	 * type de monstre au hasard (gobelin, etc...) et
	 * renvoie le msg specifique a ce monstre
	 * A utiliser si on sait que c'est un monstre.
	 *
	 * Pour les monstres : “Un [type de monstre] affreux!”
	 *
	 * @return String msg specifique a ce monstre
	 */
	private static String genererMsgMonstre(){
		Monstre monstre = new Gobelin(); // instancier qqc par defaut
		int r = new Random().nextInt(3);
		switch (r) {
			case 0:
				monstre = new Gobelin();//… choix 1
				break;
			case 1:
				monstre = new Orque();//… choix 2
				break;
			case 2:
				monstre = new Gargouille();//… choix 3
		}
		return monstre.rencontrer();
	}

	/***
	 * Instancie un tresor en choisissant le type de
	 * monstre au hasard (potion, etc...) et
	 * renvoie le msg specifique a ce tresor
	 * A utiliser si on sait que c'est un Tresor.
	 *
	 * Pour les trésors : “[type de trésor]! Quelle chance!”
	 * @return String msg specifique au type de tresor
	 */
	private static String genererMsgTresor(){
		Tresor tresor = new Potion(); // instancier qqc par default
		int r = new Random().nextInt(3);
		switch (r) {
			case 0:
				tresor = new Potion();//… choix 1
				break;
			case 1:
				tresor = new SacDeButin();//… choix 2
				break;
			case 2:
				tresor = new ArtefactMagique();//… choix 3
		}
		return tresor.rencontrer();

	}


}
