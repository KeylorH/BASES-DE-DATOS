package serverlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logica.Controladora;
import logica.Persona;

@WebServlet(name = "SvRegistro", urlPatterns = {"/SvRegistro"})
public class SvRegistro extends HttpServlet {

    private Controladora control;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            control = new Controladora();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("No se pudo inicializar la Controladora", e);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Puedes dejarlo vacío si no se usa, o eliminarlo
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("registro.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String correo = request.getParameter("correo");
        String password = request.getParameter("password");
        int idDistrito = Integer.parseInt(request.getParameter("distrito"));

        if (nombre == null || apellido == null || correo == null || password == null ||
            nombre.trim().isEmpty() || apellido.trim().isEmpty() ||
            correo.trim().isEmpty() || password.trim().isEmpty()) {
            response.sendRedirect("registroError.jsp");
            return;
        }

        boolean correoExistente = control.verificarCorreoExistente(correo);
        if (correoExistente) {
            response.sendRedirect("registroError.jsp");
            return;
        }

        Persona nuevaPersona = new Persona();
        nuevaPersona.setNombre(nombre);
        nuevaPersona.setApellido(apellido);
        nuevaPersona.setCorreoElectronico(correo);
        nuevaPersona.setPassword(password);
        nuevaPersona.setIsEmpleado(0);
        nuevaPersona.setDistrito(control.obtenerDistrito(idDistrito));

        try {
            control.registrarPersona(nuevaPersona);
            response.sendRedirect("registroExitoso.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("registroError.jsp");
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet de registro de usuarios";
    }
}
