package it.marconivr.microblog.repos;

import org.springframework.data.repository.CrudRepository;

import it.marconivr.microblog.entities.Post;

/**
 * 
 * Post Repository
 * 
 * @author Alessio Trentin - 5^EI
 * @version 1.0.0 - 15/03/2020
 */
public interface IPostRepo extends CrudRepository<Post, Long> {

}