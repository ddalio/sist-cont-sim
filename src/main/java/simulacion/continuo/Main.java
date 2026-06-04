package simulacion.continuo;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Localizar el archivo FXML en el classpath
            URL fxmlLocation = getClass().getResource("/simulacion/continuo/sistema_vista.fxml");
            
            if (fxmlLocation == null) {
                fxmlLocation = getClass().getResource("sistema_vista.fxml");
            }

            if (fxmlLocation == null) {
                throw new RuntimeException("Error crítico: No se encontró el archivo 'sistema_vista.fxml'. Verificá su ubicación en los recursos.");
            }

            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();
            
            Scene scene = new Scene(root, 950, 650);
            
            primaryStage.setTitle("Simulador de Sistema Continuo - Masa Resorte");
            primaryStage.setScene(scene);
            
            primaryStage.setOnCloseRequest(event -> {
                System.out.println("Cerrando simulación...");
                System.exit(0);
            });
            
            primaryStage.show();
            System.out.println("Interfaz de simulación cargada correctamente.");

        } catch(Exception e) {
            System.err.println("Ocurrió un error al iniciar la aplicación gráfica:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}