package pe.edu.upeu.asistencia.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
import pe.edu.upeu.asistencia.model.SolicitudVacacion;
import pe.edu.upeu.asistencia.service.SolicitudVacacionService;
import pe.edu.upeu.asistencia.enums.EstadoSolicitud;
import java.time.LocalDate;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonBar;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
public class EmpleadoDashboardController {

    @FXML private Label lblNombre;
    @FXML private Label lblUsername;
    @FXML private Label lblRol;
    @FXML private Button btnMarcarAsistencia;

    // Campos adicionales del FXML
    @FXML private Label lblUsuario;
    @FXML private Label lblEmail;
    @FXML private Label lblTelefono;
    @FXML private Label lblFechaHoy;
    @FXML private Label lblHoraActual;
    @FXML private Label lblMensajeAsistencia;
    @FXML private ComboBox<String> cboPeriodo;

    // Tabla Mis Asistencias (original)
    @FXML private TableView<Asistencia> tblMisAsistencias;
    @FXML private TableColumn<Asistencia, String> colFecha;
    @FXML private TableColumn<Asistencia, String> colHoraEntrada;
    @FXML private TableColumn<Asistencia, String> colHoraSalida;
    @FXML private TableColumn<Asistencia, String> colEstado;

    // Tabla Mis Asistencias (nuevas columnas del FXML)
    @FXML private TableView<Asistencia> tableMisAsistencias;
    @FXML private TableColumn<Asistencia, String> colMiAsistFecha;
    @FXML private TableColumn<Asistencia, String> colMiAsistEntrada;
    @FXML private TableColumn<Asistencia, String> colMiAsistSalida;
    @FXML private TableColumn<Asistencia, String> colMiAsistEstado;
    @FXML private TableColumn<Asistencia, String> colMiAsistObs;

    // Labels de estadísticas
    @FXML private Label lblTotalPresente;
    @FXML private Label lblTotalTarde;
    @FXML private Label lblTotalAusente;
    @FXML private Label lblTotalJustificado;

    // Tabla de vacaciones
    @FXML private TableView<Object> tableMisVacaciones;
    @FXML private TableColumn<Object, String> colVacFechaInicio;
    @FXML private TableColumn<Object, String> colVacFechaFin;
    @FXML private TableColumn<Object, String> colVacDias;
    @FXML private TableColumn<Object, String> colVacEstado;
    @FXML private TableColumn<Object, String> colVacFechaSolicitud;
    @FXML private TableColumn<Object, String> colVacAcciones;

    // Tabla Mensajes (original)
    @FXML private TableView<Mensaje> tblMensajes;
    @FXML private TableColumn<Mensaje, String> colEmisor;
    @FXML private TableColumn<Mensaje, String> colAsunto;
    @FXML private TableColumn<Mensaje, String> colLeido;
    @FXML private TextArea txtMensajeContenido;
    @FXML private Label lblMensajesNoLeidos;

    // Tabla de mensajes (nuevas columnas del FXML)
    @FXML private TableView<Mensaje> tableMisMensajes;
    @FXML private TableColumn<Mensaje, String> colMiMsjEmisor;
    @FXML private TableColumn<Mensaje, String> colMiMsjAsunto;
    @FXML private TableColumn<Mensaje, String> colMiMsjFecha;
    @FXML private TableColumn<Mensaje, String> colMiMsjLeido;
    @FXML private TableColumn<Mensaje, String> colMiMsjAcciones;

    @Autowired
    private AsistenciaService asistenciaService;

    @Autowired
    private MensajeService mensajeService;

    @Autowired
    private ConfigurableApplicationContext springContext;

    @Autowired
    private StageManager stageManager;

    @Autowired
    private SolicitudVacacionService solicitudVacacionService;

    private Usuario usuarioActual;
    private ObservableList<Asistencia> asistencias = FXCollections.observableArrayList();
    private ObservableList<Mensaje> mensajes = FXCollections.observableArrayList();
    private ObservableList<SolicitudVacacion> misVacaciones = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        usuarioActual = SessionManager.getInstance().getUsuarioActual();

        mostrarDatosUsuario();
        configurarTablaAsistencias();
        configurarTablaMensajes();

        cargarAsistencias();
        cargarMensajes();

        configurarTablaVacacionesEmpleado();
        cargarMisVacaciones();
    }

    private void mostrarDatosUsuario() {
        lblNombre.setText(usuarioActual.getNombre());
        lblUsername.setText("Usuario: " + usuarioActual.getUsername());
        if (lblRol != null) {
            lblRol.setText("Rol: " + usuarioActual.getRol());
        }
    }

    private void configurarTablaAsistencias() {
        if (colFecha != null) {
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

            if (tblMisAsistencias != null) {
                tblMisAsistencias.setItems(asistencias);
            }
        }

        // Configurar tabla adicional si existe
        if (tableMisAsistencias != null && colMiAsistFecha != null) {
            colMiAsistFecha.setCellValueFactory(cellData ->
                    new javafx.beans.property.SimpleStringProperty(
                            cellData.getValue().getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    )
            );
            colMiAsistEntrada.setCellValueFactory(cellData ->
                    new javafx.beans.property.SimpleStringProperty(
                            cellData.getValue().getHoraEntrada().format(DateTimeFormatter.ofPattern("HH:mm"))
                    )
            );
            colMiAsistSalida.setCellValueFactory(cellData -> {
                String horaSalida = cellData.getValue().getHoraSalida() != null
                        ? cellData.getValue().getHoraSalida().format(DateTimeFormatter.ofPattern("HH:mm"))
                        : "---";
                return new javafx.beans.property.SimpleStringProperty(horaSalida);
            });
            colMiAsistEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
            tableMisAsistencias.setItems(asistencias);
        }
    }

    private void configurarTablaMensajes() {
        if (colEmisor != null && tblMensajes != null) {
            colEmisor.setCellValueFactory(cellData ->
                    new javafx.beans.property.SimpleStringProperty(
                            cellData.getValue().getEmisor().getNombre()
                    )
            );
            colAsunto.setCellValueFactory(new PropertyValueFactory<>("asunto"));
            colLeido.setCellValueFactory(cellData ->
                    new javafx.beans.property.SimpleStringProperty(
                            cellData.getValue().getLeido() ? "✓ Leído" : "● No leído"
                    )
            );

            tblMensajes.setItems(mensajes);

            // Mostrar contenido y marcar como leído
            tblMensajes.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null && txtMensajeContenido != null) {
                    txtMensajeContenido.setText(newVal.getContenido());
                    if (!newVal.getLeido()) {
                        mensajeService.marcarComoLeido(newVal.getId());
                        cargarMensajes();
                    }
                }
            });
        }

        // Configurar tabla adicional si existe
        if (tableMisMensajes != null && colMiMsjEmisor != null) {
            colMiMsjEmisor.setCellValueFactory(cellData ->
                    new javafx.beans.property.SimpleStringProperty(
                            cellData.getValue().getEmisor().getNombre()
                    )
            );
            colMiMsjAsunto.setCellValueFactory(new PropertyValueFactory<>("asunto"));
            colMiMsjFecha.setCellValueFactory(cellData ->
                    new javafx.beans.property.SimpleStringProperty(
                            cellData.getValue().getFechaEnvio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                    )
            );
            colMiMsjLeido.setCellValueFactory(cellData ->
                    new javafx.beans.property.SimpleStringProperty(
                            cellData.getValue().getLeido() ? "✓" : "●"
                    )
            );
            tableMisMensajes.setItems(mensajes);
        }
    }

    private void cargarAsistencias() {
        asistencias.clear();
        asistencias.addAll(asistenciaService.listarPorUsuario(usuarioActual));
    }

    private void cargarMensajes() {
        mensajes.clear();
        mensajes.addAll(mensajeService.obtenerMensajesRecibidos(usuarioActual));

        long noLeidos = mensajes.stream().filter(m -> !m.getLeido()).count();
        if (lblMensajesNoLeidos != null) {
            lblMensajesNoLeidos.setText(noLeidos > 0 ? "(" + noLeidos + " nuevos)" : "");
        }
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

    @FXML
    private void handleBuscarMisAsistencias() {
        cargarAsistencias();
    }

    @FXML
    private void handleSolicitarVacaciones() {
        Dialog<SolicitudVacacion> dialog = new Dialog<>();
        dialog.setTitle("Solicitar Vacaciones");
        dialog.setHeaderText("Nueva Solicitud de Vacaciones");

        ButtonType btnSolicitar = new ButtonType("Solicitar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(btnSolicitar, ButtonType.CANCEL);

        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        DatePicker dpFechaInicio = new DatePicker();
        DatePicker dpFechaFin = new DatePicker();
        TextArea txtMotivo = new TextArea();
        txtMotivo.setPrefRowCount(3);

        grid.add(new Label("Fecha Inicio:"), 0, 0);
        grid.add(dpFechaInicio, 1, 0);
        grid.add(new Label("Fecha Fin:"), 0, 1);
        grid.add(dpFechaFin, 1, 1);
        grid.add(new Label("Motivo:"), 0, 2);
        grid.add(txtMotivo, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == btnSolicitar) {
                if (dpFechaInicio.getValue() == null || dpFechaFin.getValue() == null || txtMotivo.getText().trim().isEmpty()) {
                    mostrarAlerta("Error", "Complete todos los campos", Alert.AlertType.ERROR);
                    return null;
                }
                try {
                    return solicitudVacacionService.solicitarVacaciones(
                            usuarioActual,
                            dpFechaInicio.getValue(),
                            dpFechaFin.getValue(),
                            txtMotivo.getText()
                    );
                } catch (Exception e) {
                    mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR);
                    return null;
                }
            }
            return null;
        });

        Optional<SolicitudVacacion> resultado = dialog.showAndWait();
        if (resultado.isPresent()) {
            cargarMisVacaciones();
            mostrarAlerta("Éxito", "Solicitud enviada correctamente", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    private void handleActualizarVacaciones() {
        cargarMisVacaciones();
        mostrarAlerta("Éxito", "Lista actualizada", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void handleActualizarMensajes() {
        cargarMensajes();
    }

    @FXML
    private void handleMarcarTodosLeidos() {
        mensajeService.marcarTodosComoLeidos(usuarioActual);
        cargarMensajes();
        mostrarAlerta("Éxito", "Todos los mensajes marcados como leídos", Alert.AlertType.INFORMATION);
    }

    private void configurarTablaVacacionesEmpleado() {
        if (tableMisVacaciones != null && colVacFechaInicio != null) {
            colVacFechaInicio.setCellValueFactory(cellData ->
                    new javafx.beans.property.SimpleStringProperty(
                            ((SolicitudVacacion) cellData.getValue()).getFechaInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    )
            );
            colVacFechaFin.setCellValueFactory(cellData ->
                    new javafx.beans.property.SimpleStringProperty(
                            ((SolicitudVacacion) cellData.getValue()).getFechaFin().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    )
            );
            colVacDias.setCellValueFactory(cellData ->
                    new javafx.beans.property.SimpleStringProperty(
                            String.valueOf(((SolicitudVacacion) cellData.getValue()).getDiasSolicitados())
                    )
            );
            colVacEstado.setCellValueFactory(cellData -> {
                String estado = ((SolicitudVacacion) cellData.getValue()).getEstado().toString();
                String emoji = "";
                switch (estado) {
                    case "PENDIENTE": emoji = "🟡 "; break;
                    case "APROBADA": emoji = "🟢 "; break;
                    case "RECHAZADA": emoji = "🔴 "; break;
                }
                return new javafx.beans.property.SimpleStringProperty(emoji + estado);
            });
            colVacFechaSolicitud.setCellValueFactory(cellData ->
                    new javafx.beans.property.SimpleStringProperty(
                            ((SolicitudVacacion) cellData.getValue()).getFechaSolicitud().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                    )
            );

            tableMisVacaciones.setItems((ObservableList) misVacaciones);
        }
    }

    private void cargarMisVacaciones() {
        misVacaciones.clear();
        misVacaciones.addAll(solicitudVacacionService.listarPorEmpleado(usuarioActual));
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }
}