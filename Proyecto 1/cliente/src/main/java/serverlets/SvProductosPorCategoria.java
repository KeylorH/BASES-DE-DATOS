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

@WebServlet(name = "SvProductosPorCategoria", urlPatterns = {"/SvProductosPorCategoria"})
public class SvProductosPorCategoria extends HttpServlet {

    Controladora control = new Controladora();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Obtener el parámetro idCategoria de la URL
        int idCategoria = Integer.parseInt(request.getParameter("idCategoria"));
        
        // Obtener los productos de la categoría
        List<Producto> productos = control.obtenerProductosPorCategoria(idCategoria);
        
        // Establecer los productos en la sesión
        request.getSession().setAttribute("productosPorCategoria", productos);
        
        // Debug
        System.out.println("Productos obtenidos: " + productos.size());
        
        // Redirigir a la página JSP que va a mostrar los productos
        request.getRequestDispatcher("/pages/productosPorCategoria.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet para obtener productos por categoría";
    }
}
