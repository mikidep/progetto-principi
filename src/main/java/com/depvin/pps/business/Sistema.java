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
        if (t == 0)
            ordine.setEvaso(true);
    }

    public float modificaBudget(Progetto progetto, float budget) {
        float variab = progetto.getBudget() + budget;
        if (variab >= 0)
            progetto.setBudget(variab);
        return progetto.getBudget();
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

    public void aggiungiDipendente(String username, String name, String surname, Progetto progetto, String password)
            throws NoSuchUserException, UserAlreadyExistsException, NoSuchAlgorithmException {

        //verifica dell'esistenza di un'altro dipendente

        Dipendente dip = UtenteDAO.getNewDipendente(username, HashPassword(password));
        dip.setCognome(surname);
        dip.setNome(name);
        dip.getProgetti().add(progetto);
    }

    public void aggiungiMagazziniere(String username, String password, Magazzino magazzino, String name, String surname)
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

    public void login(String username, byte[] hash) {

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