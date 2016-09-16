package com.depvin.pps.model;


public class Magazziniere extends Utente {
    private Magazzino magazzino;

    protected Magazziniere() {
    }

    public Magazziniere(String username, byte[] passwordHash, Magazzino magazzino) {
        super(username, passwordHash);
        this.magazzino = magazzino;
    }

    public Magazzino getMagazzino() {
        return magazzino;
    }

    public void setMagazzino(Magazzino magazzino) {
        this.magazzino = magazzino;
    }


}
