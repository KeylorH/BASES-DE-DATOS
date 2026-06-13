<%@ page import="java.util.Map" %>
<%@ page import="logica.Producto" %>
<%@ page import="logica.Carrito" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
    Carrito carrito = (Carrito) session.getAttribute("carrito");
    if (carrito == null) {
        carrito = new Carrito();
        session.setAttribute("carrito", carrito);
    }
    Map<Producto, Integer> productos = carrito.getProductos();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Carrito de Compras</title>
</head>
<body>
    <h1>Carrito de Compras</h1>

    <% if (productos.isEmpty()) { %>
        <p>No tienes productos en el carrito.</p>
    <% } else { %>
        <table border="1">
            <tr>
                <th>Producto</th>
                <th>Precio</th>
                <th>Cantidad</th>
                <th>Subtotal</th>
                <th>Eliminar</th>
            </tr>
            <% for (Map.Entry<Producto, Integer> entry : productos.entrySet()) {
                Producto p = entry.getKey();
                int cantidad = entry.getValue();
            %>
            <tr>
                <td><%= p.getNombre() %></td>
                <td>₡<%= p.getPrecio() %></td>
                <td><%= cantidad %></td>
                <td>₡<%= p.getPrecio() * cantidad %></td>
                <td>
                    <a href="SvCarrito?action=eliminar&idProducto=<%= p.getIdProducto() %>">Eliminar</a>
                </td>
            </tr>
            <% } %>
        </table>
        <p><strong>Total:</strong> ₡<%= carrito.obtenerTotal() %></p>
    <% } %>
</body>
</html>