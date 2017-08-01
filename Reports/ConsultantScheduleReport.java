package Reports;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author matgreten
 */
public class ConsultantScheduleReport {
    
    private static ArrayList<Consultant> consultantReport = new ArrayList<>();

    /**
     * @return the consultantReport
     */
    public static ArrayList<Consultant> getConsultantReport() {
        return consultantReport;
    }

    /**
     * @param aConsultantReport the consultantReport to set
     */
    public static void setConsultantReport(ArrayList<Consultant> aConsultantReport) {
        consultantReport = aConsultantReport;
    }
    
    public static void resetConsultantReport(){
        consultantReport = new ArrayList<>();
    }
    
    public static String getConsultantReportData(){
        String result = "\n";
        
        for(int i = 0; i < getConsultantReport().size();  i++){
            String consultantEntry = getConsultantReport().get(i).getConsultantAppointments();
            result += consultantEntry;
        }
        return result;
    }
    
    public static void addAppointmentEntry(String inputCustomer, LocalDate inputDate, String inputStart, String inputEnd, String inputTitle, String inputDescription, String inputConsultant){
        int consultantIndex = -1;

        if (getConsultantReport().isEmpty() == true){
            
            Consultant tempConsultant = new Consultant(inputConsultant);
            
            tempConsultant.addAppointmentEntry(inputDate, inputStart, inputEnd, inputTitle, inputDescription, inputCustomer);
            
            getConsultantReport().add(tempConsultant);
            
            System.out.println("The consultant list was empty. Added " + inputConsultant + " to the list.");
            System.out.println("Added the appointment: " + inputTitle + " to the newly created consultant entry" );
        } else {
            for(int i = 0; i < getConsultantReport().size();  i++){
                if (getConsultantReport().get(i).getConsultantName().equals(inputConsultant)){
                    getConsultantReport().get(i).addAppointmentEntry(inputDate, inputStart, inputEnd, inputTitle, inputDescription, inputCustomer);
                    consultantIndex = i;
                    break;
                }
            }
            if (consultantIndex == -1){
                Consultant tempConsultant = new Consultant(inputConsultant);
                tempConsultant.addAppointmentEntry(inputDate, inputStart, inputEnd, inputTitle, inputDescription, inputCustomer);
                getConsultantReport().add(tempConsultant);
            }
        }
    }
}
