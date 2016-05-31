package com.depvin.pps.dao;

import com.depvin.pps.model.Categoria;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by costantino on 31/05/16.
 */
public class CategoriaDAOTest {
    @Test
    public void getAllCategorie() throws Exception {
        List<Categoria> cats = CategoriaDAO.getAllCategorie();
        for (Categoria c : cats) {
            System.out.println(c.getNome());
        }
    }

}