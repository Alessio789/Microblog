package DAO;

import DAO.exceptions.NonexistentEntityException;
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
 * Dao Post
 *
 * @author Alessio Trentin - 5^EI
 * @version 1.0.0 - 10/02/2020
 */
public class PostDao implements Serializable {

    private static EntityManager em = Dao.em;

    public static EntityManager getEntityManager() {
        return em;
    }

    public static void create(Post post) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(post);
            em.getTransaction().commit();
        }
        catch(Exception e) {
            e.printStackTrace();
            if(em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }

    public static void edit(Post post) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            post = em.merge(post);
            em.getTransaction().commit();
        }
        catch(Exception ex) {
            String msg = ex.getLocalizedMessage();
            if(msg == null || msg.length() == 0) {
                Long id = post.getId();
                if(findPost(id) == null) {
                    throw new NonexistentEntityException("The post with id " + id + " no longer exists.");
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
            Post post;
            try {
                post = em.getReference(Post.class, id);
                post.getId();
            }
            catch(EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The post with id " + id + " no longer exists.", enfe);
            }
            em.remove(post);
            em.getTransaction().commit();
        }
        catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }

    public static List<Post> findPostEntities() {
        return findPostEntities(true, -1, -1);
    }

    public static List<Post> findPostEntities(int maxResults, int firstResult) {
        return findPostEntities(false, maxResults, firstResult);
    }

    private static List<Post> findPostEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Post.class));
            Query q = em.createQuery(cq);
            if( ! all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        }
        catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        return null;
    }

    public static Post findPost(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Post.class, id);
        }
        catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        return null;
    }

    public static int getPostCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Post> rt = cq.from(Post.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        }
        catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        return 0;
    }

    
    public static List<Post> findAll() {
        TypedQuery<Post> typedQuery = em.createQuery("SELECT p FROM Post p ORDER BY p.dataOra DESC", Post.class);
        List<Post> postList = typedQuery.getResultList();
        return postList;
    }
}
