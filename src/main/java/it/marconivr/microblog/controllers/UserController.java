package it.marconivr.microblog.controllers;

import com.google.common.base.Charsets;
import com.google.common.hash.*;

import it.marconivr.microblog.SaltGenerator;
import it.marconivr.microblog.entities.User;
import it.marconivr.microblog.repos.IUserRepo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 
 * User Controller
 * 
 * @author Alessio Trentin - 5^EI 
 * @version 2.2.1 - 21/03/2020
 */
@Controller
@RequestMapping("/Microblog/Users")
public class UserController {

    @Autowired
    IUserRepo repo;

    /**
     * 
     * Return the successful registration page
     * 
     * @param u
     * @param model
     * @param request
     * @return String
     */
    @RequestMapping(value = "AddUser", method = RequestMethod.POST)
    public String addUser(User u, Model model, HttpServletRequest request) {

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
     * Return the successful login page
     * 
     * @param username
     * @param password
     * @param model
     * @param request
     * @return String
     */
    @RequestMapping(value = "LoginSuccessful", method=RequestMethod.POST)
    public String loginSuccessful(String username, String password, Model model, HttpServletRequest request) {

        User u = repo.findByUsername(username);

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

        return "redirect:/Microblog/Users/LoginFailed";
    }
    
    /**
     * 
     * If an error occurs in the login, return the error page
     * 
     * @return String
     */
    @RequestMapping("LoginFailed")
    public String loginFailed() {
        
        return "html/loginError.html";
    }
}