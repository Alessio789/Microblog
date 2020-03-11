/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import entity.Utente;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * Dao Utente
 * 
 * @author Alessio Trentin - 5^EI
 * @version 1.0.0 - 10/02/2020
 */
public class UtenteDao implements Serializable {

    private static EntityManager em = Dao.em;

    public static EntityManager getEntityManager() {
        return em;
    }

    public static boolean create(Utente utente) {
        em.getTransaction().begin();
        try {
            em.persist(utente); 
            // -- workaround cache entity manager
            em.flush();
            em.clear();   
            // --
            em.getTransaction().commit();
            
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            return false;
        }
    }

    public static void edit(Utente utente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            utente = em.merge(utente);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String username = utente.getUsername();
                if (findUtente(username) == null) {
                    throw new NonexistentEntityException("The utente with id " + username + " no longer exists.");
                }
            }
            throw ex;
        } 
    }

    public static void destroy(String username) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Utente utente;
            try {
                utente = em.getReference(Utente.class, username);
                utente.getUsername();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The utente with id " + username + " no longer exists.", enfe);
            }
            em.remove(utente);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }

    public static List<Utente> findUtenteEntities() {
        return findUtenteEntities(true, -1, -1);
    }

    public static List<Utente> findUtenteEntities(int maxResults, int firstResult) {
        return findUtenteEntities(false, maxResults, firstResult);
    }

    private static List<Utente> findUtenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Utente.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        return null;
    }

    public static Utente findUtente(String username) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Utente.class, username);
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        return null;
    }

    public static int getUtenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Utente> rt = cq.from(Utente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        return 0;
    }
    
    public static Utente getUtenteByUsername(String username) {
        Utente utente = (Utente) em.createQuery("SELECT u "
                + "FROM Utente u WHERE u.username LIKE :username").setParameter(
                        "username", username).getSingleResult();
        return utente;
    }
}
