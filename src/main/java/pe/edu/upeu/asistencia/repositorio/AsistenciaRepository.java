package pe.edu.upeu.asistencia.repositorio;

import pe.edu.upeu.asistencia.modelo.Asistencia;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class AsistenciaRepository {
    private static final AsistenciaRepository INSTANCE = new AsistenciaRepository();
    public static AsistenciaRepository getInstance() { return INSTANCE; }

    private final Map<Long, Asistencia> map = new LinkedHashMap<>();
    private final AtomicLong seq = new AtomicLong(1);

    public Asistencia save(Asistencia a) {
        if (a.getId()==null) a.setId(seq.getAndIncrement()); map.put(a.getId(), a); return a;
    }
    public List<Asistencia> findAll() {
        return new ArrayList<>(map.values());
    }
    public List<Asistencia> findByEmpleadoId(Long id) {
        return map.values().stream().filter(x->x.getEmpleado()!=null && x.getEmpleado().getId().equals(id)).collect(Collectors.toList());
    }
}
