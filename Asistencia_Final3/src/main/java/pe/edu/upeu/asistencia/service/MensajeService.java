package pe.edu.upeu.asistencia.service;

import pe.edu.upeu.asistencia.model.Mensaje;
import pe.edu.upeu.asistencia.model.Usuario;
import pe.edu.upeu.asistencia.repository.MensajeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MensajeService {

    private final MensajeRepository mensajeRepository;

    // ✅ CONSTRUCTOR CORREGIDO
    public MensajeService(MensajeRepository mensajeRepository) {
        this.mensajeRepository = mensajeRepository;
    }

    /**
     * Enviar mensaje
     */
    public Mensaje enviarMensaje(Usuario emisor, Usuario receptor, String asunto, String contenido) {
        Mensaje mensaje = new Mensaje();
        mensaje.setEmisor(emisor);
        mensaje.setReceptor(receptor);
        mensaje.setAsunto(asunto);
        mensaje.setContenido(contenido);
        mensaje.setLeido(false);

        return mensajeRepository.save(mensaje);
    }

    /**
     * Obtener mensajes recibidos
     */
    public List<Mensaje> obtenerMensajesRecibidos(Usuario receptor) {
        return mensajeRepository.findByReceptorOrderByFechaEnvioDesc(receptor);
    }

    /**
     * Obtener mensajes enviados
     */
    public List<Mensaje> obtenerMensajesEnviados(Usuario emisor) {
        return mensajeRepository.findByEmisorOrderByFechaEnvioDesc(emisor);
    }

    /**
     * Obtener mensajes no leídos
     */
    public List<Mensaje> obtenerMensajesNoLeidos(Usuario receptor) {
        return mensajeRepository.findByReceptorAndLeidoFalseOrderByFechaEnvioDesc(receptor);
    }

    /**
     * Contar mensajes no leídos
     */
    public long contarMensajesNoLeidos(Usuario receptor) {
        return mensajeRepository.countByReceptorAndLeidoFalse(receptor);
    }

    /**
     * Marcar mensaje como leído
     */
    public void marcarComoLeido(Long mensajeId) {
        Optional<Mensaje> mensaje = mensajeRepository.findById(mensajeId);

        if (mensaje.isPresent()) {
            mensaje.get().setLeido(true);
            mensajeRepository.save(mensaje.get());
        }
    }

    /**
     * Marcar todos los mensajes de un receptor como leídos
     */
    public void marcarTodosComoLeidos(Usuario receptor) {
        List<Mensaje> mensajesNoLeidos = obtenerMensajesNoLeidos(receptor);

        for (Mensaje mensaje : mensajesNoLeidos) {
            mensaje.setLeido(true);
            mensajeRepository.save(mensaje);
        }
    }

    /**
     * Obtener conversación entre dos usuarios
     */
    public List<Mensaje> obtenerConversacion(Usuario usuario1, Usuario usuario2) {
        return mensajeRepository.findConversacion(usuario1, usuario2);
    }

    /**
     * Obtener últimos 20 mensajes recibidos
     */
    public List<Mensaje> obtenerUltimosMensajes(Usuario receptor) {
        List<Mensaje> todos = mensajeRepository.findByReceptorOrderByFechaEnvioDesc(receptor);
        return todos.size() > 20 ? todos.subList(0, 20) : todos;
    }

    /**
     * Eliminar mensaje
     */
    public void eliminar(Long id) {
        mensajeRepository.deleteById(id);
    }

    /**
     * Buscar mensaje por ID
     */
    public Optional<Mensaje> buscarPorId(Long id) {
        return mensajeRepository.findById(id);
    }

    /**
     * Listar todos los mensajes (para admin)
     */
    public List<Mensaje> listarTodos() {
        return mensajeRepository.findAll();
    }
}