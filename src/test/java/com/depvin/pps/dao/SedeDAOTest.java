package com.depvin.pps.dao;

import com.depvin.pps.model.Sede;
import org.junit.Test;

import java.util.List;

public class SedeDAOTest {
    @Test
    public void getAllCategorie() throws Exception {
        List<Sede> sedi = SedeDAO.getAllSedi();
        for (Sede s : sedi) {
            System.out.println(s.getNome());
        }
    }

}