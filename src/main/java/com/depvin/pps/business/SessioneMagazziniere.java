package com.depvin.pps.business;

import com.depvin.pps.model.*;

/**
 * Created by costantino on 05/12/15.
 */
public class SessioneMagazziniere implements Sessione {

    public void stampaOrdine(Ordine ordine) {
        Sistema.getInstance().stampaOrdine(ordine);
    }

    public void aggiungiArticoloMagazzino(Articolo articolo, Magazzino magazzino, int disponibilità) {
        ArticoloMagazzino articoloMagazzino = new ArticoloMagazzino(magazzino, articolo, disponibilità);
        magazzino.getArticoliMagazzino().add(articoloMagazzino);

    }
}
