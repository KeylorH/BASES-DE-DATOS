package serverlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import logica.Categoria;
import logica.Controladora;

@WebServlet(name = "SvAccionCategorias", urlPatterns = {"/SvAccionCategorias"})
public class SvAccionCategorias extends HttpServlet {

    Controladora control = new Controladora();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Categoria> listaCategorias = control.obtenerCategorias();

        HttpSession session = request.getSession();
        session.setAttribute("listaCategorias", listaCategorias);

        response.sendRedirect("verCategorias.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombreNuevo = request.getParameter("nombre");
        String descripcionNueva = request.getParameter("descripcion");
        double impuestoNuevo = Double.parseDouble(request.getParameter("impuesto"));

        control.crearCategoria(nombreNuevo, descripcionNueva, impuestoNuevo);

        response.sendRedirect("SvAccionCategorias");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
