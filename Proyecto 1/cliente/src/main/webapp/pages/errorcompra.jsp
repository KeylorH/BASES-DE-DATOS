<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es" class="h-full">
    <head>
        <meta charset="UTF-8">
        <title>Error en la Compra</title>
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
            .error-card {
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
                <div class="error-card rounded-2xl shadow-2xl overflow-hidden">
                    <div class="gradient-bg p-6 text-white text-center">
                        <div class="flex justify-center items-center">
                            <i class="fas fa-exclamation-triangle text-4xl mr-2"></i>
                            <span class="text-2xl font-bold">Error en la Compra</span>
                        </div>
                    </div>
                    <div class="p-8 text-center">
                        <p class="text-lg font-semibold text-gray-700">Ha ocurrido un error durante el proceso de compra.</p>
                        <p class="text-gray-600">Por favor, inténtalo de nuevo más tarde.</p>
                        <a href="<%= request.getContextPath() %>/pages/productosPorCategoria.jsp?idCategoria=1" class="mt-4 inline-block px-6 py-2 bg-indigo-600 text-white rounded-lg hover:bg-indigo-700">Volver a los Productos</a>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
