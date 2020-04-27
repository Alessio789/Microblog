package it.marconivr.microblog.rest.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.marconivr.microblog.entities.Post;
import it.marconivr.microblog.entities.User;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

/**
 * Post Rest Controller
 *
 * @author Alessio Trentin
 */
@Api("CRUD operations on Posts")
@RequestMapping("Microblog/rest/posts")
@RestController
public class PostRestController {

    @Autowired
    IPostRepo repo;

    @Autowired
    IUserRepo userRepo;

    /**
     * Return the list of all the post
     *
     * @return List<Post>
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ApiOperation("Return the list of all the post")
    @GetMapping
    public ResponseEntity<List<Post>> getPosts() {

        List<Post> postList = (List<Post>) repo.findAll();

        for (Post p : postList) {

            p.add(linkTo(methodOn(PostRestController.class).getPost(p.getId())).withSelfRel());
            p.add(linkTo(methodOn(PostRestController.class).getPosts()).withRel("posts"));
            p.add(linkTo(methodOn(UserRestController.class).getUser((p.getUser().getUsername()))).withRel("user"));
        }

        return new ResponseEntity<List<Post>>(postList, HttpStatus.OK);
    }

    /**
     * Return the post with the given ID
     *
     * @param id
     * @return ResponseEntity
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ApiOperation("Return the post with the given ID")
    @GetMapping(value = "{id}")
    public ResponseEntity<Post> getPost(
            @ApiParam(value = "The id of the post that will be returned") @PathVariable("id") long id) {

        Optional<Post> op = repo.findById(id);

        if (op.isPresent()) {

            Post p = op.get();

            p.add(linkTo(methodOn(PostRestController.class).getPost(id)).withSelfRel());
            p.add(linkTo(methodOn(PostRestController.class).getPosts()).withRel("posts"));
            p.add(linkTo(methodOn(UserRestController.class).getUser(p.getUser().getUsername())).withRel("user"));

            return new ResponseEntity<Post>(p, HttpStatus.OK);

        } else {

            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ApiOperation("Returns the posts of the user having the username passed as a parameter")
    @GetMapping(value = "user/{username}")
    public ResponseEntity getPostsByUser(@ApiParam(value = "The username of the user to search for posts") @PathVariable("username") String username) {

        User u = userRepo.findByUsername(username);

        if (u != null) {

            List<Post> postList = repo.findByUser(u);
            return new ResponseEntity<List<Post>>(postList, HttpStatus.OK);

        } else {

            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Create a new post
     *
     * @param post
     * @param auth
     * @return ResponseEntity
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ApiOperation("Create a new post")
    @RequestMapping(method = POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createPost(@ApiParam(value = "The post that will be created") @RequestBody Post post, HttpServletRequest request, UsernamePasswordAuthenticationToken auth) {

        if (post == null) {

            return new ResponseEntity(HttpStatus.BAD_REQUEST);

        } else {

            Post p = new Post();
            p.setBody(post.getBody());
            p.setTitle(post.getTitle());
            p.setDateHour(new Date());
            p.setUser(userRepo.findByUsername(post.getUser().getUsername()));

            repo.save(p);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .header("location", request.getRequestURL().toString() + "/" + p.getId())
                    .body(new JsonResponseBody(HttpStatus.CREATED.value(), null));
        }
    }

    /**
     * Replaces the post having the same id as the given post, with the given post
     *
     * @param post
     * @return ResponseEntity
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ApiOperation("Replaces the post having the same id as the given post, with the given post")
    @RequestMapping(method = PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
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
     * Delete the post with the given id
     *
     * @param id
     * @return ResponseEntity
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @ApiOperation("Delete the post with the given id")
    @DeleteMapping(value = "{id}")
    public ResponseEntity deletePost(@ApiParam(value = "The id of the post that will be deleted") @PathVariable("id") long id) {

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