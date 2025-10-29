package pe.edu.upeu.asistencia;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import pe.edu.upeu.asistencia.modelo.Usuario;
import pe.edu.upeu.asistencia.enums.Role;
import pe.edu.upeu.asistencia.repositorio.UsuarioRepository;
import pe.edu.upeu.asistencia.util.HashUtil;

@SpringBootApplication
public class AsistenciaApplication {

    private static ConfigurableApplicationContext springContext;

    public static void main(String[] args) {
        // 1️⃣ Inicia Spring Boot
        springContext = new SpringApplicationBuilder(AsistenciaApplication.class)
                .run(args);

        // 2️⃣ Lanza JavaFX
        Application.launch(App.class, args);

        // 3️⃣ Cierra contexto al salir
        springContext.close();
    }

    public static ConfigurableApplicationContext getSpringContext() {
        return springContext;
    }

    // 🧩 Crear usuario admin por defecto si no existe
    @Bean
    public Runnable initAdmin(UsuarioRepository repo) {
        return () -> {
            boolean exists = repo.findByUsername("admin").isPresent();
            if (!exists) {
                Usuario admin = new Usuario();
                admin.setNombre("Administrador");
                admin.setUsername("admin");
                admin.setPasswordHash(HashUtil.sha256("admin"));
                admin.setRol(Role.ADMIN); // ✅ CORREGIDO

                repo.save(admin);
                System.out.println("✅ Admin creado: usuario='admin', contraseña='admin'");
            } else {
                System.out.println("ℹ️ Admin ya existe");
            }
        };
    }
}
