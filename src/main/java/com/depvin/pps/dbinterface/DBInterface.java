package com.depvin.pps.dbinterface;

/**
 * Created by Michele De Pascalis on 05/12/15.
 */

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DBInterface {

    private static DBInterface ourInstance = new DBInterface();
    private EntityManager entityManager;

    private DBInterface() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mainUnit");
        entityManager = emf.createEntityManager();
    }

    public static DBInterface getInstance() {
        return ourInstance;
    }

    public static void save() {
        EntityManager entityManager = getInstance().getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.flush();
        entityManager.getTransaction().commit();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    protected void finalize() throws Throwable {
        save();
        entityManager.close();
        super.finalize();
    }

}
