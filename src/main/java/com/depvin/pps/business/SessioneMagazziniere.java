package com.depvin.pps.business;

import com.depvin.pps.model.*;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by costantino on 05/12/15.
 */
public class SessioneMagazziniere implements Sessione {

    private Magazziniere magazziniere;

    public SessioneMagazziniere(Magazziniere magazziniere) {
        this.magazziniere = magazziniere;
    }

    public Magazziniere getUtente() {
        return magazziniere;
    }

    /*public void eliminaArticoloMagazzino(ArticoloMagazzino articoloMagazzino) {
        Magazzino magazzino = getUtente().getMagazzino();
        Sistema.getInstance().eliminaArticoloMagazzino(articoloMagazzino, magazzino);
    }//Non implementato*/

    public void aggiungiArticoloMagazzino(Articolo articolo, int disponibilità) {
        Magazzino magazzino = getUtente().getMagazzino();
        ArticoloMagazzino articoloMagazzino = new ArticoloMagazzino(magazzino, articolo, disponibilità);
        Sistema.getInstance().aggiungiArticoloMagazzino(articoloMagazzino, magazzino);
    }

    public void modificaQuantitàArticolo(ArticoloMagazzino articoloMagazzino, int quantità) {
        Sistema.getInstance().modificaQuantitàArticolo(articoloMagazzino, quantità);
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

    public void modificaProdottoCategoriaArticolo(ArticoloMagazzino articoloMagazzino, String nomeProdotto, Categoria categoria) {
        List<Categoria> listC = new ArrayList<Categoria>();
        List<Categoria> listCAT = ottieniListaCategoria();
        for (Categoria cat : listCAT) {
            if (cat.getNome().equals(categoria.getNome()))
                listC.add(cat);
        }
        if (listC.isEmpty())
            listC.add(categoria);
        Sistema.getInstance().modificaProdottoCategoriaArticolo(articoloMagazzino, new Prodotto(nomeProdotto, listC));
    }

    public void modificaProduttoreArticolo(ArticoloMagazzino articoloMagazzino, String nomeProduttore) {
        Produttore produttore = new Produttore(nomeProduttore);
        Sistema.getInstance().modificaProduttoreArticolo(articoloMagazzino, produttore);
    }

    public void modificaImmagineArticolo(ArticoloMagazzino articoloMagazzino, byte[] bytes) {
        Sistema.getInstance().modificaImmagineArticolo(articoloMagazzino, bytes);
    }

    public void aggiungiImmagineArticolo(Articolo articolo, byte[] bytes) {
        Sistema.getInstance().aggiungiImmagineArticolo(articolo, bytes);
    }

    public void aggiungiFornitoreArticolo(ArticoloMagazzino articoloMagazzino, String nomeFornitore) {
        Fornitore fornitore = new Fornitore(nomeFornitore);
        Sistema.getInstance().aggiungiFornitoreArticolo(articoloMagazzino, fornitore);
    }

    public void aggiungiCategoriaArticolo(ArticoloMagazzino articoloMagazzino, String nomeCategoria) {
        Sistema.getInstance().aggiungiCategoriaArticolo(articoloMagazzino, nomeCategoria);
    }

    public void modificaCategoriaArticolo(ArticoloMagazzino articoloMagazzino, String nuovaCategoria, String vecchiaCategoria) {
        Sistema.getInstance().modificaCategoriaArticolo(articoloMagazzino, nuovaCategoria, vecchiaCategoria);
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

}
