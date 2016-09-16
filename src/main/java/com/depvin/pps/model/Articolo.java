package com.depvin.pps.model;

import java.util.List;


public class Articolo {
    private long id;
    private String nome;
    private String descrizione;
    private byte[] immagine;
    private float prezzo;
    private Prodotto prodotto;
    private Produttore produttore;
    private List<Fornitore> fornitori;
    private List<ArticoloOrdine> inOrdine;
    private List<ArticoloMagazzino> inMagazzino;

    public Articolo(String nome, String descrizione, float prezzo, Prodotto prodotto, Produttore produttore, List<Fornitore> fornitori) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.prodotto = prodotto;
        this.produttore = produttore;
        this.fornitori = fornitori;
    }

    protected Articolo() {

    }

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

    public List<Fornitore> getFornitori() {
        return fornitori;
    }

    public List<ArticoloMagazzino> getInMagazzino() {
        return inMagazzino;
    }
}
