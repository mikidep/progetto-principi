package com.depvin.pps.startup;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Michele De Pascalis on 01/12/15.
 *
 */

public class StartUp {
    public static void main(String args[]) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mainUnit");
        EntityManager entityManager = emf.createEntityManager();
        entityManager.close();
        System.out.println("Done!");
    }
}
