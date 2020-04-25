package it.marconivr.microblog.controllers;

import it.marconivr.microblog.entities.Comment;
import it.marconivr.microblog.entities.Post;
import it.marconivr.microblog.entities.User;
import it.marconivr.microblog.repos.ICommentRepo;
import it.marconivr.microblog.repos.IPostRepo;
import it.marconivr.microblog.repos.IUserRepo;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * Comment Controller
 *
 * @author Alessio Trentin
 */
@Controller
@RequestMapping("/Microblog/Posts")
public class CommentController {

    @Autowired
    ICommentRepo repo;

    @Autowired
    IPostRepo postRepo;

    @Autowired
    IUserRepo userRepo;

    /**
     * Return the addComment page
     *
     * @param postId
     * @param model
     * @return String
     */
    @RequestMapping("AddComment/{postId}")
    public String addCommentPage(@PathVariable(value = "postId") long postId, Model model) {


        Optional<Post> optional = postRepo.findById(postId);
        Post post = optional.get();

        model.addAttribute("post", post);
        model.addAttribute("repo", repo);

        return "html/addComment.html";

    }


    /**
     * Save the comment and return the page with the post list
     *
     * @param postId
     * @param c
     * @return String
     */
    @RequestMapping("AddComment/SaveComment/{postId}")
    public String saveComment(@PathVariable(value = "postId") long postId, Comment c) {

        c.setDateHour(new Date());

        Optional<Post> optional = postRepo.findById(postId);
        c.setPost(optional.get());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();


        User user = userRepo.findByUsername(username);
        c.setUser(user);

        repo.save(c);

        return "redirect:/Microblog/Posts";
    }
}