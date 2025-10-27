package pe.edu.upeu.asistencia.repositorio;

import pe.edu.upeu.asistencia.enums.VacacionEstado;
import pe.edu.upeu.asistencia.modelo.Vacacion;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class VacacionRepository {
    private static final VacacionRepository INSTANCE = new VacacionRepository();
    public static VacacionRepository getInstance() { return INSTANCE; }

    private final Map<Long, Vacacion> map = new LinkedHashMap<>();
    private final AtomicLong seq = new AtomicLong(1);

    public Vacacion save(Vacacion v) {
        if (v.getId() == null) v.setId(seq.getAndIncrement());
        map.put(v.getId(), v);
        return v;
    }

    public List<Vacacion> findAll() {
        return new ArrayList<>(map.values());
    }

    public List<Vacacion> findByEmpleadoId(Long empleadoId) {
        return map.values().stream().filter(v -> v.getEmpleado()!=null && v.getEmpleado().getId().equals(empleadoId)).collect(Collectors.toList());
    }

    public List<Vacacion> findPendientes() {
        return map.values().stream().filter(v -> v.getEstado() == VacacionEstado.PENDIENTE).collect(Collectors.toList());
    }

    public Optional<Vacacion> findById(Long id) { return Optional.ofNullable(map.get(id)); }
}
