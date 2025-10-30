package pe.edu.upeu.asistencia.repository;

import pe.edu.upeu.asistencia.model.Asistencia;
import pe.edu.upeu.asistencia.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {

    /**
     * Busca asistencias por usuario
     */
    List<Asistencia> findByUsuario(Usuario usuario);

    /**
     * Busca asistencias por fecha
     */
    List<Asistencia> findByFecha(LocalDate fecha);

    /**
     * Busca asistencias por usuario y fecha (para verificar si ya registró hoy)
     */
    List<Asistencia> findByUsuarioAndFecha(Usuario usuario, LocalDate fecha);  // ✅ AGREGA ESTE MÉTODO
}