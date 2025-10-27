package pe.edu.upeu.asistencia.repositorio;

import pe.edu.upeu.asistencia.modelo.Horario;
import pe.edu.upeu.asistencia.modelo.Usuario;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Optional;

public class HorarioRepository {
    private static final HorarioRepository INSTANCE = new HorarioRepository();
    public static HorarioRepository getInstance() { return INSTANCE; }

    private final Map<Long, Horario> map = new LinkedHashMap<>();
    private final AtomicLong seq = new AtomicLong(1);

    public Horario save(Horario h) {
        if (h.getId()==null) h.setId(seq.getAndIncrement()); map.put(h.getId(), h);
        return h;
    }
    public Optional<Horario> findByEmpleado(Usuario u) {
        return map.values().stream().filter(h -> h.getEmpleado()!=null && h.getEmpleado().getId().equals(u.getId())).findFirst();
    }
    public List<Horario> findAll() {
        return new ArrayList<>(map.values());
    }
}
