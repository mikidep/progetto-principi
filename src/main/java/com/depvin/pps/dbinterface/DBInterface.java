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

    public static DBInterface getInstance() {
        return ourInstance;
    }

    private DBInterface() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mainUnit");
        entityManager = emf.createEntityManager();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    protected void finalize() throws Throwable {
        entityManager.flush();
        entityManager.close();
        super.finalize();
    }
}
