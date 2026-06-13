package serverlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.Controladora;
import logica.Distrito;
import logica.Persona;

@WebServlet(name = "SvEditarEmpleado", urlPatterns = {"/SvEditarEmpleado"})
public class SvEditarEmpleado extends HttpServlet {

    Controladora control = new Controladora();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int idEmpleado = Integer.parseInt(request.getParameter("idEmpleado"));
        Persona empleado = control.obtenerCliente(idEmpleado);

        List<Distrito> listaDistritos = control.obtenerDistritos();

        HttpSession session = request.getSession();
        session.setAttribute("empleadoEditar", empleado);
        session.setAttribute("listaDistritos", listaDistritos);

        response.sendRedirect("editarEmpleado.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession();

            Persona personaOriginal = (Persona) session.getAttribute("empleadoEditar");

            // Obtener valores del formulario
            String nombre = request.getParameter("nombre");
            String apellido = request.getParameter("apellido");
            if (apellido != null && apellido.length() > 20) {
                request.setAttribute("error", "El apellido no puede superar 20 caracteres.");
                request.getRequestDispatcher("editarEmpleado.jsp").forward(request, response);
                return;
            }
            String correo = request.getParameter("correo");
            int idDistrito = Integer.parseInt(request.getParameter("distrito"));

            // Obtener el distrito seleccionado
            Distrito nuevoDistrito = control.obtenerDistrito(idDistrito);
            personaOriginal.setNombre(nombre);
            personaOriginal.setApellido(apellido);
            personaOriginal.setCorreoElectronico(correo);
            personaOriginal.setDistrito(nuevoDistrito);

            control.editarEmpleado(personaOriginal);

            // Limpiar la sesión si querés
            session.removeAttribute("empleadoEditar");

            // Redirigir a listado o mensaje de éxito
            response.sendRedirect("SvEmpleados");
        } catch (Exception ex) {

            Logger.getLogger(SvEditarCategoria.class.getName()).log(Level.SEVERE, null, ex);
            // Agrega el mensaje de error en la petición
            request.setAttribute("error", ex.getMessage());

            // Reenvía la petición a la misma página para mostrar el error
            request.getRequestDispatcher("editarEmpleado.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
