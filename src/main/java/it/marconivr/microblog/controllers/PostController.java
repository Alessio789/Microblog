package it.marconivr.microblog.controllers;

import it.marconivr.microblog.entities.Post;
import it.marconivr.microblog.entities.Utente;
import it.marconivr.microblog.repos.ICommentoRepo;
import it.marconivr.microblog.repos.IPostRepo;
import it.marconivr.microblog.repos.IUtenteRepo;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {

    @Autowired
    IPostRepo repo;

    @Autowired
    ICommentoRepo commentoRepo;

    @Autowired
    IUtenteRepo utenteRepo;

    @RequestMapping(value = "/Microblog/ListaPost/AggiungiPost", method = RequestMethod.GET)
    public String paginaAggiungiPost(HttpSession session) {

        if (session.getAttribute("username") != null) {
            Utente u = utenteRepo.findByUsername((String) session.getAttribute("username"));
            if (u.getRuolo().equals("ADMIN")) {
                return "html/addPost.html";
            }
        }
        return "redirect:/Microblog/ErrorPost";
    }

    @RequestMapping(value = "/Microblog/ListaPost/PostAggiunto", method = RequestMethod.POST)
    public String aggiungiPost(Post p, HttpSession session) {

        p.setDataOra(new Date());
        p.setUtente(utenteRepo.findByUsername((String) session.getAttribute("username")));

        repo.save(p);

        return "redirect:/Microblog/ListaPost";
    }

    @RequestMapping("/Microblog/ListaPost")
    public String paginaPost(Model model) {
        model.addAttribute("listaPost", repo.findAll());
        model.addAttribute("commentoRepo", commentoRepo);
        return "html/listaPost.html";
    }

    @RequestMapping("/Microblog/ErrorPost")
    public String errorPost() {
        return "html/errorPost.html";
    }
}
