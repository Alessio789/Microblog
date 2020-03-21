package it.marconivr.microblog.controllers;

import it.marconivr.microblog.entities.Post;
import it.marconivr.microblog.entities.User;
import it.marconivr.microblog.repos.ICommentRepo;
import it.marconivr.microblog.repos.IPostRepo;
import it.marconivr.microblog.repos.IUserRepo;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 
 * Post Controller
 * 
 * @author Alessio Trentin - 5^EI
 * @version 1.0.1 - 21/03/2020
 */
@Controller
@RequestMapping("/Microblog/Posts")
public class PostController {

    @Autowired
    IPostRepo repo;

    @Autowired
    ICommentRepo commentoRepo;

    @Autowired
    IUserRepo utenteRepo;

    /**
     * 
     * Return the post addition page
     * 
     * @param session
     * @return String
     */
    @RequestMapping(value = "AddPost", method = RequestMethod.GET)
    public String addPostPage(HttpSession session) {

   
        if (session.getAttribute("username") != null) {
            
            User u = utenteRepo.findByUsername((String) session.getAttribute("username"));
            
            
            if (u.getRole().equals("ADMIN")) {
                
                return "html/addPost.html";
            }
        }
        
        return "redirect:/Microblog/Posts/ErrorPost";
    }

    /**
     * 
     * Save the post and redirect the page to view the post list
     * 
     * @param p
     * @param session
     * @return String
     */
    @RequestMapping(value = "PostAdded", method = RequestMethod.POST)
    public String addPost(Post p, HttpSession session) {

        p.setDateHour(new Date());
        p.setUser(utenteRepo.findByUsername((String) session.getAttribute("username")));

        repo.save(p);

        return "redirect:/Microblog/Posts";
    }

    /**
     * 
     * Return the page with the list of posts
     * 
     * @param model
     * @return String
     */
    @RequestMapping
    public String posts(Model model) {
        
        model.addAttribute("postList", repo.findAll());
        model.addAttribute("commentRepo", commentoRepo);
        
        return "html/posts.html";
    }

    /**
     * 
     * Return the page of errorPost
     * 
     * @return 
     */
    @RequestMapping("PostError")
    public String errorPost() {
        
        return "html/postError.html";
    }
}
