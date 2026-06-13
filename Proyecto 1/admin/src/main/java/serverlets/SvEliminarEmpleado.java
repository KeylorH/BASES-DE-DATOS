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
import persistencia.exceptions.NonexistentEntityException;

@WebServlet(name = "SvEliminarEmpleado", urlPatterns = {"/SvEliminarEmpleado"})
public class SvEliminarEmpleado extends HttpServlet {

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
            int idEmpleado = Integer.parseInt(request.getParameter("idEmpleado"));

            control.eliminarEmpleado(idEmpleado);

            response.sendRedirect("SvEmpleados");

        } catch (NonexistentEntityException ex) {
            Logger.getLogger(SvEliminarCategoria.class.getName()).log(Level.SEVERE, null, ex);

            // Agrega el mensaje de error en la petición
            request.setAttribute("error", ex.getMessage());

            // Reenvía la petición a la misma página para mostrar el error
            request.getRequestDispatcher("SvEmpleados.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
