/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import DAO.UtenteDao;
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


/**
 *
 * @author Alessio Trentin - 5^EI
 * @version 1.0.0 - 09/02/2020
 */
public class AccessServlet extends HttpServlet {

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
        String password = request.getParameter("password");

        Utente u = UtenteDao.getUtenteByUsername(username);
        String salt = u.getSalt();

        String passwordSalt = password + salt;

        Long passwordHash = u.getPasswordHash();

        Hasher hasher = Hashing.sha256().newHasher();
        hasher.putString(passwordSalt, Charsets.UTF_8);
        long sha256 = hasher.hash().asLong();

        if(sha256 == passwordHash) {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            request.setAttribute("username", username);
            request.getRequestDispatcher("jsp/success.jsp").forward(request, response);
        }
        else {
            request.getRequestDispatcher("html/errorLogin.html").forward(request, response);
        }

    }
}
