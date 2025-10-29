package pe.edu.upeu.asistencia.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.asistencia.enums.Role;
import pe.edu.upeu.asistencia.modelo.Usuario;
import pe.edu.upeu.asistencia.repositorio.UsuarioRepository;
import pe.edu.upeu.asistencia.util.HashUtil;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository repo;
    @Autowired
    private UsuarioRepository usuarioRepository;

    // ✅ Crear usuario admin por defecto si no hay ninguno
    @PostConstruct
    public void initAdmin() {
        if (repo.count() == 0) {
            Usuario admin = new Usuario();
            admin.setNombre("Administrador");
            admin.setUsername("admin");
            admin.setPasswordHash(HashUtil.sha256("admin"));
            admin.setRol(Role.ADMIN);
            repo.save(admin);
            System.out.println("✅ Usuario administrador creado -> usuario: admin / clave: admin");
        }
    }

    public List<Usuario> findAll() {
        return repo.findAll();
    }

    // ✅ Autenticación segura y depurable
    public Optional<Usuario> authenticate(String username, String password) {
        System.out.println("Buscando usuario: " + username);
        Optional<Usuario> opt = repo.findByUsername(username);

        if (opt.isEmpty()) {
            System.out.println("❌ Usuario no encontrado");
            return Optional.empty();
        }

        Usuario u = opt.get();
        String hashIngresado = HashUtil.sha256(password);
        System.out.println("➡ Hash almacenado: " + u.getPasswordHash());
        System.out.println("➡ Hash ingresado : " + hashIngresado);

        if (u.getPasswordHash().equals(hashIngresado)) {
            System.out.println(" Contraseña correcta");
            return Optional.of(u);
        } else {
            System.out.println(" Contraseña incorrecta");
            return Optional.empty();
        }
    }

    public Usuario save(Usuario u) {
        if (u.getPasswordHash() == null || u.getPasswordHash().length() < 40) {
            u.setPasswordHash(HashUtil.sha256(u.getPasswordHash()));
        }
        return repo.save(u);
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }
    }

