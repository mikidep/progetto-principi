package com.depvin.pps.business;

import com.depvin.pps.dao.NoSuchUserException;
import com.depvin.pps.dao.UserAlreadyExistsException;
import com.depvin.pps.model.Amministratore;
import com.depvin.pps.model.CapoProgetto;
import com.depvin.pps.model.Magazzino;
import com.depvin.pps.model.Sede;

import java.security.NoSuchAlgorithmException;

/**
 * Created by costantino on 05/12/15.
 */
public class SessioneAmministratore implements Sessione {

    private Amministratore amministratore;

    public SessioneAmministratore(Amministratore amministratore) {
        this.amministratore = amministratore;
    }

    public Amministratore getUtente() {
        return amministratore;
    }

    public void aggiungiDipendente(String name, String surname, String username, String password, CapoProgetto capoProgetto, String progetto)
            throws NoSuchUserException, UserAlreadyExistsException, NoSuchAlgorithmException {
        Sistema.getInstance().aggiungiDipendente(name, surname, username, password);

    }

    public void aggiungiMagazziniere(String name, String surname, String username, String password, Sede sede, String magazzinoNome)
            throws NoSuchUserException, UserAlreadyExistsException, NoSuchAlgorithmException {
        Magazzino magazzino = new Magazzino(sede);
        magazzino.setNome(magazzinoNome);
        Sistema.getInstance().aggiungiMagazziniere(name, surname, magazzino, username, password);
    }

    public void aggiungiCapoProgetto(String name, String surname, String username, String password)
            throws NoSuchUserException, UserAlreadyExistsException, NoSuchAlgorithmException {
        Sistema.getInstance().aggiungiCapoProgetto(name, surname, username, password);
    }

    public void aggiungiAmministratore(String name, String surname, String username, String password)
            throws NoSuchUserException, UserAlreadyExistsException, NoSuchAlgorithmException {
        Sistema.getInstance().aggiungiAmministratore(name, surname, username, password);
    }

    public void aggiungiProgetto(String nome, Sede sede, float budget, CapoProgetto capoProgetto) {
        Sistema.getInstance().aggiungiProgetto(nome, sede, budget, capoProgetto);
    }
}