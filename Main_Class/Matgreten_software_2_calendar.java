package Main_Class;

import View_Controller.LoginPrompt;
import java.util.Locale;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author matgreten
 */
public class Matgreten_software_2_calendar extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        /*
        *Uncomment line 22 to change location to Spain, setting the default language to Spanish.
        */
        // Locale.setDefault(new Locale("es", "es-es"));
                        
        FXMLLoader mainLoader = new FXMLLoader(LoginPrompt.class.getResource("/View_Controller/LoginPrompt.fxml"));
        AnchorPane mainScreen = (AnchorPane) mainLoader.load();
        Scene sceneMainScreen = new Scene(mainScreen);
        stage.setScene(sceneMainScreen);
        LoginPrompt controller = mainLoader.getController();
        stage.show();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
