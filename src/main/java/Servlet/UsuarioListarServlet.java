package Servlet;

import dto.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet(name = "UsuarioListarServlet", urlPatterns = {"/usuario"})
public class UsuarioListarServlet extends HttpServlet {

    private EntityManagerFactory emf;

    @Override
    public void init() throws ServletException {
        emf = Persistence.createEntityManagerFactory("com.mycompany_Cripto062_war_1.0-SNAPSHOTPU");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String token = request.getParameter("token");
        boolean b = utils.JwtUtil.validarToken(token);
        EntityManager em = emf.createEntityManager();

        if (b) {
            List<Usuario> usuarios = em.createNamedQuery("Usuario.findAll", Usuario.class).getResultList();
            JSONArray jsonArray = new JSONArray();
            for (Usuario u : usuarios) {
                JSONObject obj = new JSONObject();
                obj.put("codiUsua", u.getCodiUsua());
                obj.put("logiUsua", u.getLogiUsua());
                obj.put("passUsua", u.getPassUsua());
                jsonArray.put(obj);
            }

            response.setContentType("application/json;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.print(jsonArray.toString());
            } finally {
                em.close();
            }
        } else {
            response.setContentType("application/json;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.print("{\"resultado\":\"error\"}");
            } finally {
                em.close();
            }
        }
    }

    @Override
    public void destroy() {
        if (emf != null) {
            emf.close();
        }
    }
}
