package com.depvin.pps.business;

import com.depvin.pps.model.*;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SessioneMagazziniere implements Sessione {

    private Magazziniere magazziniere;

    public SessioneMagazziniere(Magazziniere magazziniere) {
        this.magazziniere = magazziniere;
    }

    public Magazziniere getUtente() {
        return magazziniere;
    }

    public void aggiungiArticoloMagazzino(Articolo articolo, int disponibilità) {
        Magazzino magazzino = getUtente().getMagazzino();
        ArticoloMagazzino articoloMagazzino = new ArticoloMagazzino(magazzino, articolo, disponibilità);
        Sistema.getInstance().aggiungiArticoloMagazzino(articoloMagazzino, magazzino);
    }

    public void modificaQuantitàArticolo(ArticoloMagazzino articoloMagazzino, int quantità, Magazzino magazzino) {
        Sistema.getInstance().modificaQuantitàArticolo(articoloMagazzino, quantità, magazzino);
    }

    public void modificaNomeArticolo(ArticoloMagazzino articoloMagazzino, String nome) {
        Sistema.getInstance().modificaNomeArticolo(articoloMagazzino, nome);
    }

    public void modificaDescrizioneArticolo(ArticoloMagazzino articoloMagazzino, String descrizione) {
        Sistema.getInstance().modificaDescrizioneArticolo(articoloMagazzino, descrizione);
    }

    public void modificaPrezzoArticolo(ArticoloMagazzino articoloMagazzino, float prezzo) {
        Sistema.getInstance().modificaPrezzoArticolo(articoloMagazzino, prezzo);
    }

    public void modificaFornitoreArticolo(ArticoloMagazzino articoloMagazzino, String nuovoFornitore, String vecchioFornitore) {
        Sistema.getInstance().modificaFornitoreArticolo(articoloMagazzino, nuovoFornitore, vecchioFornitore);
    }

    public void modificaProdottoArticolo(ArticoloMagazzino articoloMagazzino, String nomeProdotto) {
        List<Categoria> listCAT = articoloMagazzino.getArticolo().getProdotto().getCategorie();
        Sistema.getInstance().modificaProdottoArticolo(articoloMagazzino, nomeProdotto, listCAT);
    }

    public void modificaProduttoreArticolo(ArticoloMagazzino articoloMagazzino, String nomeProduttore) {
        Sistema.getInstance().modificaProduttoreArticolo(articoloMagazzino, nomeProduttore);
    }

    public void modificaImmagineArticolo(ArticoloMagazzino articoloMagazzino, byte[] bytes) {
        Sistema.getInstance().modificaImmagineArticolo(articoloMagazzino, bytes);
    }

    public void aggiungiImmagineArticolo(Articolo articolo, byte[] bytes) {
        Sistema.getInstance().aggiungiImmagineArticolo(articolo, bytes);
    }

    public void aggiungiFornitoreArticolo(ArticoloMagazzino articoloMagazzino, String nomeFornitore) {
        Sistema.getInstance().aggiungiFornitoreArticolo(articoloMagazzino, nomeFornitore);
    }

    public void aggiungiCategoriaArticolo(ArticoloMagazzino articoloMagazzino, String nomeCategoria) {
        Sistema.getInstance().aggiungiCategoriaArticolo(articoloMagazzino, nomeCategoria);
    }

    public void modificaCategoriaArticolo(ArticoloMagazzino articoloMagazzino, String nomeCategoria) {
        Sistema.getInstance().modificaCategoriaArticolo(articoloMagazzino, nomeCategoria);
    }

    public ByteArrayOutputStream stampaArticoliOrdine(String nome, List<ArticoloOrdine> listAO) throws ReportCreationFailedException {
        return Sistema.getInstance().articoliToPDFBytes(nome, listAO);
    }

    public List<ArticoloOrdine> ottieniListaArticoliOrdine(Magazzino magazzino) {
        return Sistema.getInstance().ottieniListaArticoliOrdine(magazzino);
    }

    public List<Categoria> ottieniListaCategoria() {
        return Sistema.getInstance().ottieniListaCategorie();
    }

    public List<Articolo> ottieniListaArticoli() {
        return Sistema.getInstance().ottieniListaArticoli();
    }

    public void evadiOrdine(Ordine ordine, Magazzino magazzino) throws EvasionException {
        Sistema.getInstance().evadiOrdine(ordine, magazzino);
    }

    public List<RichiestaArticolo> ottieniListaRichiestaArticoliSede(Sede sede) {
        return Sistema.getInstance().ottieniListaRichiestaArticoliSede(sede);
    }

    public List<Articolo> ottieniListaArticoliRichiestiiProgetto(Progetto progetto) {
        return Sistema.getInstance().ottieniListaArticoliRichiestiProgetto(progetto);
    }

    public List<Prodotto> ottieniListaProdotto(Categoria categoria) {
        return Sistema.getInstance().ottieniListaProdottiPerCategoria(categoria);
    }

    public void rimuoviRichiesta(RichiestaArticolo richiestaArticolo) {
        Sistema.getInstance().rimuoviRichiesta(richiestaArticolo);
    }

    public void sistemaSave() {
        Sistema.getInstance().sistemaSave();
    }

}
