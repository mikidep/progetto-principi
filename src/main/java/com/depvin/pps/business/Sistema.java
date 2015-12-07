package com.depvin.pps.business;

import com.depvin.pps.dao.NoSuchUserException;
import com.depvin.pps.dao.UserAlreadyExistsException;
import com.depvin.pps.dao.UtenteDAO;
import com.depvin.pps.model.*;
import com.sun.org.apache.bcel.internal.util.ByteSequence;
import com.sun.xml.internal.ws.util.ByteArrayBuffer;
import sun.nio.cs.UTF_32BE_BOM;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
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
            throws NoSuchUserException {
        if (UtenteDAO.getUtenteWithUsernameAndHash(username, HashPassword(password)) == null) {
            //gestire più throws
        }
    }

    public void aggiungiMagazziniere() {

    }

    public void aggiungiCapoProgetto() {

    }

    public void login(String username, byte[] hash) {

    }

    public byte[] HashPassword(String password) {//Verrà aggiustata più avanti porcoddio
        byte[] result = new byte[0];
        try {
            result = password.getBytes("UTF_32BE_BOM");
        } catch (Exception e) {
            System.out.println("errore");
        }
        return result;
    }


}