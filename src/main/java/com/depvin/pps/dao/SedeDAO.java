package com.depvin.pps.dao;

/**
 * Created by costantino on 31/05/16.
 */

import com.depvin.pps.dbinterface.DBInterface;
import com.depvin.pps.model.Sede;

import java.util.List;

public class SedeDAO {
    public static List<Sede> getAllSedi() {
        return (List<Sede>) DBInterface.getInstance().getEntityManager()
                .createQuery("SELECT c from Sede c", Sede.class)
                .getResultList();
    }
}
