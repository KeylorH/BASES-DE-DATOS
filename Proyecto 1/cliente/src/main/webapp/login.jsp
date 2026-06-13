<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es" class="h-full">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="ElectroTech - Tienda de tecnología y electrónica">
        <meta name="author" content="ElectroTech">
        <title>Login | ElectroTech</title>
        <!-- TailwindCSS -->
        <script src="https://cdn.tailwindcss.com"></script>
        <!-- Font Awesome para íconos -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <style>
            .gradient-bg {
                background: linear-gradient(135deg, #6e8efb, #a777e3);
            }
            .input-focus:focus {
                box-shadow: 0 0 0 3px rgba(110, 142, 251, 0.3);
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
                <div class="login-card rounded-2xl shadow-2xl overflow-hidden">
                    <div class="gradient-bg p-6 text-white text-center">
                        <div class="flex justify-center items-center">
                            <i class="fas fa-laptop text-4xl mr-2"></i>
                            <span class="text-2xl font-bold">ElectroTech</span>
                        </div>
                        <h1 class="mt-4 text-xl font-semibold">Bienvenidos al sitio WEB</h1>
                        <p class="text-sm opacity-90 mt-1">Ingresa tus credenciales para acceder</p>
                    </div>
                    
                    <div class="p-8">
                        <form class="space-y-6" action="SvLogin" method="POST">
                            <div class="space-y-2">
                                <label for="email" class="block text-sm font-medium text-gray-700">Correo electrónico</label>
                                <div class="relative">
                                    <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                        <i class="fas fa-envelope text-gray-400"></i>
                                    </div>
                                    <input type="email" name="correo" id="email" autocomplete="email" required 
                                           class="input-focus block w-full pl-10 pr-3 py-2 border border-gray-300 rounded-lg focus:outline-none transition duration-200"
                                           placeholder="ejemplo@correo.com">
                                </div>
                            </div>
                            
                            <div class="space-y-2">
                                <label for="password" class="block text-sm font-medium text-gray-700">Contraseña</label>
                                <div class="relative">
                                    <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                        <i class="fas fa-lock text-gray-400"></i>
                                    </div>
                                    <input type="password" name="password" id="password" autocomplete="current-password" required 
                                           class="input-focus block w-full pl-10 pr-3 py-2 border border-gray-300 rounded-lg focus:outline-none transition duration-200"
                                           placeholder="••••••••">
                                </div>
                            </div>
                            
                            <div class="flex items-center justify-between">
                                <div class="flex items-center">
                                    <input id="remember-me" name="remember-me" type="checkbox" 
                                           class="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded">
                                    <label for="remember-me" class="ml-2 block text-sm text-gray-700">Recordarme</label>
                                </div>
                                <div class="text-sm">
                                    <a href="#" class="font-medium text-indigo-600 hover:text-indigo-500">¿Olvidaste tu contraseña?</a>
                                </div>
                            </div>
                            
                            <button type="submit" 
                                    class="gradient-bg w-full flex justify-center py-3 px-4 border border-transparent rounded-lg shadow-sm text-sm font-medium text-white hover:opacity-90 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 transition duration-200 group">
                                <span class="absolute left-0 inset-y-0 flex items-center pl-3">
                                    <i class="fas fa-sign-in-alt text-white group-hover:animate-bounce"></i>
                                </span>
                                Iniciar sesión
                            </button>
                        </form>
                        
                        <div class="mt-6">
                            <div class="relative">
                                <div class="absolute inset-0 flex items-center">
                                    <div class="w-full border-t border-gray-300"></div>
                                </div>
                                <div class="relative flex justify-center text-sm">
                                    <span class="px-2 bg-transparent text-gray-500">¿No tienes una cuenta?</span>
                                </div>
                            </div>
                            
                            <div class="mt-6">
                                <a href="registro.jsp" 
                                   class="w-full flex justify-center py-2 px-4 border border-gray-300 rounded-lg shadow-sm text-sm font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 transition duration-200">
                                    <i class="fas fa-user-plus mr-2 tech-icon"></i> Regístrate ahora
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="mt-6 text-center text-xs text-gray-300">
                    &copy; 2025 ElectroTech. Todos los derechos reservados.
                </div>
            </div>
        </div>
    </body>
</html>
