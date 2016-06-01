package com.depvin.pps.business;

import com.depvin.pps.model.*;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

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

    public ByteArrayOutputStream stampaOrdineProgetto(Progetto progetto) throws ReportCreationFailedException {
        ArrayList<ArticoloOrdine> lista = new ArrayList<ArticoloOrdine>();
        for (Ordine o : progetto.getOrdini()) {
            for (ArticoloOrdine ao : o.getArticoliOrdine()) {
                lista.add(ao);
            }
        }
        /*DateTime jodaTime = new DateTime();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss.SSS");*/
        return Sistema.getInstance().articoliToPDFBytes(progetto.getNome(), lista);
    }

    public ByteArrayOutputStream stampaOrdine(Ordine ordine) throws ReportCreationFailedException {
        return Sistema.getInstance().articoliToPDFBytes(ordine.getNome(), Sistema.getInstance().ottieniListaOrdine(ordine));
    }

}
