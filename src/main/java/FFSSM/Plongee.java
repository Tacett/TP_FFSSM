/**
 * @(#) Plongee.java
 */
package FFSSM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Plongee {

	public Site lieu;

	public Moniteur chef;

	public LocalDate date;

	public int profondeur;

	public int duree;

	ArrayList<Licence> palanquees;


	public Plongee(Site lieu, Moniteur chef, LocalDate date, int profondeur, int duree) {
		this.lieu = lieu;
		this.chef = chef;
		this.date = date;
		this.profondeur = profondeur;
		this.duree = duree;
		this.palanquees = new ArrayList<>();
	}

	public Plongee(Moniteur chef){
		this.chef = chef;
		this.palanquees = new ArrayList<>();
	}


	public void ajouteParticipant(Plongeur participant) throws Exception {
		palanquees.add(participant.derniereLicence());
	}

	public boolean estConforme() throws Exception {
		for (Licence l : palanquees){
			if (!l.estValide(this.date)){
				return false;
			}
		}
		return true;
	}

	public LocalDate getDate() {
		return date;
	}

	public ArrayList<Licence> getPalanquees() {
		return palanquees;
	}

}