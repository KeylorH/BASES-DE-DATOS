package serverlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import logica.Controladora;

@WebServlet(name = "SvLogin", urlPatterns = {"/SvLogin"})
public class SvLogin extends HttpServlet {

    private Controladora control;

    @Override
    public void init() throws ServletException {
        super.init();
        control = new Controladora();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String correo = request.getParameter("correo");
        String password = request.getParameter("password");

        if (correo == null || correo.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            response.sendRedirect("login.jsp");
            return;
        }

        boolean esCliente = control.esCliente(correo, password);

        if (esCliente) {
            HttpSession session = request.getSession(true);
            session.setAttribute("correo", correo);
            response.sendRedirect(request.getContextPath() + "/SvProductosPorCategoria?idCategoria=1");
        } else {
            response.sendRedirect("loginError.jsp");
        }
    }
}
