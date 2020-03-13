package webservices;

import DAO.CommentoDao;
import DAO.exceptions.NonexistentEntityException;
import entity.Commento;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


/**
 *
 * @author Alessio Trentin - 5^EI
 * @version 1.0.0 - 13/03/2020
 */
@Path("/comments")
public class CommentDataService {
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Commento> getCommentsList() {
        List<Commento> commentsList = new ArrayList<>();
        commentsList.addAll(CommentoDao.findAll());
        return commentsList;
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Commento getCommento(@PathParam("id") String commentoID) {
        Commento c = CommentoDao.findCommento(Long.parseLong(commentoID));
        return c;
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_JSON)
    public void newCommento(Commento c) throws IOException {
        Commento commento = new Commento();
        commento.setId(c.getId());
        commento.setDataOra(c.getDataOra());
        commento.setUtente(c.getUtente());
        commento.setContenuto(c.getContenuto());
        commento.setPost(c.getPost());
        CommentoDao.create(commento);
    }

    @PUT
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateCommento(Commento c) throws Exception {
        Commento commento = new Commento();
        commento.setId(c.getId());
        commento.setDataOra(c.getDataOra());
        commento.setUtente(c.getUtente());
        commento.setContenuto(c.getContenuto());
        commento.setPost(c.getPost());
        CommentoDao.edit(commento);
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_HTML)
    public void deleteCommento(@PathParam("id") String commentoID) throws NonexistentEntityException {
        CommentoDao.destroy(Long.parseLong(commentoID));
    }
}
