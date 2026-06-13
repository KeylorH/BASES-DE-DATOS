<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es" class="h-full bg-gray-50">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="ElectroTech - Tienda de tecnología y electrónica">
        <meta name="author" content="ElectroTech">
        <title>Registro Exitoso | ElectroTech</title>
        <!-- TailwindCSS -->
        <script src="https://cdn.tailwindcss.com"></script>
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <style>
            .gradient-bg {
                background: linear-gradient(135deg, #6e8efb, #a777e3);
            }
            .checkmark-circle {
                stroke-dasharray: 166;
                stroke-dashoffset: 166;
                stroke-width: 2;
                stroke-miterlimit: 10;
                stroke: #4CAF50;
                animation: stroke 0.6s cubic-bezier(0.65, 0, 0.45, 1) forwards;
            }
            .checkmark {
                width: 56px;
                height: 56px;
                border-radius: 50%;
                display: block;
                stroke-width: 2;
                stroke: #fff;
                stroke-miterlimit: 10;
                margin: 10% auto;
                box-shadow: 0 0 0 rgba(76, 175, 80, 0.4);
                animation: fill .4s ease-in-out .4s forwards, scale .3s ease-in-out .9s both;
            }
            .checkmark-check {
                transform-origin: 50% 50%;
                stroke-dasharray: 48;
                stroke-dashoffset: 48;
                animation: stroke 0.3s cubic-bezier(0.65, 0, 0.45, 1) 0.8s forwards;
            }
            @keyframes stroke {
                100% {
                    stroke-dashoffset: 0;
                }
            }
            @keyframes scale {
                0%, 100% {
                    transform: none;
                }
                50% {
                    transform: scale3d(1.1, 1.1, 1);
                }
            }
            @keyframes fill {
                100% {
                    box-shadow: inset 0 0 0 100px #4CAF50;
                }
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
                        <h1 class="mt-4 text-xl font-semibold">¡Registro exitoso!</h1>
                    </div>

                    <div class="p-8 text-center">
                        <svg class="checkmark" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 52 52">
                        <circle class="checkmark-circle" cx="26" cy="26" r="25" fill="none"/>
                        <path class="checkmark-check" fill="none" d="M14.1 27.2l7.1 7.2 16.7-16.8"/>
                        </svg>

                        <h3 class="text-xl font-semibold text-gray-800 mb-2">¡Bienvenido a ElectroTech!</h3>
                        <p class="text-gray-600 mb-6">Tu cuenta ha sido creada exitosamente. Ahora puedes iniciar sesión y disfrutar de nuestros productos.</p>

                        <a href="login.jsp" 
                           class="gradient-bg w-full flex justify-center py-2 px-4 border border-transparent rounded-lg shadow-sm text-sm font-medium text-white hover:opacity-90 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 transition duration-200">
                            <i class="fas fa-sign-in-alt mr-2"></i> Ir a Inicio de Sesión
                        </a>
                    </div>
                </div>

                <div class="mt-6 text-center text-xs text-gray-500">
                    &copy; 2025 ElectroTech. Todos los derechos reservados.
                </div>
            </div>
        </div>
    </body>
</html>