/**
 * @(#) LicencePlongeur.java
 */
package FFSSM;

import java.time.LocalDate;
import java.util.Objects;

public class Licence {

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Licence licence = (Licence) o;
        return possesseur.equals(licence.possesseur)
                && numero.equals(licence.numero)
                && delivrance.equals(licence.delivrance)
                && club.equals(licence.club);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.possesseur);
        hash = 67 * hash + Objects.hashCode(this.numero);
        hash = 67 * hash + Objects.hashCode(this.delivrance);
        hash = 67 * hash + Objects.hashCode(this.club);
        return hash;
    }

    public Personne possesseur;

    public String numero;

    public LocalDate delivrance;

    public Club club;

    public Licence(Personne possesseur, String numero, LocalDate delivrance, Club club) {
        this.possesseur = possesseur;
        this.numero = numero;
        this.delivrance = delivrance;
        this.club = club;
    }

    public Personne getPossesseur() {
        return possesseur;
    }

    public String getNumero() {
        return numero;
    }

    public LocalDate getDelivrance() {
        return delivrance;
    }

    public Club getClub() {
        return club;
    }

    public boolean estValide(LocalDate d) throws Exception {
        if (d.isAfter(delivrance) || d.isEqual(delivrance)) {
            return !d.isAfter(delivrance.plusYears(1));
        } else {
            throw new Exception("La date entrée en parametre est avant la date de délivrance");
        }
    }

}
