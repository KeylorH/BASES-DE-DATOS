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
import logica.Categoria;
import logica.Controladora;
import logica.Producto;

@WebServlet(name = "SvEditarProducto", urlPatterns = {"/SvEditarProducto"})
public class SvEditarProducto extends HttpServlet {

    Controladora control = new Controladora();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        Producto producto = control.obtenerProducto(idProducto);

        List<Categoria> listaCategorias = control.obtenerCategorias();

        HttpSession session = request.getSession();
        session.setAttribute("productoEditar", producto);
        session.setAttribute("listaCategorias", listaCategorias);

        response.sendRedirect("editarProducto.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        Producto productoOriginal = (Producto) session.getAttribute("productoEditar");
        // Obtener valores del formulario
        String nombre = request.getParameter("nombre");
        double precioNuevo = Double.parseDouble(request.getParameter("precio"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        int idCategoria = Integer.parseInt(request.getParameter("categoria"));

        // Obtener la categoría seleccionada
        Categoria nuevaCategoria = control.obtenerCategoria(idCategoria);

        // Verificar si el precio cambió
        double precioAnterior = productoOriginal.getPrecio();
        boolean cambioPrecio = precioAnterior != precioNuevo;

        // Actualizar el producto
        productoOriginal.setNombre(nombre);
        productoOriginal.setPrecio(precioNuevo);
        productoOriginal.setCantidad(cantidad);
        productoOriginal.setCategoria(nuevaCategoria);

        try {
            control.editarProducto(productoOriginal);
        } catch (Exception ex) {
            Logger.getLogger(SvEditarProducto.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (cambioPrecio) {
            control.registrarBitacora(productoOriginal, precioAnterior, precioNuevo);
        }

        // Limpiar la sesión si querés
        session.removeAttribute("productoEditar");

        // Redirigir a listado o mensaje de éxito
        response.sendRedirect("SvProductos");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
