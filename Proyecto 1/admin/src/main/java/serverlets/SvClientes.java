package serverlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import logica.Controladora;
import logica.Persona;

@WebServlet(name = "SvClientes", urlPatterns = {"/SvClientes"})
public class SvClientes extends HttpServlet {

    Controladora control = new Controladora();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Persona> listaClientes = control.obtenerClientes();

        HttpSession session = request.getSession();
        session.setAttribute("listaClientes", listaClientes);

        response.sendRedirect("verClientes.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Recuperar parámetros del formulario
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String correo = request.getParameter("correo");
        String distrito = request.getParameter("distrito");

        int idDistrito = Integer.parseInt(distrito);

        control.crearCliente(nombre, apellido, correo, idDistrito);

        response.sendRedirect("SvClientes");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
