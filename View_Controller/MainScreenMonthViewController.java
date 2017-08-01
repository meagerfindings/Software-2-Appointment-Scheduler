package View_Controller;

import Model.Appointment;
import static Model.Appointment.getAppointmentData;
import Model.CalendarDB;
import static Model.CalendarDB.deleteEntry;
import static Model.CalendarDB.getAppointmentAdminDetails;
import static Model.CalendarDB.getAppointmentTypesReport;
import static Model.CalendarDB.getClientAppointmentsReport;
import static Model.CalendarDB.getConsultantsAppointmentsReport;
import static Model.Time.getLocalTime;
import static Reports.AppointmentTypeReport.resetTypeReport;
import static Reports.ClientAppointmentReport.resetClientReport;
import static Reports.ConsultantScheduleReport.resetConsultantReport;
import static Reports.IOWriter.exportReportToFile;
import static View_Controller.MainScreenController.getCurrentAppointment;
import static View_Controller.MainScreenController.setCurrentAppointment;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/* FXML Controller class
 *
 * @author matgreten
 */

public class MainScreenMonthViewController implements Initializable {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    @FXML // fx:id="allAppointmentsView"
    private RadioButton allAppointmentsView; // Value injected by FXMLLoader

    @FXML // fx:id="viewWeekToggle"
    private RadioButton viewWeekToggle; // Value injected by FXMLLoader

    @FXML // fx:id="viewToggleGroup"
    private ToggleGroup viewToggleGroup; // Value injected by FXMLLoader

    @FXML // fx:id="viewMonthToggle"
    private RadioButton viewMonthToggle; // Value injected by FXMLLoader

    @FXML // fx:id="addAppointmentButton"
    private Button addAppointmentButton; // Value injected by FXMLLoader

    @FXML // fx:id="deleteAppointmentButton"
    private Button deleteAppointmentButton; // Value injected by FXMLLoader
    
    @FXML // fx:id="custoemrDataTable"
    private TableView<Appointment> appointmentDataTable;
    
    @FXML // fx:id="columnDate"
    private TableColumn<Appointment, String> columnDate; // Value injected by FXMLLoader

    @FXML // fx:id="columnStart"
    private TableColumn<Appointment, String> columnStart; // Value injected by FXMLLoader

    @FXML // fx:id="columnEnd"
    private TableColumn<Appointment, String> columnEnd; // Value injected by FXMLLoader

    @FXML // fx:id="columnTitle"
    private TableColumn<Appointment, String> columnMeetingTitle; // Value injected by FXMLLoader

    @FXML // fx:id="columnCusName"
    private TableColumn<Appointment, String> columnCusName; // Value injected by FXMLLoader

    @FXML // fx:id="columnMeetingLocation"
    private TableColumn<Appointment, String> columnMeetingLocation; // Value injected by FXMLLoader

    @FXML // fx:id="columnCusPhone"
    private TableColumn<Appointment, String> columnCusPhone; // Value injected by FXMLLoader

    @FXML // fx:id="columnMeetingUrl"
    private TableColumn<Appointment, String> columnMeetingUrl; // Value injected by FXMLLoader

    @FXML // fx:id="columnMeetingDescription"
    private TableColumn<Appointment, String> columnMeetingDescription; // Value injected by FXMLLoader

    @FXML // fx:id="exportReportsDropdown"
    private MenuButton exportReportsDropdown; // Value injected by FXMLLoader

    @FXML // fx:id="reportAppointmentByMonth"
    private MenuItem reportAppointmentByMonth; // Value injected by FXMLLoader

    @FXML // fx:id="reportClientAppointments"
    private MenuItem reportClientAppointments; // Value injected by FXMLLoader

    @FXML // fx:id="logOffButton"
    private Button logOffButton; // Value injected by FXMLLoader

    @FXML // fx:id="modifyAppointmentButton"
    private Button modifyAppointmentButton; // Value injected by FXMLLoader

    @FXML // fx:id="viewAppointmentButton"
    private Button viewAppointmentButton; // Value injected by FXMLLoader
        
    @FXML
    void addAppointment(ActionEvent event) throws IOException {
        System.out.println("Opening add appointment screen.");
        
        cleanAppointmentList();
        
        try {
            reloadCustomerDatabase();
        } catch (SQLException ex) {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        FXMLLoader addAppointmentLoader = new FXMLLoader(AppointmentAddController.class.getResource("AppointmentAdd.fxml"));
        GridPane addApppointmentScreen = (GridPane) addAppointmentLoader.load();
        Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                
        Scene sceneMainScreen = new Scene(addApppointmentScreen);
        newStage.setScene(sceneMainScreen);
        AppointmentAddController controller = addAppointmentLoader.getController();
        newStage.show();
    }

    @FXML
    boolean deleteAppointment(ActionEvent event) throws IOException, SQLException  {
        
        if (getCurrentAppointment() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selction Error");
            alert.setHeaderText("No Appointment Selected");
            alert.setContentText("Select an appointment first.");
            alert.showAndWait();
            return false;
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deletion Confirmation");
        alert.setHeaderText("Deletion Warning");
        alert.setContentText("You are about to delete the appointment " + getCurrentAppointment().getTitle() + "." + "\n\nWould you like to proceed?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() != ButtonType.OK){
        // user chose cancel, exit this selection.
            return false;
        }
        
        int target = getCurrentAppointment().getPK();
        
        String deleteQuery = "DELETE FROM `appointment` WHERE `appointmentId`='" + target + "'";
        
        boolean delResult = deleteEntry(deleteQuery);
        
        if (delResult == true){
            System.out.println("Appointment " + getCurrentAppointment().getTitle() + " was successfully eliminated from the database.");
        } else {
            System.out.println("Appointment " + getCurrentAppointment().getTitle() + " could not be removed from the database.");
        }
                
        // Reload this page with current results
        FXMLLoader mainLoader = new FXMLLoader(MainScreenController.class.getResource("/View_Controller/MainScreen.fxml"));
        GridPane mainScreen = (GridPane) mainLoader.load();
        Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                
        Scene sceneMainScreen = new Scene(mainScreen);
        newStage.setScene(sceneMainScreen);
        MainScreenController controller = mainLoader.getController();
        newStage.show();
        return delResult;
    }
    
    @FXML
    boolean modifyAppointment(ActionEvent event) throws IOException {
        
        if (getCurrentAppointment() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selction Error");
            alert.setHeaderText("No Appointment Selected");
            alert.setContentText("Select an appointment first.");
            alert.showAndWait();
            return false;
        }
        
        FXMLLoader modifyAppointmentLoader = new FXMLLoader(AppointmentModifyController.class.getResource("AppointmentModify.fxml"));
        GridPane modifyAppointmentScreen = (GridPane) modifyAppointmentLoader.load();
        Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                
        Scene sceneMainScreen = new Scene(modifyAppointmentScreen);
        newStage.setScene(sceneMainScreen);
        AppointmentModifyController controller = modifyAppointmentLoader.getController();
        newStage.show();
        return true;
    }

    @FXML
    boolean viewAppointment(ActionEvent event) throws SQLException {
        
        if (getCurrentAppointment() == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selction Error");
            alert.setHeaderText("No Appointment Selected");
            alert.setContentText("Select an appointment first.");
            alert.showAndWait();
            return false;
        }
        
        String title = getCurrentAppointment().getTitle();
        String customer = getCurrentAppointment().getName();
        LocalDate date = getCurrentAppointment().getStartDate();
        String startTime = getCurrentAppointment().getStartHr() + ":" + getCurrentAppointment().getStartMin() + " " + getCurrentAppointment().getStartAMPM();
        String endTime = getCurrentAppointment().getStopHr() + ":" + getCurrentAppointment().getStopMin() + " " + getCurrentAppointment().getStopAMPM();
        String location = getCurrentAppointment().getLocation();
        String phone = getCurrentAppointment().getPhone();
        String description = getCurrentAppointment().getDescription();
        String contact = getCurrentAppointment().getContact();
        String url = getCurrentAppointment().getUrl();
        int primaryKey = getCurrentAppointment().getPK();
        
        String[] adminDetails = getAppointmentAdminDetails(primaryKey);
        String host;
        String createdAt;
        String lastUpdatedBy;
        String updatedAt;
        String messageDetails;
        
        if (adminDetails[0].equals("failed search")){ // If the SQL search failed do not show created by & updated by information
            
            messageDetails = "Appointment with " + customer + ".\n" +
                                "Date: " + date +"\n" +
                                "Time: " + startTime + " - " + endTime + "\n" + 
                                "Location: " + location + "\n" +
                                "Phone: " + phone + "\n" +
                                "Descripiton: " + description + "\n" +
                                "Contact: " + contact + "\n" +
                                "URL: " + url;
            
        } else { // If the SQL was successful show created by & updated by information
            host = adminDetails[0];
            createdAt = adminDetails[1];
            lastUpdatedBy = adminDetails[2];
            updatedAt = adminDetails[3];
            
            messageDetails = "Appointment with " + customer + ".\n" +
                                "Date: " + date +"\n" +
                                "Time: " + startTime + " - " + endTime + "\n" + 
                                "Location: " + location + "\n" +
                                "Phone: " + phone + "\n" +
                                "Descripiton: " + description + "\n" +
                                "Contact: " + contact + "\n" +
                                "URL: " + url + "\n\n\n" +
                                "Created by " + host + " at " + createdAt + ".\n" +
                                "Last modified by " + lastUpdatedBy + " at " + updatedAt + ".";
        }
        
        System.out.println(messageDetails);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText("Appointment Details");
        alert.setContentText(messageDetails);
        alert.showAndWait();
        
        return true;
    }

     @FXML
    void exportAppointmentByMonthReport(ActionEvent event) throws SQLException {
        String messageDetails = getAppointmentTypesReport();
        String exportResult = exportReportToFile(messageDetails, "report_appointment_by_month");
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Appointment Types by Month Report");
        alert.setHeaderText(exportResult);
        alert.setContentText(messageDetails);
        alert.showAndWait();
        
        resetTypeReport(); // Clear out report in case it is run a second time.
    }

    @FXML
    void exportClientAppointmentReport(ActionEvent event) throws SQLException {
        String messageDetails = getClientAppointmentsReport();
        String exportResult = exportReportToFile(messageDetails, "report_client_appointments");
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Client Appointments Report");
        alert.setHeaderText(exportResult);
        alert.setContentText(messageDetails);
        System.out.println(messageDetails);
        alert.showAndWait();
        
        resetClientReport(); // Clear out report in case it is run a second time.
    }

    @FXML
    void exportConsultantSchedulesReport(ActionEvent event) throws SQLException {
        String messageDetails = getConsultantsAppointmentsReport();
        String exportResult = exportReportToFile(messageDetails, "report_consultant_schedules");
        
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Consultant Schedule Report:");
        alert.setHeaderText(exportResult);
        alert.setContentText(messageDetails);
        System.out.println(messageDetails);
        alert.showAndWait();
        
        resetConsultantReport(); // Clear out report in case it is run a second time.
    }

    @FXML
    void logOff(ActionEvent event) throws IOException {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Log Off and Exit Confirmation");
        alert.setContentText("Are you sure you want to log out and exit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // Exit was chosen again. Quit the program.
            System.out.println("Closing up shop. Exiting the program.");
            System.exit(0);
        } else {
            // Cancel was chosen, let the confirmation window exit and do nothing.
        }
    }

    @FXML
    void viewByMonth(ActionEvent event) {
        // No Action necessary
    }

    @FXML
    void viewByWeek(ActionEvent event) throws IOException {
        FXMLLoader mainLoader = new FXMLLoader(MainScreenWeekViewController.class.getResource("/View_Controller/MainScreenWeekView.fxml"));
        GridPane mainScreen = (GridPane) mainLoader.load();
        Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene sceneMainScreen = new Scene(mainScreen);
        newStage.setScene(sceneMainScreen);
        MainScreenWeekViewController controller = mainLoader.getController();
        newStage.show();
    }
    
    @FXML
    void viewAllAppointments(ActionEvent event) throws IOException {
        FXMLLoader mainLoader = new FXMLLoader(MainScreenController.class.getResource("/View_Controller/MainScreen.fxml"));
        GridPane mainScreen = (GridPane) mainLoader.load();
        Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene sceneMainScreen = new Scene(mainScreen);
        newStage.setScene(sceneMainScreen);
        MainScreenController controller = mainLoader.getController();
        newStage.show();
    }
    
    private void loadCustomerDatabase() throws SQLException { // Method to populate the customers list from which we populate the tableview.
       String sqlJoin =   "SELECT appointmentId, title, description, location, contact, url, customer.customerId, start, end, customerName, phone, appointment.createdBy FROM appointment " +
                            "INNER JOIN customer " +
                            "ON appointment.customerId = customer.customerId " +
                            "INNER JOIN address " +
                            "ON customer.addressId = address.addressId " +
                            "WHERE YEAR(`start`) = YEAR(NOW()) AND MONTH(`start`) = MONTH(NOW()) AND customer.active = 1 " +
                            "ORDER BY start, end, title";
       
        CalendarDB dbConnection = new CalendarDB();
        Statement tableStatment = dbConnection.connectDB().createStatement();
        ResultSet tblRS = tableStatment.executeQuery(sqlJoin);
        
       try {
            while (tblRS.next()){
                // Parse out dates, hours, minutes, and am/pm for start and end times
                Timestamp rawStartDate = tblRS.getTimestamp(8);
                Timestamp localRawStart = getLocalTime(rawStartDate);
                LocalDate startDate = localRawStart.toLocalDateTime().toLocalDate();
                String semiRawStartHr = new SimpleDateFormat("HH").format(localRawStart);
                String convertedStartMin = new SimpleDateFormat("mm").format(localRawStart);
                String startAmPm = "AM";

                int semiConvStartHr = Integer.parseInt(semiRawStartHr);
                int convertedStartHr = semiConvStartHr;

                if (semiConvStartHr > 11){
                    startAmPm = "PM";

                    if (semiConvStartHr > 12){
                        convertedStartHr = semiConvStartHr - 12;
                    }
                } else if (semiConvStartHr == 0){
                    convertedStartHr = 12;
                }

                Timestamp rawStopDate = tblRS.getTimestamp(9);
                Timestamp localRawStop = getLocalTime(rawStopDate);
                LocalDate stopDate = localRawStop.toLocalDateTime().toLocalDate();
                String semiRawStopHr = new SimpleDateFormat("HH").format(localRawStop);
                String convertedStopMin = new SimpleDateFormat("mm").format(localRawStop);
                String stopAmPm = "AM";

                int semiConvStopHr = Integer.parseInt(semiRawStopHr);
                int convertedStopHr = semiConvStopHr;

                if (semiConvStopHr > 11){
                    stopAmPm = "PM";

                    if (semiConvStopHr > 12){
                        convertedStopHr = semiConvStopHr - 12;
                    }
                } else if (semiConvStopHr == 0){
                    convertedStopHr = 12;
                }

                String stringStopHr = Integer.toString(convertedStopHr);

                if (convertedStopHr < 10){
                    stringStopHr = "0" + convertedStopHr;
                }
                
                // Create the appointment entry in the table
                Model.Appointment appointmentEntry = new Appointment(tblRS.getInt("appointmentId"), tblRS.getString("title"), tblRS.getString("description"), tblRS.getString("location"), tblRS.getString("contact"), tblRS.getString("url"), tblRS.getInt("customerId"), convertedStartHr, convertedStartMin, startAmPm, convertedStopHr, convertedStopMin, stopAmPm, startDate, stopDate, tblRS.getString("phone"), tblRS.getString("customerName"), tblRS.getString("createdBy"));
                getAppointmentData().add(appointmentEntry);
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
    
    private void reloadCustomerDatabase() throws SQLException { // Method to populate the customers list from which we populate the tableview.
       String sqlJoin =   "SELECT appointmentId, title, description, location, contact, url, customer.customerId, start, end, customerName, phone, active, appointment.createdBy FROM appointment " +
                            "INNER JOIN customer " +
                            "ON appointment.customerId = customer.customerId " +
                            "INNER JOIN address " +
                            "ON customer.addressId = address.addressId " +
                            "WHERE customer.active = 1 ";
       CalendarDB dbConnection = new CalendarDB();
        Statement tableStatment = dbConnection.connectDB().createStatement();
        ResultSet tblRS = tableStatment.executeQuery(sqlJoin);
        
       try {
            while (tblRS.next()){
                // Parse out dates, hours, minutes, and am/pm for start and end times
                Timestamp rawStartDate = tblRS.getTimestamp(8);
                Timestamp localRawStart = getLocalTime(rawStartDate);
                LocalDate startDate = localRawStart.toLocalDateTime().toLocalDate();
                String semiRawStartHr = new SimpleDateFormat("HH").format(localRawStart);
                String convertedStartMin = new SimpleDateFormat("mm").format(localRawStart);
                String startAmPm = "AM";

                int semiConvStartHr = Integer.parseInt(semiRawStartHr);
                int convertedStartHr = semiConvStartHr;

                if (semiConvStartHr > 11){
                    startAmPm = "PM";

                    if (semiConvStartHr > 12){
                        convertedStartHr = semiConvStartHr - 12;
                    }
                } else if (semiConvStartHr == 0){
                    convertedStartHr = 12;
                }

                Timestamp rawStopDate = tblRS.getTimestamp(9);
                Timestamp localRawStop = getLocalTime(rawStopDate);
                LocalDate stopDate = localRawStop.toLocalDateTime().toLocalDate();
                String semiRawStopHr = new SimpleDateFormat("HH").format(localRawStop);
                String convertedStopMin = new SimpleDateFormat("mm").format(localRawStop);
                String stopAmPm = "AM";

                int semiConvStopHr = Integer.parseInt(semiRawStopHr);
                int convertedStopHr = semiConvStopHr;

                if (semiConvStopHr > 11){
                    stopAmPm = "PM";

                    if (semiConvStopHr > 12){
                        convertedStopHr = semiConvStopHr - 12;
                    }
                } else if (semiConvStopHr == 0){
                    convertedStopHr = 12;
                }

                String stringStopHr = Integer.toString(convertedStopHr);

                if (convertedStopHr < 10){
                    stringStopHr = "0" + convertedStopHr;
                }
                
                // Create the appointment entry in the table
                Model.Appointment appointmentEntry = new Appointment(tblRS.getInt("appointmentId"), tblRS.getString("title"), tblRS.getString("description"), tblRS.getString("location"), tblRS.getString("contact"), tblRS.getString("url"), tblRS.getInt("customerId"), convertedStartHr, convertedStartMin, startAmPm, convertedStopHr, convertedStopMin, stopAmPm, startDate, stopDate, tblRS.getString("phone"), tblRS.getString("customerName"), tblRS.getString("createdBy"));
                getAppointmentData().add(appointmentEntry);
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
    
    public void setupAppointmentTable() {
        // Add observable list data to the Customer table
        appointmentDataTable.setItems(Appointment.getAppointmentData());
    }
    
    private void cleanAppointmentList(){
        // Remove the appointment from the temporary appointment list holder.
        while (getAppointmentData().size() > 0) {
        for (int i = 0; i < getAppointmentData().size(); ++i) {
            getAppointmentData().remove(i); 
            }  
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cleanAppointmentList();
        setupAppointmentTable();
        
        try {
            loadCustomerDatabase();
        } catch (SQLException ex) {
            Logger.getLogger(MainScreenMonthViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Initializes display of componenets for Appointments TableView
        columnDate.setCellValueFactory(cellData -> cellData.getValue().renderedDateProperty());
        columnCusName.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
        columnStart.setCellValueFactory(cellData -> cellData.getValue().startTimeProperty());
        columnEnd.setCellValueFactory(cellData -> cellData.getValue().endTimeProperty());
        columnMeetingTitle.setCellValueFactory(cellData -> cellData.getValue().appointmentTitleProperty());
        columnMeetingLocation.setCellValueFactory(cellData -> cellData.getValue().appointmentLocationProperty());
        columnCusName.setCellValueFactory(cellData -> cellData.getValue().customerNameProperty());
        columnCusPhone.setCellValueFactory(cellData -> cellData.getValue().customerPhoneProperty());
        columnMeetingDescription.setCellValueFactory(cellData -> cellData.getValue().appointmentDescriptionProperty());
        
        //Initializes listener for selections in the Appointments Table
        appointmentDataTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, currentSelection) -> setCurrentAppointment(currentSelection));    
    }  

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert allAppointmentsView != null : "fx:id=\"allAppointmentsView\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert viewWeekToggle != null : "fx:id=\"viewWeekToggle\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert viewToggleGroup != null : "fx:id=\"viewToggleGroup\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert viewMonthToggle != null : "fx:id=\"viewMonthToggle\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert addAppointmentButton != null : "fx:id=\"addAppointmentButton\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert deleteAppointmentButton != null : "fx:id=\"deleteAppointmentButton\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert appointmentDataTable != null : "fx:id=\"appointmentDataTable\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert columnDate != null : "fx:id=\"columnDate\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert columnStart != null : "fx:id=\"columnStart\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert columnEnd != null : "fx:id=\"columnEnd\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert columnMeetingTitle != null : "fx:id=\"columnMeetingTitle\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert columnCusName != null : "fx:id=\"columnCusName\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert columnMeetingLocation != null : "fx:id=\"columnMeetingLocation\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert columnCusPhone != null : "fx:id=\"columnCusPhone\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert columnMeetingUrl != null : "fx:id=\"columnMeetingUrl\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert columnMeetingDescription != null : "fx:id=\"columnMeetingDescription\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert exportReportsDropdown != null : "fx:id=\"exportReportsDropdown\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert reportAppointmentByMonth != null : "fx:id=\"reportAppointmentByMonth\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert reportClientAppointments != null : "fx:id=\"reportClientAppointments\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert logOffButton != null : "fx:id=\"logOffButton\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert modifyAppointmentButton != null : "fx:id=\"modifyAppointmentButton\" was not injected: check your FXML file 'MainScreen.fxml'.";
        assert viewAppointmentButton != null : "fx:id=\"viewAppointmentButton\" was not injected: check your FXML file 'MainScreen.fxml'.";
    }
}