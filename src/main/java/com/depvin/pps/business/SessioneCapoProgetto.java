package com.depvin.pps.business;

import com.depvin.pps.model.CapoProgetto;
import com.depvin.pps.model.Dipendente;
import com.depvin.pps.model.Ordine;
import com.depvin.pps.model.Progetto;

import java.io.ByteArrayOutputStream;

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

    public ByteArrayOutputStream stampaOrdineDipendente(Dipendente dipendente, Progetto progetto) throws ReportCreationFailedException {
        return Sistema.getInstance().articoliToPDFBytes(dipendente.getNome(), Sistema.getInstance().ottieniListaDipendente(dipendente, progetto));
    }

    public ByteArrayOutputStream stampaOrdineProgetto(Progetto progetto, Ordine ordine) throws ReportCreationFailedException {
        return Sistema.getInstance().articoliToPDFBytes(ordine.getNome(), Sistema.getInstance().ottieniListaOrdine(ordine));
    }

}
