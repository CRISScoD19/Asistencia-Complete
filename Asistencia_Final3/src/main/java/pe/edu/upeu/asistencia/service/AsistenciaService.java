package pe.edu.upeu.asistencia.service;

import pe.edu.upeu.asistencia.dto.ResumenAsistenciaDTO;
import pe.edu.upeu.asistencia.enums.EstadoAsistencia;
import pe.edu.upeu.asistencia.enums.Rol;
import pe.edu.upeu.asistencia.model.Asistencia;
import pe.edu.upeu.asistencia.model.Usuario;
import pe.edu.upeu.asistencia.repository.AsistenciaRepository;
import pe.edu.upeu.asistencia.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AsistenciaService {

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Registrar entrada de asistencia
    public Asistencia registrarAsistencia(Usuario usuario) {
        if (yaRegistroHoy(usuario)) {
            throw new RuntimeException("Ya existe un registro de asistencia para hoy");
        }

        Asistencia asistencia = new Asistencia();
        asistencia.setUsuario(usuario);
        asistencia.setFecha(LocalDate.now());
        asistencia.setHoraEntrada(LocalTime.now());
        asistencia.setEstado(EstadoAsistencia.PRESENTE.name());

        return asistenciaRepository.save(asistencia);
    }

    // Registrar salida de asistencia
    public Asistencia registrarSalida(Long asistenciaId) {
        Asistencia asistencia = asistenciaRepository.findById(asistenciaId)
                .orElseThrow(() -> new RuntimeException("Asistencia no encontrada"));

        if (asistencia.getHoraSalida() != null) {
            throw new RuntimeException("La salida ya fue registrada");
        }

        asistencia.setHoraSalida(LocalTime.now());
        return asistenciaRepository.save(asistencia);
    }

    // Listar todas las asistencias
    public List<Asistencia> listarTodas() {
        return asistenciaRepository.findAll();
    }

    // Listar asistencias de un usuario
    public List<Asistencia> listarPorUsuario(Usuario usuario) {
        return asistenciaRepository.findByUsuario(usuario);
    }

    // Listar asistencias por fecha
    public List<Asistencia> listarPorFecha(LocalDate fecha) {
        return asistenciaRepository.findByFecha(fecha);
    }

    // Generar resumen de todos los empleados
    public List<ResumenAsistenciaDTO> generarResumenPorEmpleado() {
        List<ResumenAsistenciaDTO> resumen = new ArrayList<>();
        List<Usuario> empleados = usuarioRepository.findByRol(Rol.EMPLEADO);

        for (Usuario empleado : empleados) {
            resumen.add(generarResumenPorEmpleado(empleado));
        }

        return resumen;
    }

    // Generar resumen de un empleado
    public ResumenAsistenciaDTO generarResumenPorEmpleado(Usuario empleado) {
        long presente = asistenciaRepository.countByUsuarioAndEstado(empleado, EstadoAsistencia.PRESENTE.name());
        long tarde = asistenciaRepository.countByUsuarioAndEstado(empleado, EstadoAsistencia.TARDE.name());
        long ausente = asistenciaRepository.countByUsuarioAndEstado(empleado, EstadoAsistencia.AUSENTE.name());
        long justificado = asistenciaRepository.countByUsuarioAndEstado(empleado, EstadoAsistencia.JUSTIFICADO.name());

        return new ResumenAsistenciaDTO(
                empleado.getId(),
                empleado.getNombre(),
                presente,
                tarde,
                ausente,
                justificado
        );
    }

    // Generar resumen filtrado por periodo
    public List<ResumenAsistenciaDTO> generarResumenPorPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
        List<ResumenAsistenciaDTO> resumen = new ArrayList<>();
        List<Usuario> empleados = usuarioRepository.findByRol(Rol.EMPLEADO);

        for (Usuario empleado : empleados) {
            List<Asistencia> asistenciasPeriodo = asistenciaRepository.findByUsuarioAndFechaBetween(empleado, fechaInicio, fechaFin);

            long presente = asistenciasPeriodo.stream()
                    .filter(a -> a.getEstado().equals(EstadoAsistencia.PRESENTE.name()))
                    .count();
            long tarde = asistenciasPeriodo.stream()
                    .filter(a -> a.getEstado().equals(EstadoAsistencia.TARDE.name()))
                    .count();
            long ausente = asistenciasPeriodo.stream()
                    .filter(a -> a.getEstado().equals(EstadoAsistencia.AUSENTE.name()))
                    .count();
            long justificado = asistenciasPeriodo.stream()
                    .filter(a -> a.getEstado().equals(EstadoAsistencia.JUSTIFICADO.name()))
                    .count();

            resumen.add(new ResumenAsistenciaDTO(
                    empleado.getId(),
                    empleado.getNombre(),
                    presente,
                    tarde,
                    ausente,
                    justificado
            ));
        }

        return resumen;
    }

    // Buscar asistencia por ID
    public Asistencia buscarPorId(Long id) {
        return asistenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asistencia no encontrada"));
    }

    // Eliminar asistencia
    public void eliminarAsistencia(Long id) {
        asistenciaRepository.deleteById(id);
    }

    // Verificar si ya registró asistencia hoy
    public boolean yaRegistroHoy(Usuario usuario) {
        return !asistenciaRepository.findByUsuarioAndFecha(usuario, LocalDate.now()).isEmpty();
    }
}
