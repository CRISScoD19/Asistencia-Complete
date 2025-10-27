package pe.edu.upeu.asistencia.servicio;

import pe.edu.upeu.asistencia.modelo.Horario;
import pe.edu.upeu.asistencia.modelo.Usuario;
import pe.edu.upeu.asistencia.repositorio.HorarioRepository;
import java.time.LocalTime;
import java.util.Optional;

public class HorarioService {
    private final HorarioRepository repo = HorarioRepository.getInstance();

    public HorarioService() {}

    public Horario getOrCreateDefaultHorario(Usuario u) {
        Optional<Horario> opt = repo.findByEmpleado(u);
        if (opt.isPresent()) return opt.get();
        Horario h = new Horario(null, u, LocalTime.of(9,0), LocalTime.of(18,0), "LUN-MAR-MIE-JUE-VIE");
        return repo.save(h);
    }

    public Horario asignarHorario(Usuario u, LocalTime inicio, LocalTime fin, String dias) {
        Horario h = new Horario(null, u, inicio, fin, dias);
        return repo.save(h);
    }
}
