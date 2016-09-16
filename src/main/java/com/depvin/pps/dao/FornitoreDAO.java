package com.depvin.pps.dao;


import com.depvin.pps.dbinterface.DBInterface;
import com.depvin.pps.model.Fornitore;

import javax.persistence.EntityManager;
import java.util.List;

public class FornitoreDAO {
    public static List<Fornitore> getAllFornitori() {
        return (List<Fornitore>) DBInterface.getInstance().getEntityManager()
                .createQuery("SELECT f from Fornitore f", Fornitore.class)
                .getResultList();
    }

    public static void addFornitoreWithNome(String nome) {
        Fornitore f = new Fornitore(nome);
        EntityManager em = DBInterface.getInstance().getEntityManager();
        em.getTransaction().begin();
        em.persist(f);
        em.getTransaction().commit();
    }
}
