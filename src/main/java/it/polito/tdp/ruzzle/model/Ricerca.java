package it.polito.tdp.ruzzle.model;

import java.util.ArrayList;
import java.util.List;

public class Ricerca { // classe dove metto i metodi ricorsivi

	// divido la procedura in due parti dove la fondamentale è quella che mi
	// permetta di poter dire se una parola è presente o meno nella mia 
	// matrice, avrò quindi un metodo ricorsivo che data una parola mi dice
	// se è presente o no nella matrice.
	
	public List<Pos> trovaParola (String parola, Board board){
		
		for(Pos p : board.getPositions()) {
			// devo capire se la lettera in pos è == alla prima lettera di parola
			if(board.getCellValueProperty(p).get().charAt(0) == parola.charAt(0)) { // per passare da una stringProperty 
				                                                                  // a una stringa devo fare ancora un .get()
				
			// ora posso far partire la ricrsione
				List<Pos> parziale = new ArrayList<Pos>();
				parziale.add(p);
				if (cerca(parola,parziale,1,board)) {
					return parziale;
				}
			}
		}
		
		return null;
	}
	
	
	public boolean cerca(String parola, 
			List<Pos> percorso,int livello, Board board) {
		
		//caso terminale: mi fermo se il livello è uguale alla lunghezza della parola da riconoscere
		if(livello == parola.length()) {
			return true; // ho trovato la parola
		}
		
		// se non sono giunto alla fine continuo a vedere se posso trovare la parola: prendo l'ultima 
		// lettera inserita, prendo tutte le sue lettere adiacenti (ovvero quelle sulle quali riuscirei
		// ad andare) e vedo se qualcuna corrisponde alla lettera successiva della parola da verificare,
		// se corrisponde l'aggiungo e vado avanti, viceversa mi fermerò.
		
		Pos ultima = percorso.get(percorso.size()-1); //recuperò l'ultima posizione inserita al giro precedente
		                                              //e lo faccio attraverso un accesso posizionale
		List<Pos> adiacenti = board.getAdjacencies(ultima); //presa l'ultima pos inserita prendo le adiacenti, ovvero una lista di Pos
		
		//scorro tutte le lettere di adiacenti e verifico se qualche lettera fa il match con la lettera della parola che sto cercando
		for(Pos a : adiacenti) {
			if(!percorso.contains(a) && board.getCellValueProperty(a).get().charAt(0) == parola.charAt(livello)){
					
					//posso 'continuare' il mio percorso facendo andare avanti la ricorsione
					percorso.add(a);
					
					//uscita rapida dalla ricorsione: se trovo la parola non ha senso continuare e fare un backtracking
					if (cerca(parola, percorso, livello + 1, board)) {
						return true;
					}
					
					percorso.remove(percorso.size() -1); //backtracking 
			}
		}
		
		return false;
	}
}