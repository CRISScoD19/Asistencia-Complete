package pe.edu.upeu.asistencia.servicio;

import pe.edu.upeu.asistencia.enums.VacacionEstado;
import pe.edu.upeu.asistencia.modelo.Vacacion;
import pe.edu.upeu.asistencia.repositorio.VacacionRepository;

import java.util.List;
import java.util.Optional;

public class VacacionService {
    private final VacacionRepository repo = VacacionRepository.getInstance();
    public Vacacion solicitar(Vacacion v) { v.setEstado(VacacionEstado.PENDIENTE); return repo.save(v); }
    public List<Vacacion> listarPorEmpleado(Long id) { return repo.findByEmpleadoId(id); }
    public List<Vacacion> listarPendientes() { return repo.findPendientes(); }
    public Optional<Vacacion> obtener(Long id) { return repo.findById(id); }
    public Vacacion actualizarEstado(Long id, VacacionEstado estado) { var op = repo.findById(id); if(op.isPresent()){ var v = op.get(); v.setEstado(estado); repo.save(v); return v; } return null; }
}
