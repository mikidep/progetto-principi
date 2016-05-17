package com.depvin.pps.business;

import com.depvin.pps.model.ArticoloOrdine;
import com.depvin.pps.model.Dipendente;
import com.depvin.pps.model.Ordine;
import com.depvin.pps.model.Progetto;

import java.io.ByteArrayOutputStream;

/**
 * Created by costantino on 05/12/15.
 */
public class SessioneDipendente implements Sessione {

    private Dipendente dipendente;

    public SessioneDipendente(Dipendente dipendente) {
        this.dipendente = dipendente;
    }

    public Dipendente getUtente() {
        return dipendente;
    }

    public void confermaOrdine(Ordine ordine) {
        Sistema.getInstance().confermaOrdine(ordine);
    }

    public void rimuoviOrdine(Ordine ordine) {
        Sistema.getInstance().rimuoviOrdine(ordine);
    }

    public void rimuoviArticoloOrdine(Ordine ordine, ArticoloOrdine articoloOrdine) {
        Sistema.getInstance().rimuoviArticoloOrdine(ordine, articoloOrdine);
    }

    public void richiediNotifica(ArticoloOrdine articoloOrdine) {
        Sistema.getInstance().richiediNotifica(articoloOrdine);
    }

    public void aggiungiOrdineProgetto(String nome, Progetto progetto) {
        Ordine o = new Ordine(nome, progetto, getUtente());
        Sistema.getInstance().aggiungiOrdineProgetto(o, progetto);
        getUtente().getOrdini().add(o);
    }

    public ByteArrayOutputStream stampaOrdine(Ordine ordine) throws ReportCreationFailedException {
        return Sistema.getInstance().articoliToPDFBytes(ordine.getNome(), Sistema.getInstance().ottieniListaOrdine(ordine));
    }

}
