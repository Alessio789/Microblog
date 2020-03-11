package DAO;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;


/**
 *
 * @author Alessio Trentin - 5^EI 
 * @version 1.0.0 - 03/02/2020
 */
public class Dao {
    
    protected final static String PERSISTENCE_UNIT_NAME = "microblog_Microblog_war_0PU";
    
    protected final static EntityManager em = 
            Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME)
                    .createEntityManager();
}
