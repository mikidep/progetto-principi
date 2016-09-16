package com.depvin.pps.business;

import com.depvin.pps.model.Magazzino;
import com.depvin.pps.model.Nazione;
import com.depvin.pps.model.Sede;
import org.junit.Test;

public class SessioneAmministratoreTest {

    @Test
    public void AggiungiAmministratore() throws Exception {
        String name = "Mario";
        String surname = "Rossi";
        String username = "admin";
        String password = "admin";
        Sistema.getInstance().aggiungiAmministratore(name, surname, username, password);
    }

    @Test
    public void aggiungiCapoProgetto() throws Exception {
        String name = "Mario";
        String surname = "Bianchi";
        String username = "cp";
        String password = "cp";
        Sistema.getInstance().aggiungiCapoProgetto(name, surname, username, password);
    }

    @Test
    public void aggiungiDipendente() throws Exception {
        String name = "Alberto";
        String surname = "Monti";
        String username = "dip";
        String password = "dip";
        Sistema.getInstance().aggiungiDipendente(name, surname, username, password);
    }

    @Test
    public void aggiungiMagazziniere() throws Exception {
        String name = "Gennaro";
        String surname = "D'Angelo";
        String username = "mag";
        String password = "mag";
        Nazione nazione = new Nazione("Italiana", 5.60f);
        Sede sede = new Sede("Roma", nazione);
        Magazzino magazzino = new Magazzino("Magazzino1", sede);
        Sistema.getInstance().aggiungiMagazziniere(name, surname, magazzino, username, password);
    }

}