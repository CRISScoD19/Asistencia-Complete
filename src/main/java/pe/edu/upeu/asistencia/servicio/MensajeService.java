package pe.edu.upeu.asistencia.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upeu.asistencia.modelo.Mensaje;
import pe.edu.upeu.asistencia.modelo.Usuario;
import pe.edu.upeu.asistencia.repositorio.MensajeRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MensajeService {

    @Autowired
    private MensajeRepository repo;

    public Mensaje enviarMensaje(Usuario remitente, Usuario destinatario, String contenido) {
        Mensaje m = new Mensaje();
        m.setRemitente(remitente);
        m.setDestinatario(destinatario);
        m.setContenido(contenido);
        m.setFechaHora(LocalDateTime.now());
        return repo.save(m);
    }

    public List<Mensaje> obtenerRecibidos(Usuario u) { return repo.findByDestinatario(u); }
    public List<Mensaje> obtenerEnviados(Usuario u) { return repo.findByRemitente(u); }
}

