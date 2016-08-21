package com.depvin.pps.dao;

import com.depvin.pps.dbinterface.DBInterface;
import com.depvin.pps.model.Prodotto;
import com.depvin.pps.model.Categoria;

import java.util.List;

public class ProdottoDAO {
    public static List<Prodotto> getProdottiPerCategoria(Categoria c) {
        return DBInterface.getInstance().getEntityManager()
                .createQuery("SELECT p FROM Prodotto p JOIN p.categorie c WHERE c.id = :cId", Prodotto.class)
                .setParameter("cId", c.getId())
                .getResultList();
    }
}
