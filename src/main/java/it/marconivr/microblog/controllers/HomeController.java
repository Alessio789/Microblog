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
 * @version 1.0.1 - 15/03/2020
 */
@Controller
public class HomeController {

    /**
     * 
     * Redirect to the microblog home page
     * 
     * @return String
     */
    @RequestMapping("/")
    public String toMicroblog() {
        
        return "redirect:/Microblog";
    }

    /**
     * 
     * Return the home page of the microblog
     * 
     * @return String
     */
    @RequestMapping("/Microblog") 
    public String home() {
        
        return "index.html";
    }

    /**
     * 
     * Return the login page
     * 
     * @return String
     */
    @RequestMapping(value = "/Microblog/Login", method = RequestMethod.GET)
    public String loginPage() {
        
        return "html/login.html";
    }
        
    /**
     * 
     * Return the registration page
     * 
     * @return String
     */
    @RequestMapping(value = "/Microblog/Registration", method = RequestMethod.GET)
    public String registrationPage() {
        
        return "html/registration.html";
    }

    /**
     * 
     * If the user is logged in, return the login page, otherwise return 
     * the home page
     * 
     * @param session
     * @param model
     * @return String
     */
    @RequestMapping(value = "/Microblog/Home") 
    public String success(HttpSession session, Model model) {

        if (session.getAttribute("username") != null) {
            
            model.addAttribute("username", session.getAttribute("username"));

            return "html/success.html";
        }

        return "redirect:/Microblog";
    }

    /**
     * 
     * Log out and return to the home page
     * 
     * @param session
     * @return String
     */
    @RequestMapping(value="/Microblog/Logout", method=RequestMethod.GET)
    public String logout(HttpSession session) {
        
        session.invalidate();
        
        return "redirect:/Microblog";
    }
    
}
