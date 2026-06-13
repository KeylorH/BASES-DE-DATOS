package serverlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.Categoria;
import logica.Controladora;

@WebServlet(name = "SvEditarCategoria", urlPatterns = {"/SvEditarCategoria"})
public class SvEditarCategoria extends HttpServlet {

    Controladora control = new Controladora();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idCategoria = Integer.parseInt(request.getParameter("idCategoria"));
        Categoria categoria = control.obtenerCategoria(idCategoria);

        HttpSession session = request.getSession();
        session.setAttribute("categoriaEditar", categoria);

        response.sendRedirect("editarCategoria.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            HttpSession session = request.getSession();

            Categoria categoriaOriginal = (Categoria) session.getAttribute("categoriaEditar");
            String nombreNuevo = request.getParameter("nombre");
            String descripcionNueva = request.getParameter("descripcion");
            double impuestoNuevo = Double.parseDouble(request.getParameter("impuesto"));

            categoriaOriginal.setNombre(nombreNuevo);
            categoriaOriginal.setDescripcion(descripcionNueva);
            categoriaOriginal.setImpuesto(impuestoNuevo);

            control.editarCategoria(categoriaOriginal);

            // Limpiar la sesión si querés
            session.removeAttribute("categoriaEditar");

            // Redirigir a listado o mensaje de éxito
            response.sendRedirect("SvAccionCategorias");
        } catch (Exception ex) {
            Logger.getLogger(SvEditarCategoria.class.getName()).log(Level.SEVERE, null, ex);
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
