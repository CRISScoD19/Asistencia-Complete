package pe.edu.upeu.asistencia.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import pe.edu.upeu.asistencia.App;
import pe.edu.upeu.asistencia.enums.VacacionEstado;
import pe.edu.upeu.asistencia.modelo.Usuario;
import pe.edu.upeu.asistencia.modelo.Vacacion;
import pe.edu.upeu.asistencia.servicio.HorarioService;
import pe.edu.upeu.asistencia.servicio.UsuarioService;
import pe.edu.upeu.asistencia.servicio.VacacionService;

import java.time.LocalDate;
import java.time.LocalTime;

public class AdminController {
    @FXML
    private TextField nombreField;
    @FXML
    private TextField usuarioField;
    @FXML
    private PasswordField passField;
    @FXML
    private TableView<Usuario> tablaUsuarios;
    @FXML
    private TableColumn<Usuario,String> colNombre;
    @FXML
    private TableColumn<Usuario,String> colUsuario;
    @FXML

    private TableColumn<Usuario,String> colRol;

    @FXML
    private TableView<Vacacion> tablaVacPendientes;
    @FXML
    private TableColumn<Vacacion,String> colEmp;
    @FXML
    private TableColumn<Vacacion,LocalDate> colInicio;
    @FXML
    private TableColumn<Vacacion,LocalDate> colFin;
    @FXML
    private TableColumn<Vacacion,String> colEstado;

    @FXML
    private TextField horarioInicioField;
    @FXML
    private TextField horarioFinField;
    @FXML
    private TextField diasField;

    private final UsuarioService usuarioService = new UsuarioService();
    private final VacacionService vacacionService = new VacacionService();
    private final HorarioService horarioService = new HorarioService();

    private ObservableList<Usuario> usuariosData;
    private ObservableList<Vacacion> vacData;

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNombre()));
        colUsuario.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getUsername()));
        colRol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getRol()!=null?c.getValue().getRol().toString():""));
        usuariosData = FXCollections.observableArrayList(usuarioService.listar());
        tablaUsuarios.setItems(usuariosData);

        colEmp.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getEmpleado().getNombre()));
        colInicio.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getFechaInicio()));
        colFin.setCellValueFactory(c -> new javafx.beans.property.SimpleObjectProperty<>(c.getValue().getFechaFin()));
        colEstado.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getEstado().toString()));
        vacData = FXCollections.observableArrayList(vacacionService.listarPendientes());
        tablaVacPendientes.setItems(vacData);
    }

    @FXML
    private void registrarEmpleado() {
        String nombre = nombreField.getText(); String user = usuarioField.getText(); String pass = passField.getText();
        if (nombre.isEmpty() || user.isEmpty() || pass.isEmpty()) return;
        usuarioService.createEmpleado(nombre, user, pass);
        usuariosData.setAll(usuarioService.listar());
        nombreField.clear(); usuarioField.clear(); passField.clear();
    }

    @FXML
    private void asignarHorario() {
        Usuario sel = tablaUsuarios.getSelectionModel().getSelectedItem();
        if (sel==null) return;
        try {
            LocalTime inicio = LocalTime.parse(horarioInicioField.getText());
            LocalTime fin = LocalTime.parse(horarioFinField.getText());
            String dias = diasField.getText();
            horarioService.asignarHorario(sel, inicio, fin, dias);
            horarioInicioField.clear(); horarioFinField.clear(); diasField.clear();
            Alert a = new Alert(Alert.AlertType.INFORMATION, "Horario asignado a " + sel.getNombre());
            a.showAndWait();
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Formato horario inválido. Use HH:MM (ej. 09:00)");
            a.showAndWait();
        }
    }

    @FXML
    private void aceptarVacacion() {
        Vacacion v = tablaVacPendientes.getSelectionModel().getSelectedItem();
        if (v==null)
            return;
        vacacionService.actualizarEstado(v.getId(), VacacionEstado.ACEPTADA);
        vacData.setAll(vacacionService.listarPendientes());
    }

    @FXML
    private void rechazarVacacion() {
        Vacacion v = tablaVacPendientes.getSelectionModel().getSelectedItem();
        if (v==null)
            return;
        vacacionService.actualizarEstado(v.getId(), VacacionEstado.RECHAZADA);
        vacData.setAll(vacacionService.listarPendientes());
    }

    @FXML
    private void volverLogin() {
        try {
            App.changeScene("login.fxml");
        } catch(Exception e) {
            e.printStackTrace();
        } }
}
