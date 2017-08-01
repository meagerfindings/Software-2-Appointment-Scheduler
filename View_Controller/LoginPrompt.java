package View_Controller;

import Model.CalendarDB;
import static Model.CalendarDB.getLoggedInUser;
import static Model.CalendarDB.setLoggedInUser;
import static Model.Time.getTimeStamp;
import static Reports.IOWriter.writeToLog;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/* FXML Controller class
 *
 * @author matgreten
 */

public class LoginPrompt implements Initializable {
    
    private final LocalChecker locationSpain = () -> Locale.getDefault().getDisplayCountry().equalsIgnoreCase("es-es");
    
    @FXML // fx:id="titleLabel"
    private Label titleLabel; // Value injected by FXMLLoader

    @FXML // fx:id="usernameLabel"
    private Text usernameLabel; // Value injected by FXMLLoader

    @FXML // fx:id="usernameInput"
    private TextField usernameInput; // Value injected by FXMLLoader

    @FXML // fx:id="passwordLabel"
    private Text passwordLabel; // Value injected by FXMLLoader

    @FXML // fx:id="passwordInput"
    private PasswordField passwordInput; // Value injected by FXMLLoader

    @FXML // fx:id="submitButton"
    private Button submitButton; // Value injected by FXMLLoader

    @FXML // fx:id="cancelButton"
    private Button cancelButton; // Value injected by FXMLLoader
    
    @FXML
    void submitButtonClicked(ActionEvent event) throws IOException, SQLException {
        
        String enteredUserName = usernameInput.getText();
        String enteredPassword = passwordInput.getText();
        
        CalendarDB userData = new CalendarDB();
        
        PreparedStatement storedPassword = userData.connectDB().prepareStatement("SELECT password FROM user WHERE userName = ?");
        storedPassword.setString(1, enteredUserName);
        ResultSet rs = storedPassword.executeQuery();

        if (rs.next() == true) { // If the user exists check the password
            String password = rs.getString(1);

            if (password == null ? enteredPassword == null : password.equals(enteredPassword)){
                writeToLog(getTimeStamp() + ": '" + enteredUserName + "' successfully logged in.");
                
                setLoggedInUser(enteredUserName);
                
                System.out.println("The logged in user is now: '" + getLoggedInUser() + "'.");

                FXMLLoader mainScreenLoader = new FXMLLoader(MainScreenController.class.getResource("MainScreen.fxml"));
                GridPane mainScreen = (GridPane) mainScreenLoader.load();
                Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

                Scene sceneMainScreen = new Scene(mainScreen);
                newStage.setScene(sceneMainScreen);
                newStage.centerOnScreen();
                MainScreenController controller = mainScreenLoader.getController();
                newStage.show();
            } else { // If the password does not match the user's password in the database, display alert.
                incorrectLogin(enteredUserName); // Trigger Login failure alert.
            }
        } else { // If the username does not exist in the table display alert.
            incorrectLogin(enteredUserName); // Trigger Login failure alert.
        }
    }
    
    private void incorrectLogin(String enteredUserName){
         passwordInput.setText("");
                writeToLog(getTimeStamp() + ": " + "Unsuccessful Login Attempt with username: '" + enteredUserName + "'.");
                
                String headerText = "Invalid Username or Password";
                String messageDetails = "Unsuccessful Login Attempt: An invalid user name or password was entered.";
                
                if (locationSpain.locationCheck()){
                    headerText = "Usuario o contraseña invalido";
                    messageDetails = "Intentos de inicio de sesión infructuosos: se ingresó un nombre de usuario o una contraseña no válidos.";
                } 
        
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(headerText);
                alert.setContentText(messageDetails);
                System.out.println(messageDetails);
                alert.showAndWait();
    }
       
    @FXML      //Exits Program.
    private void exitButtonClicked(ActionEvent event) throws IOException {
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        if (locationSpain.locationCheck()){
            alert.setContentText("¿Seguro que quieres salir?");
        } else {
            alert.setContentText("Are you sure you want to exit?");
        }

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // Exit was chosen again. Quit the program.
            System.out.println("Closing up shop. Exiting the program.");
            System.exit(0);
        } else {
            // Cancel was chosen, let the confirmation window exit and do nothing.
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Location checking
        if (locationSpain.locationCheck()){
            submitButton.setText("Enviar");
            cancelButton.setText("Cancelar");
            titleLabel.setText("Inicio de Sesión de Cita");
            usernameLabel.setText("Usuario:");
            passwordLabel.setText("Contraseña:");
            usernameInput.setPromptText("Nombre de usuario");
            passwordInput.setPromptText("Contraseña");
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert titleLabel != null : "fx:id=\"titleLabel\" was not injected: check your FXML file 'LoginPrompt.fxml'.";
        assert usernameLabel != null : "fx:id=\"usernameLabel\" was not injected: check your FXML file 'LoginPrompt.fxml'.";
        assert usernameInput != null : "fx:id=\"usernameInput\" was not injected: check your FXML file 'LoginPrompt.fxml'.";
        assert passwordLabel != null : "fx:id=\"passwordLabel\" was not injected: check your FXML file 'LoginPrompt.fxml'.";
        assert passwordInput != null : "fx:id=\"passwordInput\" was not injected: check your FXML file 'LoginPrompt.fxml'.";
        assert submitButton != null : "fx:id=\"submitButton\" was not injected: check your FXML file 'LoginPrompt.fxml'.";
        assert cancelButton != null : "fx:id=\"cancelButton\" was not injected: check your FXML file 'LoginPrompt.fxml'.";
    }
}

@FunctionalInterface
interface LocalChecker {
    boolean locationCheck();
}