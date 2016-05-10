package com.depvin.pps.business;

import com.depvin.pps.dao.NoSuchUserException;
import com.depvin.pps.dao.UserAlreadyExistsException;
import com.depvin.pps.dao.UtenteDAO;
import com.depvin.pps.model.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * Created by costantino on 05/12/15.
 */
public class Sistema {

    private static Sistema ourInstance;

    static synchronized Sistema getInstance() {
        if (ourInstance == null)
            ourInstance = new Sistema();
        return ourInstance;
    }

    public ArrayList<ArticoloMagazzino> getListaMagazzino(Magazzino magazzino) {
        ArrayList<ArticoloMagazzino> lista = new ArrayList<ArticoloMagazzino>();
        for (ArticoloMagazzino am : magazzino.getArticoliMagazzino())
            lista.add(am);
        return lista;
    }

    public void aggiungiProgetto(String nome, Sede sede, float budget, CapoProgetto capoProgetto) {
        Progetto progetto = new Progetto(nome, budget, sede);
        capoProgetto.getProgetti().add(progetto);
    }

    public void rimuoviOrdine(Ordine ordine, ArticoloOrdine articoloOrdine) {
        for (ArticoloOrdine ao : ordine.getArticoliOrdine())
            if (ao.equals(articoloOrdine))
                ordine.getArticoliOrdine().remove(ao);
    }//si limita ad eliminare un elemento dall'ordine

    public void confermaOrdine(Ordine ordine) {
        int t = 0;
        for (ArticoloOrdine ao : ordine.getArticoliOrdine()) {
            if (!ao.isDisponibile())
                t++;
        }
        if (t == 0) {
            if (ordine.getTotale() <= ordine.getProgetto().getBudget()) {
                float appoggio = ordine.getProgetto().getBudget() - ordine.getTotale();
                ordine.getProgetto().setBudget(appoggio);
                ordine.setEvaso(true);
            }
        }
    }

    public void modificaBudget(Progetto progetto, float budget) {
        float variab = progetto.getBudget() + budget;
        if (variab >= 0)
            progetto.setBudget(variab);
    }

    public ArrayList<ArticoloOrdine> stampaOrdine(Ordine ordine) {
        ArrayList<ArticoloOrdine> lista = new ArrayList<ArticoloOrdine>();
        for (ArticoloOrdine ao : ordine.getArticoliOrdine())
            lista.add(ao);
        return lista;
    }

    public void richiediNotifica(ArticoloOrdine articoloOrdine) {
        articoloOrdine.setRichiesto(true);
    }

    public void aggiungiDipendente(String name, String surname, String username, String password)
            throws NoSuchUserException, UserAlreadyExistsException, NoSuchAlgorithmException {

        //verifica dell'esistenza di un'altro dipendente

        Dipendente dip = UtenteDAO.getNewDipendente(username, HashPassword(password));
        dip.setCognome(surname);
        dip.setNome(name);
    }

    public void aggiungiMagazziniere(String name, String surname, Magazzino magazzino, String username, String password)
            throws NoSuchUserException, UserAlreadyExistsException, NoSuchAlgorithmException {

        //verifica dell'esistenza di un'altro dipendente

        Magazziniere mag = UtenteDAO.getNewMagazziniere(username, HashPassword(password), magazzino);
        mag.setNome(name);
        mag.setCognome(surname);
    }

    public void aggiungiCapoProgetto(String name, String surname, String username, String Password)
            throws NoSuchUserException, UserAlreadyExistsException, NoSuchAlgorithmException {
        //verifica dell'esistenza di un'altro dipendente

        CapoProgetto cap = UtenteDAO.getNewCapoProgetto(username, HashPassword(Password));
        cap.setNome(name);
        cap.setCognome(surname);
    }

    public void aggiungiAmministratore(String name, String surname, String username, String password)
            throws NoSuchUserException, UserAlreadyExistsException, NoSuchAlgorithmException {

        //verifica dell'esistenza di un'altro dipendente

        Amministratore amm = UtenteDAO.getNewAmministratore(username, HashPassword(password));
        amm.setNome(name);
        amm.setCognome(surname);
    }

    public Sessione login(String username, String password) throws UserNotFoundException, UserLoadingException {
        try {
            Utente utente = UtenteDAO.getUtenteWithUsernameAndHash(username, HashPassword(password));
            if (utente instanceof Dipendente) {
                ((Dipendente) utente).addListener(new Dipendente.NotificaArticoloListener() {
                    public void articoloOrdineIsDisponibile(ArticoloOrdine articoloOrdine, Magazzino magazzino) {

                        // TODO: Chiamerà il Presenter
                    }
                });
                return new SessioneDipendente((Dipendente) utente);
            } else if (utente instanceof Amministratore) {
                return new SessioneAmministratore((Amministratore) utente);
            } else if (utente instanceof Magazziniere) {
                return new SessioneMagazziniere((Magazziniere) utente);
            } else {
                return new SessioneCapoProgetto((CapoProgetto) utente);
            }

        } catch (com.depvin.pps.dao.NoSuchUserException e) {
            throw new UserNotFoundException(e.getMessage(), e);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace(System.err);
            throw new UserLoadingException(e.getMessage(), e);
        }
    }

    private byte[] HashPassword(String password) throws NoSuchAlgorithmException {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte byteData[] = md.digest();
            return byteData;
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException("No Provider supports a MessageDigestSpi implementation for the specified algorithm", e);
        }
    }

}