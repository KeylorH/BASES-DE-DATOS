package serverlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import logica.Controladora;
import logica.Producto;

@WebServlet(name = "SvProductos", urlPatterns = {"/SvProductos"})
public class SvProductos extends HttpServlet {

    Controladora control = new Controladora();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Producto> listaProductos = new ArrayList<Producto>();

        listaProductos = control.getProductos();

        HttpSession session = request.getSession();
        session.setAttribute("listaProductos", listaProductos);

        response.sendRedirect("pages/home.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recuperar parámetros del formulario
        String nombre = request.getParameter("nombre");
        String precioStr = request.getParameter("precio");
        String cantidadStr = request.getParameter("cantidad");
        String categoriaStr = request.getParameter("categoria");

        // Validar que ninguno de los parámetros sea nulo y parsear los valores
        double precio = Double.parseDouble(precioStr);
        int cantidad = Integer.parseInt(cantidadStr);
        int idCategoria = Integer.parseInt(categoriaStr);

        control.crearProducto(nombre, precio, cantidad, idCategoria);

        response.sendRedirect("SvProductos");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
