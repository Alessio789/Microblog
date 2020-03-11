/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import DAO.CommentoDao;
import DAO.PostDao;
import DAO.UtenteDao;
import entity.Commento;
import entity.Post;
import entity.Utente;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 *
 * Servlet per il salvataggio dei commenti
 *
 * @author Alessio Trentin - 5^EI
 * @version 1.0.0 - 11/02
 */
public class SaveCommentServlet extends HttpServlet {

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

        
        HttpSession session = request.getSession(false);
        
        if(session != null) {
            String username = (String) session.getAttribute("username");
            String contenuto = request.getParameter("contenuto");
            Utente utente = UtenteDao.getUtenteByUsername(username);
            Post post = null;
            for (Post p : PostDao.findAll()) {
                if (request.getParameter(String.valueOf(p.getId())) != null) {
                    post = p;
                    break;
                }
            }
            
            Commento commento = new Commento();
            commento.setContenuto(contenuto);
            commento.setDataOra(new Date());
            commento.setUtente(utente);
            commento.setPost(post);
            
            CommentoDao.create(commento);
            
            List<Commento> commentoList = (List<Commento>) CommentoDao.findCommentoByPost(post);
            
            
            request.setAttribute("postList", PostDao.findAll());
            request.getRequestDispatcher("jsp/post.jsp").forward(request, response);
        } 
        else
            request.getRequestDispatcher("html/errorComment.html").forward(request, response);
    }
}
