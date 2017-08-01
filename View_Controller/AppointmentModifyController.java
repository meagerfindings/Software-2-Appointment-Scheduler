package View_Controller;

import Model.Appointment;
import static Model.Appointment.getAppointmentData;
import static Model.CalendarDB.modifyAppointment;
import static Model.CalendarDB.modifyReminder;
import static Model.CalendarDB.setRemindersUnChecked;
import Model.Customer;
import static Model.Time.convertDate;
import static Model.Time.datePiecesToString;
import static Model.Time.getLocalTime;
import static View_Controller.MainScreenController.getCurrentAppointment;
import static View_Controller.MainScreenController.setCurrentAppointment;
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

public class AppointmentModifyController implements Initializable {

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
    
    private static Customer modCustomer; // Which Customer is currently on the appointment.
    
    // Lambda used to generate alert messages.
    private final ModAlert throwModErrorMessage = (String title, String header, String context) -> {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
        return false;
    };
    
    /**
     * @return the apptCustomer
     */
    static Customer getModCustomer() {
        return modCustomer;
    }

    /**
     * @param inputCustomer the apptCustomer to set
     */
    static void setModCustomer(Customer inputCustomer) {
        modCustomer = inputCustomer;
    }
    
    private static Appointment modAppointment = new Appointment();; // Java object to hold the field values for the appointment we are creating. This allows for navigating away from this screen to select a customer, create a customer, and return to this screen and refill the fields with these values.
    
    /**
     * @return the modAppointment
     */
    static Appointment getModAppt() {
        return modAppointment;
    }

    /**
     * @param inputAppointment the modAppointment to set
     */
    public static void setModAppt(Appointment inputAppointment) {
        modAppointment = inputAppointment;
    }

    @FXML
    void cancelButtonClicked(ActionEvent event) throws IOException{
        // Set the temprorary holder for appointment details and customer details to null.
        setModCustomer(null);
        setModAppt(null);
        
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
        CustomerMainController.setReferalPage("AppointmentModifyController");
        
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
        setCurrentAppointment(new Appointment());
        
        // Write field values to temporary appointment holder.
        if (inputTitle != null && !inputTitle.isEmpty() ){
            modAppointment.setTitle(inputTitle);
        }
        
        if (inputLocation != null && !inputLocation.isEmpty() ){
            modAppointment.setLocation(inputLocation);
        }
        
        if (inputContact != null && !inputContact.isEmpty() ){
            modAppointment.setContact(inputContact);
        }
        
        if (inputStartDate != null){
            modAppointment.setStartDate(inputStartDate);
        }
        
        modAppointment.setStartHr(inputStartHr);
        modAppointment.setStopHr(inputStopHr);
        
        if (inputStartMin != null && !inputStartMin.isEmpty() ){
            modAppointment.setStartMin(inputStartMin);
        }
        
        if (inputStopMin != null && !inputStopMin.isEmpty() ){
            modAppointment.setStopMin(inputStopMin);
        }
        
        if (inputStartAMPM != null && !inputStartAMPM.isEmpty() ){
            modAppointment.setStartAMPM(inputStartAMPM);
        }
        
        if (inputStopAMPM != null && !inputStopAMPM.isEmpty() ){
            modAppointment.setStopAMPM(inputStopAMPM);
        }
        
        if (inputUrl != null && !inputUrl.isEmpty() ){
            modAppointment.setUrl(inputUrl);
        }
        
        if (inputDescription != null && !inputDescription.isEmpty() ){
            modAppointment.setDescription(inputDescription);
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
        int customerId = getModAppt().getCustomerKey();
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
        int primaryKey = modAppointment.getPK();
        
        // Date Field and Description Field Null/Empty Checks
        if (inputStartDate == null){
            
            boolean exit = throwModErrorMessage.modAppAlert("Appointment Date Error",
                                                             "An appointment date is necessary.",
                                                             "Cannot modify an appointment without a date selected.");
            return exit;
        }
        
        if (inputDescription == null || inputDescription.isEmpty()){
            
            boolean exit = throwModErrorMessage.modAppAlert("Appointment Description Error",
                                                             "An appointment description is necessary.",
                                                             "Cannot modify an appointment without a description.");
            return exit;
        }
        
        // Start time checks
        if (inputStartHr < 7 && inputStartAMPM.equals("AM")){
            boolean exit = throwModErrorMessage.modAppAlert("Business Hours Error",
                                                             "Appointment outside of businesss hours.",
                                                             "Appointment 'Start Time' cannot be earlier than 7 AM local time.");
            return exit;
            
        } else if (inputStartHr == 12 && inputStartAMPM.equals("AM")){
            boolean exit = throwModErrorMessage.modAppAlert("Business Hours Error",
                                                             "Business Hours Error",
                                                             "Appointment 'Start Time' cannot be earlier than 7 AM local time.");
            return exit;
            
        } else if (inputStartAMPM.equals("PM")){
            if (inputStartHr == 12) {   
                //This is 12 noon, no harm done, exit this else if.
            } else if (inputStartHr >= 6){
                boolean exit = throwModErrorMessage.modAppAlert("Business Hours Error",
                                                                  "Appointment outside of businesss hours.",
                                                                  "Appointment 'Start Time' cannot be later than 6 PM local time.");
                return exit;
            }
        } 
        
        //Stop time checks
        if (inputStopHr < 7 && inputStopAMPM.equals("AM")){
            boolean exit = throwModErrorMessage.modAppAlert("Business Hours Error",
                                                              "Appointment outside of businesss hours.",
                                                              "Appointment 'Stop Time' cannot be earlier than 7 AM local time.");
            return exit;
            
        } else if (inputStopHr == 12 && inputStopAMPM.equals("AM")){
            boolean exit = throwModErrorMessage.modAppAlert("Business Hours Error",
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
                    boolean exit = throwModErrorMessage.modAppAlert("Business Hours Error",
                                                                      "Appointment outside of businesss hours.",
                                                                      "Appointment 'Stop Time' cannot be later than 6 PM local time.");
                    return exit;
                }
            }
        } 
        
        // Format dates into format: "yyyy-MM-dd HH:mm"
        calculatedStartDate = datePiecesToString(inputStartDate, inputStartHr, inputStartMin, inputStartAMPM);
        calculatedStopDate = datePiecesToString(inputStopDate, inputStopHr, inputStopMin, inputStopAMPM);
        Timestamp convertedStartDate = convertDate(calculatedStartDate);
        Timestamp convertedStopDate = convertDate(calculatedStopDate);
        
        //AM PM check
        if (inputStartAMPM.equals("PM") && inputStopAMPM.equals("AM")){
            boolean exit = throwModErrorMessage.modAppAlert("PM - AM Error",
                                                              "PM before AM Error",
                                                              "Appointment cannot end before it starts.");
            return exit;

        } else if (convertedStartDate.after(convertedStopDate)){
            boolean exit = throwModErrorMessage.modAppAlert("End Time Start Time Error",
                                                              "Start time is later than end time.",
                                                              "Appointment cannot end before it starts.");
            return exit;
        }
        
        //Check for conflicting appointments      
        for(int i = 0; i < getAppointmentData().size();  i++){
            Appointment appointmentEntry = getAppointmentData().get(i);

            if (appointmentEntry.getConsultantName().equals(modAppointment.getConsultantName()) && appointmentEntry.getPK() != modAppointment.getPK()) { // Check if the appointment we are iterating over is assigned to the user who created the mod appointment. Also check that this is not the same appointment as the one we are modifying, if it is the same, ignore this iterated appointment.
                
                String startTime = datePiecesToString(appointmentEntry.getStartDate(), appointmentEntry.getStartHr(), appointmentEntry.getStartMin(), appointmentEntry.getStartAMPM());
                String stopTime = datePiecesToString(appointmentEntry.getStopDate(), appointmentEntry.getStopHr(), appointmentEntry.getStopMin(), appointmentEntry.getStopAMPM());

                String localStartExisting = appointmentEntry.getStartDate() + " " + appointmentEntry.getStartHr() + ":" +  appointmentEntry.getStartMin() + " " + appointmentEntry.getStartAMPM();
                String localStopExisting = appointmentEntry.getStopDate() + " " + appointmentEntry.getStopHr() + ":" +  appointmentEntry.getStopMin() + " " + appointmentEntry.getStopAMPM();
            
                Timestamp checkExistingStart = convertDate(startTime);
                Timestamp checkExistingStop = convertDate(stopTime);
                
                if (convertedStartDate.after(checkExistingStart) && convertedStartDate.before(checkExistingStop)){
                    String messageDetails = "The Start time: " + getLocalTime(convertedStartDate).toLocalDateTime() + " conflicted with the appointment: " + appointmentEntry.getTitle() + "\nwhich starts at: " + localStartExisting + "\nand ends at: " + localStopExisting;
                    System.out.println(messageDetails);

                    boolean exit = throwModErrorMessage.modAppAlert("Scheduling Conflict",
                                                                    "Scheduling conflict with another appointment.",
                                                                    messageDetails);
                    return exit;
                    
                } else if (convertedStopDate.after(checkExistingStart) && convertedStopDate.before(checkExistingStop)){
                    String messageDetails = "The Stop time: " + getLocalTime(convertedStopDate).toLocalDateTime() + " conflicted with the appointment: " + appointmentEntry.getTitle() + "\nwhich starts at: " + localStartExisting + "\nand ends at: " + localStopExisting;
                    System.out.println(messageDetails);

                    boolean exit = throwModErrorMessage.modAppAlert("Scheduling Conflict",
                                                                    "Scheduling conflict with another appointment.",
                                                                    messageDetails);
                    return exit;
                }
            }
        }
        
        //Modify Appointment
        modifyAppointment(customerId, inputTitle, inputDescription, inputLocation, inputContact, inputUrl, convertedStartDate, convertedStopDate, primaryKey);
        
        //Modify Reminder
        modifyReminder(primaryKey, convertedStartDate);
        
        System.out.println("Opening main screen.");

        FXMLLoader mainScreenLoader = new FXMLLoader(MainScreenController.class.getResource("MainScreen.fxml"));
        GridPane mainScreen = (GridPane) mainScreenLoader.load();
        Stage newStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                
        Scene sceneMainScreen = new Scene(mainScreen);
        
        newStage.setScene(sceneMainScreen);
        
        MainScreenController controller = mainScreenLoader.getController();

        newStage.show();  

        // Set the temprorary holder for appointment details and customer details to null.
        modAppointment.setPK(-1); // Setting primary key to -1 so that we know when returning to the modify screen that we need to ignor the mod appointment value from before.
        setModCustomer(null);
        
        // Reset reminders check in case this appointment is in the next 15 minutes.
        setRemindersUnChecked();
        
        return true;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Setup ChoiceBoxes
        startHr.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        startMin.getItems().addAll(minutesArray);
        startAMPM.getItems().addAll(amPmArray);
        stopHr.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
        stopMin.getItems().addAll(minutesArray);
        stopAMPM.getItems().addAll(amPmArray);
        apptDescription.getItems().addAll(appointmentTypes);
        
        setModAppt(getCurrentAppointment());
            
        //Setup the appointment customer.
        if (getModCustomer() != null){
                modAppointment.setCustomerKey(modCustomer.getPK());
                modAppointment.setName(modCustomer.getName());
            }
        
        //Populate form fields
        populateFields();
    }
    
    private void populateFields(){
        if (modAppointment != null) {
        
            // Fill in fields if this appointment is already in the creatino process.
            if (modAppointment.getName() != null) {
                apptCusName.setText(modAppointment.getName());
            }

            if (modAppointment.getTitle() != null && !modAppointment.getTitle().isEmpty()) {
                apptTitle.setText(modAppointment.getTitle());
            }

            if (modAppointment.getLocation() != null && !modAppointment.getLocation().isEmpty()) {
                apptLocation.setText(modAppointment.getLocation());
            }

            if (modAppointment.getContact() != null && !modAppointment.getContact().isEmpty()) {
                apptContact.setText(modAppointment.getContact());
            }

            if (modAppointment.getStartDate() != null) {
                apptStartDate.setValue(modAppointment.getStartDate());
            }


            if (modAppointment.getUrl() != null && !modAppointment.getUrl().isEmpty()) {
                apptUrl.setText(modAppointment.getUrl());
            }

            if (modAppointment.getDescription() != null && !modAppointment.getDescription().isEmpty()) {
                apptDescription.setValue(modAppointment.getDescription());
            }

            if (modAppointment.getStartHr() != 12){
                startHr.setValue(modAppointment.getStartHr());
            } else {
                startHr.setValue(12);
            }

            if (!modAppointment.getStartMin().equals("00")){
                startMin.setValue(modAppointment.getStartMin());
            } else {
                startMin.setValue("00");
            }

            if (!modAppointment.getStartAMPM().equals("PM")){
                startAMPM.setValue(modAppointment.getStartAMPM());
            } else {
                startAMPM.setValue("PM");
            }       

            if (modAppointment.getStopHr() != 12){
                stopHr.setValue(modAppointment.getStopHr());
            } else {
                stopHr.setValue(12);
            }

            if (!modAppointment.getStopMin().equals("00")){
                stopMin.setValue(modAppointment.getStopMin());
            } else {
                stopMin.setValue("00");
            }

            if (!modAppointment.getStopAMPM().equals("PM")){
                stopAMPM.setValue(modAppointment.getStopAMPM());
            } else {
                stopAMPM.setValue("PM");
            }
        } else { // Defaults if the modAppointment was NULL. However, this is no longer reachable do to alert handeling in the Main Screens.
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
interface ModAlert {
    boolean modAppAlert(String title, String header, String context);
}