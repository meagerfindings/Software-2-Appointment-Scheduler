package View_Controller;

import static Model.CalendarDB.getAddressPrimaryKey;
import static Model.CalendarDB.getCustomerPrimaryKey;
import static Model.CalendarDB.getPrimaryKeyNoFK;
import static Model.CalendarDB.getPrimaryKeyWithFK;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author matgreten
 */
public class CustomerAddController implements Initializable {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cusName"
    private TextField cusName; // Value injected by FXMLLoader

    @FXML // fx:id="cusPhone"
    private TextField cusPhone; // Value injected by FXMLLoader

    @FXML // fx:id="cusAddress1"
    private TextField cusAddress1; // Value injected by FXMLLoader

    @FXML // fx:id="cusAddress2"
    private TextField cusAddress2; // Value injected by FXMLLoader

    @FXML // fx:id="cusCity"
    private TextField cusCity; // Value injected by FXMLLoader

    @FXML // fx:id="cusZip"
    private TextField cusZip; // Value injected by FXMLLoader

    @FXML // fx:id="cusCountry"
    private TextField cusCountry; // Value injected by FXMLLoader

    @FXML // fx:id="save"
    private Button save; // Value injected by FXMLLoader

    @FXML // fx:id="cancel"
    private Button cancel; // Value injected by FXMLLoader

    @FXML // fx:id="clearButton"
    private Button clearButton; // Value injected by FXMLLoader

    @FXML
    void cancelButtonClicked(ActionEvent event) throws IOException{
        System.out.println("Opening main customer screen.");

        FXMLLoader mainCustomerScreenLoader = new FXMLLoader(CustomerMainController.class.getResource("CustomerMain.fxml"));
        GridPane mainCustomerScreen = (GridPane) mainCustomerScreenLoader.load();
        Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                
        Scene sceneMainScreen = new Scene(mainCustomerScreen);
        newStage.setScene(sceneMainScreen);
        CustomerMainController controller = mainCustomerScreenLoader.getController();
        newStage.show();
    }

    @FXML
    void clearForm(ActionEvent event) {
        cusName.setText("");
        cusPhone.setText("");
        cusAddress1.setText("");
        cusAddress2.setText("");
        cusCity.setText("");
        cusZip.setText("");
        cusCountry.setText("");        
    }

    @FXML 
    boolean saveButtonClicked(ActionEvent event) throws IOException, SQLException {
        
        String inputName = cusName.getText();
        String inputPhone = cusPhone.getText();
        String inputAddress1 = cusAddress1.getText();
        String inputAddress2 = cusAddress2.getText();
        String inputCity = cusCity.getText();
        String inputZip = cusZip.getText();
        String inputCountry = cusCountry.getText();
                
        if (inputName.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty Customer Name");
            alert.setHeaderText("Empty customer name");
            alert.setContentText("You cannot save the customer without a customer name.");
            alert.showAndWait();
            
            return false;
        } 

        String[] numericSymCheck = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ";", "*", "!", "@", "#", "&", "$", "(", ")", "[", "]", "{", "}", "+", "_", "=", ":", "/", ",", "%", "^", "?", "`", "~"};
        
        String[] nameArray = new String[inputName.length()];
        
        for (String numericSymCheck1 : numericSymCheck) {
            if (inputName.contains(numericSymCheck1)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Character");
                alert.setHeaderText("Invalid character in customer name");
                alert.setContentText("You cannot add non-letter characters to the customer name.\nInvalid character = " + numericSymCheck1);
                alert.showAndWait();
                return false;
            }   
        }
        
        if (inputPhone.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty Customer Phone Number");
            alert.setHeaderText("Empty customer phone number");
            alert.setContentText("You cannot save the customer without a phone number.");
            alert.showAndWait();
            
            return false;
        }
        
        String[] alphaSymCheck = new String[]{";", "*", "!", "@", "#", "&", "$", "(", ")", "[", "]", "{", "}", "+", "_", "=", ":", "/", ",", "%", "^", "?", "`", "~", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "."};

        for (String alphaSymCheck1 : alphaSymCheck) {
            if (inputPhone.contains(alphaSymCheck1)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Character");
                alert.setHeaderText("Invalid character in customer phone number");
                alert.setContentText("You cannot add alpha characters or symbols other than '-' to the customer's phone number.\nInvalid character = " + alphaSymCheck1);
                alert.showAndWait();
            
                return false;
            }   
        }
        
        if (inputZip.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty Customer Zip Code");
            alert.setHeaderText("Empty customer zip code");
            alert.setContentText("You cannot save the customer without a zip code.");
            alert.showAndWait();
            
            return false;
        }
        
        alphaSymCheck = new String[]{";", "*", "!", "@", "#", "&", "$", "(", ")", "[", "]", "{", "}", "+", "_", "=", ":", "/", ",", "%", "^", "?", "`", "~", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "-", ".", " "};

        for (String alphaSymCheck1 : alphaSymCheck) {
            if (inputZip.contains(alphaSymCheck1)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Character");
                alert.setHeaderText("Invalid character in customer phone number");
                alert.setContentText("You cannot add alpha characters or symbols to the customer's zip code.\nInvalid character = " + alphaSymCheck1);
                alert.showAndWait();
            
                return false;
            }   
        }
        
        if (inputAddress1.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty Customer Address");
            alert.setHeaderText("Empty customer Address");
            alert.setContentText("You cannot save the customer without a primary address.");
            alert.showAndWait();
            
            return false;
        }
        
        if (inputCity.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty Customer City");
            alert.setHeaderText("Empty customer city");
            alert.setContentText("You cannot save the customer without a city in their address.");
            alert.showAndWait();
            
            return false;
        }
        
        for (String numericSymCheck1 : numericSymCheck) {
            if (inputCity.contains(numericSymCheck1)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Character");
                alert.setHeaderText("Invalid character in customer's city.");
                alert.setContentText("You cannot add non-letter characters to the city.");
                alert.showAndWait();
            
                return false;
            }   
        }
        
        if (inputCountry.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty Customer Country");
            alert.setHeaderText("Empty customer country");
            alert.setContentText("You cannot save the customer without a country in their address.");
            alert.showAndWait();
            
            return false;
        }
        
        for (String numericSymCheck1 : numericSymCheck) {
            if (inputCountry.contains(numericSymCheck1)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Character");
                alert.setHeaderText("Invalid character in customer's country.");
                alert.setContentText("You cannot add non-letter characters to the country.\nInvalid character = " + numericSymCheck1);
                alert.showAndWait();
            
                return false;
            }   
        }
        
        // If country not in db add otherwise get the primary key
        int countryKey = getPrimaryKeyNoFK("countryId", "country", "country", inputCountry);
        
        // If city not created add otherwise get the primary key
        int cityKey = getPrimaryKeyWithFK("cityId", "city", "countryId", countryKey, "city", inputCity);
        
        // Unique address not created add otherwise get the primary key
        int addressKey = getAddressPrimaryKey("addressId", "address", "cityId", cityKey, "address", inputAddress1, inputAddress2, inputZip, inputPhone);
               
        // Add customer with primary keys above
        int customerKey = getCustomerPrimaryKey("customerId", "customer", "addressId", addressKey, "customerName", inputName);
        
        System.out.println("The Primary Key for the customer '" + inputName + "' is: "+ customerKey);
        
        System.out.println("Opening main customer screen.");

        FXMLLoader mainCustomerScreenLoader = new FXMLLoader(CustomerMainController.class.getResource("CustomerMain.fxml"));
        GridPane mainCustomerScreen = (GridPane) mainCustomerScreenLoader.load();
        Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                
        Scene sceneMainScreen = new Scene(mainCustomerScreen);
        newStage.setScene(sceneMainScreen);
        CustomerMainController controller = mainCustomerScreenLoader.getController();
        newStage.show();
        return true;
    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

@FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cusName != null : "fx:id=\"cusName\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert cusPhone != null : "fx:id=\"cusPhone\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert cusAddress1 != null : "fx:id=\"cusAddress1\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert cusAddress2 != null : "fx:id=\"cusAddress2\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert cusCity != null : "fx:id=\"cusCity\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert cusZip != null : "fx:id=\"cusZip\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert cusCountry != null : "fx:id=\"cusCountry\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert save != null : "fx:id=\"save\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert cancel != null : "fx:id=\"cancel\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
        assert clearButton != null : "fx:id=\"clearButton\" was not injected: check your FXML file 'CustomerAdd.fxml'.";
    }
}