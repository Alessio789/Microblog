package webservices;

import DAO.CommentoDao;
import DAO.PostDao;
import DAO.exceptions.NonexistentEntityException;
import entity.Commento;
import entity.Post;
import java.io.IOException;
import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


/**
 *
 * @author Alessio Trentin - 5^EI
 * @version 1.0.1 - 13/03/2020
 */
@Path("/posts")
public class PostDataService {

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Post> getPostsList() {
        List<Post> postsList = new ArrayList<>();
        postsList.addAll(PostDao.findAll());
        return postsList;
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Post getPost(@PathParam("id") String postID) {
        Post p = PostDao.findPost(Long.parseLong(postID));
        return p;
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_JSON)
    public void newPost(Post p) throws IOException {
        Post post = new Post();
        post.setId(p.getId());
        post.setDataOra(p.getDataOra());
        post.setUtente(p.getUtente());
        post.setTitolo(p.getTitolo());
        post.setContenuto(p.getContenuto());
        PostDao.create(post);
    }

    @PUT
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_JSON)
    public void updatePost(Post p) throws Exception {
        Post post = new Post();
        post.setId(p.getId());
        post.setDataOra(p.getDataOra());
        post.setUtente(p.getUtente());
        post.setTitolo(p.getTitolo());
        post.setContenuto(p.getContenuto());
        PostDao.edit(post);
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_HTML)
    public void deletePost(@PathParam("id") String postID) throws NonexistentEntityException {
        Post p = PostDao.findPost(Long.parseLong(postID));
        List<Commento> commentList = CommentoDao.findCommentoByPost(p);
        for (int i = 0; i < commentList.size(); i++) {
            CommentoDao.destroy(commentList.get(i).getId());
        }
        PostDao.destroy(Long.parseLong(postID));
    }
}
