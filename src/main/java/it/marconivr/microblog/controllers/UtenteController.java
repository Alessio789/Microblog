package it.marconivr.microblog.controllers;

import it.marconivr.microblog.SaltGenerator;
import it.marconivr.microblog.entities.Utente;
import it.marconivr.microblog.repos.IUtenteRepo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import com.google.common.base.Charsets;
import com.google.common.hash.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 
 * Controller Utente
 * 
 * @author Alessio Trentin - 5^EI 
 * @version 1.0.0 - 15/03/2020
 */
@Controller
public class UtenteController {

    @Autowired
    IUtenteRepo repo;

    /**
     * 
     * Restituisce la pagina di registrazione avvenuta
     * 
     * @param u
     * @param model
     * @return String
     */
    @RequestMapping(value = "/Microblog/Benvenuto", method = RequestMethod.POST)
    public String nuovaRegistrazione(Utente u, Model model, HttpServletRequest request) {

        String salt = DatatypeConverter.printBase64Binary(SaltGenerator.getNextSalt());
        u.setSalt(salt);

        String passwordSalt = u.getPassword()+ salt;

        Hasher hasher = Hashing.sha256().newHasher();
        hasher.putString(passwordSalt, Charsets.UTF_8);
        String sha256 = hasher.hash().toString();

        u.setPassword(sha256);

        HttpSession session = request.getSession();
        session.setAttribute("username", u.getUsername());
        model.addAttribute("username", u.getUsername());

        repo.save(u);

        return "redirect:/Microblog/Home";
    }

    /**
     * 
     * Restituisce la pagina di accesso avvenuto
     * 
     * @param username
     * @param password
     * @param model
     * @return String
     */
    @RequestMapping(value = "/Microblog/AccessoRiuscito", method=RequestMethod.POST)
    public String accesso(String username, String password, Model model, HttpServletRequest request) {

        Utente u = repo.findByUsername(username);

        String salt = u.getSalt();
        String passwordSalt = password + salt;

        String passwordHash = u.getPassword();

        Hasher hasher = Hashing.sha256().newHasher();
        hasher.putString(passwordSalt, Charsets.UTF_8);
        String sha256 = hasher.hash().toString();
        
        if(sha256.equals(passwordHash)) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            model.addAttribute("username", username);
            return "redirect:/Microblog/Home";
        }

        return "redirect:/Microblog/AccessoFallito";
    }
    
    @RequestMapping("/Microblog/AccessoFallito")
    public String accessoFallito() {
        return "html/errorLogin.html";
    }
}