package Reports;

import java.time.LocalDate;

/**
 *
 * @author matgreten
 */
public class ConsultantAppointment {
    
    private String customerName;
    private LocalDate date;
    private String title;
    private String description;
    private String startTime;
    private String endTime;

    /**
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    
    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public ConsultantAppointment(){
        this.title = "";
        this.description = "";
        this.startTime = "";
        this.endTime = "";
        this.customerName = "";
    }
    
    public ConsultantAppointment(LocalDate inputDate, String inputStart, String inputEnd, String inputTitle, String inputDescription, String inputCustomer){
        this.title = inputTitle;
        this.description = inputDescription;
        this.date = inputDate;
        this.startTime = inputStart;
        this.endTime = inputEnd;
        this.customerName = inputCustomer;
    }
    
    public String getAppointmentData(){
        
        String result = getDate() + " " + getStartTime() + " - " + getEndTime() + 
                " Customer: " + customerName +
                " Title: " + getTitle() +
                " Description: " + getDescription() + "\n";
        
        return result;
    }
    
}
