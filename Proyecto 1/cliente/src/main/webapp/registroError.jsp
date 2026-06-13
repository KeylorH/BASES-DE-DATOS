<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es" class="h-full bg-gray-50">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="ElectroTech - Tienda de tecnología y electrónica">
        <meta name="author" content="ElectroTech">
        <title>Error de Registro | ElectroTech</title>
        <!-- TailwindCSS -->
        <script src="https://cdn.tailwindcss.com"></script>
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <style>
            .gradient-bg {
                background: linear-gradient(135deg, #6e8efb, #a777e3);
            }
            body {
                background: 
                    linear-gradient(rgba(0, 0, 0, 0.7), rgba(0, 0, 0, 0.7)),
                    url('https://images.unsplash.com/photo-1662758392656-0e5d4b0f53fb?q=80&w=2080&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D');
                background-size: cover;
                background-position: center;
                background-attachment: fixed;
                background-repeat: no-repeat;
            }
            .login-card {
                backdrop-filter: blur(8px);
                background-color: rgba(255, 255, 255, 0.85);
                border: 1px solid rgba(255, 255, 255, 0.2);
            }
            .tech-icon {
                background: linear-gradient(135deg, #6e8efb, #a777e3);
                -webkit-background-clip: text;
                background-clip: text;
                color: transparent;
            }
        </style>
    </head>
    <body class="h-full">
        <div class="min-h-screen flex items-center justify-center p-4">
            <div class="w-full max-w-md">
                <div class="bg-white rounded-2xl shadow-xl overflow-hidden">
                    <div class="gradient-bg p-6 text-white text-center">
                        <div class="flex justify-center">
                            <i class="fas fa-laptop text-4xl mr-2"></i>
                            <span class="text-2xl font-bold">ElectroTech</span>
                        </div>
                        <h1 class="mt-4 text-xl font-semibold">Error en el registro</h1>
                    </div>
                    
                    <div class="p-8 text-center">
                        <div class="mb-6 p-4 bg-red-50 rounded-lg border border-red-200">
                            <i class="fas fa-exclamation-triangle text-red-500 text-2xl mb-2"></i>
                            <p class="text-red-600 font-medium">Error al crear la cuenta</p>
                            <p class="text-sm text-red-500 mt-1">El correo electrónico ya está registrado o hay un problema con los datos</p>
                        </div>
                        
                        <a href="registro.jsp" 
                           class="gradient-bg w-full flex justify-center py-2 px-4 border border-transparent rounded-lg shadow-sm text-sm font-medium text-white hover:opacity-90 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 transition duration-200">
                            <i class="fas fa-redo mr-2"></i> Intentar nuevamente
                        </a>
                        
                        <div class="mt-4">
                            <a href="login.jsp" class="text-sm text-indigo-600 hover:text-indigo-500">
                                ¿Ya tienes una cuenta? Inicia sesión
                            </a>
                        </div>
                    </div>
                </div>
                
                <div class="mt-6 text-center text-xs text-gray-500">
                    &copy; 2025 ElectroTech. Todos los derechos reservados.
                </div>
            </div>
        </div>
    </body>
</html>