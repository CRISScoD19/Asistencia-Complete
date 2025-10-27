package pe.edu.upeu.asistencia.servicio;

import pe.edu.upeu.asistencia.modelo.Asistencia;
import pe.edu.upeu.asistencia.repositorio.AsistenciaRepository;

import java.time.LocalDateTime;
import java.util.List;

public class AsistenciaService {
    private final AsistenciaRepository repo = AsistenciaRepository.getInstance();

    public Asistencia registrarEntradaSalida(Asistencia a) {
        // If there is an open attendance for the employee (salida == null), set salida; else create new entrada
        var list = repo.findByEmpleadoId(a.getEmpleado().getId());
        var open = list.stream().filter(x -> x.getSalida() == null).findFirst();
        if (open.isPresent()) {
            var existing = open.get();
            existing.setSalida(LocalDateTime.now());
            return repo.save(existing);
        } else {
            a.setEntrada(LocalDateTime.now());
            return repo.save(a);
        }
    }

    public List<Asistencia> listarPorEmpleado(Long id) {
        return repo.findByEmpleadoId(id);
    }
}
