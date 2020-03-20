package it.marconivr.microblog.rest.controllers;

import java.util.List;
import java.util.Optional;

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
import it.marconivr.microblog.entities.Commento;
import it.marconivr.microblog.repos.ICommentoRepo;

/**
 * 
 * Comment Rest Controller 
 * 
 * @author Alessio Trentin - 5^EI
 * @version 1.0.0 - 20/03/2020
 */
@Api("CRUD operations on comments")
@RequestMapping("Microblog/rest/comments")
@RestController
public class CommentRestController {

    @Autowired
    ICommentoRepo repo;

    /**
     * 
     * Return the list of all the comments
     * 
     * @return List<Commento>
     */
    @ApiOperation("Return the list of all the comments")
    @GetMapping
    public ResponseEntity<List<Commento>> getComments() {
        return new ResponseEntity<List<Commento>>((List<Commento>) repo.findAll(), HttpStatus.OK);
    }

    /**
     * 
     * Return the comment with the given ID
     * 
     * @param id
     * @return ResponseEntity
     */
    @ApiOperation("Return the comment with the given ID")
    @GetMapping(value = "{id}")
    public ResponseEntity<Optional<Commento>> getCommento(
            @ApiParam(value = "The id of the commento that will be returned") @PathVariable("id") long id) {
        if (repo.findById(id) != null) {

            return new ResponseEntity<Optional<Commento>>(repo.findById(id), HttpStatus.OK);

        } else {

            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 
     * Create a new comment
     * 
     * @param commento
     * @return ResponseEntity
     */
    @ApiOperation("Create a new comment")
    @PostMapping
    public ResponseEntity createCommento(@ApiParam(value = "The comment that will be creted") Commento commento) {

        if (commento == null) {

            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        } else {

            repo.save(commento);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    /**
     * 
     * Replaces the comment having the same id as the given comment, with the given comment
     * 
     * @param commento
     * @return ResponseEntity
     */
    @ApiOperation("Replaces the comment having the same id as the given comment, with the given comment")
    @PutMapping
    public ResponseEntity updateCommento(@ApiParam(value = "The updated comment") @RequestBody Commento commento) {

        if (repo.findById(commento.getId()) == null) {

            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        } else if (repo.findById(commento.getId()) != null) {
            repo.save(commento);
            return new ResponseEntity(HttpStatus.OK);

        } else {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    /**
     * 
     * Delete the comment with the given id
     * 
     * @param id
     * @return ResponseEntity
     */
    @ApiOperation("Delete the comment with the given id")
    @DeleteMapping(value = "{id}")
    public ResponseEntity deleteCommento(@ApiParam(value = "The id of the comment that will be deleted") @PathVariable("id") long id) {

        if (repo.findById(id) == null) {

            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        else if (repo.findById(id) != null) {

            repo.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);

        } else {

            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }
}