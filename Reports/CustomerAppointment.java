package Reports;

import java.time.LocalDate;

/**
 *
 * @author matgreten
 */
public class CustomerAppointment {
    
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
    
    public CustomerAppointment(){
        this.title = "";
        this.description = "";
        this.startTime = "";
        this.endTime = "";
    }
    
    public CustomerAppointment(LocalDate inputDate, String inputStart, String inputEnd, String inputTitle, String inputDescription){
        this.title = inputTitle;
        this.description = inputDescription;
        this.date = inputDate;
        this.startTime = inputStart;
        this.endTime = inputEnd;
    }
    
    public String getAppointmentData(){
        
        String result = getDate() + " " + getStartTime() + " - " + getEndTime() + 
                " Title: " + getTitle() +
                " Description: " + getDescription() + "\n";
        
        return result;
    }
    
}
