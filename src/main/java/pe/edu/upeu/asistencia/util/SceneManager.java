package pe.edu.upeu.asistencia.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import pe.edu.upeu.asistencia.AsistenciaApplication;
import pe.edu.upeu.asistencia.SpringContext;

public class SceneManager {

    public static void changeScene(String fxmlFile, String title) {
        try {
            ApplicationContext ctx = SpringContext.getContext();
            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/fxml/" + fxmlFile));
            loader.setControllerFactory(ctx::getBean);
            Parent root = loader.load();
            Stage stage = pe.edu.upeu.asistencia.App.getPrimaryStage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            if (title != null) stage.setTitle(title);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
