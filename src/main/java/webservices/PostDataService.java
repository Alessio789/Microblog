package webservices;

import DAO.PostDao;
import entity.Post;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 *
 * @author Alessio Trentin - 5^EI
 * @version 1.0.0 - 11/03/2020
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
    @Produces(MediaType.APPLICATION_JSON)
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
}
