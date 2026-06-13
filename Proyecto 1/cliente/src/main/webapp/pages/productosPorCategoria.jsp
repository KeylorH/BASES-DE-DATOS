<%@ page import="java.util.List" %>
<%@ page import="logica.Producto" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%
    List<Producto> productos = (List<Producto>) session.getAttribute("productosPorCategoria");
%>
<!DOCTYPE html>
<html lang="es" class="h-full">
    <head>
        <meta charset="UTF-8">
        <title>ElectroTech</title>
        <!-- TailwindCSS imports -->
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
            .product-card {
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
        <main class="flex flex-col w-full h-screen pt-[112px]">
            <header class="absolute inset-x-0 top-0 z-50">
                <nav class="flex items-center justify-between p-6 lg:px-8" aria-label="Global">
                    <div class="flex lg:flex-1">
                        <a href="pages/audio.jsp" class="-m-1.5 p-1.5">
                            <i class="fas fa-laptop text-4xl mr-2"></i>
                        </a>
                    </div>
                    <div class="hidden lg:flex lg:gap-x-12">
                        <a href="#" class="text-sm/6 font-semibold text-gray-900">Inicio</a>
                        <div class="group relative inline-block">
                            <a href="pages/productos.jsp" class="text-sm/6 font-semibold text-gray-900"> </a>
                        </div>
                    </div>
                    <div class="hidden lg:flex lg:flex-1 lg:justify-end">
                        <a href="#" class="text-sm/6 font-semibold text-gray-900">Username</a>
                        <a href="<%= request.getContextPath()%>/pages/verCarrito.jsp" id="enlaceCarrito">
                            🛒 Carrito (<span id="contadorCarrito">0</span>)
                        </a>
                    </div>
                </nav>
            </header>
            <div class="grid grid-cols-[200px_1fr] w-[900px] self-center gap-x-2">
                <aside class="flex flex-col">
                    <h3 class="font-sans text-xs font-medium text-gray-500 px-3">CATEGORIAS</h3>
                    <div class="flex flex-col pt-4">
                        <a href="<%= request.getContextPath()%>/SvProductosPorCategoria?idCategoria=1" class="text-sm font-medium text-gray-900 px-3 py-2 hover:bg-gray-100 rounded-md">Laptops</a>
                        <a href="<%= request.getContextPath()%>/SvProductosPorCategoria?idCategoria=2" class="text-sm font-medium text-gray-900 px-3 py-2 hover:bg-gray-100 rounded-md">Desktops</a>
                        <a href="<%= request.getContextPath()%>/SvProductosPorCategoria?idCategoria=3" class="text-sm font-medium text-gray-900 px-3 py-2 hover:bg-gray-100 rounded-md">Componentes</a>
                        <a href="<%= request.getContextPath()%>/SvProductosPorCategoria?idCategoria=4" class="text-sm font-medium text-gray-900 px-3 py-2 hover:bg-gray-100 rounded-md">Periféricos</a>
                        <a href="<%= request.getContextPath()%>/SvProductosPorCategoria?idCategoria=5" class="text-sm font-medium text-gray-900 px-3 py-2 hover:bg-gray-100 rounded-md">Accesorios</a>
                        <a href="<%= request.getContextPath()%>/SvProductosPorCategoria?idCategoria=6" class="text-sm font-medium text-gray-900 px-3 py-2 hover:bg-gray-100 rounded-md">Redes</a>
                        <a href="<%= request.getContextPath()%>/SvProductosPorCategoria?idCategoria=7" class="text-sm font-medium text-gray-900 px-3 py-2 hover:bg-gray-100 rounded-md">Software</a>
                        <a href="<%= request.getContextPath()%>/SvProductosPorCategoria?idCategoria=8" class="text-sm font-medium text-gray-900 px-3 py-2 hover:bg-gray-100 rounded-md">Gaming</a>
                        <a href="<%= request.getContextPath()%>/SvProductosPorCategoria?idCategoria=9" class="text-sm font-medium text-gray-900 px-3 py-2 hover:bg-gray-100 rounded-md">Audio</a>
                        <a href="<%= request.getContextPath()%>/SvProductosPorCategoria?idCategoria=10" class="text-sm font-medium text-gray-900 px-3 py-2 hover:bg-gray-100 rounded-md">Almacenamiento</a>
                    </div>
                </aside>
                <div class="bg-white">
                    <div class="w-full">
                        <h2 class="text-2xl font-semibold tracking-tight text-gray-900">Productos</h2>
                        <div class="mt-6 grid grid-cols-1 gap-x-6 gap-y-10 sm:grid-cols-2 lg:grid-cols-4 xl:gap-x-8">
                            <%
                                if (productos != null && !productos.isEmpty()) {
                                    for (Producto p : productos) {
                            %>
                            <div class="product-card flex flex-col gap-y-2 group relative p-4 rounded-md shadow-sm">
                                <div class="flex flex-col relative">
                                    <img src="https://www.svgrepo.com/show/508699/landscape-placeholder.svg" class="aspect-square w-full rounded-md bg-gray-200 object-cover group-hover:opacity-75 lg:aspect-auto lg:h-40">
                                    <p class="absolute bottom-2 right-2 text-sm font-medium text-gray-900">₡<%= p.getPrecio()%></p>
                                </div>
                                <div class="flex justify-between">
                                    <h3 class="text-sm text-gray-700 truncate">
                                        <%= p.getNombre()%>
                                    </h3>
                                </div>
                                <!-- Botón con AJAX -->
                                <button class="btnAgregar flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold text-white shadow-xs hover:bg-indigo-500 focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600" data-id="<%= p.getIdProducto()%>">Agregar al carrito</button>
                            </div>
                            <%
                                }
                            } else {
                            %>
                            <p>No hay productos disponibles para esta categoría.</p>
                            <%
                                }
                            %>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <!-- Script para manejar AJAX -->
        <script>
            function actualizarContadorCarrito() {
                fetch('<%= request.getContextPath()%>/SvCarritoContador')
                        .then(response => response.text())
                        .then(data => {
                            document.getElementById("contadorCarrito").textContent = data;
                        })
                        .catch(error => console.error("Error actualizando el contador:", error));
            }

            document.addEventListener("DOMContentLoaded", () => {
                actualizarContadorCarrito(); // Al cargar la página

                // Manejar el click de cada botón de agregar al carrito
                const botones = document.querySelectorAll(".btnAgregar");
                botones.forEach(boton => {
                    boton.addEventListener("click", () => {
                        const idProducto = boton.getAttribute("data-id");

                        fetch('<%= request.getContextPath()%>/SvCarrito', {
                            method: "POST",
                            headers: {
                                "Content-Type": "application/x-www-form-urlencoded",
                                "X-Requested-With": "XMLHttpRequest"
                            },
                            body: "accion=agregar&idProducto=" + encodeURIComponent(idProducto)
                        })
                        .then(response => response.text())
                        .then(data => {
                            if (data === "ok") {
                                // Mostrar mensaje de éxito
                                alert("Producto agregado al carrito ✅");
                                actualizarContadorCarrito(); // 🔁 Actualizar contador
                            } else {
                                // Mostrar mensaje de error
                                alert("No se pudo agregar al carrito ❌");
                            }
                        })
                        .catch(error => {
                            console.error("Error en AJAX:", error);
                        });
                    });
                });
            });
        </script>
    </body>
</html>
