package Test_FFSSM;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import FFSSM.Club;
import FFSSM.Embauche;
import FFSSM.Licence;
import FFSSM.Moniteur;
import FFSSM.Plongee;
import FFSSM.Plongeur;
import FFSSM.Site;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;

public class FFSSM_JUnit_Test {

    //LES ATTRIBUTS
    Moniteur paul;
    Plongeur arnaud;
    Plongeur leo;
    Club clubGlouglou;

    // BEFORE EACH POUR LES INSTANCES DE TEST
    @BeforeEach
    public void setUp() {

        paul = new Moniteur("PO4206671", "ORTEGA", "paul", "16 Place Soult 81100 Castres", "0781096789", LocalDate.of(2000, 11, 20), 31320);
        arnaud = new Plongeur("AB4206672", "BONNAFOUS", "arnaud", "15 Place Soult 81100 Castres", "0700000001", LocalDate.of(2000, 11, 12));
        leo = new Plongeur("ML85202283", "LEROY", "leo", "15 Place Soult 81100 Castres", "0700000002", LocalDate.of(2000, 10, 28));
        clubGlouglou = new Club(paul, "Glouglou", "0485236812");

    }

    @Test
    public void testAjouteLicence() throws Exception {

        Licence licence1 = new Licence(paul, "", LocalDate.of(2021, 12, 2), clubGlouglou);
        Licence licence2 = new Licence(paul, "TY845", LocalDate.of(2021, 6, 4), clubGlouglou);
        Licence licence3 = new Licence(arnaud, "89IBU", LocalDate.of(2020, 11, 3), clubGlouglou);

        assertThrows(Exception.class, () -> {
            paul.derniereLicence();
        });

        paul.ajouteLicence("", LocalDate.of(2021, 12, 2), clubGlouglou);

        assertEquals(paul.derniereLicence(), licence1);

        paul.ajouteLicence("TY845", LocalDate.of(2021, 6, 4), clubGlouglou);

        assertEquals(paul.derniereLicence(), licence2);
    }

    @Test
    public void testNouvelEmbauche() {
        Embauche emb1 = new Embauche(LocalDate.of(2020, 6, 4), paul, clubGlouglou);
        Embauche emb2 = new Embauche(LocalDate.of(2020, 3, 3), paul, clubGlouglou);

        ArrayList<Embauche> listEmbauches = new ArrayList<>();
        listEmbauches.add(emb1);
        listEmbauches.add(emb2);

        paul.nouvelleEmbauche(clubGlouglou, LocalDate.of(2020, 6, 4));
        paul.nouvelleEmbauche(clubGlouglou, LocalDate.of(2020, 3, 3));

        assertEquals(listEmbauches, paul.emplois());
    }

    @Test
    public void testTerminerEmbauche() throws Exception {

        assertThrows(Exception.class, () -> {
            paul.terminerEmbauche(LocalDate.now());
        });

        paul.nouvelleEmbauche(clubGlouglou, LocalDate.of(2020, 6, 4));

        paul.terminerEmbauche(LocalDate.now());

        assertTrue(paul.emplois().get(paul.emplois().size() - 1).estTerminee());
    }

    @Test
    public void testEmployeurActuel() throws Exception {

        //TODO regler ça
        assertEquals(paul.employeurActuel(), Optional.empty());

        paul.nouvelleEmbauche(clubGlouglou, LocalDate.of(2020, 6, 4));

        assertEquals(paul.employeurActuel(), Optional.of(clubGlouglou));

        Club club2 = new Club(paul, "GlouGlou", "0346798763");

        paul.nouvelleEmbauche(clubGlouglou, LocalDate.of(2020, 6, 4));
        paul.terminerEmbauche(LocalDate.now());
        paul.nouvelleEmbauche(club2, LocalDate.now());

        assertEquals(paul.employeurActuel(), Optional.of(club2));

        paul.terminerEmbauche(LocalDate.now());

        assertEquals(paul.employeurActuel(), Optional.empty());

    }

    @Test
    public void testEstTerminee() throws Exception {

        paul.nouvelleEmbauche(clubGlouglou, LocalDate.of(2002, 3, 7));
        assertFalse(paul.emplois().get(paul.emplois().size() - 1).estTerminee());

        paul.terminerEmbauche(LocalDate.of(2021, 3, 7));
        assertTrue(paul.emplois().get(paul.emplois().size() - 1).estTerminee());

        Club club2 = new Club(paul, "GlouGlou", "0346798763");
        paul.nouvelleEmbauche(club2, LocalDate.of(2002, 3, 7));
        paul.emplois().get(paul.emplois().size() - 1).terminer(LocalDate.of(2021, 9, 2));

        assertTrue(paul.emplois().get(paul.emplois().size() - 1).estTerminee());
        assertEquals(paul.emplois().get(paul.emplois().size() - 1).getFin(), LocalDate.of(2021, 9, 2));

    }

    @Test
    public void testPlongeesNonConformes() throws Exception {

        Plongee plongee1 = new Plongee(new Site("Danlo", "Cmouillé"), paul, LocalDate.of(2021, 12, 7), 20, 2);
        Plongee plongee2 = new Plongee(new Site("Soulo", "YaDPoisson"), paul, LocalDate.of(2021, 12, 7), 20, 2);
        Plongee plongee3 = new Plongee(new Site("Ocean Aquatique", "CaTrempe"), paul, LocalDate.of(2021, 12, 7), 20, 2);

        paul.ajouteLicence("", LocalDate.of(2021, 12, 2), clubGlouglou);
        leo.ajouteLicence("TY845", LocalDate.of(2021, 6, 4), clubGlouglou);
        arnaud.ajouteLicence("89IBU", LocalDate.of(2020, 11, 3), clubGlouglou); // Cette licence est expirée

        plongee1.ajouteParticipant(paul);
        plongee1.ajouteParticipant(leo);

        plongee2.ajouteParticipant(leo);
        plongee2.ajouteParticipant(arnaud);
        // Cette plongée n'est pas conforme

        plongee3.ajouteParticipant(arnaud);
        // Cette plongée n'est pas conforme
        plongee3.ajouteParticipant(paul);

        HashSet<Plongee> plongeesNonConformes = new HashSet<>();

        clubGlouglou.organisePlongee(plongee1);

        assertEquals(clubGlouglou.plongeesNonConformes(), plongeesNonConformes);

        plongeesNonConformes.add(plongee2);
        clubGlouglou.organisePlongee(plongee2);

        assertEquals(clubGlouglou.plongeesNonConformes(), plongeesNonConformes);

        plongeesNonConformes.add(plongee3);
        clubGlouglou.organisePlongee(plongee3);

        assertEquals(clubGlouglou.plongeesNonConformes(), plongeesNonConformes);
    }

    @Test
    public void testEstConforme() throws Exception {
        Plongee plongee1 = new Plongee(new Site("Danlo", "Cmouillé"), paul, LocalDate.of(2021, 12, 7), 20, 2);

        paul.ajouteLicence("49TTY", LocalDate.of(2021, 12, 2), clubGlouglou);
        leo.ajouteLicence("TY845", LocalDate.of(2021, 6, 4), clubGlouglou);
        arnaud.ajouteLicence("89IBU", LocalDate.of(2020, 11, 3), clubGlouglou);

        plongee1.ajouteParticipant(paul);
        plongee1.ajouteParticipant(leo);

        ArrayList<Licence> listLicences = new ArrayList<>();

        listLicences.add(paul.derniereLicence());
        listLicences.add(leo.derniereLicence());

        assertTrue(plongee1.estConforme());

        plongee1.ajouteParticipant(arnaud);
        listLicences.add(arnaud.derniereLicence());

        assertFalse(plongee1.estConforme());
        assertEquals(plongee1.getPalanquees(), listLicences);

    }

    @Test
    public void testEstValide() throws Exception {
        Licence l1 = new Licence(arnaud, "TY874AB", LocalDate.of(2010, 1, 2), clubGlouglou);
        assertTrue(l1.estValide(LocalDate.of(2010, 2, 4)));
        assertFalse(l1.estValide(LocalDate.of(2015, 1, 1)));
        assertTrue(l1.estValide(LocalDate.of(2010, 1, 1)));
    }

}
