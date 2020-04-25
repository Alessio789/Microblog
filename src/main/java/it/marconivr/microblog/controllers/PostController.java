package it.marconivr.microblog.controllers;

import it.marconivr.microblog.entities.Post;
import it.marconivr.microblog.entities.User;
import it.marconivr.microblog.repos.ICommentRepo;
import it.marconivr.microblog.repos.IPostRepo;
import it.marconivr.microblog.repos.IUserRepo;

import java.util.Date;

import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 *
 * Post Controller
 *
 * @author Alessio Trentin
 */
@Controller
@RequestMapping("/Microblog/Posts")
public class PostController {

    @Autowired
    IPostRepo repo;

    @Autowired
    ICommentRepo commentRepo;

    @Autowired
    IUserRepo userRepo;

    /**
     * Return the post addition page
     *
     * @return String
     */
    @RequestMapping(value = "AddPost", method = RequestMethod.GET)
    public String addPostPage() {

        return "html/addPost.html";
    }

    /**
     * Save the post and redirect the page to view the post list
     *
     * @param p
     * @return String
     */
    @RequestMapping(value = "PostAdded", method = RequestMethod.POST)
    public String addPost(Post p) {

        p.setDateHour(new Date());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User u = userRepo.findByUsername(username);
        p.setUser(u);

        repo.save(p);

        return "redirect:/Microblog/Posts";
    }

    /**
     * Return the page with the list of posts
     *
     * @param model
     * @return String
     */
    @RequestMapping
    public String posts(Model model) {

        model.addAttribute("postList", repo.findAll());
        model.addAttribute("commentRepo", commentRepo);

        return "html/posts.html";
    }
}
