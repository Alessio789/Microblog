package it.marconivr.microblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Home Controller
 *
 * @author Alessio Trentin
 */
@Controller
public class HomeController {

    /**
     * Redirect to the microblog home page
     *
     * @return String
     */
    @RequestMapping("/")
    public String toMicroblog() {

        return "redirect:/Microblog";
    }

    /**
     * Return the home page of the microblog
     *
     * @return String
     */
    @RequestMapping("/Microblog")
    public String home() {

        return "index.html";
    }

    /**
     * Return the login page
     *
     * @return String
     */
    @RequestMapping(value = "/Microblog/Login", method = RequestMethod.GET)
    public String loginPage() {

        return "html/login.html";
    }

    /**
     * Return the registration page
     *
     * @return String
     */
    @RequestMapping(value = "/Microblog/Registration", method = RequestMethod.GET)
    public String registrationPage() {

        return "html/registration.html";
    }

    /**
     * Redirect to the home page
     *
     * @return String
     */
    @RequestMapping(value = "/Microblog/Home")
    public String success() {

        return "redirect:/Microblog";
    }

    /**
     * Return the login page
     *
     * @return HTML page - login page
     */
    @GetMapping("/login")
    public String login() {
        return "html/login.html";
    }

    /**
     * Login form with error
     *
     * @return HTML page - login page
     */
    @RequestMapping("/login-error.html")
    public String loginError(Model model) {

        model.addAttribute("loginError", true);
        return "html/login.html";
    }
}
