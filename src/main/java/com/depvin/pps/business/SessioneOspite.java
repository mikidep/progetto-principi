package com.depvin.pps.business;

import com.depvin.pps.model.ArticoloMagazzino;
import com.depvin.pps.model.Magazzino;

/**
 * Created by costantino on 05/12/15.
 */
public class SessioneOspite implements Sessione {

    public void stampaLista(Magazzino magazzino) {
        for (ArticoloMagazzino ao : Sistema.getInstance().getListaMagazzino(magazzino))
            System.out.println("L'articolo " + ao.getArticolo() + " è disponibile in " + ao.getDisponibilita() + " pezzi");
    }//In seguito sarà collegato con un listener
}


