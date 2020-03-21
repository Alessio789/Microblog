package it.marconivr.microblog.controllers;

import it.marconivr.microblog.entities.Comment;
import it.marconivr.microblog.entities.Post;
import it.marconivr.microblog.entities.User;
import it.marconivr.microblog.repos.ICommentRepo;
import it.marconivr.microblog.repos.IPostRepo;
import it.marconivr.microblog.repos.IUserRepo;

import java.util.Date;
import java.util.Optional;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * Comment Controller
 * 
 * @author Alessio Trentin - 5^EI
 * @version 1.0.1 - 21/03/2020
 */
@Controller
@RequestMapping("/Microblog/Posts")
public class CommentController {

    @Autowired
    ICommentRepo repo;

    @Autowired
    IPostRepo postRepo;

    @Autowired
    IUserRepo utenteRepo;

    /**
     * 
     * Return the addComment page
     * 
     * @param postId
     * @param model
     * @param session
     * @return String
     */
    @RequestMapping("AddComment/{postId}")
    public String addCommentPage(@PathVariable(value = "postId") long postId, Model model,
            HttpSession session) {

        if (session.getAttribute("username") != null) {

            Optional<Post> optional = postRepo.findById(postId);
            Post post = optional.get();

            model.addAttribute("post", post);
            model.addAttribute("repo", repo);

            return "html/addComment.html";
        }

        return "redirect:/Microblog/Posts/CommentError";
    }

    
    /**
     * 
     * Save the comment and return the page with the post list
     * 
     * @param postId
     * @param c
     * @param session
     * @return String
     */
    @RequestMapping("AddComment/SaveComment/{postId}")
    public String saveComment(@PathVariable(value = "postId") long postId, Comment c, HttpSession session) {
        
        c.setDateHour(new Date());

        Optional<Post> optional = postRepo.findById(postId);
        c.setPost(optional.get());

        User utente = utenteRepo.findByUsername((String) session.getAttribute("username"));
        c.setUser(utente);

        repo.save(c);

        return "redirect:/Microblog/Posts";
    }

    /**
     * 
     * Return the errorComment page
     * 
     * @return String
     */
    @RequestMapping("errorComment") 
    public String errorCommento(){

        return "html/errorComment.html";
    }
}