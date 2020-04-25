package it.marconivr.microblog.rest.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.marconivr.microblog.entities.User;
import it.marconivr.microblog.repos.IUserRepo;
import it.marconivr.microblog.util.JsonResponseBody;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * 
 * User Rest Controller
 * 
 * @author Alessio Trentin
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

        User u = repo.findByUsername(username);

        if (u != null) {

            u.add(linkTo(methodOn(UserRestController.class).getUser(username)).withSelfRel());
            u.add(linkTo(methodOn(UserRestController.class).getUsers()).withRel("users"));
            u.add(linkTo(methodOn(PostRestController.class).getPostByUser(username)).withRel("user's posts"));
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
    @RequestMapping(method = POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@ApiParam(value = "The user that will be created") User user, HttpServletRequest request) {

        if (user == null) {

            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        } else {

            repo.save(user);
            
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .header("location", request.getRequestURL().toString() + "/"+ user.getUsername())
                    .body(new JsonResponseBody(HttpStatus.CREATED.value(),null));
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
     @RequestMapping(method = PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
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