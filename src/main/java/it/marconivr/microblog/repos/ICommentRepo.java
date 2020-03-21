package it.marconivr.microblog.repos;

import it.marconivr.microblog.entities.Comment;
import it.marconivr.microblog.entities.Post;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

/**
 * 
 * Comment Repository 
 * 
 * @author Alessio Trentin - 5^EI
 * @version 1.0.0 - 15/03/2020
 */
public interface ICommentRepo extends CrudRepository<Comment, Long> {

    List<Comment> findByPost(Post post);
}