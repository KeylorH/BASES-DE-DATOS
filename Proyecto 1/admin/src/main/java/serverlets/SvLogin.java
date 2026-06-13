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

    Controladora control = new Controladora();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userName = request.getParameter("usuario");
        String password = request.getParameter("password");

        if (userName == null || userName.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            response.sendRedirect("login.jsp");
            return;
        }

        System.out.println("username " + userName);
        System.out.println("password  " + password);

        boolean esEmpleado = control.esEmpleado(userName, password);

        System.out.println("ES EMPLEADO:  " + esEmpleado);

        if (esEmpleado) {
            HttpSession session = request.getSession(true);
            session.setAttribute("usuario", userName);
            response.sendRedirect("index.jsp");
        } else {
            response.sendRedirect("loginError.jsp");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
