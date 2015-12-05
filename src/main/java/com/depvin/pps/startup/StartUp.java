package com.depvin.pps.startup;

import com.depvin.pps.dbinterface.DBInterface;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Michele De Pascalis on 01/12/15.
 */

public class StartUp {
    public static void main(String args[]) {

        DBInterface.getInstance().getEntityManager();
        System.out.println("Done!");
    }
}
