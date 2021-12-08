/**
 * @(#) Site.java
 */

package FFSSM;

import java.util.ArrayList;

public class Site
{
	public String nom;
	private String precisions;
	private ArrayList<Plongee> plongees;

	public Site(String nom, String precisions) {
		this.nom = nom;
		this.precisions = precisions;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Get the value of precisions
	 *
	 * @return the value of precisions
	 */
	public String getprecisions() {
		return precisions;
	}

	/**
	 * Set the value of precisions
	 *
	 * @param precisions new value of precisions
	 */
	public void setprecisions(String precisions) {
		this.precisions = precisions;
	}

	@Override
	public String toString() {
		return "Site{" + "nom=" + nom + ", precisions=" + precisions + '}';
	}

	
}