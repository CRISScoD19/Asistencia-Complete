package pe.edu.upeu.asistencia.modelo;

import pe.edu.upeu.asistencia.enums.Role;

public class Usuario {
    private Long id;
    private String nombre;
    private String username;
    private String passwordHash;
    private Role rol;

    public Usuario() {}

    public Usuario(Long id, String nombre, String username, String passwordHash, Role rol) {
        this.id = id;
        this.nombre = nombre;
        this.username = username;
        this.passwordHash = passwordHash;
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    public Role getRol() {
        return rol;
    } public void setRol(Role rol) {
        this.rol = rol;
    }
}
