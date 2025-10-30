package pe.edu.upeu.asistencia.service;

import pe.edu.upeu.asistencia.model.Asistencia;
import pe.edu.upeu.asistencia.model.Usuario;
import pe.edu.upeu.asistencia.repository.AsistenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Servicio para la gestión de asistencias.
 * Permite registrar y consultar asistencias de usuarios.
 *
 * @author Tu Nombre
 * @version 1.0
 * @since 2024
 */
@Service
@Transactional
public class AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    /**
     * Registra la asistencia de entrada de un usuario.
     * Verifica que no exista ya un registro para el día actual.
     *
     * @param usuario Usuario que marca asistencia
     * @return Asistencia registrada
     * @throws RuntimeException si ya existe un registro para hoy
     */
    public Asistencia registrarAsistencia(Usuario usuario) {
        // Verificar si ya registró hoy
        List<Asistencia> asistenciasHoy = asistenciaRepository.findByUsuarioAndFecha(usuario, LocalDate.now());

        if (!asistenciasHoy.isEmpty()) {
            throw new RuntimeException("Ya existe un registro de asistencia para hoy");
        }

        Asistencia asistencia = new Asistencia();
        asistencia.setUsuario(usuario);
        asistencia.setFecha(LocalDate.now());
        asistencia.setHoraEntrada(LocalTime.now());  // ✅ Usar LocalTime.now()
        asistencia.setEstado("PRESENTE");

        return asistenciaRepository.save(asistencia);
    }

    /**
     * Registra la hora de salida de una asistencia.
     *
     * @param asistenciaId ID de la asistencia
     * @return Asistencia actualizada
     * @throws RuntimeException si no se encuentra la asistencia
     */
    public Asistencia registrarSalida(Long asistenciaId) {
        Asistencia asistencia = asistenciaRepository.findById(asistenciaId)
                .orElseThrow(() -> new RuntimeException("Asistencia no encontrada"));

        asistencia.setHoraSalida(LocalTime.now());  // ✅ Usar LocalTime.now()
        return asistenciaRepository.save(asistencia);
    }

    /**
     * Lista todas las asistencias de un usuario.
     *
     * @param usuario Usuario a consultar
     * @return Lista de asistencias
     */
    public List<Asistencia> listarPorUsuario(Usuario usuario) {
        return asistenciaRepository.findByUsuario(usuario);
    }

    /**
     * Lista todas las asistencias de una fecha específica.
     *
     * @param fecha Fecha a consultar
     * @return Lista de asistencias
     */
    public List<Asistencia> listarPorFecha(LocalDate fecha) {
        return asistenciaRepository.findByFecha(fecha);
    }

    /**
     * Lista todas las asistencias del sistema.
     *
     * @return Lista de todas las asistencias
     */
    public List<Asistencia> listarTodas() {
        return asistenciaRepository.findAll();
    }

    /**
     * Busca una asistencia por su ID.
     *
     * @param id ID de la asistencia
     * @return Asistencia encontrada
     * @throws RuntimeException si no se encuentra
     */
    public Asistencia buscarPorId(Long id) {
        return asistenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asistencia no encontrada"));
    }

    /**
     * Elimina una asistencia por su ID.
     *
     * @param id ID de la asistencia a eliminar
     */
    public void eliminarAsistencia(Long id) {
        asistenciaRepository.deleteById(id);
    }

    /**
     * Verifica si un usuario ya registró asistencia hoy.
     *
     * @param usuario Usuario a verificar
     * @return true si ya registró hoy, false si no
     */
    public boolean yaRegistroHoy(Usuario usuario) {
        List<Asistencia> asistenciasHoy = asistenciaRepository.findByUsuarioAndFecha(usuario, LocalDate.now());
        return !asistenciasHoy.isEmpty();
    }
}