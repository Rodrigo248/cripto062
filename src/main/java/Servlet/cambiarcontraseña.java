/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import dao.UsuarioJpaController;
import dto.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.HashUtil;

/**
 *
 * @author lord_
 */
@WebServlet(name = "cambiarcontraseña", urlPatterns = {"/cambiarcontrase_a"})
public class cambiarcontraseña extends HttpServlet {

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
        String logiUsua = request.getParameter("logiUsua");
        String passUsua = request.getParameter("passUsua");       // actual en texto plano
        String nuevaPass = request.getParameter("newPass");       // nueva en texto plano

        UsuarioJpaController dao = new UsuarioJpaController();
        Usuario usuario = dao.buscarPorLogin(logiUsua);

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        if (usuario != null) {
            // Cifrar la contraseña actual ingresada por el usuario
            String passIngresadaCifrada = HashUtil.sha1(passUsua);

            // Comparar con la contraseña cifrada almacenada en la base de datos
            if (usuario.getPassUsua().equals(passIngresadaCifrada)) {
                try {
                    // Cifrar la nueva contraseña antes de guardar
                    String nuevaCifrada = HashUtil.sha1(nuevaPass);
                    usuario.setPassUsua(nuevaCifrada);
                    dao.edit(usuario);

                    out.print("{\"resultado\": \"ok\", \"mensaje\": \"Contra cambiada exitosamente\"}");
                } catch (Exception e) {
                    out.print("{\"resultado\": \"error\", \"mensaje\": \"Error al actualizar contraseña\"}");
                }
            } else {
                out.print("{\"resultado\": \"error\", \"mensaje\": \"La contraseña actual es incorrecta\"}");
            }
        } else {
            out.print("{\"resultado\": \"error\", \"mensaje\": \"Usuario no encontrado\"}");
        }

        out.close();
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
