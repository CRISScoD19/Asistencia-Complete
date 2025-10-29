package pe.edu.upeu.asistencia.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.asistencia.modelo.Horario;
import pe.edu.upeu.asistencia.repositorio.HorarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class HorarioService {

    @Autowired
    private HorarioRepository repo;

    public Horario save(Horario h) { return repo.save(h); }
    public List<Horario> listar() { return repo.findAll(); }
    public Optional<Horario> findById(Long id) { return repo.findById(id); }
    public void delete(Long id) { repo.deleteById(id); }
}
