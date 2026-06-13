package serverlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import logica.Carrito;

@WebServlet("/SvCarritoContador")
public class SvCarritoContador extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Carrito carrito = (Carrito) session.getAttribute("carrito");
        int cantidad = 0;

        if (carrito != null) {
            for (Integer cant : carrito.getProductos().values()) {
                cantidad += cant;
            }
        }

        response.setContentType("text/plain");
        response.getWriter().write(String.valueOf(cantidad));
    }
}
