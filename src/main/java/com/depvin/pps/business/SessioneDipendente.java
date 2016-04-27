package com.depvin.pps.business;

import com.depvin.pps.model.ArticoloOrdine;
import com.depvin.pps.model.Ordine;

/**
 * Created by costantino on 05/12/15.
 */
public class SessioneDipendente implements Sessione {

    public void confermaOrdine(Ordine ordine) {
        Sistema.getInstance().confermaOrdine(ordine);
    }

    public void rimuoviOridne(Ordine ordine, ArticoloOrdine articoloOrdine) {
        Sistema.getInstance().rimuoviOrdine(ordine, articoloOrdine);
    }

    public void richiediNotifica(ArticoloOrdine articoloOrdine) {
        Sistema.getInstance().richiediNotifica(articoloOrdine);
    }
}
