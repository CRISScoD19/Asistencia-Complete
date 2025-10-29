package pe.edu.upeu.asistencia.modelo;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "asistencias")
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario empleado;

    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;

    public Asistencia() {}

    public Long getId() { return id; }
    public void setId(Long v) { id = v; }
    public Usuario getEmpleado() { return empleado; }
    public void setEmpleado(Usuario v) { empleado = v; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime v) { fechaHora = v; }
}
