package com.depvin.pps.business;

import com.depvin.pps.dbinterface.DBInterface;
import com.depvin.pps.model.*;
import org.junit.Test;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

/**
 * Created by costantino on 24/05/16.
 */
public class SessioneAmministratoreTest {

    @Test
    public void AggiungiAmministratore() throws Exception {
        String name = "Gesù";
        String surname = "Cristo";
        String username = "Jesusa";
        String password = "Pezzente";
        Sistema.getInstance().aggiungiAmministratore(name, surname, username, password);
    }

    @Test
    public void aggiungiCapoProgetto() throws Exception {
        String name = "Francesco";
        String surname = "Schettino";
        String username = "Costa";
        String password = "Concordia";
        Sistema.getInstance().aggiungiCapoProgetto(name, surname, username, password);
    }

    @Test
    public void aggiungiDipendente() throws Exception {
        String name = "Vincenzo";
        String surname = "Guarini";
        String username = "Berzerk";
        String password = "Hue";
        Sistema.getInstance().aggiungiDipendente(name, surname, username, password);
    }

    @Test
    public void aggiungiMagazziniere() throws Exception {
        String name = "Renato";
        String surname = "Rinino";
        String username = "Renè";
        String password = "Lupen";
        Nazione nazione = new Nazione("Italiana", 5.60f);
        Sede sede = new Sede("Savona", nazione);
        Magazzino magazzino = new Magazzino("ParDeCazzi", sede);
        Sistema.getInstance().aggiungiMagazziniere(name, surname, magazzino, username, password);
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
    public void removeAmministratore() throws Exception {
        Amministratore amministratore = (Amministratore) DBInterface.getInstance().getEntityManager()
                .createQuery("SELECT u FROM Utente AS u WHERE u.username = :username AND u.passwordHash = :hash", Utente.class)
                .setParameter("username", "1").setParameter("hash", "\203\263mi\341]y\367\241\031\034\376\337\322pv\304\220\035\246O\035r\222\341:\245r\034\315\246".getBytes())
                .getSingleResult();
        System.out.println("Amministratore " + amministratore.getUsername());
    }//Non funziona il cast

    @Test
    public void removeCapoProgetto() throws Exception {
        CapoProgetto capoProgetto = (CapoProgetto) DBInterface.getInstance().getEntityManager()
                .createQuery("SELECT u FROM Utente AS u WHERE u.username = :username AND u.passwordHash = :hash", Utente.class)
                .setParameter("username", "1").setParameter("hash", "".getBytes())
                .getSingleResult();
        System.out.println("Capo Progetto " + capoProgetto.getUsername());
    }//Non funziona il cast per ottenere il capo progetto

    @Test
    public void removeDipendente() throws Exception {
        Dipendente dipendente = (Dipendente) DBInterface.getInstance().getEntityManager()
                .createQuery("SELECT u FROM Utente AS u WHERE u.username = :username AND u.passwordHash = :hash", Utente.class)
                .setParameter("username", "1").setParameter("hash", "".getBytes());
        System.out.println("Dipendente " + dipendente.getUsername());
    }//Non funziona il cast

    @Test
    public void removeMagazziniere() throws Exception {
        Magazziniere magazziniere = (Magazziniere) DBInterface.getInstance().getEntityManager()
                .createQuery("SELECT u FROM Utente AS u WHERE u.username = :username AND u.passwordHash = :hash", Utente.class)
                .setParameter("username", "1").setParameter("hash", "".getBytes())
                .getSingleResult();
        System.out.println("Magazziniere " + magazziniere.getUsername());
    }//Non funziona il cast

}