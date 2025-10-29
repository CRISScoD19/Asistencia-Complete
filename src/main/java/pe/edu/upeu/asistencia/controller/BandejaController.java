package pe.edu.upeu.asistencia.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.asistencia.util.SceneManager;

@Controller
public class BandejaController {

    @FXML
    void volver(ActionEvent event) {
        SceneManager.changeScene("admin.fxml", "Panel del Administrador");
    }
}
