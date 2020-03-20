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
import it.marconivr.microblog.entities.Post;
import it.marconivr.microblog.repos.IPostRepo;

/**
 * 
 * Post Rest Controller
 * 
 * @author Alessio Trentin - 5^EI
 * @version 1.0.0 - 20/03/2020
 */
@Api("CRUD operations on Posts")
@RequestMapping("Microblog/rest/posts")
@RestController
public class PostRestController {

    @Autowired
    IPostRepo repo;

    /**
     * 
     * Return the list of all the post
     * 
     * @return List<Post>
     */
    @ApiOperation("Return the list of all the post")
    @GetMapping
    public ResponseEntity<List<Post>> getPosts() {
        return new ResponseEntity<List<Post>>((List<Post>) repo.findAll(), HttpStatus.OK);
    }

    /**
     * 
     * Return the post with the given ID
     * 
     * @param id
     * @return ResponseEntity
     */
    @ApiOperation("Return the post with the given ID")
    @GetMapping(value = "{id}")
    public ResponseEntity<Optional<Post>> getPost(
            @ApiParam(value = "The id of the post that will be returned") @PathVariable("id") long id) {
        if (repo.findById(id) != null) {

            return new ResponseEntity<Optional<Post>>(repo.findById(id), HttpStatus.OK);

        } else {

            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * 
     * Create a new post
     * 
     * @param post
     * @return ResponseEntity
     */
    @ApiOperation("Create a new post")
    @PostMapping
    public ResponseEntity createPost(@ApiParam(value = "The post that will be creted") Post post) {

        if (post == null) {

            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        } else {

            repo.save(post);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    /**
     * 
     * Replaces the post having the same id as the given post, with the given post
     * 
     * @param post
     * @return ResponseEntity
     */
    @ApiOperation("Replaces the post having the same id as the given post, with the given post")
    @PutMapping
    public ResponseEntity updatePost(@ApiParam(value = "The updated post") @RequestBody Post post) {

        if (repo.findById(post.getId()) == null) {

            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        } else if (repo.findById(post.getId()) != null) {
            repo.save(post);
            return new ResponseEntity(HttpStatus.OK);

        } else {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    /**
     * 
     * Delete the post with the given id
     * 
     * @param id
     * @return ResponseEntity
     */
    @ApiOperation("Delete the post with the given id")
    @DeleteMapping(value = "{id}")
    public ResponseEntity deletePost(@ApiParam(value = "The id of the post that will be deleted") @PathVariable("id") long id) {

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