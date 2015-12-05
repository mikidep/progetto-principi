package com.depvin.pps.business;

import com.depvin.pps.model.*;

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

    public void getListaMagazzino(Magazzino magazzino) {
        for (ArticoloMagazzino am : magazzino.getArticoliMagazzino()) {
            System.out.println("L'articolo " + am.getArticolo() + " è disponibile in "
                    + am.getDisponibilita() + " pezzi nel magazzino " + am.getMagazzino());
        }
    }//Da cambiare più avanti con qualcosa di più serio

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
        if (t > 0)
            System.out.println("Non si può chiudere l'ordine perchè non tutti gli articoli scelti sono disponibili");
        else
            ordine.setEvaso(true);
    }

    public void modificaBudget(Progetto progetto, float budget) {
        float variab = progetto.getBudget() + budget;
        if (variab >= 0)
            progetto.setBudget(variab);
        else
            System.out.println("Errore nell'inserimento del budget, saldo del progetto negativo, ritenta");
    }//l'errore sarà poi gestito dalle eccezzioni

    public void stampaOrdine(Ordine ordine) {
        System.out.println("L'ordine del progetto: " + ordine.getProgetto() + " è composto da: ");
        for (ArticoloOrdine ao : ordine.getArticoliOrdine()) {
            System.out.println("L'articolo " + ao.getArticolo() + " in quantità " + ao.getQuantita() + " dal magazzino " + ao.getMagazzino());
        }
        System.out.println("Prezzo totale" + ordine.getTotale());
    }//dovrebbe andare bene visto che il prof voleva una stampa

    public void richiediNotifica(ArticoloOrdine articoloOrdine) {
        articoloOrdine.setRichiesto(true);
    }//Premesso che non si possa richiedere la disponibilità anche quando è disponibile le quantità necessarie


    public void aggiungiDipendente() {

    }

    public void aggiungiMagazziniere() {

    }

    public void aggiungiCapoProgetto() {

    }

    public void login(String username, byte[] hash) {

    }

}