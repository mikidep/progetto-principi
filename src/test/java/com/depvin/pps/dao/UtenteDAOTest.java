package com.depvin.pps.dao;

import com.depvin.pps.model.CapoProgetto;
import org.junit.Test;

import java.util.List;

public class UtenteDAOTest {
    @Test
    public void getAllCapiProgetto() throws Exception {
        List<CapoProgetto> cats = UtenteDAO.getAllCapiProgetto();
        for (CapoProgetto c : cats) {
            System.out.println(c.getNome());
        }
    }

}