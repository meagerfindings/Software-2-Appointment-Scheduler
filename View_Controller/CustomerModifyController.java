package View_Controller;

import static Model.CalendarDB.getAddressPrimaryKey;
import static Model.CalendarDB.getPrimaryKeyNoFK;
import static Model.CalendarDB.getPrimaryKeyWithFK;
import static Model.CalendarDB.updateCustomerFK;
import static Model.CalendarDB.updateCustomerName;
import static Model.CalendarDB.updateCustomerStatus;
import static Reports.IOWriter.writeToLog;
import static View_Controller.CustomerMainController.getCurrentCustomer;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author matgreten
 */
public class CustomerModifyController implements Initializable {

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
    
    @FXML // fx:id="activeInactive"
    private ChoiceBox<String> activeInactive; // Value injected by FXMLLoader
    
    private final String[] activeInactiveArray = new String[]{"Active", "Inactive"};

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
        // Retrieve user input from fields.
        String inputName = cusName.getText();
        String inputPhone = cusPhone.getText();
        String inputAddress1 = cusAddress1.getText();
        String inputAddress2 = cusAddress2.getText();
        String inputCity = cusCity.getText();
        String inputZip = cusZip.getText();
        String inputCountry = cusCountry.getText();
        String activeValue = activeInactive.getValue();
        
        if (inputName.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty Customer Name");
            alert.setHeaderText("Empty customer name");
            alert.setContentText("You cannot save the customer without a customer name.");
            alert.showAndWait();
            
            return false;
        } 

        String[] numericSymCheck = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ";", "*", "!", "@", "#", "&", "$", "(", ")", "[", "]", "{", "}", "+", "_", "=", ":", "/", ",", "%", "^", "?", "`", "~"};
        
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
        
        if (inputName.equalsIgnoreCase("Rick Astley")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Released in 1987");
            alert.setHeaderText("from the album Whenever You Need Somebody");
            String rRoll = "We're no strangers to love\n" +
                                "You know the rules and so do I\n" +
                                "A full commitment's what I'm thinking of\n" +
                                "You wouldn't get this from any other guy\n" +
                                "\n" +
                                "I just want to tell you how I'm feeling\n" +
                                "Gotta make you understand\n" +
                                "\n" +
                                "Never gonna give you up, never gonna let you down\n" +
                                "Never gonna run around and desert you\n" +
                                "Never gonna make you cry, never gonna say goodbye\n" +
                                "Never gonna tell a lie and hurt you\n" +
                                "\n" +
                                "We've known each other for so long\n" +
                                "Your heart's been aching but you're too shy to say it\n" +
                                "Inside we both know what's been going on\n" +
                                "We know the game and we're gonna play it\n" +
                                "\n" +
                                "And if you ask me how I'm feeling\n" +
                                "Don't tell me you're too blind to see\n" +
                                "\n" +
                                "Never gonna give you up, never gonna let you down\n" +
                                "Never gonna run around and desert you\n" +
                                "Never gonna make you cry, never gonna say goodbye\n" +
                                "Never gonna tell a lie and hurt you\n" +
                                "\n" +
                                "Never gonna give you up, never gonna let you down\n" +
                                "Never gonna run around and desert you\n" +
                                "Never gonna make you cry, never gonna say goodbye\n" +
                                "Never gonna tell a lie and hurt you\n" +
                                "\n" +
                                "We've known each other for so long\n" +
                                "Your heart's been aching but you're too shy to say it\n" +
                                "Inside we both know what's been going on\n" +
                                "We know the game and we're gonna play it\n" +
                                "\n" +
                                "I just want to tell you how I'm feeling\n" +
                                "Gotta make you understand\n" +
                                "\n" +
                                "Never gonna give you up, never gonna let you down\n" +
                                "Never gonna run around and desert you\n" +
                                "Never gonna make you cry, never gonna say goodbye\n" +
                                "Never gonna tell a lie and hurt you";
                writeToLog(rRoll);
                alert.setContentText(rRoll);
                alert.showAndWait();
            } 
        
        // Initialize current defaults for primary keys
        int customerKey = getCurrentCustomer().getPK();
        int addressKey = getCurrentCustomer().getAddressKey();
        int cityKey = getCurrentCustomer().getCityyKey();
        int countryKey = getCurrentCustomer().getCountryKey();
            
        if (getCurrentCustomer().getName() == null ? inputName != null : !getCurrentCustomer().getName().equals(inputName)){ // if name has chagned trigger update of name
            
            System.out.println("Modifying customer " + getCurrentCustomer().getName() + " to be " + inputName);
            updateCustomerName(inputName, customerKey);
        }
        
        if (getCurrentCustomer().getActive() == null ? inputName != null : !getCurrentCustomer().getActive().equals(activeValue)){ // if name has chagned trigger update of name
            
            System.out.println("Setting the customer " + getCurrentCustomer().getName() + " as " + activeValue);
            updateCustomerStatus(activeValue, customerKey);
        }
        
        // Check if the country has changed, create a new entry if it has changed.
        if (getCurrentCustomer().getCountry() == null ? inputCountry != null : !getCurrentCustomer().getCountry().equals(inputCountry)){
            countryKey = getPrimaryKeyNoFK("countryId", "country", "country", inputCountry); // replace default key
            
            System.out.println("The Primary Key for the country '" + inputCountry + "' is: "+ countryKey);
        }
        
        // Check if the city has changed, create a new entry if it has changed.
        if (getCurrentCustomer().getCity() == null ? inputCity != null : !getCurrentCustomer().getCity().equals(inputCity)){
            cityKey = getPrimaryKeyWithFK("cityId", "city", "countryId", countryKey, "city", inputCity);  // replace default key

            System.out.println("The Primary Key for the city '" + inputCity + "' is: "+ cityKey);
        }
        
        // Check if the address has changed, create a new address if it has changed. Multiple checks so that we can detect any change in the address. If any check is triggered, we update trigger creation of a new address to preserve addresses.
        if (getCurrentCustomer().getAddress1() == null ? inputAddress1 != null : !getCurrentCustomer().getAddress1().equals(inputAddress1)){
        
            addressKey = getAddressPrimaryKey("addressId", "address", "cityId", cityKey, "address", inputAddress1, inputAddress2, inputZip, inputPhone);
            
            System.out.println("Updated address 1.");
            System.out.println("The Primary Key for the address '" + inputAddress1 + "' is: "+ addressKey);
            
        } else if (getCurrentCustomer().getAddress2() == null ? inputAddress2 != null : !getCurrentCustomer().getAddress2().equals(inputAddress2)){
            addressKey = getAddressPrimaryKey("addressId", "address", "cityId", cityKey, "address", inputAddress1, inputAddress2, inputZip, inputPhone);
            
            System.out.println("Updated address 2.");
            System.out.println("The Primary Key for the address '" + inputAddress1 + "' is: "+ addressKey);
            
        } else if (getCurrentCustomer().getPhone() == null ? inputPhone != null : !getCurrentCustomer().getPhone().equals(inputPhone)){
            addressKey = getAddressPrimaryKey("addressId", "address", "cityId", cityKey, "address", inputAddress1, inputAddress2, inputZip, inputPhone);
            
            System.out.println("Updated phone number.");
            System.out.println("The Primary Key for the address '" + inputAddress1 + "' is: "+ addressKey);
            
        } else if (getCurrentCustomer().getZip() == null ? inputZip != null : !getCurrentCustomer().getZip().equals(inputZip)){
            addressKey = getAddressPrimaryKey("addressId", "address", "cityId", cityKey, "address", inputAddress1, inputAddress2, inputZip, inputPhone);
            
            System.out.println("Updated Zip Code");
            System.out.println("The Primary Key for the address '" + inputAddress1 + "' is: "+ addressKey);
            
        } else if (getCurrentCustomer().getCity() == null ? inputCity != null : !getCurrentCustomer().getCity().equals(inputCity)){
            addressKey = getAddressPrimaryKey("addressId", "address", "cityId", cityKey, "address", inputAddress1, inputAddress2, inputZip, inputPhone);
            
            System.out.println("Updated city in the address.");
            System.out.println("The Primary Key for the address '" + inputAddress1 + "' is: "+ addressKey);
        }
        
        // If the address has changed, then update the forgeign key (cityId) in the Customer entry.
        if (getCurrentCustomer().getAddressKey() != addressKey){
            updateCustomerFK(customerKey,addressKey);
            System.out.println("Updated the address on file for " + getCurrentCustomer().getName());
        }
        
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
        //Setup ChoiceBox
        activeInactive.getItems().addAll(activeInactiveArray);
        
        //Set initial form values
        cusName.setText(getCurrentCustomer().getName());
        cusPhone.setText(getCurrentCustomer().getPhone());
        cusAddress1.setText(getCurrentCustomer().getAddress1());
        cusAddress2.setText(getCurrentCustomer().getAddress2());
        cusCity.setText(getCurrentCustomer().getCity());
        cusZip.setText(getCurrentCustomer().getZip());
        cusCountry.setText(getCurrentCustomer().getCountry());
        activeInactive.setValue(getCurrentCustomer().getActive());
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
        assert activeInactive != null : "fx:id=\"activeInactive\" was not injected: check your FXML file 'CustomerModify.fxml'.";
    }
}

