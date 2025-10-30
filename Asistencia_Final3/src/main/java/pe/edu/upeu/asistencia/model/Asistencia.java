package pe.edu.upeu.asistencia.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "asistencia")
public class Asistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDate fecha;  // ✅ Para la fecha

    @Column(nullable = false)
    private LocalTime horaEntrada;  // ✅ Para la hora de entrada

    private LocalTime horaSalida;  // ✅ Para la hora de salida (puede ser null)

    @Column(nullable = false)
    private String estado = "PRESENTE";
}