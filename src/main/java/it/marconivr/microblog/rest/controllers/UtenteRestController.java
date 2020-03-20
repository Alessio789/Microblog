package it.marconivr.microblog.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.marconivr.microblog.entities.Utente;
import it.marconivr.microblog.repos.IUtenteRepo;

/**
 * 
 * Utente Rest Controller
 * 
 * @author Alessio Trentin - 5^EI
 * @version 1.0.0 - 20/03/2020
 */
@Api("CRUD operations on users")
@RequestMapping("Microblog/rest/users")
@RestController
public class UtenteRestController {

    @Autowired
    IUtenteRepo repo;

    /**
     * 
     * Return the list of all the Utente
     * 
     * @return List<Utente>
     */
    @ApiOperation("Return the list of all the users")
    @GetMapping
    public ResponseEntity<List<Utente>> getUsers() {
        return new ResponseEntity<List<Utente>>((List<Utente>) repo.findAll(), HttpStatus.OK);
    }

    /**
     * 
     * Return the user with the given username
     * 
     * @param username
     * @return ResponseEntity
     */
    @ApiOperation("Return the user with the given username")
    @GetMapping(value = "{username}")
    public ResponseEntity<Utente> getUtente(
            @ApiParam(value = "The username of the user that will be returned") @PathVariable("username") String username) {
        if (repo.findByUsername(username) != null) {

            return new ResponseEntity<Utente>(repo.findByUsername(username), HttpStatus.OK);

        } else {

            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 
     * Create a new user
     * 
     * @param utente
     * @return ResponseEntity
     */
    @ApiOperation("Create a new user")
    @PostMapping
    public ResponseEntity createUtente(@ApiParam(value = "The user that will be creted") Utente utente) {

        if (utente == null) {

            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        } else {

            repo.save(utente);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    /**
     * 
     * Replaces the user having the same username as the given user, with the given user
     * 
     * @param utente
     * @return ResponseEntity
     */
    @ApiOperation("Replaces the Utente having the same username as the given user, with the given user")
    @PutMapping
    public ResponseEntity updateUtente(@ApiParam(value = "The updated Utente") @RequestBody Utente utente) {

        if (repo.findByUsername(utente.getUsername()) == null) {

            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        } else if (repo.findById(utente.getUsername()) != null) {

            repo.save(utente);
            return new ResponseEntity(HttpStatus.OK);

        } else {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    /**
     * 
     * Delete the Utente with the given username
     * 
     * @param id
     * @return ResponseEntity
     */
    @ApiOperation("Delete the Utente with the given username")
    @DeleteMapping(value = "{username}")
    public ResponseEntity deleteUtente(@ApiParam(value = "The username of the user that will be deleted") @PathVariable("username") String username) {

        if (repo.findByUsername(username) == null) {

            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        else if (repo.findByUsername(username) != null) {

            repo.deleteById(username);
            return new ResponseEntity(HttpStatus.OK);

        } else {

            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }
}