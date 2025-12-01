# CuidaPetApp
Nombre App: CuidaPet.
Integrantes: Maria Carrero, Alejandro Acosta.
Funcionalidades de CuidaPet:
- Gestión de usuarios: registro, visualización y conexión con backend.
- Registro de mascotas: formulario validado y persistencia con Room.
- Exploración de razas: lista con imágenes desde The Dog API.
- Notificaciones de citas: recordatorios con lógica temporal.
- Conectividad estable: integración con Spring Boot y despliegue en AWS.
- Firma de app: generación de .jks y APK firmado para entrega.
- Cobertura de pruebas: configuración de JaCoCo y reportes en Android Studio.

EndPoints usados:
- GET /usuarios → Listar usuarios
- POST /usuarios → Registrar usuario
- GET /mascotas/usuario/{id} → Mascotas por usuario
- POST /mascotas → Registrar mascota
- GET https://api.thedogapi.com/v1/breeds → Obtener razas de perro

Pasos para ejecutar CuidaPet
- Abrir el proyecto
- Abre Android Studio
- Selecciona Open Project y carga la carpeta de tu proyecto CuidaPet
- Sincronizar dependencias
- Android Studio descargará las librerías (Glide, Retrofit, Room, etc.)
- Espera a que termine el Gradle Sync
- Configurar el backend
- Asegúrate de que tu backend en Spring Boot esté corriendo
- Verifica que la URL base en Retrofit apunte correctamente al servidor
- Seleccionar dispositivo/emulador
- Conecta tu celular con USB Debugging activado
- O usa un emulador configurado en Android Studio
- Ejecutar la app
- Haz clic en el botón Run 
- Android Studio compilará y desplegará la app en el dispositivo/emulador
- Probar funcionalidades
- Registro de usuarios y mascotas
- Visualización de razas desde The Dog API
