package it.marconivr.microblog.repos;

import it.marconivr.microblog.entities.User;

import org.springframework.data.repository.CrudRepository;

/**
 * 
 * User Repository
 * 
 * @author Alessio Trentin - 5^EI
 * @version 1.0.0 - 15/03/2020
 */
public interface IUserRepo extends CrudRepository<User, String> {

    User findByUsername(String username);
}