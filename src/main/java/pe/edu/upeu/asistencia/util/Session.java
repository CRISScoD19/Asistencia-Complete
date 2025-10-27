package pe.edu.upeu.asistencia.util;

import pe.edu.upeu.asistencia.modelo.Usuario;

public class Session {
    private static Usuario currentUser;
    public static Usuario getCurrentUser() { return currentUser; }
    public static void setCurrentUser(Usuario u) { currentUser = u; }
}
