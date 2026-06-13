package serverlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.Controladora;

@WebServlet(name = "SvEliminarCategoria", urlPatterns = {"/SvEliminarCategoria"})
public class SvEliminarCategoria extends HttpServlet {

    Controladora control = new Controladora();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int idCategoria = Integer.parseInt(request.getParameter("idCategoria"));

            control.eliminarCategoria(idCategoria);

            response.sendRedirect("SvAccionCategorias");
        } catch (Exception ex) {
            Logger.getLogger(SvEliminarCategoria.class.getName()).log(Level.SEVERE, null, ex);

            // Agrega el mensaje de error en la petición
            request.setAttribute("error", ex.getMessage());

            // Reenvía la petición a la misma página para mostrar el error
            request.getRequestDispatcher("editarCategoria.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
