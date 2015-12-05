package com.depvin.pps.dao;

/**
 * Created by Michele De Pascalis on 05/12/15.
 */

import com.depvin.pps.dbinterface.DBInterface;
import com.depvin.pps.model.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class UtenteDAO {
    public static Dipendente getNewDipendente(String username, byte[] hash) throws UserAlreadyExistsException {
        try {
            Dipendente u = new Dipendente(username, hash);
            EntityManager em = DBInterface.getInstance().getEntityManager();
            em.getTransaction().begin();
            em.persist(u);
            em.getTransaction().commit();
            return u;
        } catch (EntityExistsException e) {
            throw new UserAlreadyExistsException("A user with given username already exists!", e);
        }
    }

    public static Amministratore getNewAmministratore(String username, byte[] hash) throws UserAlreadyExistsException {
        try {
            Amministratore u = new Amministratore(username, hash);
            EntityManager em = DBInterface.getInstance().getEntityManager();
            em.getTransaction().begin();
            em.persist(u);
            em.getTransaction().commit();
            return u;
        } catch (EntityExistsException e) {
            throw new UserAlreadyExistsException("A user with given username already exists!", e);
        }
    }

    public static CapoProgetto getNewCapoProgetto(String username, byte[] hash) throws UserAlreadyExistsException {
        try {
            CapoProgetto u = new CapoProgetto(username, hash);
            EntityManager em = DBInterface.getInstance().getEntityManager();
            em.getTransaction().begin();
            em.persist(u);
            em.getTransaction().commit();
            return u;
        } catch (EntityExistsException e) {
            throw new UserAlreadyExistsException("A user with given username already exists!", e);
        }
    }

    public static Magazziniere getNewMagazziniere(String username, byte[] hash, Magazzino magazzino) throws UserAlreadyExistsException {
        try {
            Magazziniere u = new Magazziniere(username, hash, magazzino);
            EntityManager em = DBInterface.getInstance().getEntityManager();
            em.getTransaction().begin();
            em.persist(u);
            em.getTransaction().commit();
            return u;
        } catch (EntityExistsException e) {
            throw new UserAlreadyExistsException("A user with given username already exists!", e);
        }
    }
    
    public static Utente getUtenteWithUsernameAndHash(String username, byte[] hash) throws NoSuchUserException {
        try {
            return DBInterface.getInstance().getEntityManager()
                    .createQuery("SELECT u FROM Utente AS u WHERE u.username = :username AND u.passwordHash = :hash", Utente.class)
                    .setParameter("username", username).setParameter("hash", hash)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new NoSuchUserException("No user with given username and password hash exists!", e);
        }
    }
}
