package serverlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import logica.Carrito;
import logica.Controladora;
import logica.Factura;
import logica.Persona;
import logica.Producto;
import logica.ProductoXFactura;

@WebServlet(name = "SvConfirmarCompra", urlPatterns = {"/SvConfirmarCompra"})
public class SvConfirmarCompra extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            Carrito carrito = (Carrito) session.getAttribute("carrito");
            Persona cliente = (Persona) session.getAttribute("cliente"); // Asegúrate de tener el cliente en la sesión
            if (carrito == null || carrito.getProductos().isEmpty() || cliente == null) {
                response.sendRedirect("pages/errorcompra.jsp"); // Redirigir a una página de error si no hay productos o cliente
                return;
            }
            Controladora control = new Controladora();
            Factura factura = new Factura();
            factura.setPersona(cliente);
            factura.setFecha(new Date());
            factura.setTotal(carrito.obtenerTotal());
            // Crear la factura
            control.crearFactura(factura);
            // Asociar productos a la factura
            for (Map.Entry<Producto, Integer> entry : carrito.getProductos().entrySet()) {
                Producto producto = entry.getKey();
                int cantidad = entry.getValue();
                ProductoXFactura productoXFactura = new ProductoXFactura();
                productoXFactura.setFactura(factura);
                productoXFactura.setProducto(producto);
                productoXFactura.setCantidadProducto(cantidad);
                control.crearProductoXFactura(productoXFactura);
                // Actualizar el inventario
                producto.setCantidad(producto.getCantidad() - cantidad);
                control.editarProducto(producto);
            }
            // Limpiar el carrito
            session.removeAttribute("carrito");
            // Redirigir a la página de confirmación
            response.sendRedirect("pages/confirmacionCompra.jsp");
        } catch (Exception e) {
            Logger.getLogger(SvConfirmarCompra.class.getName()).log(Level.SEVERE, "Error al procesar la compra", e);
            // Redirigir a la página de error en caso de excepción
            response.sendRedirect("pages/errorcompra.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(SvConfirmarCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(SvConfirmarCompra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
