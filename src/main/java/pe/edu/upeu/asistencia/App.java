package pe.edu.upeu.asistencia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;

public class App extends Application {

    private static Stage primaryStage;
    private ConfigurableApplicationContext springContext;

    @Override
    public void init() {
        springContext = AsistenciaApplication.getSpringContext();
    }

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        loader.setControllerFactory(AsistenciaApplication.getSpringContext()::getBean);
        // 🔹 Spring inyecta controladores

        Scene scene = new Scene(loader.load());
        stage.setTitle("Sistema de Asistencia");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        springContext.close();
    }

    // ✅ Método para cambiar de ventana
    public static void setRoot(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/" + fxml));
        loader.setControllerFactory(AsistenciaApplication.getSpringContext()::getBean);
        Scene scene = new Scene(loader.load());
        getPrimaryStage().setScene(scene);
    }

    // ✅ Aquí definimos el getter que te faltaba
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}
