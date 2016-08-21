package com.depvin.pps.dao;

import com.depvin.pps.dbinterface.DBInterface;
import com.depvin.pps.model.Articolo;
import com.depvin.pps.model.Categoria;
import com.depvin.pps.model.Prodotto;

import java.util.List;

public class ArticoloDAO {
    public static List<Articolo> getArticoliPerCategoria(Categoria c) {
        return DBInterface.getInstance().getEntityManager()
                .createQuery("SELECT a FROM Articolo a JOIN a.prodotto.categorie c WHERE c.id = :cId", Articolo.class)
                .setParameter("cId", c.getId())
                .getResultList();
    }

    public static List<Articolo> getArticoliPerProdotto(Prodotto p) {
        return DBInterface.getInstance().getEntityManager()
                .createQuery("SELECT a FROM Articolo AS a WHERE a.prodotto.id = :pId", Articolo.class)
                .setParameter("pId", p.getId())
                .getResultList();
    }

    public static List<Articolo> getArticoliPerRicerca(String ss) {
        return DBInterface.getInstance().getEntityManager()
                .createQuery("SELECT a FROM Articolo AS a WHERE LOWER(a.nome) LIKE :ss" +
                        " OR LOWER(a.descrizione) LIKE :ss" +
                        " OR LOWER(a.prodotto.nome) LIKE :ss" +
                        " OR LOWER(a.produttore.nome) LIKE :ss", Articolo.class)
                .setParameter("ss", "%"+ss.toLowerCase()+"%")
                .getResultList();
    }
}
