package com.depvin.pps.dao;

import com.depvin.pps.model.Categoria;
import com.depvin.pps.model.Prodotto;
import org.junit.Test;

import java.util.List;

public class ProdottoDAOTest {
    @Test
    public void getProdottiPerCategoriaTest() {
        Categoria c = CategoriaDAO.getAllCategorie().get(0);
        List<Prodotto> rl = ProdottoDAO.getProdottiPerCategoria(c);
        for (Prodotto p: rl) {
            System.out.println(p.getId() + " - " + p.getNome());
        }
    }
}
