package pe.edu.upeu.asistencia.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.asistencia.modelo.Mensaje;
import pe.edu.upeu.asistencia.modelo.Usuario;
import java.util.List;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    List<Mensaje> findByDestinatario(Usuario destinatario);
    List<Mensaje> findByRemitente(Usuario remitente);
}
