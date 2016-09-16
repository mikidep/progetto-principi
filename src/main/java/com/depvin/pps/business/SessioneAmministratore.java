package com.depvin.pps.business;

import com.depvin.pps.model.*;

import java.util.List;

public class SessioneAmministratore implements Sessione {

    private Amministratore amministratore;

    public SessioneAmministratore(Amministratore amministratore) {
        this.amministratore = amministratore;
    }

    public Amministratore getUtente() {
        return amministratore;
    }

    public void aggiungiDipendente(String name, String surname, String username, String password)//, CapoProgetto capoProgetto, String nomeProgetto)
            throws UserExistsException, UserLoadingException {
        Sistema.getInstance().aggiungiDipendente(name, surname, username, password);
    }

    public void aggiungiMagazziniere(String name, String surname, String username, String password, Sede sede, String magazzinoNome)
            throws UserExistsException, UserLoadingException {
        Magazzino magazzino = new Magazzino(magazzinoNome, sede);
        Sistema.getInstance().aggiungiMagazziniere(name, surname, magazzino, username, password);
    }

    public void aggiungiCapoProgetto(String name, String surname, String username, String password)
            throws UserExistsException, UserLoadingException {
        Sistema.getInstance().aggiungiCapoProgetto(name, surname, username, password);
    }

    public void aggiungiAmministratore(String name, String surname, String username, String password)
            throws UserExistsException, UserLoadingException {
        Sistema.getInstance().aggiungiAmministratore(name, surname, username, password);
    }

    public void aggiungiProgetto(String nome, Sede sede, float budget, CapoProgetto capoProgetto) {
        Sistema.getInstance().aggiungiProgetto(nome, sede, budget, capoProgetto);
    }

    public Utente ottieniUtente(String username, String password) throws UserNotFoundException, UserLoadingException {
        return Sistema.getInstance().ottieniUtente(username, password);
    }

    public List<CapoProgetto> ottieniListaCapoProgetto() {
        return Sistema.getInstance().ottieniListaCapoProgetto();
    }

    public List<Sede> ottieniListaSede() {
        return Sistema.getInstance().ottieniListaSede();
    }
}