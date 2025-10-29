package pe.edu.upeu.asistencia.modelo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vacaciones")
public class Vacacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario empleado;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String motivo;

    public Vacacion() {}

    public Long getId() { return id; }
    public void setId(Long v) { id = v; }
    public Usuario getEmpleado() { return empleado; }
    public void setEmpleado(Usuario v) { empleado = v; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate v) { fechaInicio = v; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate v) { fechaFin = v; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String v) { motivo = v; }
}
