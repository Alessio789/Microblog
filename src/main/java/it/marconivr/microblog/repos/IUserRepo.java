package it.marconivr.microblog.repos;

import it.marconivr.microblog.entities.User;

import org.springframework.data.repository.CrudRepository;

/**
 * User Repository
 *
 * @author Alessio Trentin
 */
public interface IUserRepo extends CrudRepository<User, String> {

    User findByUsername(String username);
}