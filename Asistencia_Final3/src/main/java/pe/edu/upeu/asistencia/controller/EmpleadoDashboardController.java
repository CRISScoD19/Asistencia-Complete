package pe.edu.upeu.asistencia.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import pe.edu.upeu.asistencia.model.Asistencia;
import pe.edu.upeu.asistencia.model.Mensaje;
import pe.edu.upeu.asistencia.model.Usuario;
import pe.edu.upeu.asistencia.service.AsistenciaService;
import pe.edu.upeu.asistencia.service.MensajeService;
import pe.edu.upeu.asistencia.config.SessionManager;
import pe.edu.upeu.asistencia.config.StageManager;

import java.time.format.DateTimeFormatter;

@Component
public class EmpleadoDashboardController {

    @FXML private Label lblNombre;
    @FXML private Label lblUsername;
    @FXML private Label lblRol;
    @FXML private Button btnMarcarAsistencia;

    // Tabla Mis Asistencias
    @FXML private TableView<Asistencia> tblMisAsistencias;
    @FXML private TableColumn<Asistencia, String> colFecha;
    @FXML private TableColumn<Asistencia, String> colHoraEntrada;
    @FXML private TableColumn<Asistencia, String> colHoraSalida;
    @FXML private TableColumn<Asistencia, String> colEstado;

    // Tabla Mensajes
    @FXML private TableView<Mensaje> tblMensajes;
    @FXML private TableColumn<Mensaje, String> colEmisor;
    @FXML private TableColumn<Mensaje, String> colAsunto;
    @FXML private TableColumn<Mensaje, String> colLeido;
    @FXML private TextArea txtMensajeContenido;
    @FXML private Label lblMensajesNoLeidos;

    @Autowired
    private AsistenciaService asistenciaService;

    @Autowired
    private MensajeService mensajeService;

    @Autowired
    private ConfigurableApplicationContext springContext;

    @Autowired
    private StageManager stageManager;

    private Usuario usuarioActual;
    private ObservableList<Asistencia> asistencias = FXCollections.observableArrayList();
    private ObservableList<Mensaje> mensajes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        usuarioActual = SessionManager.getInstance().getUsuarioActual();

        mostrarDatosUsuario();
        configurarTablaAsistencias();
        configurarTablaMensajes();

        cargarAsistencias();
        cargarMensajes();
    }

    private void mostrarDatosUsuario() {
        lblNombre.setText(usuarioActual.getNombre());
        lblUsername.setText("Usuario: " + usuarioActual.getUsername());
        lblRol.setText("Rol: " + usuarioActual.getRol());
    }

    private void configurarTablaAsistencias() {
        colFecha.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                )
        );
        colHoraEntrada.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getHoraEntrada().format(DateTimeFormatter.ofPattern("HH:mm"))
                )
        );
        colHoraSalida.setCellValueFactory(cellData -> {
            String horaSalida = cellData.getValue().getHoraSalida() != null
                    ? cellData.getValue().getHoraSalida().format(DateTimeFormatter.ofPattern("HH:mm"))
                    : "---";
            return new javafx.beans.property.SimpleStringProperty(horaSalida);
        });
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        tblMisAsistencias.setItems(asistencias);
    }

    private void configurarTablaMensajes() {
        colEmisor.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getEmisor().getNombre()
                )
        );
        colAsunto.setCellValueFactory(new PropertyValueFactory<>("asunto"));
        colFecha.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getFecha().format(
                                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                        )
                )
        );
        colLeido.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getLeido() ? "✓ Leído" : "● No leído"
                )
        );

        tblMensajes.setItems(mensajes);

        // Mostrar contenido y marcar como leído
        tblMensajes.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                txtMensajeContenido.setText(newVal.getContenido());
                if (!newVal.getLeido()) {
                    mensajeService.marcarComoLeido(newVal.getId());
                    cargarMensajes(); // Recargar para actualizar estado
                }
            }
        });
    }

    private void cargarAsistencias() {
        asistencias.clear();
        asistencias.addAll(asistenciaService.listarPorUsuario(usuarioActual));
    }

    private void cargarMensajes() {
        mensajes.clear();
        mensajes.addAll(mensajeService.obtenerMensajesRecibidos(usuarioActual));

        long noLeidos = mensajes.stream().filter(m -> !m.getLeido()).count();
        lblMensajesNoLeidos.setText(noLeidos > 0 ? "(" + noLeidos + " nuevos)" : "");
    }

    @FXML
    private void handleMarcarAsistencia() {
        try {
            asistenciaService.registrarAsistencia(usuarioActual);
            cargarAsistencias();
            mostrarAlerta("Éxito", "Asistencia registrada correctamente", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleCerrarSesion() {
        SessionManager.getInstance().cerrarSesion();
        stageManager.cambiarEscena("/fxml/login.fxml", "Sistema de Asistencia - Login", 400, 500);
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}