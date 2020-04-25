package it.marconivr.microblog.controllers;

import it.marconivr.microblog.entities.User;
import it.marconivr.microblog.repos.IUserRepo;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
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
     * Return the successful registration page
     *
     * @param u
     * @param model
     * @param request
     * @return String
     */
    @RequestMapping(value = "AddUser", method = RequestMethod.POST)
    public String addUser(User u, Model model, HttpServletRequest request) {

        u.setRoles("USER");

        String password = u.getPassword();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        u.setPassword(passwordEncoder.encode(password));

        model.addAttribute("username", u.getUsername());

        repo.save(u);

        return "redirect:/Microblog/Home";
    }
}