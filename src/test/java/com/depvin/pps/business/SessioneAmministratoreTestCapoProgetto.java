package com.depvin.pps.business;

import com.depvin.pps.dbinterface.DBInterface;
import com.depvin.pps.model.*;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by costantino on 26/05/16.
 */
public class SessioneAmministratoreTestCapoProgetto {

    @Test
    public void aggiungiCapoProgetto() throws Exception {
        String name = "Francesco";
        String surname = "Schettino";
        String username = "Costa";
        String password = "Concordia";
        Sistema.getInstance().aggiungiCapoProgetto(name, surname, username, password);
    }

    @Test
    public void aggiungiProgetto() throws Exception {
        CapoProgetto capoProgetto = (CapoProgetto) DBInterface.getInstance().getEntityManager()
                .createQuery("SELECT u FROM Utente AS u WHERE u.username = :username AND u.passwordHash = :hash", Utente.class)
                .setParameter("username", "1").setParameter("hash", "".getBytes())
                .getSingleResult();
        Nazione nazione = new Nazione("Italia", 5.60f);
        Sede sede = new Sede("Napoli", nazione);
        Progetto pgg = new Progetto("Cancro", 100.0f, sede);
        Sistema.getInstance().aggiungiProgetto("008", sede, 0.02f, capoProgetto);

    }//Risolvere il prome del cast per ottenere il capo progetto

    @Test
    public void removeCapoProgetto() throws Exception {
        CapoProgetto capoProgetto = (CapoProgetto) DBInterface.getInstance().getEntityManager()
                .createQuery("SELECT u FROM Utente AS u WHERE u.username = :username AND u.passwordHash = :hash", Utente.class)
                .setParameter("username", "1").setParameter("hash", "".getBytes())
                .getSingleResult();
        System.out.println("Capo Progetto " + capoProgetto.getUsername());
    }//Non funziona il cast per ottene il capo progetto
}