package it.marconivr.microblog.repos;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.marconivr.microblog.entities.Commento;
import it.marconivr.microblog.entities.Post;

/**
 * 
 * Commento Repository 
 * 
 * @author Alessio Trentin - 5^EI
 * @version 1.0.0 - 15/03/2020
 */
public interface ICommentoRepo extends CrudRepository<Commento, Long> {

    List<Commento> findByPost(Post post);
}