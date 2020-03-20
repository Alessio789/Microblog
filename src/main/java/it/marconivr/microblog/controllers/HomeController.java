package it.marconivr.microblog.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * Home Controller
 * 
 * @author Alessio Trentin - 5^EI
 * @version 1.0.0 - 15/03/2020
 */
@Controller
public class HomeController {

    /**
     * 
     * Reindirizza alla home page del microblog
     * 
     * @return String
     */
    @RequestMapping("/")
    public String toMicroblog() {
        return "redirect:/Microblog";
    }

    /**
     * 
     * Restituisce la home page del microblog
     * 
     * @return String
     */
    @RequestMapping("/Microblog") 
    public String home() {
        return "index.html";
    }

    /**
     * 
     * Restituisce la pagina di accesso
     * 
     * @return String
     */
    @RequestMapping(value = "/Microblog/Accesso", method = RequestMethod.GET)
    public String paginaAccesso() {
        return "html/login.html";
    }
        
    /**
     * 
     * Restituisce la pagina di registrazione
     * 
     * @return String
     */
    @RequestMapping(value = "/Microblog/Registrazione", method = RequestMethod.GET)
    public String paginaRegistrazione() {
        return "html/registration.html";
    }

    /**
     * 
     * Se l'utente ha eseguito l'accesso ritorna la pagina di accesso avvenuto,
     * altrimenti ritorna l'home page
     * 
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "/Microblog/Home") 
    public String success(HttpSession session, Model model) {

        if (session.getAttribute("username") != null) {
            
            model.addAttribute("username", session.getAttribute("username"));

            return "html/success.html";
        }

        return "redirect:/Microblog";
    }

    @RequestMapping(value="/Microblog/Logout", method=RequestMethod.GET)
    public String requestMethodName(HttpSession session) {
        session.invalidate();
        return "redirect:/Microblog";
    }
    
}
