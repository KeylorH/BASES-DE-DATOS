package serverlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import logica.Carrito;
import logica.Controladora;
import logica.Producto;

@WebServlet(name = "SvCarrito", urlPatterns = {"/SvCarrito"})
public class SvCarrito extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String accion = request.getParameter("accion");
        try {
            int idProducto = Integer.parseInt(request.getParameter("idProducto"));
            Carrito carrito = (Carrito) session.getAttribute("carrito");
            if (carrito == null) {
                carrito = new Carrito();
                session.setAttribute("carrito", carrito);
            }
            Controladora control = new Controladora();
            Producto producto = control.obtenerProducto(idProducto);
            if (producto != null) {
                if ("agregar".equals(accion)) {
                    carrito.agregarProducto(producto);
                    session.setAttribute("carrito", carrito);
                    // Responder correctamente a la petición AJAX
                    response.setContentType("text/plain");
                    response.getWriter().write("ok");
                }
            } else {
                // Producto no encontrado
                response.setContentType("text/plain");
                response.getWriter().write("error");
            }
        } catch (Exception e) {
            // Error general (por ejemplo, ID inválido)
            response.setContentType("text/plain");
            response.getWriter().write("error");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        Carrito carrito = (Carrito) session.getAttribute("carrito");

        if (carrito == null) {
            carrito = new Carrito();
            session.setAttribute("carrito", carrito);
        }

        if ("eliminar".equals(action)) {
            try {
                int idProducto = Integer.parseInt(request.getParameter("idProducto"));
                Producto productoAEliminar = null;

                // Buscar el producto en el carrito
                for (Producto p : carrito.getProductos().keySet()) {
                    if (p.getIdProducto() == idProducto) {
                        productoAEliminar = p;
                        break;
                    }
                }

                // Si se encuentra el producto, eliminarlo
                if (productoAEliminar != null) {
                    carrito.eliminarProducto(productoAEliminar);
                    // Actualizar la sesión con el carrito modificado
                    session.setAttribute("carrito", carrito);
                    // Responder con éxito si el producto se eliminó correctamente
                    response.setContentType("application/json");
                    response.getWriter().write("{\"success\": true, \"message\": \"Producto eliminado correctamente.\"}");
                } else {
                    // Producto no encontrado
                    response.setContentType("application/json");
                    response.getWriter().write("{\"success\": false, \"message\": \"Producto no encontrado.\"}");
                }

            } catch (NumberFormatException e) {
                // Error si el parámetro no es válido
                response.setContentType("application/json");
                response.getWriter().write("{\"success\": false, \"message\": \"ID de producto inválido.\"}");
            }
        }
    }
}