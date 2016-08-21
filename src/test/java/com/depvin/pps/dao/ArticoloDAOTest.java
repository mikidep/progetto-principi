package com.depvin.pps.dao;

import com.depvin.pps.model.Articolo;
import org.junit.Test;

import java.util.List;

public class ArticoloDAOTest {
    @Test
    public void getArticoliPerRicercaTest() {
        List<Articolo> rl = ArticoloDAO.getArticoliPerRicerca("che");
        for (Articolo a: rl) {
            System.out.println(a.getNome() + ": " + a.getDescrizione());
        }
    }
}
