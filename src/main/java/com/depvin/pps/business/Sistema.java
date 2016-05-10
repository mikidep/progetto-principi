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

    public static synchronized Sistema getInstance() {
        if (ourInstance == null)
            ourInstance = new Sistema();
        return ourInstance;
    }

    void aggiungiProgetto(String nome, Sede sede, float budget, CapoProgetto capoProgetto) {
        Progetto progetto = new Progetto(nome, budget, sede);
        capoProgetto.getProgetti().add(progetto);
    }

    void rimuoviArticoloOrdine(Ordine ordine, ArticoloOrdine articoloOrdine) {
        ordine.getArticoliOrdine().remove(articoloOrdine);
    }

    void rimuoviOrdine(Ordine ordine) {
        Dipendente d = ordine.getDipendente();
        d.getOrdini().remove(ordine);
    }

    void confermaOrdine(Ordine ordine) {
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

    void modificaBudget(Progetto progetto, float budget) {
        float variab = progetto.getBudget() + budget;
        if (variab >= 0)
            progetto.setBudget(variab);
    }

    ArrayList<ArticoloOrdine> stampaOrdine(Ordine ordine) {
        ArrayList<ArticoloOrdine> lista = new ArrayList<ArticoloOrdine>();
        for (ArticoloOrdine ao : ordine.getArticoliOrdine())
            lista.add(ao);
        return lista;
    }

    void richiediNotifica(ArticoloOrdine articoloOrdine) {
        articoloOrdine.setRichiesto(true);
    }

    void aggiungiDipendente(String name, String surname, String username, String password)
            throws UserExistsException, UserLoadingException {
        try {
            Dipendente dip = UtenteDAO.getNewDipendente(username, hashPassword(password));
            dip.setCognome(surname);
            dip.setNome(name);
        } catch (com.depvin.pps.dao.UserAlreadyExistsException e) {
            throw new UserExistsException(e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace(System.err);
            throw new UserLoadingException(e.getMessage(), e);
        }
    }

    void aggiungiMagazziniere(String name, String surname, Magazzino magazzino, String username, String password)
            throws UserExistsException, UserLoadingException {
        try {
            Magazziniere mag = UtenteDAO.getNewMagazziniere(username, hashPassword(password), magazzino);
        mag.setNome(name);
        mag.setCognome(surname);
        } catch (com.depvin.pps.dao.UserAlreadyExistsException e) {
            throw new UserExistsException(e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace(System.err);
            throw new UserLoadingException(e.getMessage(), e);
        }
    }

    void aggiungiCapoProgetto(String name, String surname, String username, String Password)
            throws UserExistsException, UserLoadingException {
        try {
            CapoProgetto cap = UtenteDAO.getNewCapoProgetto(username, hashPassword(Password));
        cap.setNome(name);
        cap.setCognome(surname);
        } catch (com.depvin.pps.dao.UserAlreadyExistsException e) {
            throw new UserExistsException(e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace(System.err);
            throw new UserLoadingException(e.getMessage(), e);
        }
    }

    void aggiungiAmministratore(String name, String surname, String username, String password)
            throws UserExistsException, UserLoadingException {
        try {
            Amministratore amm = UtenteDAO.getNewAmministratore(username, hashPassword(password));
        amm.setNome(name);
        amm.setCognome(surname);
        } catch (com.depvin.pps.dao.UserAlreadyExistsException e) {
            throw new UserExistsException(e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace(System.err);
            throw new UserLoadingException(e.getMessage(), e);
        }
    }

    public Sessione login(String username, String password) throws UserNotFoundException, UserLoadingException {
        try {
            Utente utente = UtenteDAO.getUtenteWithUsernameAndHash(username, hashPassword(password));
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

    private byte[] hashPassword(String password) throws NoSuchAlgorithmException {
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