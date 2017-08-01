package Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author matgreten
 */
public class Appointment {
    // Initialize​ variables for the customer
    private final StringProperty title;
    private final StringProperty description;
    private final StringProperty location;
    private final StringProperty contact;
    private final StringProperty url;
    private final IntegerProperty primaryKey;
    private final IntegerProperty customerKey;
    private final IntegerProperty startHr;
    private final StringProperty startMin;
    private final StringProperty startAMPM;
    private final IntegerProperty stopHr;
    private final StringProperty stopMin;
    private final StringProperty stopAMPM;
    private LocalDate startDate;
    private LocalDate stopDate;
    private final StringProperty renderedDate;
    private final StringProperty phone;
    private final StringProperty customerName;
    private final StringProperty startTime;
    private final StringProperty endTime;
    private final StringProperty consultant;
    
    private static final ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    
    //Return method for populating customer table in customer selection screen. 
    public static ObservableList<Appointment> getAppointmentData() {
        return appointmentList;
    }
        
    public StringProperty appointmentTitleProperty(){
        return title;
    }
    
    public void setTitle(String title){
        appointmentTitleProperty().set(title);
        
    }
    
    public final String getTitle(){
        
        return appointmentTitleProperty().get();
    }
    
    public StringProperty appointmentDescriptionProperty(){
        return description;
    }
    
    public final void setDescription(String description){
        appointmentDescriptionProperty().set(description);
        
    }
    
    public final String getDescription(){
        
        return appointmentDescriptionProperty().get();
    }
    
    public StringProperty appointmentLocationProperty(){
        return location;
    }
    
    public final void setLocation(String location){
        appointmentLocationProperty().set(location);
        
    }
    
    public final String getLocation(){
        
        return appointmentLocationProperty().get();
    }
    
    public StringProperty appointmentContactProperty(){
        return contact;
    }
    
    public final void setContact(String contact){
        appointmentContactProperty().set(contact);
        
    }
    
    public final String getContact(){
        
        return appointmentContactProperty().get();
    }
    
    public StringProperty appointmentUrlProperty(){
        return url;
    }
    
    public final void setUrl(String url){
        appointmentUrlProperty().set(url);
        
    }
    
    public final String getUrl(){
        
        return appointmentUrlProperty().get();
    }
    
    public IntegerProperty customerPKProperty(){
        return primaryKey;
    }
    
    public final void setPK(int primaryKey){
        customerPKProperty().set(primaryKey);
    }
    
    public final int getPK(){
        
        return customerPKProperty().get();
    }
    
    public IntegerProperty customerAddKeyProperty(){
        return customerKey;
    }
    
    public final void setCustomerKey(int customerKey){
        customerAddKeyProperty().set(customerKey);
    }
    
    public final int getCustomerKey(){
        return customerAddKeyProperty().get();
    }
    
    public StringProperty startTimeProperty(){
        return startTime;
    }

    public final void setStartTime(){
        String time = getStartHr() + ":" + getStartMin() + " " + getStartAMPM();
        startTimeProperty().set(time);
    }
    
    public final String getStartTime(){
        return startTimeProperty().get();
    }
    
    public StringProperty endTimeProperty(){
        return endTime;
    }

    public final void setEndTime(){
        String time = getStopHr() + ":" + getStopMin() + " " + getStopAMPM();
        endTimeProperty().set(time);
    }
    
    public final String getEndTime(){
        return endTimeProperty().get();
    }
    
    public IntegerProperty startHrProperty(){
        return startHr;
    }
    
    public final void setStartHr(int startHr){
        startHrProperty().set(startHr);
    }
    
    public final int getStartHr(){
        
        return startHrProperty().get();
    }
    
    public StringProperty startMinProperty(){
        return startMin;
    }
    
    public final void setStartMin(String startMin){
        startMinProperty().set(startMin);
    }
    
    public final String getStartMin(){
        
        return startMinProperty().get();
    }
    
    public StringProperty startAMPMProperty(){
        return startAMPM;
    }
    
    public final void setStartAMPM(String startAMPM){
        startAMPMProperty().set(startAMPM);
    }
    
    public final String getStartAMPM(){
        
        return startAMPMProperty().get();
    }
    
    public IntegerProperty stopHrProperty(){
        return stopHr;
    }
    
    public final void setStopHr(int stopHr){
        stopHrProperty().set(stopHr);
    }
    
    public final int getStopHr(){
        
        return stopHrProperty().get();
    }
    
    public StringProperty stopMinProperty(){
        return stopMin;
    }
    
    public final void setStopMin(String stopMin){
        stopMinProperty().set(stopMin);
    }
    
    public final String getStopMin(){
        
        return stopMinProperty().get();
    }
    
    public StringProperty stopAMPMProperty(){
        return stopAMPM;
    }
    
    public final void setStopAMPM(String stopAMPM){
        stopAMPMProperty().set(stopAMPM);
    }
    
    public final String getStopAMPM(){
        
        return stopAMPMProperty().get();
    }
    
    public void setStartDate(LocalDate startDate){
        this.startDate = startDate;
    }
    
    public final LocalDate getStartDate(){
        return this.startDate;
    }
    
    public final StringProperty renderedDateProperty(){
        return renderedDate;
    }
    
    public final String getrenderedDate(){
        return renderedDateProperty().get();
    }
    
    public void setStopDate(LocalDate stopDate){
        this.stopDate = stopDate;
    }
    
    public final LocalDate getStopDate(){
        return this.stopDate;
    }
    
    public StringProperty customerPhoneProperty(){
        return phone;
    }
    
    public final void setPhone(String phone){
        customerPhoneProperty().set(phone);
        
    }
    
    public final String getPhone(){
        
        return customerPhoneProperty().get();
    }
    
    public StringProperty customerNameProperty(){
        return customerName;
    }
    
    public final void setName(String customerName){
        customerNameProperty().set(customerName);
        
    }
    
    public final String getName(){
        
        return customerNameProperty().get();
    }
    
    public StringProperty consultantNameProperty(){
        return consultant;
    }
    
    public final void setConsultantName(String consultantName){
        consultantNameProperty().set(consultantName);
        
    }
    
    public final String getConsultantName(){
        
        return consultantNameProperty().get();
    }
    
    public Appointment(int primaryKey, String title, String description, String location, String contact, String url, int customerKey, int startHr, String startMin, String startAMPM, int stopHr, String stopMin,  String stopAMPM, LocalDate startDate, LocalDate stopDate, String phone, String customerName, String consultant){
        this.primaryKey = new SimpleIntegerProperty(primaryKey);
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.location = new SimpleStringProperty(location);
        this.contact = new SimpleStringProperty(contact);
        this.url = new SimpleStringProperty(url);
        this.customerKey = new SimpleIntegerProperty(customerKey);
        this.startHr = new SimpleIntegerProperty(startHr);
        this.startMin = new SimpleStringProperty(startMin);
        this.startAMPM = new SimpleStringProperty(startAMPM);
        this.stopHr = new SimpleIntegerProperty(stopHr);
        this.stopMin = new SimpleStringProperty(stopMin);
        this.stopAMPM = new SimpleStringProperty(stopAMPM);
        this.startDate = startDate;
        this.stopDate = stopDate;
        this.phone = new SimpleStringProperty(phone);
        this.customerName = new SimpleStringProperty(customerName);
        this.startTime = new SimpleStringProperty(startHr + ":" + startMin + " " + startAMPM);
        this.endTime = new SimpleStringProperty(stopHr + ":" + stopMin + " " + stopAMPM);
        
        DateTimeFormatter formatPattern = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String convertedStartDate = startDate.format(formatPattern);
        this.renderedDate = new SimpleStringProperty(convertedStartDate);
        this.consultant = new SimpleStringProperty(consultant);
    }
    
    public Appointment(){
        this.primaryKey = new SimpleIntegerProperty(-1);
        this.title = new SimpleStringProperty("");
        this.description = new SimpleStringProperty("");
        this.location = new SimpleStringProperty("");
        this.contact = new SimpleStringProperty("");
        this.url = new SimpleStringProperty("");
        this.customerKey = new SimpleIntegerProperty(-1);
        this.startHr = new SimpleIntegerProperty(-1);
        this.startMin = new SimpleStringProperty("");
        this.startAMPM = new SimpleStringProperty("");
        this.stopHr = new SimpleIntegerProperty(-1);
        this.stopMin = new SimpleStringProperty("");
        this.stopAMPM = new SimpleStringProperty("");
        this.startDate = null;
        this.stopDate = null;
        this.phone = new SimpleStringProperty("");
        this.customerName = new SimpleStringProperty("");
        this.startTime = new SimpleStringProperty("");
        this.endTime = new SimpleStringProperty("");
        this.renderedDate = new SimpleStringProperty("");
        this.consultant = new SimpleStringProperty("");
    }
    
}