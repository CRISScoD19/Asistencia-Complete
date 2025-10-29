package pe.edu.upeu.asistencia.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.asistencia.servicio.AsistenciaService;
import pe.edu.upeu.asistencia.util.SessionManager;
import pe.edu.upeu.asistencia.util.SceneManager;

@Controller
public class EmpleadoController {

    @Autowired private AsistenciaService asistenciaService;

    @FXML
    void marcarAsistencia(ActionEvent event) {
        if (!SessionManager.isLoggedIn()) { new Alert(Alert.AlertType.WARNING, "No hay sesión").showAndWait(); return; }
        Long id = SessionManager.getUsuarioActual().getId();
        asistenciaService.marcarAsistenciaPorUsuarioId(id);
        new Alert(Alert.AlertType.INFORMATION, "Asistencia registrada").showAndWait();
    }

    @FXML
    void verHistorial(ActionEvent event) {
        SceneManager.changeScene("mis_asistencias.fxml", "Mis Asistencias");
    }

    @FXML
    void cerrarSesion(ActionEvent event) {
        SessionManager.clear();
        SceneManager.changeScene("login.fxml", "Login");
    }
}
