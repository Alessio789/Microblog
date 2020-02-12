package servlet;

import DAO.PostDao;
import DAO.UtenteDao;
import entity.Post;
import entity.Utente;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 *
 * Servlet per il salvataggio dei post
 *
 * @author Alessio Trentin - 5^EI
 * @version 1.0.0 - 10/02/2020
 */
public class SavePostServlet extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        throw new UnsupportedOperationException();
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);
        if(session != null) {
            String username = (String) session.getAttribute("username");

            String titolo = request.getParameter("titolo");
            String contenuto = request.getParameter("contenuto");
            Date timestamp = new Date();
            Utente utente = UtenteDao.getUtenteByUsername(username);

            Post p = new Post();
            p.setUtente(utente);
            p.setContenuto(contenuto);
            p.setTitolo(titolo);
            p.setDataOra(timestamp);

            PostDao.create(p);

            request.setAttribute("username", username);
            request.getRequestDispatcher("jsp/success.jsp").forward(request, response);
        }
        else {
            request.getRequestDispatcher("html/errorPost.html").forward(request, response);
        }
    }
}
