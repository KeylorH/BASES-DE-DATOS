<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es" class="h-full">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Registro | ElectroTech</title>
        <!-- TailwindCSS -->
        <script src="https://cdn.tailwindcss.com"></script>
        <!-- Font Awesome -->
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
                        <h1 class="mt-4 text-xl font-semibold">Crea tu cuenta</h1>
                        <p class="text-sm opacity-90 mt-1">Únete a nuestra comunidad tecnológica</p>
                    </div>
                    
                    <div class="p-8">
                        <form class="space-y-4" action="SvRegistro" method="POST">
                            <div class="grid grid-cols-2 gap-4">
                                <div class="space-y-2">
                                    <label for="nombre" class="block text-sm font-medium text-gray-700">Nombre</label>
                                    <div class="relative">
                                        <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                            <i class="fas fa-user text-gray-400"></i>
                                        </div>
                                        <input type="text" id="nombre" name="nombre" required 
                                               class="input-focus block w-full pl-10 pr-3 py-2 border border-gray-300 rounded-lg focus:outline-none transition duration-200"
                                               placeholder="Tu nombre">
                                    </div>
                                </div>
                                
                                <div class="space-y-2">
                                    <label for="apellido" class="block text-sm font-medium text-gray-700">Apellido</label>
                                    <div class="relative">
                                        <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                            <i class="fas fa-user text-gray-400"></i>
                                        </div>
                                        <input type="text" id="apellido" name="apellido" required 
                                               class="input-focus block w-full pl-10 pr-3 py-2 border border-gray-300 rounded-lg focus:outline-none transition duration-200"
                                               placeholder="Tu apellido">
                                    </div>
                                </div>
                            </div>
                            
                            <div class="space-y-2">
                                <label for="correo" class="block text-sm font-medium text-gray-700">Correo electrónico</label>
                                <div class="relative">
                                    <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                        <i class="fas fa-envelope text-gray-400"></i>
                                    </div>
                                    <input type="email" id="correo" name="correo" required 
                                           class="input-focus block w-full pl-10 pr-3 py-2 border border-gray-300 rounded-lg focus:outline-none transition duration-200"
                                           placeholder="correo@ejemplo.com">
                                </div>
                            </div>
                            
                            <div class="space-y-2">
                                <label for="password" class="block text-sm font-medium text-gray-700">Contraseña</label>
                                <div class="relative">
                                    <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                        <i class="fas fa-lock text-gray-400"></i>
                                    </div>
                                    <input type="password" id="password" name="password" required 
                                           class="input-focus block w-full pl-10 pr-3 py-2 border border-gray-300 rounded-lg focus:outline-none transition duration-200"
                                           placeholder="••••••••">
                                </div>
                            </div>
                            
                            <div class="space-y-2">
                                <label for="distrito" class="block text-sm font-medium text-gray-700">Distrito</label>
                                <div class="relative">
                                    <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                        <i class="fas fa-map-marker-alt text-gray-400"></i>
                                    </div>
                                    <input type="number" id="distrito" name="distrito" required 
                                           class="input-focus block w-full pl-10 pr-3 py-2 border border-gray-300 rounded-lg focus:outline-none transition duration-200"
                                           placeholder="Número de distrito">
                                </div>
                            </div>
                            
                            <div class="flex items-center">
                                <input id="terms" name="terms" type="checkbox" required
                                       class="h-4 w-4 text-indigo-600 focus:ring-indigo-500 border-gray-300 rounded">
                                <label for="terms" class="ml-2 block text-sm text-gray-700">
                                    Acepto los <a href="#" class="text-indigo-600 hover:text-indigo-500">términos y condiciones</a>
                                </label>
                            </div>
                            
                            <button type="submit" 
                                    class="gradient-bg w-full flex justify-center py-3 px-4 border border-transparent rounded-lg shadow-sm text-sm font-medium text-white hover:opacity-90 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 transition duration-200 group">
                                <span class="absolute left-0 inset-y-0 flex items-center pl-3">
                                    <i class="fas fa-user-plus text-white"></i>
                                </span>
                                Registrar cuenta
                            </button>
                        </form>
                        
                        <div class="mt-6 text-center text-sm">
                            <p class="text-gray-500">
                                ¿Ya tienes una cuenta?
                                <a href="login.jsp" class="font-medium text-indigo-600 hover:text-indigo-500">Inicia sesión</a>
                            </p>
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