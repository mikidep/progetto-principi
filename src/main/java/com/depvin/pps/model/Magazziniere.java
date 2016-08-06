package com.depvin.pps.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Michele De Pascalis on 01/12/15.
 */

public class Magazziniere extends Utente {
    private Set<NotificaArticoloListener> listeners = new HashSet<NotificaArticoloListener>();
    private Magazzino magazzino;

    protected Magazziniere() {
    }

    public Magazziniere(String username, byte[] passwordHash, Magazzino magazzino) {
        super(username, passwordHash);
        this.magazzino = magazzino;
    }

    public Magazzino getMagazzino() {
        return magazzino;
    }

    public void setMagazzino(Magazzino magazzino) {
        this.magazzino = magazzino;
    }

    void notificaRichiestaArticolo(ArticoloOrdine articoloOrdine) {
        for (NotificaArticoloListener listener : listeners) {
            listener.articoloOrdineRichiesto(articoloOrdine);
        }
    }

    public interface NotificaArticoloListener {
        void articoloOrdineRichiesto(ArticoloOrdine articoloOrdine);
    }
}
