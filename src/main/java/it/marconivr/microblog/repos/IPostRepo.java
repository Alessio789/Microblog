package it.marconivr.microblog.repos;

import it.marconivr.microblog.entities.User;
import org.springframework.data.repository.CrudRepository;

import it.marconivr.microblog.entities.Post;

import java.util.List;

/**
 * Post Repository
 *
 * @author Alessio Trentin
 */
public interface IPostRepo extends CrudRepository<Post, Long> {

    List<Post> findByUser(User u);

}