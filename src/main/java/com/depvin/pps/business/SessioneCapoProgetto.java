package com.depvin.pps.business;

import com.depvin.pps.model.CapoProgetto;
import com.depvin.pps.model.Dipendente;
import com.depvin.pps.model.Ordine;
import com.depvin.pps.model.Progetto;

/**
 * Created by costantino on 05/12/15.
 */
public class SessioneCapoProgetto implements Sessione {

    private CapoProgetto capoProgetto;

    public SessioneCapoProgetto(CapoProgetto capoProgetto) {
        this.capoProgetto = capoProgetto;
    }

    public CapoProgetto getUtente() {
        return capoProgetto;
    }

    public void modificaBudget(Progetto progetto, float budget) {
        Sistema.getInstance().modificaBudget(progetto, budget);
    }

    public void stampaOrdineProgetto(Ordine ordine) {
        Sistema.getInstance().stampaOrdine(ordine);
        Sistema.getInstance().calcolaSpeseProgetto(ordine.getProgetto());
    }

    public void stampaOrdineDipendente(Dipendente dipendente, Progetto progetto) {
        Sistema.getInstance().stampaOrdineDipendente(dipendente, progetto);
        Sistema.getInstance().calcolaSpeseDipendente(dipendente, progetto);
    }

}
