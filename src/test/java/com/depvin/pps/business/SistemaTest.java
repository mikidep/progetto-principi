package com.depvin.pps.business;

import com.depvin.pps.model.*;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SistemaTest {
    @Test
    public void articoliToPDFBytes() throws Exception {
        Sistema s = Sistema.getInstance();
        List<ArticoloOrdine> aolist = new ArrayList<ArticoloOrdine>();

        Produttore p = new Produttore("Luzzio");
        Prodotto pr = new Prodotto("Cancro");
        Fornitore fo = new Fornitore("mammata");
        List<Fornitore> fol = new ArrayList<Fornitore>();
        fol.add(fo);
        Articolo a = new Articolo("Teaser", "Abbastanza cancro", 12.98f, pr, p, fol);
        Dipendente d = new Dipendente("OPPSS", "Ilik".getBytes());
        Nazione nazione = new Nazione("Fanculandia", 5.60f);
        Sede sede = new Sede("Sede di Fanculo", nazione);
        Magazzino m = new Magazzino("ParDeCazzi", sede);
        Progetto pgg = new Progetto("Progetto1", 100.0f, sede);
        Ordine o = new Ordine("Candidio", pgg, d);

        for (int i = 9; i < 16; i++) {
            aolist.add(new ArticoloOrdine(o, a, i, m));
        }

        ByteArrayOutputStream bytes = s.articoliToPDFBytes("Test cazzoneso", aolist);
        FileOutputStream of = new FileOutputStream("test.pdf");
        bytes.writeTo(of);
        of.close();
    }
}