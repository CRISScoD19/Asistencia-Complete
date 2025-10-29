package pe.edu.upeu.asistencia.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "horarios")
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre; // ejemplo: "Turno mañana"
    private String horaInicio; // "08:00"
    private String horaFin;    // "17:00"

    public Horario() {}

    public Long getId() { return id; }
    public void setId(Long v) { id = v; }
    public String getNombre() { return nombre; }
    public void setNombre(String v) { nombre = v; }
    public String getHoraInicio() { return horaInicio; }
    public void setHoraInicio(String v) { horaInicio = v; }
    public String getHoraFin() { return horaFin; }
    public void setHoraFin(String v) { horaFin = v; }
}
