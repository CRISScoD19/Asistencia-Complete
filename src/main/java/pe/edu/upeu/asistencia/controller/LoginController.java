package pe.edu.upeu.asistencia.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pe.edu.upeu.asistencia.App;
import pe.edu.upeu.asistencia.modelo.Usuario;
import pe.edu.upeu.asistencia.servicio.UsuarioService;
import pe.edu.upeu.asistencia.util.Session;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label lblError;
    private final UsuarioService usuarioService = new UsuarioService();

    @FXML
    public void initialize(){ lblError.setText(""); }

    @FXML private void login() {
        String u = usernameField.getText();
        String p = passwordField.getText();
        var op = usuarioService.authenticate(u,p);
        if (op.isPresent()) {
            Usuario user = op.get();
            Session.setCurrentUser(user);
            try {
                if (user.getRol()!=null && user.getRol().toString().equals("ADMIN")) App.changeScene("admin_dashboard.fxml"); else App.changeScene("empleado_dashboard.fxml");
            } catch (Exception e) { e.printStackTrace(); lblError.setText("Error al cargar la vista"); }
        } else {
            lblError.setText("Credenciales inválidas");
        }
    }
}
