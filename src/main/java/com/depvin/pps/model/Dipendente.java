package com.depvin.pps.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Michele De Pascalis on 27/11/15.
 */

public class Dipendente extends Utente {
    public interface NotificaArticoloListener {
        void articoloOrdineIsDisponibile(ArticoloOrdine articoloOrdine, Magazzino magazzino);
    }

    private List<Progetto> progetti;
    private List<Ordine> ordini;

    private Set<NotificaArticoloListener> listeners = new HashSet<NotificaArticoloListener>();

    protected Dipendente() {
    }

    public Dipendente(String username, byte[] passwordHash) {
        super(username, passwordHash);
    }

    public List<Progetto> getProgetti() {
        return progetti;
    }

    public List<Ordine> getOrdini() {
        return ordini;
    }

    public void addListener(NotificaArticoloListener listener) {
        listeners.add(listener);
    }

    void notificaDisponibilitàInMagazzino(ArticoloOrdine articoloOrdine, Magazzino magazzino) {
        for (NotificaArticoloListener listener : listeners) {
            listener.articoloOrdineIsDisponibile(articoloOrdine, magazzino);
        }
    }
}
