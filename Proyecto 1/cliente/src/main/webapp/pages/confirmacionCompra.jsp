<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirmación de Compra</title>
    </head>
    <body>
        <h1>¡Gracias por tu compra!</h1>
        <p>Tu compra ha sido procesada exitosamente.</p>
        <a href="<%= request.getContextPath() %>/pages/productoPorCategoria.jsp">Volver al inicio</a>
    </body>
</html>