
package Model;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author matgreten
 */
public class Time {
    
    /*
    * Shoikana. (2017, June 9). Timestamp Conversion to Instant in Java Adds 
    *   Unnecessary Time Offset. Retrieved from https://stackoverflow.com/a/44462605
    
    * After three days of struggling, implemented code as suggested by Shokina 
    * and modified as necessary, with additional checks seen in getTimeStamp() method.
    *
    * Oracle. (2016). Oracle Java Documents: Class Timestamp. Retrieved from 
    *   https://docs.oracle.com/javase/8/docs/api/java/sql/Timestamp.html
    *
    * Implemented passing in SQL formatted timestamp as 
    * inspired by Oracle's documention for the class Timestamp.
    */
    
    public static  Timestamp getTimeStamp(){ // Creates timestamp in SQL format. Inspried by

        java.sql.Timestamp initTimeStamp = new java.sql.Timestamp(new java.util.Date().getTime());
        
        String txtStartTime = initTimeStamp.toString();
        
        DateTimeFormatter df;
        
        /*
        * Implemented else check below to accommodate varying millisecond Timestamp lengths that would break the conversion.
        *
        * For Example: 2017-07-28 23:50:42.000000005 is converted by yyyy-MM-dd HH:mm:ss.n
        * whereas 2017-07-28 23:53:06.000000219 needs to be converted by yyyy-MM-dd HH:mm:ss.nnn
        *
        * To test the checks below I ran the following for > 10 minutes in my initial class, without errors being thrown:
        *
        *    for(int i = 0; i < 100000;  i++){
        *        System.out.println(getTimeStamp());
        *        System.out.println(getTimeStamp());
        *        Thread.sleep((long)(Math.random() * 1000));
        *        System.out.println(getTimeStamp());
        *        System.out.println(getTimeStamp());
        *    }
        */
        
        // Get the LocalDateTime Objects from String values
        if (txtStartTime.length() == "yyyy-MM-dd HH:mm:ss.nnn".length()){
             df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.nnn");
        } else if (txtStartTime.length() == "yyyy-MM-dd HH:mm:ss.nnnn".length()){
             df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.nnnn");
        } else if (txtStartTime.length() == "yyyy-MM-dd HH:mm:ss.nn".length()) {
             df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.nn");
        } else {
             df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.n");
        }

        LocalDateTime ldtStart = LocalDateTime.parse(txtStartTime, df);

        //Convert to a ZonedDate Time in UTC
        ZoneId zid = ZoneId.systemDefault();
        ZonedDateTime zdtStart = ldtStart.atZone(zid);
        ZonedDateTime utcStart = zdtStart.withZoneSameInstant(ZoneId.of("UTC"));
        ldtStart = utcStart.toLocalDateTime();
        
        //Create Timestamp values from Instants to update database
        Timestamp result = Timestamp.valueOf(ldtStart); //this value can be inserted into database

        return result;
    }
    
    public static Timestamp convertDate(String inputString){ // Creates timestamp in SQL format.

        String txtStartTime = inputString;
                
        DateTimeFormatter df;
        
        //Getting the LocalDateTime Objects from String values
        df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        LocalDateTime ldtStart = LocalDateTime.parse(txtStartTime, df);

        //Convert to a ZonedDate Time in UTC
        ZoneId zid = ZoneId.systemDefault();

        ZonedDateTime zdtStart = ldtStart.atZone(zid);
        ZonedDateTime utcStart = zdtStart.withZoneSameInstant(ZoneId.of("UTC"));
        ldtStart = utcStart.toLocalDateTime();
        
        //Create Timestamp values from Instants to update database
        Timestamp result = Timestamp.valueOf(ldtStart); //this value can be inserted into database

        return result;
    }
    
    public static String datePiecesToString(LocalDate inputDate, int inputHr, String inputMin, String inputAMPM){ // Creates timestamp in SQL format.
        String calculatedDate;
                
        // Format dates into format: "yyyy-MM-dd HH:mm"
        if (inputAMPM.equals("AM")){
            
            String timeAdj;
            // If the hour is lower than 10, add 0 to the beginning of it
            if (inputHr < 10){
                timeAdj = "0" + inputHr;
            } else if (inputHr == 12){
                timeAdj = "00";
            } else {
                timeAdj = Integer.toString(inputHr);
            }
            
            calculatedDate = inputDate + " " + timeAdj + ":" + inputMin;
        } else {
            
            int timeAdj;
            
            if (inputHr == 12){
                timeAdj = inputHr;
            } else {
                timeAdj = inputHr + 12;
            }
            
            calculatedDate = inputDate + " " + timeAdj + ":" + inputMin;
        }

        return calculatedDate;
    }
        
    public static Timestamp getLocalTime(Timestamp inputStamp){ // Converts SQL datetime into local date timestamp
                
        ZonedDateTime utcDateTime = ZonedDateTime.ofInstant(inputStamp.toInstant(), ZoneId.of("UTC"));
        LocalDateTime localDateTime = utcDateTime.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();
        
        String inputAsString = inputStamp.toString();
                
        // Convert to a ZonedDate Time in UTC
        ZoneId zid = ZoneId.systemDefault();

        ZonedDateTime zdtStart = localDateTime.atZone(ZoneId.of("UTC"));
        ZonedDateTime utcStart = zdtStart.withZoneSameInstant(zid);
        localDateTime = utcStart.toLocalDateTime();

        // Create Timestamp values from Instants to update database
        Timestamp startsqlts = Timestamp.valueOf(localDateTime); //this value can be inserted into database

        return startsqlts;
    }
}
