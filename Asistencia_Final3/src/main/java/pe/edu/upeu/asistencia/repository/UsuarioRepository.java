package pe.edu.upeu.asistencia.repository;

import pe.edu.upeu.asistencia.model.Usuario;
import pe.edu.upeu.asistencia.enums.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar usuario por username
    Optional<Usuario> findByUsername(String username);

    // Verificar si existe un username
    boolean existsByUsername(String username);

    // Buscar por rol
    List<Usuario> findByRol(Rol rol);

    // Buscar por nombre (búsqueda parcial)
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);

}