
package Reports;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author matgreten
 */
public class ReportCustomer {
    
    private ArrayList<CustomerAppointment> cusAppointmentList = new ArrayList<>();

    /**
     * @return the cusAppointmentList
     */
    public ArrayList<CustomerAppointment> getCusAppointmentList() {
        return cusAppointmentList;
    }

    /**
     * @param aCusAppointmentList the cusAppointmentList to set
     */
    public void setCusAppointmentList(ArrayList<CustomerAppointment> aCusAppointmentList) {
        cusAppointmentList = aCusAppointmentList;
    }
    
    public void resetCusAppointmentList(){
        cusAppointmentList = new ArrayList<>();
    }
    
    private String customerName;

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
    
    public boolean checkForAppointments(){
        return this.cusAppointmentList.isEmpty() != true;
        
    }
    
    public String getCustomerApopintments(){
        
        String dashes = "--";
        
        for(int i = 0; i < getCustomerName().length();  i++){
            dashes += "-";
        }
        
        String result = "\n" + getCustomerName() + "\n" + dashes + "\n";
        
        for(int i = 0; i < getCusAppointmentList().size();  i++){
            String appointmentEntry = getCusAppointmentList().get(i).getAppointmentData();
            result += appointmentEntry;
        }
        return result;
    }
    
    public ReportCustomer(){
        this.customerName = "";
    }
    
    public ReportCustomer(String inputCustomerName){
        this.customerName = inputCustomerName;
    }
    
    public void addAppointmentEntry(LocalDate inputDate, String inputStart, String inputEnd, String inputTitle, String inputDescription){
        cusAppointmentList.add(new CustomerAppointment(inputDate, inputStart, inputEnd, inputTitle, inputDescription));
    }
    
}
