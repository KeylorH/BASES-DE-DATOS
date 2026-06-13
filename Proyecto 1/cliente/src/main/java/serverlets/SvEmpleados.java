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

@WebServlet(name = "SvEmpleados", urlPatterns = {"/SvEmpleados"})
public class SvEmpleados extends HttpServlet {

    Controladora control = new Controladora();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Persona> listaEmpleados = control.obtenerEmpleados();

        HttpSession session = request.getSession();
        session.setAttribute("listaEmpleados", listaEmpleados);

        response.sendRedirect("verEmpleados.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Recuperar parámetros del formulario
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String correo = request.getParameter("correo");
        String distrito = request.getParameter("distrito");
        String password = request.getParameter("password");

        int idDistrito = Integer.parseInt(distrito);

        control.crearEmpleado(nombre, apellido, correo, idDistrito, password);

        response.sendRedirect("SvEmpleados");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
