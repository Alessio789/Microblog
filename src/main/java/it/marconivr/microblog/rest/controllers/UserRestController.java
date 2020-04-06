package it.marconivr.microblog.rest.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.marconivr.microblog.entities.User;
import it.marconivr.microblog.repos.IUserRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 
 * User Rest Controller
 * 
 * @author Alessio Trentin - 5^EI
 * @version 1.0.1 - 21/03/2020
 */
@Api("CRUD operations on users")
@RequestMapping("Microblog/rest/users")
@RestController
public class UserRestController {

    @Autowired
    IUserRepo repo;

    /**
     * 
     * Return the list of all the Utente
     * 
     * @return List<User>
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ApiOperation("Return the list of all the users")
    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<List<User>>((List<User>) repo.findAll(), HttpStatus.OK);
    }

    /**
     * 
     * Return the user with the given username
     * 
     * @param username
     * @return ResponseEntity
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ApiOperation("Return the user with the given username")
    @GetMapping(value = "{username}")
    public ResponseEntity<User> getUser(
            @ApiParam(value = "The username of the user that will be returned") @PathVariable("username") String username) {
        if (repo.findByUsername(username) != null) {

            return new ResponseEntity<User>(repo.findByUsername(username), HttpStatus.OK);

        } else {

            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * 
     * Create a new user
     * 
     * @param user
     * @return ResponseEntity
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ApiOperation("Create a new user")
    @PostMapping
    public ResponseEntity createUser(@ApiParam(value = "The user that will be creted") User user) {

        if (user == null) {

            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        } else {

            repo.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    /**
     * 
     * Replaces the user having the same username as the given user, with the given user
     * 
     * @param user
     * @return ResponseEntity
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ApiOperation("Replaces the user having the same username as the given user, with the given user")
    @PutMapping
    public ResponseEntity updateUser(@ApiParam(value = "The updated user") @RequestBody User user) {

        if (repo.findByUsername(user.getUsername()) == null) {

            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        } else if (repo.findById(user.getUsername()) != null) {

            repo.save(user);
            return new ResponseEntity(HttpStatus.OK);

        } else {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    /**
     * 
     * Delete the user with the given username
     * 
     * @param username
     * @return ResponseEntity
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ApiOperation("Delete the user with the given username")
    @DeleteMapping(value = "{username}")
    public ResponseEntity deleteUser(@ApiParam(value = "The username of the user that will be deleted") @PathVariable("username") String username) {

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