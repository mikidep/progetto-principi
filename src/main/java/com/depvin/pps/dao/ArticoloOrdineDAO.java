package com.depvin.pps.dao;

import com.depvin.pps.dbinterface.DBInterface;
import com.depvin.pps.model.ArticoloOrdine;
import com.depvin.pps.model.Magazzino;

import java.util.List;

public class ArticoloOrdineDAO {
    public List<ArticoloOrdine> getArticoliOrdinePerMagazzino(Magazzino m) {
        return DBInterface.getInstance().getEntityManager()
                .createQuery("SELECT ao FROM ArticoloOrdine AS ao WHERE ao.magazzino.id = :mId", ArticoloOrdine.class)
                .setParameter("mId", m.getId())
                .getResultList();
    }
}

