package pe.edu.upeu.asistencia.util;

import pe.edu.upeu.asistencia.modelo.Usuario;

public class SessionManager {

    private static Usuario usuarioActual;

    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static void setUsuarioActual(Usuario u) {
        usuarioActual = u;
    }

    public static void clear() {
        usuarioActual = null;
    }

    public static boolean isLoggedIn() {
        return usuarioActual != null;
    }
}
