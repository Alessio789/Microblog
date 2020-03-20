package it.marconivr.microblog.controllers;

import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import it.marconivr.microblog.entities.Commento;
import it.marconivr.microblog.entities.Post;
import it.marconivr.microblog.entities.Utente;
import it.marconivr.microblog.repos.ICommentoRepo;
import it.marconivr.microblog.repos.IPostRepo;
import it.marconivr.microblog.repos.IUtenteRepo;

/**
 * 
 * Commento Controller
 * 
 * @author Alessio Trentin - 5^EI
 * @version 1.0.0 - 19/03/2020
 */
@Controller
public class CommentoController {

    @Autowired
    ICommentoRepo repo;

    @Autowired
    IPostRepo postRepo;

    @Autowired
    IUtenteRepo utenteRepo;

    @RequestMapping("/Microblog/AggiungiCommento/{postId}")
    public String paginaAggiungiCommento(@PathVariable(value = "postId") long postId, Model model,
            HttpSession session) {

        if (session.getAttribute("username") != null) {

            Optional<Post> optional = postRepo.findById(postId);
            Post post = optional.get();

            model.addAttribute("post", post);
            model.addAttribute("repo", repo);

            return "html/addComment.html";
        }

        return "redirect:/Microblog/ErroreCommento";
    }

    @RequestMapping("/Microblog/AggiungiCommento/SalvaCommento/{postId}")
    public String salvaCommento(@PathVariable(value = "postId") long postId, Commento c, HttpSession session) {
        
        c.setDataOra(new Date());

        Optional<Post> optional = postRepo.findById(postId);
        c.setPost(optional.get());

        Utente utente = utenteRepo.findByUsername((String) session.getAttribute("username"));
        c.setUtente(utente);

        repo.save(c);

        return "redirect:/Microblog/ListaPost";
    }

    @RequestMapping("/Microblog/ErroreCommento") 
    public String erroreCommento(){

        return "html/errorComment.html";
    }
}