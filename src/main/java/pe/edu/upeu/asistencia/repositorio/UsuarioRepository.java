package pe.edu.upeu.asistencia.repositorio;

import pe.edu.upeu.asistencia.enums.Role;
import pe.edu.upeu.asistencia.modelo.Usuario;
import pe.edu.upeu.asistencia.util.HashUtil;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class UsuarioRepository {
    private static final UsuarioRepository INSTANCE = new UsuarioRepository();
    public static UsuarioRepository getInstance() { return INSTANCE; }

    private final Map<Long, Usuario> map = new LinkedHashMap<>();
    private final AtomicLong seq = new AtomicLong(1);

    private UsuarioRepository() {

        //en esta parte es donde el administrador tiene su usuario y contraseña

        Usuario admin = new Usuario(seq.getAndIncrement(),
                "Administrador", "admin", HashUtil.sha256("admin"), Role.ADMIN);
        map.put(admin.getId(), admin);
    }

    public List<Usuario> findAll() {
        return new ArrayList<>(map.values());
    }

    public Optional<Usuario> findByUsername(String username) {
        return map.values().stream().filter(u -> u.getUsername().equals(username)).findFirst();
    }

    public Optional<Usuario> findById(Long id) {
        return Optional.ofNullable(map.get(id));
    }

    public Usuario save(Usuario u) {
        if (u.getId() == null) u.setId(seq.getAndIncrement());
        map.put(u.getId(), u);
        return u;
    }

    public void deleteById(Long id) {
        map.remove(id);
    }

    public boolean checkPassword(Usuario u, String raw) {
        if (u.getPasswordHash() == null) return false;
        return u.getPasswordHash().equals(HashUtil.sha256(raw));
    }

    public void updatePassword(Long id, String raw) {
        Usuario u = map.get(id);
        if (u != null) u.setPasswordHash(HashUtil.sha256(raw));
    }
}
