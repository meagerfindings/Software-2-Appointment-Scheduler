package View_Controller;

import Model.Appointment;
import static Model.Appointment.getAppointmentData;
import static Model.CalendarDB.createAppointment;
import static Model.CalendarDB.createReminder;
import static Model.CalendarDB.getLoggedInUser;
import static Model.CalendarDB.setRemindersUnChecked;
import Model.Customer;
import static Model.Time.convertDate;
import static Model.Time.datePiecesToString;
import static Model.Time.getLocalTime;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AppointmentAddController implements Initializable {
    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="apptTitle"
    private TextField apptTitle; // Value injected by FXMLLoader

    @FXML // fx:id="apptCusName"
    private TextField apptCusName; // Value injected by FXMLLoader

    @FXML // fx:id="apptChooseCustomerButton"
    private Button apptChooseCustomerButton; // Value injected by FXMLLoader

    @FXML // fx:id="apptLocation"
    private TextField apptLocation; // Value injected by FXMLLoader

    @FXML // fx:id="apptContact"
    private TextField apptContact; // Value injected by FXMLLoader

    @FXML // fx:id="apptStartDate"
    private DatePicker apptStartDate; // Value injected by FXMLLoader
    
    @FXML // fx:id="apptDescription"
    private ChoiceBox<String> apptDescription; // Value injected by FXMLLoader

    @FXML // fx:id="apptUrl"
    private TextField apptUrl; // Value injected by FXMLLoader

    @FXML // fx:id="save"
    private Button save; // Value injected by FXMLLoader

    @FXML // fx:id="cancel"
    private Button cancel; // Value injected by FXMLLoader

    @FXML // fx:id="clearButton"
    private Button clearButton; // Value injected by FXMLLoader
    
    private final String[] minutesArray = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59"};
    private final String[] amPmArray = new String[]{"AM", "PM"};
    private final String[] appointmentTypes = new String[]{"Customer Calibration", "Troubleshooting", "Initial Consultation", "Demo",  "Routine Check-In", "Emergency Customer Assistance"};
    
    @FXML // fx:id="startHr"
    private ChoiceBox<Integer> startHr; // Value injected by FXMLLoader
    
    @FXML // fx:id="startMin"
    private ChoiceBox<String> startMin; // Value injected by FXMLLoader

    @FXML // fx:id="startAMPM"
    private ChoiceBox<String> startAMPM; // Value injected by FXMLLoader

    @FXML // fx:id="stopHr"
    private ChoiceBox<Integer> stopHr; // Value injected by FXMLLoader

    @FXML // fx:id="stopMin"
    private ChoiceBox<String> stopMin; // Value injected by FXMLLoader

    @FXML // fx:id="stopAMPM"
    private ChoiceBox<String> stopAMPM; // Value injected by FXMLLoader
    
    private static Customer apptCustomer; // Which Customer is currently on the appointment.
    
    /**
     * @return the apptCustomer
     */
    static Customer getApptCustomer() {
        return apptCustomer;
    }

    /**
     * @param inputCustomer the apptCustomer to set
     */
    static void setApptCustomer(Customer inputCustomer) {
        apptCustomer = inputCustomer;
    }
    
    private static Appointment inFlightAppt = new Appointment();; // Java object to hold the field values for the appointment we are creating. This allows for navigating away from this screen to select a customer, create a customer, and return to this screen and refill the fields with these values.
    
    /**
     * @return the inFlightAppt
     */
    static Appointment getInFlightAppt() {
        return inFlightAppt;
    }

    /**
     * @param inputAppointment the inFlightAppt to set
     */
    public static void setInFlightAppt(Appointment inputAppointment) {
        inFlightAppt = inputAppointment;
    }
    
    // Lambda used to generate alert messages.
    private final AddAlert throwAddErrorMessage = (String title, String header, String context) -> {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
        return false;
    };

    @FXML
    void cancelButtonClicked(ActionEvent event) throws IOException{
        
        // Set the temprorary holder for appointment details and customer details to null.
        setInFlightAppt(null);
        setApptCustomer(null);
        
        // Switch to the main screen.
        System.out.println("Opening main screen.");

        FXMLLoader mainScreenLoader = new FXMLLoader(MainScreenController.class.getResource("MainScreen.fxml"));
        GridPane mainScreen = (GridPane) mainScreenLoader.load();
        Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                
        Scene sceneMainScreen = new Scene(mainScreen);
        
        newStage.setScene(sceneMainScreen);
        
        MainScreenController controller = mainScreenLoader.getController();

        newStage.show();
    }
    
    @FXML
    void chooseCustomer(ActionEvent event) throws IOException {
        // Sets referral value for the customer selector
        CustomerMainController.setReferalPage("AppointmentAddController");
        
        // Initialize variables to hold field values.
        String inputTitle = apptTitle.getText();
        String inputLocation = apptLocation.getText();
        String inputContact = apptContact.getText();
        LocalDate inputStartDate = apptStartDate.getValue();
        int inputStartHr = startHr.getValue();
        String inputStartMin = startMin.getValue();
        String inputStartAMPM = startAMPM.getValue();
        int inputStopHr = stopHr.getValue();
        String inputStopMin = stopMin.getValue();
        String inputStopAMPM = stopAMPM.getValue();
        String inputUrl = apptUrl.getText();
        String inputDescription = apptDescription.getValue();
        
        // Initialize hodler for temporary appointment.
        setInFlightAppt(new Appointment());
        
        // Write field values to temporary appointment holder.
        if (inputTitle != null && !inputTitle.isEmpty() ){
            inFlightAppt.setTitle(inputTitle);
        }
        
        if (inputLocation != null && !inputLocation.isEmpty() ){
            inFlightAppt.setLocation(inputLocation);
        }
        
        if (inputContact != null && !inputContact.isEmpty() ){
            inFlightAppt.setContact(inputContact);
        }
        
        if (inputStartDate != null){
            inFlightAppt.setStartDate(inputStartDate);
        }
        
        inFlightAppt.setStartHr(inputStartHr);
        inFlightAppt.setStopHr(inputStopHr);
        
        if (inputStartMin != null && !inputStartMin.isEmpty() ){
            inFlightAppt.setStartMin(inputStartMin);
        }
        
        if (inputStopMin != null && !inputStopMin.isEmpty() ){
            inFlightAppt.setStopMin(inputStopMin);
        }
        
        if (inputStartAMPM != null && !inputStartAMPM.isEmpty() ){
            inFlightAppt.setStartAMPM(inputStartAMPM);
        }
        
        if (inputStopAMPM != null && !inputStopAMPM.isEmpty() ){
            inFlightAppt.setStopAMPM(inputStopAMPM);
        }
        
        if (inputUrl != null && !inputUrl.isEmpty() ){
            inFlightAppt.setUrl(inputUrl);
        }
        
        if (inputDescription != null && !inputDescription.isEmpty() ){
            inFlightAppt.setDescription(inputDescription);
        }
        
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
        apptTitle.setText("");
        apptCusName.setText("");
        apptLocation.setText("");
        apptContact.setText("");
        apptStartDate.getEditor().clear();
        startHr.setValue(12);
        startMin.setValue("00");
        startAMPM.setValue("PM");
        stopHr.setValue(12);
        stopMin.setValue("00");
        stopAMPM.setValue("PM");
        apptUrl.setText("");
        apptDescription.setValue("");
    }

    @FXML
    public boolean saveButtonClicked(ActionEvent event) throws IOException, SQLException{
        String inputTitle = apptTitle.getText();
        int customerId = getApptCustomer().getPK();
        String inputLocation = apptLocation.getText();
        String inputContact = apptContact.getText();
        LocalDate inputStartDate = apptStartDate.getValue();
        LocalDate inputStopDate = apptStartDate.getValue();
        int inputStartHr = startHr.getValue();
        String inputStartMin = startMin.getValue();
        String inputStartAMPM = startAMPM.getValue();
        int inputStopHr = stopHr.getValue();
        String inputStopMin = stopMin.getValue();
        String inputStopAMPM = stopAMPM.getValue();
        String inputUrl = apptUrl.getText();
        String inputDescription = apptDescription.getValue();
        String calculatedStartDate;
        String calculatedStopDate;
                
        //Start time checks
        if (inputStartHr < 7 && inputStartAMPM.equals("AM")){
            
            boolean exit = throwAddErrorMessage.addApptAlert("Business Hours Error",
                                                             "Appointment outside of businesss hours.",
                                                             "Appointment 'Start Time' cannot be earlier than 7 AM local time.");
            return exit;
            
        } else if (inputStartHr == 12 && inputStartAMPM.equals("AM")){
            boolean exit = throwAddErrorMessage.addApptAlert("Business Hours Error",
                                                             "Business Hours Error",
                                                             "Appointment 'Start Time' cannot be earlier than 7 AM local time.");
            return exit;
            
        } else if (inputStartAMPM.equals("PM")){
            if (inputStartHr == 12) {   
                //This is 12 noon, no harm done, exit this else if.
            
            } else if (inputStartHr >= 6){
                boolean exit = throwAddErrorMessage.addApptAlert("Business Hours Error",
                                                                  "Appointment outside of businesss hours.",
                                                                  "Appointment 'Start Time' cannot be later than 6 PM local time.");
                return exit;
            }
        } 
        
        //Stop time checks
        if (inputStopHr < 7 && inputStopAMPM.equals("AM")){
            boolean exit = throwAddErrorMessage.addApptAlert("Business Hours Error",
                                                              "Appointment outside of businesss hours.",
                                                              "Appointment 'Stop Time' cannot be earlier than 7 AM local time.");
            return exit;
            
        } else if (inputStopHr == 12 && inputStopAMPM.equals("AM")){
            boolean exit = throwAddErrorMessage.addApptAlert("Business Hours Error",
                                                              "Appointment outside of businesss hours.",
                                                              "Appointment 'Stop Time' cannot be earlier than 7 AM local time.");
            return exit;

        } else if (inputStopAMPM.equals("PM")){
            if (inputStopHr == 12) {   
                //This is 12 noon, no harm done, exit this else if.
            } else if (inputStopHr >= 6){
                if (inputStopMin.equals("00")) {   
                    //This is 6 pm exactly, no harm done, exit this else if.
                } else {
                    boolean exit = throwAddErrorMessage.addApptAlert("Business Hours Error",
                                                                      "Appointment outside of businesss hours.",
                                                                      "Appointment 'Stop Time' cannot be later than 6 PM local time.");
                    return exit;
                }
            }
        }
        
        if (inputStartDate == null){
            
            boolean exit = throwAddErrorMessage.addApptAlert("Appointment Date Error",
                                                             "An appointment date is necessary.",
                                                             "Cannot create an appointment without a date selected.");
            return exit;
        }
        
        if (inputDescription == null || inputDescription.isEmpty()){
            
            boolean exit = throwAddErrorMessage.addApptAlert("Appointment Description Error",
                                                             "An appointment description is necessary.",
                                                             "Cannot create an appointment without a description.");
            return exit;
        }
        
        // Format dates into format: "yyyy-MM-dd HH:mm"
        calculatedStartDate = datePiecesToString(inputStartDate, inputStartHr, inputStartMin, inputStartAMPM);
        calculatedStopDate = datePiecesToString(inputStopDate, inputStopHr, inputStopMin, inputStopAMPM);
        Timestamp convertedStartDate = convertDate(calculatedStartDate);
        Timestamp convertedStopDate = convertDate(calculatedStopDate);
        
        //AM PM check
        if (inputStartAMPM.equals("PM") && inputStopAMPM.equals("AM")){
            boolean exit = throwAddErrorMessage.addApptAlert("PM - AM Error",
                                                              "PM before AM Error",
                                                              "Appointment cannot end before it starts.");
            return exit;

        } else if (convertedStartDate.after(convertedStopDate)){
            boolean exit = throwAddErrorMessage.addApptAlert("End Time Start Time Error",
                                                              "Start time is later than end time.",
                                                              "Appointment cannot end before it starts.");
            return exit;
        }
        
        //Check for conflicting appointments      
        for(int i = 0; i < getAppointmentData().size();  i++){
            Appointment appointmentEntry = getAppointmentData().get(i);

            if (appointmentEntry.getConsultantName().equals(getLoggedInUser())) { // Check if the appointment is assigned to the signed in user.
                
                String startTime = datePiecesToString(appointmentEntry.getStartDate(), appointmentEntry.getStartHr(), appointmentEntry.getStartMin(), appointmentEntry.getStartAMPM());
                String stopTime = datePiecesToString(appointmentEntry.getStopDate(), appointmentEntry.getStopHr(), appointmentEntry.getStopMin(), appointmentEntry.getStopAMPM());

                String localStartExisting = appointmentEntry.getStartDate() + " " + appointmentEntry.getStartHr() + ":" +  appointmentEntry.getStartMin() + " " + appointmentEntry.getStartAMPM();
                String localStopExisting = appointmentEntry.getStopDate() + " " + appointmentEntry.getStopHr() + ":" +  appointmentEntry.getStopMin() + " " + appointmentEntry.getStopAMPM();
            
                Timestamp checkExistingStart = convertDate(startTime);
                Timestamp checkExistingStop = convertDate(stopTime);
                
                if (convertedStartDate.after(checkExistingStart) && convertedStartDate.before(checkExistingStop)){
                    String messageDetails = "The Start time: " + getLocalTime(convertedStartDate).toLocalDateTime() + " conflicted with the appointment: " + appointmentEntry.getTitle() + "\nwhich starts at: " + localStartExisting + "\nand ends at: " + localStopExisting;
                    System.out.println(messageDetails);
                    
                    boolean exit = throwAddErrorMessage.addApptAlert("Scheduling Conflict",
                                                                      "Scheduling conflict with another appointment.",
                                                                      messageDetails);
                    return exit;
                    
                } else if (convertedStopDate.after(checkExistingStart) && convertedStopDate.before(checkExistingStop)){
                    String messageDetails = "The Stop time: " + getLocalTime(convertedStopDate).toLocalDateTime() + " conflicted with the appointment: " + appointmentEntry.getTitle() + "\nwhich starts at: " + localStartExisting + "\nand ends at: " + localStopExisting;
                    System.out.println(messageDetails);

                    boolean exit = throwAddErrorMessage.addApptAlert("Scheduling Conflict",
                                                                      "Scheduling conflict with another appointment.",
                                                                      messageDetails);
                    return exit;
                }
            }
            
            // Reset reminders check in case this appointment is in the next 15 minutes.
            setRemindersUnChecked();
        }
        
        // Create appointment       
        int appointmentId = createAppointment(customerId, inputTitle, inputDescription, inputLocation, inputContact, inputUrl, convertedStartDate, convertedStopDate);
        
        // Create Reminder
        createReminder(appointmentId, convertedStartDate);
        
        // Set the temprorary holder for appointment details and customer details to null.
        setInFlightAppt(null);
        setApptCustomer(null);
        
        System.out.println("Opening main screen.");

        FXMLLoader mainScreenLoader = new FXMLLoader(MainScreenController.class.getResource("MainScreen.fxml"));
        GridPane mainScreen = (GridPane) mainScreenLoader.load();
        Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                
        Scene sceneMainScreen = new Scene(mainScreen);
        
        newStage.setScene(sceneMainScreen);
        
        MainScreenController controller = mainScreenLoader.getController();

        newStage.show();
        
        return true;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Setup ChoiceBoxes
        startHr.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        startMin.getItems().addAll(minutesArray);
        startAMPM.getItems().addAll(amPmArray);
        stopHr.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        stopMin.getItems().addAll(minutesArray);
        stopAMPM.getItems().addAll(amPmArray);
        apptDescription.getItems().addAll(appointmentTypes);
        
        if (inFlightAppt != null) {
        
            // Fill in fields if this appointment is already in the creatino process.
            if (getApptCustomer() != null) {
                apptCusName.setText(getApptCustomer().getName());
            }

            if (inFlightAppt.getTitle() != null && !inFlightAppt.getTitle().isEmpty()) {
                apptTitle.setText(inFlightAppt.getTitle());
            }

            if (inFlightAppt.getLocation() != null && !inFlightAppt.getLocation().isEmpty()) {
                apptLocation.setText(inFlightAppt.getLocation());
            }

            if (inFlightAppt.getContact() != null && !inFlightAppt.getContact().isEmpty()) {
                System.out.println("got to get contact on line 354");
                apptContact.setText(inFlightAppt.getContact());
            }

            if (inFlightAppt.getStartDate() != null) {
                apptStartDate.setValue(inFlightAppt.getStartDate());
            }

            if (inFlightAppt.getUrl() != null && !inFlightAppt.getUrl().isEmpty()) {
                apptUrl.setText(inFlightAppt.getUrl());
            }

            if (inFlightAppt.getDescription() != null && !inFlightAppt.getDescription().isEmpty()) {
                apptDescription.setValue(inFlightAppt.getDescription());
            }

            if (inFlightAppt.getStartHr() != 12){
                startHr.setValue(inFlightAppt.getStartHr());
            } else {
                startHr.setValue(12);
            }

            if (!inFlightAppt.getStartMin().equals("00")){
                startMin.setValue(inFlightAppt.getStartMin());
            } else {
                startMin.setValue("00");
            }

            if (!inFlightAppt.getStartAMPM().equals("PM")){
                startAMPM.setValue(inFlightAppt.getStartAMPM());
            } else {
                startAMPM.setValue("PM");
            }       

            if (inFlightAppt.getStopHr() != 12){
                stopHr.setValue(inFlightAppt.getStopHr());
            } else {
                stopHr.setValue(12);
            }

            if (!inFlightAppt.getStopMin().equals("00")){
                stopMin.setValue(inFlightAppt.getStopMin());
            } else {
                stopMin.setValue("00");
            }

            if (!inFlightAppt.getStopAMPM().equals("PM")){
                stopAMPM.setValue(inFlightAppt.getStopAMPM());
            } else {
                stopAMPM.setValue("PM");
            }
        } else { // Defaults if the inflightAppt was NULL. However, this is no longer reachable do to alert handeling in the Main Screens.
            startHr.setValue(12);
            startMin.setValue("00");
            startAMPM.setValue("PM");
            stopHr.setValue(12);
            stopMin.setValue("00");
            stopAMPM.setValue("PM");
            apptDescription.setValue("Troubleshooting");
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert apptTitle != null : "fx:id=\"apptTitle\" was not injected: check your FXML file 'AppointmentAdd.fxml'.";
        assert apptCusName != null : "fx:id=\"apptCusName\" was not injected: check your FXML file 'AppointmentAdd.fxml'.";
        assert apptChooseCustomerButton != null : "fx:id=\"apptChooseCustomerButton\" was not injected: check your FXML file 'AppointmentAdd.fxml'.";
        assert apptLocation != null : "fx:id=\"apptLocation\" was not injected: check your FXML file 'AppointmentAdd.fxml'.";
        assert apptContact != null : "fx:id=\"apptContact\" was not injected: check your FXML file 'AppointmentAdd.fxml'.";
        assert apptStartDate != null : "fx:id=\"apptStartDate\" was not injected: check your FXML file 'AppointmentAdd.fxml'.";
        assert apptDescription != null : "fx:id=\"apptDescription\" was not injected: check your FXML file 'AppointmentAdd.fxml'.";
        assert apptUrl != null : "fx:id=\"apptUrl\" was not injected: check your FXML file 'AppointmentAdd.fxml'.";
        assert save != null : "fx:id=\"save\" was not injected: check your FXML file 'AppointmentAdd.fxml'.";
        assert cancel != null : "fx:id=\"cancel\" was not injected: check your FXML file 'AppointmentAdd.fxml'.";
        assert clearButton != null : "fx:id=\"clearButton\" was not injected: check your FXML file 'AppointmentAdd.fxml'.";
        assert startHr != null : "fx:id=\"startHr\" was not injected: check your FXML file 'AppointmentAdd.fxml'.";
        assert startMin != null : "fx:id=\"startMin\" was not injected: check your FXML file 'AppointmentAdd.fxml'.";
        assert startAMPM != null : "fx:id=\"startAMPM\" was not injected: check your FXML file 'AppointmentAdd.fxml'.";
        assert stopHr != null : "fx:id=\"stopHr\" was not injected: check your FXML file 'AppointmentAdd.fxml'.";
        assert stopMin != null : "fx:id=\"stopMin\" was not injected: check your FXML file 'AppointmentAdd.fxml'.";
        assert stopAMPM != null : "fx:id=\"stopAMPM\" was not injected: check your FXML file 'AppointmentAdd.fxml'.";
    }
}

@FunctionalInterface
interface AddAlert {
    boolean addApptAlert(String title, String header, String context);
}