package pe.edu.upeu.asistencia.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pe.edu.upeu.asistencia.modelo.Usuario;
import pe.edu.upeu.asistencia.servicio.UsuarioService;
import pe.edu.upeu.asistencia.util.SessionManager;
import pe.edu.upeu.asistencia.util.SceneManager;

import java.util.Optional;

@Controller
public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label lblError;

    @Autowired private UsuarioService usuarioService;

    @FXML
    private void login(ActionEvent event) {
        String u = usernameField.getText().trim();
        String p = passwordField.getText().trim();

        if (u.isEmpty()) { lblError.setText("Ingrese usuario"); usernameField.requestFocus(); return; }
        if (p.isEmpty()) { lblError.setText("Ingrese contraseña"); passwordField.requestFocus(); return; }

        Optional<Usuario> opt = usuarioService.authenticate(u, p);
        if (opt.isEmpty()) { lblError.setText("Usuario o contraseña incorrectos"); return; }

        Usuario usuario = opt.get();
        SessionManager.setUsuarioActual(usuario);
        try {
            if (usuario.getRol() != null && usuario.getRol().name().equals("ADMIN")) {
                SceneManager.changeScene("admin_dashboard.fxml", "Panel Admin");
            } else {
                SceneManager.changeScene("empleado_dashboard.fxml", "Panel Empleado");
            }
        } catch (Exception e) {
            e.printStackTrace();
            lblError.setText("Error al abrir pantalla");
        }
    }
}
