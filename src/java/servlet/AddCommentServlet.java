package servlet;

import DAO.PostDao;
import entity.Post;
import java.io.IOException;
import javax.activation.UnsupportedDataTypeException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 *
 * Servlet per aggiungere un commento
 * 
 * @author Alessio Trentin - 5^EI
 * @version 1.0.0 - 11/02/2020
 */
public class AddCommentServlet extends HttpServlet {

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
        throw new UnsupportedDataTypeException();
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
            for (Post p : PostDao.findAll()) {
                if (request.getParameter(String.valueOf(p.getId())) != null) {
                    request.setAttribute("post", p);
                    break;
                }
            }
            request.getRequestDispatcher("jsp/addComment.jsp").forward(request, response);
        } 
        else {
            request.getRequestDispatcher("html/errorComment.html").forward(request, response);
        }
    }
}
