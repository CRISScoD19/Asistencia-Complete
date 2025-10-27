package pe.edu.upeu.asistencia.modelo;

import java.time.LocalDate;
import pe.edu.upeu.asistencia.enums.VacacionEstado;

public class Vacacion {
    private Long id;
    private Usuario empleado;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private VacacionEstado estado;

    public Vacacion() {}

    public Vacacion(Long id, Usuario empleado, LocalDate fechaInicio, LocalDate fechaFin, VacacionEstado estado) {
        this.id = id;
        this.empleado = empleado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Usuario getEmpleado() {
        return empleado;
    }
    public void setEmpleado(Usuario empleado) {
        this.empleado = empleado;
    }
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public LocalDate getFechaFin() {
        return fechaFin;
    }
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
    public VacacionEstado getEstado() {
        return estado;
    }
    public void setEstado(VacacionEstado estado) {
        this.estado = estado;
    }
}
