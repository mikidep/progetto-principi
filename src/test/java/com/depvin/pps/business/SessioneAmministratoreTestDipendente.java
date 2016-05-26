package com.depvin.pps.business;

import com.depvin.pps.dbinterface.DBInterface;
import com.depvin.pps.model.Dipendente;
import com.depvin.pps.model.Utente;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by costantino on 26/05/16.
 */
public class SessioneAmministratoreTestDipendente {
    @Test
    public void aggiungiDipendente() throws Exception {
        String name = "Vincenzo";
        String surname = "Guarini";
        String username = "Berzerk";
        String password = "Hue";
        Sistema.getInstance().aggiungiDipendente(name, surname, username, password);
    }

    @Test
    public void removeDipendente() throws Exception {
        Dipendente dipendente = (Dipendente) DBInterface.getInstance().getEntityManager()
                .createQuery("SELECT u FROM Utente AS u WHERE u.username = :username AND u.passwordHash = :hash", Utente.class)
                .setParameter("username", "1").setParameter("hash", "".getBytes());
        System.out.println("Dipendente " + dipendente.getUsername());
    }//Non funziona il cast
}