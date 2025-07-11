/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.JwtUtil;

/**
 *
 * @author lord_
 */
@WebServlet(name = "loginfacebookservlet", urlPatterns = {"/loginfacebookservlet"})
public class loginfacebookservlet extends HttpServlet {

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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet loginfacebookservlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet loginfacebookservlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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

        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        StringBuilder sb = new StringBuilder();
        String line;

        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        String jsonString = sb.toString();
        String facebookId = "";
        String nombre = "";

        try {
            JsonObject json = new Gson().fromJson(jsonString, JsonObject.class);
            facebookId = json.get("facebook_id").getAsString();
            nombre = json.get("nombre").getAsString();
        } catch (Exception e) {
            out.println("{\"resultado\":\"error\",\"mensaje\":\"JSON inválido\"}");
            return;
        }

        try {
            // Aquí puedes validar el ID en tu base de datos o registrarlo si es nuevo.
            // Supondremos que lo aceptas directamente.

            // Generar token personalizado
            String token = JwtUtil.generarToken(facebookId);

            // Crear sesión
            HttpSession sesion = request.getSession();
            sesion.setAttribute("usuario", nombre);

            JsonObject respuesta = new JsonObject();
            respuesta.addProperty("resultado", "ok");
            respuesta.addProperty("token", token);
            out.println(respuesta.toString());

        } catch (Exception e) {
            e.printStackTrace();
            out.println("{\"resultado\":\"error\",\"mensaje\":\"Excepción interna\"}");
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet de logueo con Facebook";
    }
}
