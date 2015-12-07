package com.depvin.pps.business;

import com.depvin.pps.dao.UserAlreadyExistsException;
import com.depvin.pps.dao.UtenteDAO;
import com.depvin.pps.model.*;

import javax.persistence.EntityExistsException;
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
            throws UserAlreadyExistsException {
        //Hasshare dentro la password
    }

    public void aggiungiMagazziniere() {

    }

    public void aggiungiCapoProgetto() {

    }

    public void login(String username, byte[] hash) {

    }

}