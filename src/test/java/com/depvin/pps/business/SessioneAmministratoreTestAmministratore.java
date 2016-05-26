package com.depvin.pps.business;

import com.depvin.pps.dbinterface.DBInterface;
import com.depvin.pps.model.*;
import org.junit.Test;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

/**
 * Created by costantino on 24/05/16.
 */
public class SessioneAmministratoreTestAmministratore {
    @Test
    public void AggiungiAmministratore() throws Exception {
        String name = "Gesù";
        String surname = "Cristo";
        String username = "Jesusa";
        String password = "Pezzente";
        Sistema.getInstance().aggiungiAmministratore(name, surname, username, password);
    }

    @Test
    public void removeAmministratore() throws Exception {
        Amministratore amministratore = (Amministratore) DBInterface.getInstance().getEntityManager()
                .createQuery("SELECT u FROM Utente AS u WHERE u.username = :username AND u.passwordHash = :hash", Utente.class)
                .setParameter("username", "1").setParameter("hash", "\203\263mi\341]y\367\241\031\034\376\337\322pv\304\220\035\246O\035r\222\341:\245r\034\315\246".getBytes())
                .getSingleResult();
        System.out.println("Amministratore " + amministratore.getUsername());
    }//Non funziona il cast

}