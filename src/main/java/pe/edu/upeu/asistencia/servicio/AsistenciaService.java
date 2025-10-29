package pe.edu.upeu.asistencia.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.asistencia.modelo.Asistencia;
import pe.edu.upeu.asistencia.repositorio.AsistenciaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AsistenciaService {

    @Autowired
    private AsistenciaRepository repo;

    public Asistencia marcarAsistenciaPorUsuarioId(Long usuarioId) {
        Asistencia a = new Asistencia();
        pe.edu.upeu.asistencia.modelo.Usuario u = new pe.edu.upeu.asistencia.modelo.Usuario();
        u.setId(usuarioId);
        a.setEmpleado(u);
        a.setFechaHora(LocalDateTime.now());
        return repo.save(a);
    }

    public List<Asistencia> listarAsistencias() { return repo.findAll(); }
    public List<Asistencia> listarPorEmpleado(Long id) { return repo.findByEmpleadoId(id); }
    public Optional<Asistencia> findById(Long id) { return repo.findById(id); }
}
