package DAO;

import DAO.exceptions.NonexistentEntityException;
import entity.Commento;
import entity.Post;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


/**
 *
 * Dao Commento
 *
 * @author Alessio Trentin - 5^EI
 * @version 1.0.0 - 07/02/2020
 */
public class CommentoDao implements Serializable {

    private static EntityManager em = Dao.em;

    public static EntityManager getEntityManager() {
        return em;
    }

    public static void create(Commento commento) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(commento);
            em.getTransaction().commit();
        }
        catch(Exception e) {
            e.printStackTrace();
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }

    }

    public static void edit(Commento commento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            commento = em.merge(commento);
            em.getTransaction().commit();
        }
        catch(Exception ex) {
            String msg = ex.getLocalizedMessage();
            if(msg == null || msg.length() == 0) {
                Long id = commento.getId();
                if(findCommento(id) == null) {
                    throw new NonexistentEntityException("The commento with id " + id + " no longer exists.");
                }
            }
            throw ex;
        }
    }

    public static void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Commento commento;
            try {
                commento = em.getReference(Commento.class, id);
                commento.getId();
            }
            catch(EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The commento with id " + id + " no longer exists.", enfe);
            }
            em.remove(commento);
            em.getTransaction().commit();
        }
        catch(Exception e) {
            e.printStackTrace();
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }

    public static List<Commento> findCommentoEntities() {
        return findCommentoEntities(true, -1, -1);
    }

    public static List<Commento> findCommentoEntities(int maxResults, int firstResult) {
        return findCommentoEntities(false, maxResults, firstResult);
    }

    private static List<Commento> findCommentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Commento.class));
            Query q = em.createQuery(cq);
            if( ! all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        }
        catch(Exception e) {
            e.printStackTrace();
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        return null;
    }

    public static Commento findCommento(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Commento.class, id);
        }
        finally {
            em.close();
        }
    }

    public static int getCommentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Commento> rt = cq.from(Commento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }
        catch(Exception e) {
            e.printStackTrace();
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        return 0;
    }
    
    public static List<Commento> findCommentoByPost(Post p) {
        List<Commento> commentoList = em.createQuery("SELECT c "
                + "FROM Commento c WHERE c.post = :p").setParameter(
                        "p", p).getResultList();
        return commentoList;
    }
    
    public static List<Commento> findAll() {
        TypedQuery<Commento> typedQuery = getEntityManager().createQuery("SELECT c FROM Commento c ORDER BY c.dataOra DESC", Commento.class);
        List<Commento> commentList = typedQuery.getResultList();
        return commentList;
    }
}
