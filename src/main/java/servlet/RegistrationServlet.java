package servlet;

import DAO.UtenteDao;
import blog.SaltGenerator;
import com.google.common.base.Charsets;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import entity.Utente;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * Servlet per la gestione della registrazione
 * 
 * @author Alessio Trentin - 5^EI
 * @version 1.0.0 - 07/02/2020
 */
public class RegistrationServlet extends HttpServlet {


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
        
        String username = request.getParameter("username");
        String email = request.getParameter("inputEmail");
        String password = request.getParameter("inputPassword");
        String salt = DatatypeConverter.printBase64Binary(SaltGenerator.getNextSalt());
        
        String passwordSalt = password + salt;
        
        Hasher hasher = Hashing.sha256().newHasher();
        hasher.putString(passwordSalt, Charsets.UTF_8);
        long sha256 = hasher.hash().asLong();
        
                
        Utente u = new Utente();
        u.setEmail(email);
        u.setUsername(username);
        u.setPasswordHash(sha256);
        u.setSalt(salt);   
        
        UtenteDao.create(u);
        
        HttpSession session = request.getSession();
        session.setAttribute("username", username);
        request.setAttribute("username", username);
        request.getRequestDispatcher("jsp/success.jsp").forward(request,response);
    }
}
