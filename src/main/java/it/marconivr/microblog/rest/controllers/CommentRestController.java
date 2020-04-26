package it.marconivr.microblog.rest.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.marconivr.microblog.entities.Comment;
import it.marconivr.microblog.repos.ICommentRepo;
import it.marconivr.microblog.repos.IPostRepo;
import it.marconivr.microblog.repos.IUserRepo;
import it.marconivr.microblog.util.JsonResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * Comment Rest Controller
 *
 * @author Alessio Trentin
 */
@Api("CRUD operations on comments")
@RequestMapping("Microblog/rest/comments")
@RestController
public class CommentRestController {

    @Autowired
    ICommentRepo repo;

    @Autowired
    IUserRepo userRepo;

    @Autowired
    IPostRepo postRepo;

    /**
     * Return the list of all the comments
     *
     * @return List<Comment> - commentList
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ApiOperation("Return the list of all the comments")
    @GetMapping
    public ResponseEntity<List<Comment>> getComments() {

        List<Comment> commentList = (List<Comment>) repo.findAll();

        for (Comment c : commentList) {

            c.add(linkTo(methodOn(CommentRestController.class).getComment(c.getId())).withSelfRel());
            c.add(linkTo(methodOn(CommentRestController.class).getComments()).withRel("comments"));
            c.add(linkTo(methodOn(UserRestController.class).getUser(c.getUser().getUsername())).withRel("user"));
            c.add(linkTo(methodOn(PostRestController.class).getPost(c.getPost().getId())).withRel("post"));
        }

        return new ResponseEntity<List<Comment>>(commentList, HttpStatus.OK);
    }

    /**
     * Return the comment with the given ID
     *
     * @param id
     * @return ResponseEntity
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ApiOperation("Return the comment with the given ID")
    @GetMapping(value = "{id}")
    public ResponseEntity<Comment> getComment(
            @ApiParam(value = "The id of the comment that will be returned") @PathVariable("id") long id) {

        Optional<Comment> op = repo.findById(id);

        if (op.isPresent()) {

            Comment c = op.get();

            c.add(linkTo(methodOn(CommentRestController.class).getComment(id)).withSelfRel());
            c.add(linkTo(methodOn(CommentRestController.class).getComments()).withRel("comments"));
            c.add(linkTo(methodOn(UserRestController.class).getUser(c.getUser().getUsername())).withRel("user"));
            c.add(linkTo(methodOn(PostRestController.class).getPost(c.getPost().getId())).withRel("post"));

            return new ResponseEntity<Comment>(c, HttpStatus.OK);

        } else {

            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Create a new comment
     *
     * @param comment
     * @return ResponseEntity
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ApiOperation("Create a new comment")
    @RequestMapping(method = POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createComment(@ApiParam(value = "The comment that will be created") @RequestBody Comment comment, HttpServletRequest request) {

        if (comment == null) {

            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        } else {
            Comment c = new Comment();
            c.setBody(comment.getBody());
            c.setDateHour(new Date());
            c.setUser(userRepo.findByUsername(comment.getUser().getUsername()));
            c.setPost(postRepo.findById(comment.getPost().getId()).get());

            repo.save(c);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .header("location", request.getRequestURL().toString() + "/" + c.getId())
                    .body(new JsonResponseBody(HttpStatus.CREATED.value(), null));
        }
    }

    /**
     * Replaces the comment having the same id as the given comment, with the given comment
     *
     * @param comment
     * @return ResponseEntity
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ApiOperation("Replaces the comment having the same id as the given comment, with the given comment")
    @RequestMapping(method = PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
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
     * Delete the comment with the given id
     *
     * @param id
     * @return ResponseEntity
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ApiOperation("Delete the comment with the given id")
    @DeleteMapping(value = "{id}")
    public ResponseEntity deleteComment(@ApiParam(value = "The id of the comment that will be deleted") @PathVariable("id") long id) {

        if (repo.findById(id) == null) {

            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } else if (repo.findById(id) != null) {

            repo.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);

        } else {

            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }
}