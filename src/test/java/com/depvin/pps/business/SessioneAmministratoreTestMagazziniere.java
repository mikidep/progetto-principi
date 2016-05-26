package com.depvin.pps.business;

import com.depvin.pps.dbinterface.DBInterface;
import com.depvin.pps.model.*;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by costantino on 26/05/16.
 */
public class SessioneAmministratoreTestMagazziniere {
    @Test
    public void aggiungiMagazziniere() throws Exception {
        String name = "Renato";
        String surname = "Rinino";
        String username = "Renè";
        String password = "Lupen";
        Nazione nazione = new Nazione("Italiana", 5.60f);
        Sede sede = new Sede("Savona", nazione);
        Magazzino magazzino = new Magazzino(sede);
        Sistema.getInstance().aggiungiMagazziniere(name, surname, magazzino, username, password);
        //problema nella persistenza del magazzino
        //During synchronization a new object was found through a relationship that
        // was not marked cascade PERSIST: com.depvin.pps.model.Magazzino@234c602d.
    }

    @Test
    public void removeAmministratore() throws Exception {
        Magazziniere magazziniere = (Magazziniere) DBInterface.getInstance().getEntityManager()
                .createQuery("SELECT u FROM Utente AS u WHERE u.username = :username AND u.passwordHash = :hash", Utente.class)
                .setParameter("username", "1").setParameter("hash", "".getBytes())
                .getSingleResult();
        System.out.println("Magazziniere " + magazziniere.getUsername());
    }//Non funziona il cast


}