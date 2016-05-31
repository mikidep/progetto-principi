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
                .createQuery("SELECT u FROM Utente AS u WHERE u.username = :username", Utente.class)
                .setParameter("username", "Costa")
                .getSingleResult();
        Nazione nazione = new Nazione("Italia", 5.60f);
        Sede sede = new Sede("Napoli", nazione);
        Progetto pgg = new Progetto("Cancro", 100.0f, sede);
        Sistema.getInstance().aggiungiProgetto("008", sede, 0.10f, capoProgetto);
        //DBInterface.getInstance().save();

    }//Risolvere il prome del cast per ottenere il capo progetto



}