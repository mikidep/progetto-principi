package com.depvin.pps.model;

import javax.persistence.*;

/**
 * Created by Michele De Pascalis on 27/11/15.
 *
 */

public class Utente {
    private String username;

    private String nome;

    private String cognome;

    public Utente(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
}
