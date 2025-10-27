package pe.edu.upeu.asistencia.modelo;

import java.time.LocalDateTime;

public class Asistencia {
    private Long id;
    private Usuario empleado;
    private LocalDateTime entrada;
    private LocalDateTime salida;

    public Asistencia() {}

    public Asistencia(Long id, Usuario empleado, LocalDateTime entrada, LocalDateTime salida) {
        this.id = id;
        this.empleado = empleado;
        this.entrada = entrada;
        this.salida = salida;
    }

    public Long getId() {
        return id;
    } public void setId(Long id) {
        this.id = id;
    }
    public Usuario getEmpleado() {
        return empleado;
    }
    public void setEmpleado(Usuario empleado) {
        this.empleado = empleado;
    }
    public LocalDateTime getEntrada() {
        return entrada;
    }
    public void setEntrada(LocalDateTime entrada) {
        this.entrada = entrada;
    }
    public LocalDateTime getSalida() {
        return salida;
    }
    public void setSalida(LocalDateTime salida) {
        this.salida = salida;
    }
}
