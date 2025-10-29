package pe.edu.upeu.asistencia.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.asistencia.modelo.Vacacion;
import pe.edu.upeu.asistencia.repositorio.VacacionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VacacionServiceIMP implements VacacionServiceI{

    @Autowired
    VacacionRepository vacacionRepository;

    @Override
    public void save(Vacacion vacacion) {
        vacacionRepository.save(vacacion);
    }

    @Override
    public List<Vacacion> findAll() {
        return vacacionRepository.findAll();
    }

    @Override
    public Vacacion update(Vacacion vacacion) {
        return vacacionRepository.save(vacacion);
    }

    @Override
    public void delete(Long id) {
        vacacionRepository.deleteById(id);
    }

    @Override
    public Vacacion findById(Long id) {
        return vacacionRepository.findById(id).orElse(null);
    }
}
