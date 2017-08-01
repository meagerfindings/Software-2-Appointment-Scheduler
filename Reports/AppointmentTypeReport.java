package Reports;

import java.util.ArrayList;

/**
 *
 * @author matgreten
 */
public class AppointmentTypeReport {
    
    private static ArrayList<Month> typeReport = new ArrayList<>();

    /**
     * @return the typeReport
     */
    public static ArrayList<Month> getTypeReport() {
        return typeReport;
    }

    /**
     * @param aTypeReport the typeReport to set
     */
    public static void setTypeReport(ArrayList<Month> aTypeReport) {
        typeReport = aTypeReport;
    }
    
    public static void resetTypeReport(){
        typeReport = new ArrayList<>();
    }
    
    public static void addTypeEntry(String inputMonth, String inputType){
        int monthIndex = -1;
        
        if (getTypeReport().isEmpty() == true){
            Month tempMonth = new Month(inputMonth);
            switch(inputType) {
                case "Customer Calibration" :

                    tempMonth.setCusCalib(1);
                    break;

                case "Troubleshooting" :

                    tempMonth.setTroubleshooting(1);
                    break;

                case "Initial Consultation" :

                    tempMonth.setIntialConsultation(1);
                    break;

                case "Demo" :

                    tempMonth.setDemo(1);
                    break;

                case "Routine Check-In" :

                    tempMonth.setCheckIn(1);
                    break;

                case "Emergency Customer Assistance" :

                    tempMonth.setEmergency(1);
                    break;
            }
            typeReport.add(tempMonth);
        } else {
            for(int i = 0; i < getTypeReport().size();  i++){
                if (typeReport.get(i).getMonthName().equals(inputMonth)){
                    if (inputType.equalsIgnoreCase("Customer Calibration")) {
                            typeReport.get(i).addCusCalib();
                            monthIndex = i;

                    } else if (inputType.equalsIgnoreCase("Troubleshooting")) {

                            typeReport.get(i).addTroubleshooting();
                            monthIndex = i;

                    } else if (inputType.equalsIgnoreCase("Initial Consultation")) {

                            typeReport.get(i).addIntialConsultation();
                            monthIndex = i;

                    } else if (inputType.equalsIgnoreCase("Demo")){

                            typeReport.get(i).addDemo();
                            monthIndex = i;

                    } else if (inputType.equalsIgnoreCase("Routine Check-In")){

                            typeReport.get(i).addCheckIn();
                            monthIndex = i;

                    } else if (inputType.equalsIgnoreCase("Emergency Customer Assistance")){

                            typeReport.get(i).addEmergency();
                            monthIndex = i;
                    }
                }
            }
                
            if (monthIndex == -1){
                Month tempMonth = new Month(inputMonth);

                if (inputType.equalsIgnoreCase("Customer Calibration")){

                        tempMonth.setCusCalib(1);

                } else if (inputType.equalsIgnoreCase("Troubleshooting")){

                        tempMonth.setTroubleshooting(1);

                } else if (inputType.equalsIgnoreCase("Initial Consultation")){

                        tempMonth.setIntialConsultation(1);

                } else if (inputType.equalsIgnoreCase("Demo")){

                        tempMonth.setDemo(1);

                } else if (inputType.equalsIgnoreCase("Routine Check-In")){

                        tempMonth.setCheckIn(1);

                } else if (inputType.equalsIgnoreCase("Emergency Customer Assistance")){

                        tempMonth.setEmergency(1);
                }
                typeReport.add(tempMonth);
            }
        }
    }
}
