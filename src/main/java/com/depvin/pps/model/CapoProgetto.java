package com.depvin.pps.model;

import java.util.List;

/**
 * Created by Michele De Pascalis on 30/11/15.
 *
 */

public class CapoProgetto extends Utente {
    List<Progetto> progetti;

    public CapoProgetto(String username) {
        super(username);
    }

    public List<Progetto> getProgetti() {
        return progetti;
    }
}
