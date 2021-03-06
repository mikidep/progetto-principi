package com.depvin.pps.business;

import com.depvin.pps.model.*;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class SessioneDipendente implements Sessione {

    private Dipendente dipendente;

    public SessioneDipendente(Dipendente dipendente) {
        this.dipendente = dipendente;
    }

    public Dipendente getUtente() {
        return dipendente;
    }

    public void inviaOrdine(Ordine ordine) throws SendOrderException {
        Sistema.getInstance().inviaOrdine(ordine);
    }

    public void rimuoviOrdine(Ordine ordine, Progetto progetto) {
        Sistema.getInstance().rimuoviOrdine(ordine, progetto);
    }

    public void rimuoviArticoloOrdine(Ordine ordine, ArticoloOrdine articoloOrdine) {
        Sistema.getInstance().rimuoviArticoloOrdine(ordine, articoloOrdine);
    }

    public void creaNotifica(Articolo articolo, Progetto progetto, int quantita) {
        Sistema.getInstance().creaNotifica(articolo, progetto, quantita);
    }

    public void aggiungiOrdineProgetto(String nome, Progetto progetto) {
        Ordine o = new Ordine(nome, progetto, getUtente());
        getUtente().getOrdini().add(o);
        Sistema.getInstance().aggiungiOrdineProgetto(o, progetto);
    }

    public ByteArrayOutputStream stampaOrdine(Ordine ordine) throws ReportCreationFailedException {
        return Sistema.getInstance().articoliToPDFBytes(ordine.getNome(), Sistema.getInstance().ottieniListaOrdine(ordine));
    }

    public List<Categoria> ottieniListaCategorie() {
        return Sistema.getInstance().ottieniListaCategorie();
    }

    public List<Articolo> ottieniListaArticoliPerCategoria(Categoria categoria) {
        return Sistema.getInstance().ottieniListaArticoliPerCategoria(categoria);
    }

    public List<Articolo> ottieniListaArticoliPerProdotto(Prodotto prodotto) {
        return Sistema.getInstance().ottieniListaArticoliPerProdotto(prodotto);
    }

    public List<Articolo> ottieniListaArticoliPerRicerca(String ricerca) {
        return Sistema.getInstance().ottieniListaArticoliPerRicerca(ricerca);
    }

    public List<Prodotto> ottieniListaProdottoPerCategoria(Categoria categoria) {
        return Sistema.getInstance().ottieniListaProdottiPerCategoria(categoria);
    }

    public void aggiungiArticoloOrdine(ArticoloOrdine articoloOrdine, Ordine ordine) {
        Sistema.getInstance().aggiungiArticoloOrdine(articoloOrdine, ordine);
    }

    public void modificaDisponibilitàArticoloOrdine(ArticoloOrdine articoloOrdine, int disponibilità) {
        Sistema.getInstance().modificaDisponibilitàArticoloOrdine(articoloOrdine, disponibilità);
    }

    public List<Articolo> ottieniListaArticoliRichiestiiProgetto(Progetto progetto) {
        return Sistema.getInstance().ottieniListaArticoliRichiestiProgetto(progetto);
    }

    public List<RichiestaArticolo> ottieniListaRichiestaArticoliSede(Sede sede) {
        return Sistema.getInstance().ottieniListaRichiestaArticoliSede(sede);
    }

    public void sistemaSave() {
        Sistema.getInstance().sistemaSave();
    }


}