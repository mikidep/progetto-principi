package com.depvin.pps.business;

import com.depvin.pps.model.Ordine;
import com.depvin.pps.model.Progetto;

/**
 * Created by costantino on 05/12/15.
 */
public class SessioneCapoProgetto implements Sessione {

    public void modificaBudget(Progetto progetto, float budget) {
        Sistema.getInstance().modificaBudget(progetto, budget);
    }

    public void stampaOrdine(Ordine ordine) {
        Sistema.getInstance().stampaOrdine(ordine);
    }
}
