package pe.edu.upeu.asistencia.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pe.edu.upeu.asistencia.App;
import pe.edu.upeu.asistencia.enums.VacacionEstado;
import pe.edu.upeu.asistencia.modelo.Asistencia;
import pe.edu.upeu.asistencia.modelo.Vacacion;
import pe.edu.upeu.asistencia.servicio.AsistenciaService;
import pe.edu.upeu.asistencia.servicio.HorarioService;
import pe.edu.upeu.asistencia.servicio.VacacionService;
import pe.edu.upeu.asistencia.util.Session;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmpleadoController {
    @FXML
    private Label lblNombre;
    @FXML
    private Label lblHorario;
    @FXML
    private Button btnEntradaSalida;
    @FXML
    private TableView<Vacacion> tablaVac;
    @FXML
    private TableColumn<Vacacion,LocalDate> colInicio;
    @FXML
    private TableColumn<Vacacion,LocalDate> colFin;
    @FXML
    private TableColumn<Vacacion,String> colEstado;
    @FXML
    private DatePicker dpInicio;
    @FXML
    private DatePicker dpFin;
    @FXML
    private TableView<Asistencia> tablaAsist;
    @FXML
    private TableColumn<Asistencia,String> colFechaEntrada;
    @FXML
    private TableColumn<Asistencia,String> colFechaSalida;

    private final VacacionService vacacionService = new VacacionService();
    private final HorarioService horarioService = new HorarioService();
    private final AsistenciaService asistenciaService = new AsistenciaService();
    private ObservableList<Vacacion> vacData;
    private ObservableList<Asistencia> asisData;
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @FXML
    public void initialize() {
        if (Session.getCurrentUser()!=null) lblNombre.setText("Empleado: "+Session.getCurrentUser().getNombre());
        lblHorario.setText("Horario: " + horarioService.getOrCreateDefaultHorario(Session.getCurrentUser()).getDias() + " " + horarioService.getOrCreateDefaultHorario(Session.getCurrentUser()).getHoraInicio() + "-" + horarioService.getOrCreateDefaultHorario(Session.getCurrentUser()).getHoraFin());
        colInicio.setCellValueFactory(c-> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getFechaInicio()));
        colFin.setCellValueFactory(c-> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getFechaFin()));
        colEstado.setCellValueFactory(c-> new javafx.beans.property.SimpleStringProperty(c.getValue().getEstado().toString()));
        vacData = FXCollections.observableArrayList(vacacionService.listarPorEmpleado(Session.getCurrentUser()!=null?Session.getCurrentUser().getId():-1L));
        tablaVac.setItems(vacData);

        colFechaEntrada.setCellValueFactory(c-> new javafx.beans.property.SimpleStringProperty(c.getValue().getEntrada()!=null?c.getValue().getEntrada().format(fmt):""));
        colFechaSalida.setCellValueFactory(c-> new javafx.beans.property.SimpleStringProperty(c.getValue().getSalida()!=null?c.getValue().getSalida().format(fmt):""));
        asisData = FXCollections.observableArrayList(asistenciaService.listarPorEmpleado(Session.getCurrentUser()!=null?Session.getCurrentUser().getId():-1L));
        tablaAsist.setItems(asisData);
    }

    @FXML
    private void solicitarVacacion() {
        LocalDate inicio = dpInicio.getValue(); LocalDate fin = dpFin.getValue();
        if (inicio==null||fin==null) return;
        Vacacion v = new Vacacion(null, Session.getCurrentUser(), inicio, fin, VacacionEstado.PENDIENTE);
        vacacionService.solicitar(v);
        vacData.setAll(vacacionService.listarPorEmpleado(Session.getCurrentUser().getId()));
        dpInicio.setValue(null); dpFin.setValue(null);
    }

    @FXML
    private void registrarEntradaSalida() {
        Asistencia a = new Asistencia(null, Session.getCurrentUser(), null, null);
        asistenciaService.registrarEntradaSalida(a);
        asisData.setAll(asistenciaService.listarPorEmpleado(Session.getCurrentUser().getId()));
    }

    @FXML
    private void volverLogin() { Session.setCurrentUser(null);
        try {
            App.changeScene("login.fxml");
        } catch(Exception e){
            e.printStackTrace();
        } }
}
