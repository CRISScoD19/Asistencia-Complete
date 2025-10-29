package pe.edu.upeu.asistencia.modelo;

import jakarta.persistence.*;
import pe.edu.upeu.asistencia.enums.Role;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private Role rol;

    public Usuario() {}

    // getters y setters (sin usar this)
    public Long getId() { return id; }
    public void setId(Long v) { id = v; }
    public String getNombre() { return nombre; }
    public void setNombre(String v) { nombre = v; }
    public String getUsername() { return username; }
    public void setUsername(String v) { username = v; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String v) { passwordHash = v; }
    public Role getRol() { return rol; }
    public void setRol(Role v) { rol = v; }
}
