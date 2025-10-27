# Asistencia_Complete

Proyecto JavaFX (Maven) - Sistema de Asistencia (versión para tarea)

## Cómo ejecutar
1. Descomprime el ZIP y abre el proyecto en IntelliJ como proyecto Maven.
2. Ejecuta: `mvn clean javafx:run`
   o ejecuta la clase `pe.edu.upeu.asistencia.App` desde IntelliJ.

## Credenciales por defecto
- Admin: usuario `admin`, contraseña `admin` (rol ADMIN).

## Funcionalidades incluidas
- Login (admin y empleados).
- Admin: registrar empleados, asignar horarios, ver y aprobar/rechazar vacaciones.
- Empleado: ver horario, registrar entrada/salida, solicitar vacaciones, ver historial.
- Persistencia en memoria (simulación) dentro de los repositorios singleton.
