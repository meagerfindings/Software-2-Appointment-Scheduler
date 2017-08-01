package Reports;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author matgreten
 */
public class ClientAppointmentReport {
    
    private static ArrayList<ReportCustomer> clientReport = new ArrayList<>();

    /**
     * @return the clientReport
     */
    public static ArrayList<ReportCustomer> getClientReport() {
        return clientReport;
    }

    /**
     * @param aClientReport the clientReport to set
     */
    public static void setClientReport(ArrayList<ReportCustomer> aClientReport) {
        clientReport = aClientReport;
    }
    
    public static void resetClientReport(){
        clientReport = new ArrayList<>();
    }
    
    public static String getClientReportData(){
        
        String result = "\n";
        
        for(int i = 0; i < getClientReport().size();  i++){
            String customerEntry = getClientReport().get(i).getCustomerApopintments();
            result += customerEntry;
        }
        
        return result;
    }
    
    public static void addAppointmentEntry(String inputCustomer, LocalDate inputDate, String inputStart, String inputEnd, String inputTitle, String inputDescription){
        int customerIndex = -1;

        if (getClientReport().isEmpty() == true){
            
            ReportCustomer tempCustomer = new ReportCustomer(inputCustomer);
            
            tempCustomer.addAppointmentEntry(inputDate, inputStart, inputEnd, inputTitle, inputDescription);
            
            getClientReport().add(tempCustomer);
            
            System.out.println("The customer list was empty. Added " + inputCustomer + " to the list.");
            System.out.println("Added the appointment: " + inputTitle + " to the newly created customer entry" );
            
        } else {
            
            for(int i = 0; i < getClientReport().size();  i++){
                if (getClientReport().get(i).getCustomerName().equals(inputCustomer)){
                    getClientReport().get(i).addAppointmentEntry(inputDate, inputStart, inputEnd, inputTitle, inputDescription);
                    customerIndex = i;
                    break;
                }
            }
            
            if (customerIndex == -1){
                ReportCustomer tempCustomer = new ReportCustomer(inputCustomer);
                tempCustomer.addAppointmentEntry(inputDate, inputStart, inputEnd, inputTitle, inputDescription);
                getClientReport().add(tempCustomer);
            }
        }
    }
}
