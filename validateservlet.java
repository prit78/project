/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

/**
 *
 * @author prit
 */
public class validateservlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            try {
                /* TODO output your page here. You may use following sample code. */
                Class.forName("com.mysql.jdbc.Driver");
                 Connection cn=DriverManager.getConnection("jdbc:mysql://localhost:3306/user", "root", "");
                 Statement st=cn.createStatement();
                 String name=request.getParameter("uname");
                 String pass=request.getParameter("pass");
                 String que="select * from userinfo where name='"+name+"' and password='"+pass+"' ";
                 ResultSet rs=st.executeQuery(que);
                 
                 if(rs.next())
                 {
                     int id=rs.getInt(1);
                     String nam=rs.getString(2);
                     HttpSession session=request.getSession(true);
                     session.setAttribute("uname", name);
                     session.setAttribute("uid", id);
                    response.sendRedirect("WelcomeUser.jsp");
                 }
                 
                 else
                 {
                      out.println("Invalid username/password");
                      RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
                      rd.include(request, response);
                 }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(validateservlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (SQLException ex) {
            Logger.getLogger(validateservlet.class.getName()).log(Level.SEVERE, null, ex);
        } 
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
