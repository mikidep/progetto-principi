package com.depvin.pps.model;

import java.util.List;

/**
 * Created by Michele De Pascalis on 27/11/15.
 */

public class Articolo {
    private long id;
    private String nome;
    private String descrizione;
    private byte[] immagine;
    private float prezzo;
    private Prodotto prodotto;
    private Produttore produttore;
    private Fornitore fornitore;
    private List<ArticoloOrdine> inOrdine;

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public byte[] getImmagine() {
        return immagine;
    }

    public void setImmagine(byte[] immagine) {
        this.immagine = immagine;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    public Prodotto getProdotto() {
        return prodotto;
    }

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }

    public Produttore getProduttore() {
        return produttore;
    }

    public void setProduttore(Produttore produttore) {
        this.produttore = produttore;
    }

    public Fornitore getFornitore() {
        return fornitore;
    }

    public void setFornitore(Fornitore fornitore) {
        this.fornitore = fornitore;
    }

    void verificaRichiestePerMagazzino(Magazzino magazzino) {
        for (ArticoloOrdine ao : inOrdine) {
            ao.verificaRichiestePerMagazzino(magazzino);
        }
    }
}
