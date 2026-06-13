<%@ page import="java.util.Map" %>
<%@ page import="logica.Producto" %>
<%@ page import="logica.Carrito" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // Obtener el carrito de la sesión
    Carrito carrito = (Carrito) session.getAttribute("carrito");

    // Si el carrito no existe en la sesión, crear uno nuevo
    if (carrito == null) {
        carrito = new Carrito();
        session.setAttribute("carrito", carrito);  // Guardar el carrito vacío en la sesión
    }

    // Obtener los productos del carrito
    Map<Producto, Integer> productos = carrito.getProductos();
%>
<!DOCTYPE html>
<html lang="es" class="h-full">
    <head>
        <meta charset="UTF-8">
        <title>Carrito de Compras</title>
        <!-- TailwindCSS -->
        <script src="https://cdn.tailwindcss.com"></script>
        <!-- Font Awesome para íconos -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <style>
            body {
                background:
                    linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)),
                    url('https://images.unsplash.com/photo-1662758392656-0e5d4b0f53fb?q=80&w=2080&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D');
                background-size: cover;
                background-position: center;
                background-attachment: fixed;
                background-repeat: no-repeat;
            }
            .cart-card {
                backdrop-filter: blur(8px);
                background-color: rgba(255, 255, 255, 0.85);
                border: 1px solid rgba(255, 255, 255, 0.2);
            }
            .gradient-bg {
                background: linear-gradient(135deg, #6e8efb, #a777e3);
            }
        </style>
    </head>
    <body class="h-full">
        <div class="min-h-screen flex items-center justify-center p-4">
            <div class="w-full max-w-md">
                <div class="cart-card rounded-2xl shadow-2xl overflow-hidden">
                    <div class="gradient-bg p-6 text-white text-center">
                        <div class="flex justify-center items-center">
                            <i class="fas fa-shopping-cart text-4xl mr-2"></i>
                            <span class="text-2xl font-bold">Tu Carrito</span>
                        </div>
                    </div>
                    <div class="p-8">
                        <%
                            if (productos != null && !productos.isEmpty()) {
                                for (Map.Entry<Producto, Integer> entry : productos.entrySet()) {
                                    Producto p = entry.getKey();
                                    int cantidad = entry.getValue();
                        %>
                        <div class="card mb-4 p-4 border border-gray-200 rounded-lg shadow-sm" id="producto-<%= p.getIdProducto()%>">
                            <div class="flex justify-between items-center">
                                <div>
                                    <h3 class="text-lg font-semibold"><%= p.getNombre()%></h3>
                                    <p class="text-gray-700">Precio: ₡<%= p.getPrecio()%></p>
                                    <p class="text-gray-700">Cantidad: <%= cantidad%></p>
                                </div>
                                <button class="btnEliminar text-red-500 hover:text-red-700" data-id="<%= p.getIdProducto()%>">
                                    <i class="fas fa-trash-alt"></i>
                                </button>
                            </div>
                        </div>
                        <%
                            }
                        } else {
                        %>
                        <p class="text-center text-gray-700">No hay productos en el carrito.</p>
                        <% } %>
                        <!-- Botón de Confirmación de Compra -->
                        <% if (productos != null && !productos.isEmpty()) {%>
                        <form action="<%= request.getContextPath()%>/SvConfirmarCompra" method="post" class="mt-6">
                            <button type="submit" class="gradient-bg w-full flex justify-center py-3 px-4 border border-transparent rounded-lg shadow-sm text-sm font-medium text-white hover:opacity-90 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 transition duration-200">
                                Confirmar Compra
                            </button>
                        </form>
                        <% }%>

                        <!-- Botón para volver a la página de productos -->
                        <div class="mt-8 ml-10">
                            <a href="<%= request.getContextPath()%>/pages/productosPorCategoria.jsp?idCategoria=1"
                               class="inline-flex items-center justify-center gap-x-2 rounded-md bg-indigo-600 px-4 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
                                <i class="fas fa-arrow-left"></i>
                                Volver a los Productos
                            </a>
                        </div>

                    </div>
                </div>
            </div>
        </div>
        <script>
            document.addEventListener("DOMContentLoaded", () => {
                const botonesEliminar = document.querySelectorAll(".btnEliminar");
                botonesEliminar.forEach(boton => {
                    boton.addEventListener("click", () => {
                        const id = boton.getAttribute("data-id");

                        // Hacer la solicitud para eliminar el producto
                        fetch('<%= request.getContextPath()%>/SvCarrito?action=eliminar&idProducto=' + encodeURIComponent(id), {
                            method: 'GET',
                            headers: {
                                'Accept': 'application/json',
                            },
                        })
                                .then(response => response.json())
                                .then(data => {
                                    if (data.success) {
                                        // Eliminar el producto del carrito de la sesión
                                        const divProducto = document.getElementById("producto-" + id);
                                        if (divProducto) {
                                            divProducto.remove();
                                        }

                                        // Actualizar el carrito en la sesión
                                        sessionStorage.setItem('carrito', JSON.stringify(data.carrito));

                                        // Si es necesario, actualizar algún contador en la interfaz (ejemplo, un contador en el icono del carrito)
                                        if (typeof actualizarContadorCarrito === "function") {
                                            actualizarContadorCarrito();
                                        }
                                    } else {
                                        alert("Error al eliminar producto: " + data.message);  // Mostrar mensaje de error
                                    }
                                })
                                .catch(error => {
                                    console.error("Error eliminando producto:", error);
                                    alert("Hubo un error al intentar eliminar el producto.");
                                });
                    });
                });
            });
        </script>
    </body>
</html>
