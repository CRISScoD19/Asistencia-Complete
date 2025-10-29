package pe.edu.upeu.asistencia.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.asistencia.modelo.Vacacion;
import pe.edu.upeu.asistencia.repositorio.VacacionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VacacionService {

    @Autowired
    private VacacionRepository repo;

    public Vacacion save(Vacacion v) { return repo.save(v); }
    public List<Vacacion> listar() { return repo.findAll(); }
    public Optional<Vacacion> findById(Long id) { return repo.findById(id); }
    public void delete(Long id) { repo.deleteById(id); }
}
