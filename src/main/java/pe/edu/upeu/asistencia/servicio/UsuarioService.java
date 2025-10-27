package pe.edu.upeu.asistencia.servicio;

import pe.edu.upeu.asistencia.enums.Role;
import pe.edu.upeu.asistencia.modelo.Usuario;
import pe.edu.upeu.asistencia.repositorio.UsuarioRepository;
import pe.edu.upeu.asistencia.util.HashUtil;

import java.util.List;
import java.util.Optional;

public class UsuarioService {
    private final UsuarioRepository repo = UsuarioRepository.getInstance();

    public Optional<Usuario> authenticate(String username, String password) {
        var op = repo.findByUsername(username);
        if (op.isPresent() && repo.checkPassword(op.get(), password)) return op;
        return Optional.empty();
    }

    public Usuario createEmpleado(String nombre, String username, String rawPassword) {
        String hash = HashUtil.sha256(rawPassword);
        Usuario u = new Usuario(null, nombre, username, hash, Role.EMPLEADO);
        return repo.save(u);
    }

    public List<Usuario> listar() { return repo.findAll(); }

    public Optional<Usuario> findByUsername(String u) { return repo.findByUsername(u); }

    public Optional<Usuario> findById(Long id) { return repo.findById(id); }

    public void delete(Long id) { repo.deleteById(id); }
}
