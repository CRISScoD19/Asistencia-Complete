package pe.edu.upeu.asistencia.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.asistencia.util.SceneManager;
import pe.edu.upeu.asistencia.util.SessionManager;
import pe.edu.upeu.asistencia.App;

@Controller
public class AdminController {

    @FXML
    void registrarUsuario(ActionEvent event) {
        SceneManager.changeScene("usuario-form.fxml", "Registrar Usuario");
    }

    @FXML
    void verEmpleados(ActionEvent event) {
        SceneManager.changeScene("usuarios-list.fxml", "Lista de Empleados");
    }

    @FXML
    void verAsistencias(ActionEvent event) {
        SceneManager.changeScene("asistencias-list.fxml", "Lista de Asistencias");
    }

    @FXML
    void enviarMensaje(ActionEvent event) {
        SceneManager.changeScene("mensaje-form.fxml", "Enviar Mensaje");
    }

    @FXML
    void bandeja(ActionEvent event) {
        SceneManager.changeScene("bandeja.fxml", "Bandeja de Entrada");
    }

    @FXML
    void cerrarSesion(ActionEvent event) {
        SessionManager.clear();
        try {
            App.setRoot("login.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
