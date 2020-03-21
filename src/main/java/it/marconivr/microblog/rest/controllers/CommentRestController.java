package it.marconivr.microblog.rest.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import it.marconivr.microblog.entities.Comment;
import it.marconivr.microblog.repos.ICommentRepo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 
 * Comment Rest Controller 
 * 
 * @author Alessio Trentin - 5^EI
 * @version 1.0.1 - 21/03/2020
 */
@Api("CRUD operations on comments")
@RequestMapping("Microblog/rest/comments")
@RestController
public class CommentRestController {

    @Autowired
    ICommentRepo repo;

    /**
     * 
     * Return the list of all the comments
     * 
     * @return List<Commento>
     */
    @ApiOperation("Return the list of all the comments")
    @GetMapping
    public ResponseEntity<List<Comment>> getComments() {
        return new ResponseEntity<List<Comment>>((List<Comment>) repo.findAll(), HttpStatus.OK);
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
    public ResponseEntity<Optional<Comment>> getComment(
            @ApiParam(value = "The id of the comment that will be returned") @PathVariable("id") long id) {
        if (repo.findById(id) != null) {

            return new ResponseEntity<Optional<Comment>>(repo.findById(id), HttpStatus.OK);

        } else {

            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 
     * Create a new comment
     * 
     * @param comment
     * @return ResponseEntity
     */
    @ApiOperation("Create a new comment")
    @PostMapping
    public ResponseEntity createComment(@ApiParam(value = "The comment that will be creted") Comment comment) {

        if (comment == null) {

            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        } else {

            repo.save(comment);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    /**
     * 
     * Replaces the comment having the same id as the given comment, with the given comment
     * 
     * @param comment
     * @return ResponseEntity
     */
    @ApiOperation("Replaces the comment having the same id as the given comment, with the given comment")
    @PutMapping
    public ResponseEntity updateComment(@ApiParam(value = "The updated comment") @RequestBody Comment comment) {

        if (repo.findById(comment.getId()) == null) {

            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        } else if (repo.findById(comment.getId()) != null) {
            repo.save(comment);
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
    public ResponseEntity deleteComment(@ApiParam(value = "The id of the comment that will be deleted") @PathVariable("id") long id) {

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