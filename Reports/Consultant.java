package Reports;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author matgreten
 */
public class Consultant {
    
    private ArrayList<ConsultantAppointment> consultantAppointmentList = new ArrayList<>();
    private String consultantName;

    /**
     * @return the consultantAppointmentList
     */
    public ArrayList<ConsultantAppointment> getConsultantAppointmentList() {
        return consultantAppointmentList;
    }

    /**
     * @param consultantAppointmentList the consultantAppointmentList to set
     */
    public void setConsultantAppointmentList(ArrayList<ConsultantAppointment> consultantAppointmentList) {
        this.consultantAppointmentList = consultantAppointmentList;
    }
    
    public void resetConsultantAppointmentList(){
        setConsultantAppointmentList(new ArrayList<>());
    }
    
    /**
     * @return the consultantName
     */
    public String getConsultantName() {
        return consultantName;
    }

    /**
     * @param consultantName the consultantName to set
     */
    public void setConsultantName(String consultantName) {
        this.consultantName = consultantName;
    }

    public boolean checkForAppointments(){
        return this.consultantAppointmentList.isEmpty() != true;
        
    }
    
    public String getConsultantAppointments(){
        
        String dashes = "--";
        
        for(int i = 0; i < getConsultantName().length();  i++){
            dashes += "-";
        }
        String result = "\n" + getConsultantName() + "\n" + dashes + "\n";
        
        
        for(int i = 0; i < getConsultantAppointmentList().size();  i++){
            String appointmentEntry = getConsultantAppointmentList().get(i).getAppointmentData();
            result += appointmentEntry;
        }
        
        return result;
    }
    
    public Consultant(){
        this.consultantName = "";
    }
    
    public Consultant(String inputConsultantName){
        this.consultantName = inputConsultantName;
    }
    
    public void addAppointmentEntry(LocalDate inputDate, String inputStart, String inputEnd, String inputTitle, String inputDescription, String inputCustomer){
        consultantAppointmentList.add(new ConsultantAppointment(inputDate, inputStart, inputEnd, inputTitle, inputDescription, inputCustomer));
    }
}
