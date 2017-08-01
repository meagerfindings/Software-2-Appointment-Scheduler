package View_Controller;

import Model.CalendarDB;
import Model.Customer;
import static View_Controller.AppointmentAddController.setApptCustomer;
import static View_Controller.AppointmentModifyController.setModCustomer;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author matgreten
 */
public class CustomerMainController implements Initializable {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="customerSearchField"
    private TextField customerSearchField; // Value injected by FXMLLoader

    @FXML // fx:id="customerSearchButton"
    private Button customerSearchButton; // Value injected by FXMLLoader
    
    @FXML // fx:id="custoemrDataTable"
    private TableView<Customer> customerDataTable;

    @FXML // fx:id="cusName"
    private TableColumn<Customer, String> cusName; // Value injected by FXMLLoader

    @FXML // fx:id="cusPhone"
    private TableColumn<Customer, String> cusPhone; // Value injected by FXMLLoader

    @FXML // fx:id="cusAddress1"
    private TableColumn<Customer, String> cusAddress1; // Value injected by FXMLLoader
    
    @FXML // fx:id="cusAddress2"
    private TableColumn<Customer, String> cusAddress2; // Value injected by FXMLLoader

    @FXML // fx:id="cusCity"
    private TableColumn<Customer, String> cusCity; // Value injected by FXMLLoader

    @FXML // fx:id="custCountry"
    private TableColumn<Customer, String> custCountry; // Value injected by FXMLLoader

    @FXML // fx:id="cusZip"
    private TableColumn<Customer, String> cusZip; // Value injected by FXMLLoader
    
    @FXML // fx:id="customerActive"
    private TableColumn<Customer, String> customerActive; // Value injected by FXMLLoader

    @FXML // fx:id="selectCustomerButton"
    private Button selectCustomerButton; // Value injected by FXMLLoader

    @FXML // fx:id="modifyCustomerButton"
    private Button modifyCustomerButton; // Value injected by FXMLLoader

//    @FXML // fx:id="deleteCustomerButton"
//    private Button deleteCustomerButton; // Value injected by FXMLLoader
    
    @FXML // fx:id="addCustomerButton"
    private Button addCustomerButton; // Value injected by FXMLLoader

    @FXML // fx:id="cancel"
    private Button cancel; // Value injected by FXMLLoader
    
    private static String referalPage;
    
    private static Customer currentCustomer; // Which Customer is currently selected in the customers table.
    
    /**
     * @return the currentCustomer
     */
    static Customer getCurrentCustomer() {
        return currentCustomer;
    }

    /**
     * @param aCurrentCustomer the currentCustomer to set
     */
    static void setCurrentCustomer(Customer aCurrentCustomer) {
        currentCustomer = aCurrentCustomer;
    }

    @FXML
    private final FilteredList<Customer> filteredCustomers = new FilteredList<>(Customer.customerList, search -> true);

    @FXML
    void cancelButtonClicked(ActionEvent event) throws IOException{
        System.out.println("Opening add appointment screen.");
        cleanCustomerList();
        setCurrentCustomer(null); 
        
        String referralScreen = CustomerMainController.getReferalPage();
        
        if (referralScreen.equalsIgnoreCase("AppointmentModifyController")){
            FXMLLoader appointmentAddScreenLoader = new FXMLLoader(AppointmentModifyController.class.getResource("AppointmentModify.fxml"));
            GridPane appointmentAddScreen = (GridPane) appointmentAddScreenLoader.load();
            Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene sceneMainScreen = new Scene(appointmentAddScreen);
            newStage.setScene(sceneMainScreen);
            AppointmentModifyController controller = appointmentAddScreenLoader.getController();
            newStage.show();
        } else {
            FXMLLoader appointmentAddScreenLoader = new FXMLLoader(AppointmentAddController.class.getResource("AppointmentAdd.fxml"));
            GridPane appointmentAddScreen = (GridPane) appointmentAddScreenLoader.load();
            Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene sceneMainScreen = new Scene(appointmentAddScreen);
            newStage.setScene(sceneMainScreen);
            AppointmentAddController controller = appointmentAddScreenLoader.getController();
            newStage.show();
        }
    }

    @FXML
    void customerSearch(ActionEvent event) {
        String searchValue = String.valueOf(customerSearchField.getText());
                
        System.out.println("Searching for customer by the value: " + searchValue);
        
        // Lambda function that takes in a customer object and compares the values of our search against this object's properties
        filteredCustomers.setPredicate((Customer customer) -> {
                if (searchValue == null || searchValue.isEmpty()) {
                    return true;
                }
                String upCaseSearch = searchValue.toUpperCase();
                if (customer.getName().toUpperCase().contains(upCaseSearch)) {
                    return true; //searchValue matches or is contained within a customerName.
                }
                return false; // searchValue must not have matched any values in our table
                });
    }

    /* Commented out deleteCustomer method to disable deletion in prefrence of setting custoemers to inactive.
    @FXML
    void deleteCustomer(ActionEvent event) throws IOException, SQLException {
    
    int target = getCurrentCustomer().getPK();
    
    String deleteQuery = "DELETE FROM `customer` WHERE `customerId`='" + target + "'";
    
    boolean result = deleteEntry(deleteQuery);
    
    if (result == true){
    System.out.println("Customer " + getCurrentCustomer().getName() + " was successfully eliminated from the database.");
    } else {
    System.out.println("Customer " + getCurrentCustomer().getName() + " could not be removed from the database.");
    }
    
    // Reload this page with current results
    FXMLLoader mainCustomerScreenLoader = new  FXMLLoader(CustomerMainController.class.getResource("CustomerMain.fxml"));
    GridPane mainCustomerScreen = (GridPane) mainCustomerScreenLoader.load();
    Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    
    Scene sceneMainScreen = new Scene(mainCustomerScreen);
    newStage.setScene(sceneMainScreen);
    CustomerMainController controller = mainCustomerScreenLoader.getController();
    newStage.show();
    }
    */

    @FXML
    boolean modifyCustomer(ActionEvent event) throws IOException {
        
        if (getCurrentCustomer() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selction Error");
            alert.setHeaderText("No Customer Selected");
            alert.setContentText("Select a customer first.");
            alert.showAndWait();
            return false;
        }
        
        System.out.println("Opening modify customer screen.");
        
        FXMLLoader customerModifyScreenLoader = new FXMLLoader(CustomerModifyController.class.getResource("CustomerModify.fxml"));
        GridPane customerModifyScreen = (GridPane) customerModifyScreenLoader.load();
        Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                
        Scene sceneMainScreen = new Scene(customerModifyScreen);
        newStage.setScene(sceneMainScreen);
        CustomerModifyController controller = customerModifyScreenLoader.getController();
        newStage.show();
        return true;
    }
    
    @FXML
    void addCustomer(ActionEvent event) throws IOException{
        System.out.println("Opening add customer screen.");
        
        FXMLLoader customerAddScreenLoader = new FXMLLoader(CustomerAddController.class.getResource("CustomerAdd.fxml"));
        GridPane customerAddScreen = (GridPane) customerAddScreenLoader.load();
        Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene sceneMainScreen = new Scene(customerAddScreen);
        newStage.setScene(sceneMainScreen);
        CustomerAddController controller = customerAddScreenLoader.getController();
        newStage.show();
    }

    @FXML
    boolean selectCustomer(ActionEvent event) throws IOException{
        
        if (getCurrentCustomer() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selction Error");
            alert.setHeaderText("No Customer Selected");
            alert.setContentText("Select a customer first.");
            alert.showAndWait();
            return false;
        }
        
        if (getCurrentCustomer().getActive().equalsIgnoreCase("inactive")){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Inactive Customer");
            alert.setHeaderText("Inactive customer selected.");
            alert.setContentText(getCurrentCustomer().getName() + " is currently inactive.\n\nSelecting this customer for your appointment will prevent the appointment from showing in the main scheduling screen after saving the appointment.\n\nWould you like to proceed?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() != ButtonType.OK){
            // user chose cancel, exit this selection.
                return false;
            }
        }
        
        
        System.out.println("Opening add appointment screen.");
        
        String referralScreen = CustomerMainController.getReferalPage();
        
        if (referralScreen.equals("AppointmentModifyController")){
            
            setModCustomer(getCurrentCustomer()); // Pass the currently selected Customer back to the appointment scheduler screen.
            cleanCustomerList();
            
            FXMLLoader appointmentAddScreenLoader = new FXMLLoader(AppointmentModifyController.class.getResource("AppointmentModify.fxml"));
            GridPane appointmentAddScreen = (GridPane) appointmentAddScreenLoader.load();
            Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene sceneMainScreen = new Scene(appointmentAddScreen);
            newStage.setScene(sceneMainScreen);
            AppointmentModifyController controller = appointmentAddScreenLoader.getController();
            newStage.show();
        } else {
            setApptCustomer(getCurrentCustomer()); // Pass the currently selected Customer back to the appointment scheduler screen.
            cleanCustomerList();
        
            FXMLLoader appointmentAddScreenLoader = new FXMLLoader(AppointmentAddController.class.getResource("AppointmentAdd.fxml"));
            GridPane appointmentAddScreen = (GridPane) appointmentAddScreenLoader.load();
            Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene sceneMainScreen = new Scene(appointmentAddScreen);
            newStage.setScene(sceneMainScreen);
            AppointmentAddController controller = appointmentAddScreenLoader.getController();
            newStage.show();
        }
        return true;
    }
    
    @FXML
    private void loadCustomerDatabase() throws SQLException { // Method to populate the customers list from which we populate the tableview.
        String sqlJoin = "SELECT customerId, customerName, phone, address, address2, city, postalCode, country, customer.addressId, address.cityId, city.countryId, active FROM customer " +
                         "INNER JOIN address " +
                         "ON customer.addressId = address.addressId " +
                         "INNER JOIN city " +
                         "ON address.cityId = city.cityId " +
                         "INNER JOIN country " +
                         "ON city.countryId = country.countryId";
       
        CalendarDB dbConnection = new CalendarDB();
        Statement tableStatment = dbConnection.connectDB().createStatement();
        ResultSet tblRS = tableStatment.executeQuery(sqlJoin);
        
        try {
            while (tblRS.next()){
                String activeInactive = "Active";

                if (tblRS.getInt("active") == 0){
                    activeInactive = "Inactive";
                }

                Model.Customer customerEntry = new Customer(tblRS.getInt(1), tblRS.getString(2), tblRS.getString(3), tblRS.getString(4), tblRS.getString(5), tblRS.getString(6), tblRS.getString(7), tblRS.getString(8), tblRS.getInt(9), tblRS.getInt(10), tblRS.getInt(11), activeInactive);
                Customer.customerList.add(customerEntry);
            }   
        } catch (SQLException e) {
                System.out.println("SQLException: "+e.getMessage());
                System.out.println("SQLState: "+e.getSQLState());
                System.out.println("VendorError: "+e.getErrorCode()); 
        } finally {
            try {tblRS.close(); } catch (SQLException e) { /* ignored */ }
            try { tableStatment.close(); } catch (SQLException e) { /* ignored */ }
        }
    }
    
    public void setupCustomerTable() {
        // Add observable list data to the Customer table
        customerDataTable.setItems(Model.Customer.getCustomerData());
    }
    
    @FXML
    private void cleanCustomerList(){
        // Remove the customers from the temporary Customer list holder.
        while (Customer.customerList.size() > 0) {
        for (int i = 0; i < Customer.customerList.size(); ++i) {
            Customer.customerList.remove(i); 
            }  
        }
    }
    
    /**
     * @return the referalPage
     */
    public static String getReferalPage() {
        return referalPage;
    }

    /**
     * @param referalPage the referalPage to set
     */
    public static void setReferalPage(String referalPage) {
        CustomerMainController.referalPage = referalPage;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cleanCustomerList();
        setupCustomerTable();
        
        try {
            loadCustomerDatabase();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Initializes display of componenets for Customers TableView
        cusName.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
        cusPhone.setCellValueFactory(cellData -> cellData.getValue().customerPhoneProperty());
        cusAddress1.setCellValueFactory(cellData -> cellData.getValue().customerAddress1Property());
        cusAddress2.setCellValueFactory(cellData -> cellData.getValue().customerAddress2Property());
        cusCity.setCellValueFactory(cellData -> cellData.getValue().customerCityProperty());
        cusZip.setCellValueFactory(cellData -> cellData.getValue().customerZipProperty());
        custCountry.setCellValueFactory(cellData -> cellData.getValue().customerCountryProperty());
        customerActive.setCellValueFactory(cellData -> cellData.getValue().customerActiveProperty());
                
        //Initializes search componenets for Customers Table
        customerSearchField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String searchValue) -> {
            SortedList<Customer> sortedCustomerList = new SortedList<>(filteredCustomers);
            sortedCustomerList.comparatorProperty().bind(customerDataTable.comparatorProperty());             
            customerDataTable.setItems(sortedCustomerList);
        });  
        
        //Initializes listener for selections in the Customers Table
        customerDataTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, currentSelection) -> setCurrentCustomer(currentSelection));    
    }
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert customerSearchField != null : "fx:id=\"customerSearchField\" was not injected: check your FXML file 'CustomerMain.fxml'.";
        assert customerSearchButton != null : "fx:id=\"customerSearchButton\" was not injected: check your FXML file 'CustomerMain.fxml'.";
        assert cusName != null : "fx:id=\"cusName\" was not injected: check your FXML file 'CustomerMain.fxml'.";
        assert cusPhone != null : "fx:id=\"cusPhone\" was not injected: check your FXML file 'CustomerMain.fxml'.";
        assert cusAddress1 != null : "fx:id=\"cusAddress\" was not injected: check your FXML file 'CustomerMain.fxml'.";
        assert cusAddress2 != null : "fx:id=\"cusAddress\" was not injected: check your FXML file 'CustomerMain.fxml'.";
        assert cusCity != null : "fx:id=\"cusCity\" was not injected: check your FXML file 'CustomerMain.fxml'.";
        assert custCountry != null : "fx:id=\"custCountry\" was not injected: check your FXML file 'CustomerMain.fxml'.";
        assert cusZip != null : "fx:id=\"cusZip\" was not injected: check your FXML file 'CustomerMain.fxml'.";
        assert selectCustomerButton != null : "fx:id=\"selectCustomerButton\" was not injected: check your FXML file 'CustomerMain.fxml'.";
        assert modifyCustomerButton != null : "fx:id=\"modifyCustomerButton\" was not injected: check your FXML file 'CustomerMain.fxml'.";
//        assert deleteCustomerButton != null : "fx:id=\"deleteCustomerButton\" was not injected: check your FXML file 'CustomerMain.fxml'.";
        assert cancel != null : "fx:id=\"cancel\" was not injected: check your FXML file 'CustomerMain.fxml'.";
        assert addCustomerButton != null : "fx:id=\"addCustomerButton\" was not injected: check your FXML file 'CustomerMain.fxml'.";
        assert customerActive != null : "fx:id=\"customerActive\" was not injected: check your FXML file 'CustomerMain.fxml'.";
    }
}
