package com.depvin.pps.dao;

import com.depvin.pps.model.Articolo;
import com.depvin.pps.model.Categoria;
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

    @Test
    public void getArticoliPerCategoriaTest() {
        Categoria c = CategoriaDAO.getAllCategorie().get(0);
        List<Articolo> rl = ArticoloDAO.getArticoliPerCategoria(c);
        for (Articolo a: rl) {
            System.out.println(a.getId() + " - " + a.getNome() + ": " + a.getDescrizione());
        }
    }
}
