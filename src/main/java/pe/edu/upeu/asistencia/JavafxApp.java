package pe.edu.upeu.asistencia;

import javafx.application.Application;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class JavafxApp {

    public static void main(String[] args) {
        // Inicia Spring Boot
        ConfigurableApplicationContext context =
                new SpringApplicationBuilder(AsistenciaApplication.class).run();


        // Lanza la aplicación JavaFX
        Application.launch(App.class, args);

        // Cierra el contexto al salir
        context.close();
    }
}
