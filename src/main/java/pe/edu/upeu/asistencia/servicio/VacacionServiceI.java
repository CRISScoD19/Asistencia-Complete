package pe.edu.upeu.asistencia.servicio;

import pe.edu.upeu.asistencia.modelo.Vacacion;

import java.util.List;

public interface VacacionServiceI {

    void save(Vacacion vacacion);
    List<Vacacion> findAll();
    Vacacion update(Vacacion vacacion);
    void delete(Long id);

    Vacacion findById(Long id);
}
