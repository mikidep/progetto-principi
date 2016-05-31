package com.depvin.pps.dao;

/**
 * Created by costantino on 31/05/16.
 */

import com.depvin.pps.dbinterface.DBInterface;
import com.depvin.pps.model.*;

import java.util.List;

public class CategoriaDAO {
    public static List<Categoria> getAllCategorie() {
        return (List<Categoria>) DBInterface.getInstance().getEntityManager()
                .createQuery("SELECT c from Categoria c", Categoria.class)
                .getResultList();
    }
}
