package com.depvin.pps.business;

import com.depvin.pps.model.*;

import java.io.ByteArrayOutputStream;
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

    public void eliminaArticoloMagazzino(ArticoloMagazzino articoloMagazzino) {
        Magazzino magazzino = getUtente().getMagazzino();
        Sistema.getInstance().eliminaArticoloMagazzino(articoloMagazzino, magazzino);
    }

    public void aggiungiArticoloMagazzino(Articolo articolo, int disponibilità) {
        Magazzino magazzino = getUtente().getMagazzino();
        ArticoloMagazzino articoloMagazzino = new ArticoloMagazzino(magazzino, articolo, disponibilità);
        magazzino.getArticoliMagazzino().add(articoloMagazzino);
    }

    public void modificaQuantitàArticolo(ArticoloMagazzino articoloMagazzino, int quantità) {
        Sistema.getInstance().modificaQuantitàArticolo(articoloMagazzino, quantità);
    }

    public ByteArrayOutputStream stampaOrdine(Ordine ordine) throws ReportCreationFailedException {
        return Sistema.getInstance().articoliToPDFBytes(ordine.getNome(), Sistema.getInstance().ottieniListaOrdine(ordine));
    }

    public List<Categoria> ottieniListaCategorie() {
        return Sistema.getInstance().ottieniListaCategorie();
    }

}
