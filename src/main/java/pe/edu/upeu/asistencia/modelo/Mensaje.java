package pe.edu.upeu.asistencia.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mensajes")
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario remitente;

    @ManyToOne
    private Usuario destinatario;

    @Column(length = 500)
    private String contenido;

    private LocalDateTime fechaHora;

    public Mensaje() {}

    public Long getId() { return id; }
    public void setId(Long v) { id = v; }
    public Usuario getRemitente() { return remitente; }
    public void setRemitente(Usuario v) { remitente = v; }
    public Usuario getDestinatario() { return destinatario; }
    public void setDestinatario(Usuario v) { destinatario = v; }
    public String getContenido() { return contenido; }
    public void setContenido(String v) { contenido = v; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime v) { fechaHora = v; }
}
