package pe.edu.upeu.asistencia.modelo;

import java.time.LocalTime;

public class Horario {
    private Long id;
    private Usuario empleado;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String dias;

    public Horario() {}

    public Horario(Long id, Usuario empleado, LocalTime horaInicio, LocalTime horaFin, String dias) {
        this.id = id;
        this.empleado = empleado;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.dias = dias;
    }

    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public Usuario getEmpleado() { return empleado; } public void setEmpleado(Usuario empleado) { this.empleado = empleado; }
    public LocalTime getHoraInicio() { return horaInicio; } public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    public LocalTime getHoraFin() { return horaFin; } public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }
    public String getDias() { return dias; } public void setDias(String dias) { this.dias = dias; }
}
