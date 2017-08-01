package Reports;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author matgreten
 */
public class IOWriter {
    
    /*
    * Oracle. (2015). Oracle Java Tutorials: System Properties. Retrieved from
    * https://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html
    *
    * The Java System Properties tutorial from Oracle was invaluable in writing
    * the methods for the IOWriter class. Use of File.separator and user.home properties
    * were inspired by this tutorial.
    */
    
    public static String discoverFilePath(){  
        String filePath = "";
                
        String userDirectory = System.getProperty("user.home");

        filePath = userDirectory + File.separator + "Downloads" + File.separator;
        
        return filePath;
    }
    
    public static void writeToLog(String inputString) {
        File file = new File(discoverFilePath() + "scheduler_logs.txt");
        
        System.out.println("Writing to log file at: " + file);
        
        if (file.exists()){ // If the file exists, read the file, add the new line and save the file.
            
            String fileContents = "";
            
            try (BufferedReader infoFromFile = new BufferedReader(new FileReader(file))){
                String fileContentLine = infoFromFile.readLine();
                                
                while (fileContentLine != null){
                    
                    if (fileContents.isEmpty()){
                        fileContents = fileContentLine;
                    } else {
                        fileContents = fileContents + "\n" + fileContentLine;
                    }
                    
                    fileContentLine = infoFromFile.readLine(); // move to the next line in the file
                }
                
                infoFromFile.close();
                        
            } catch (IOException ex) {
                Logger.getLogger(IOWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            try (PrintWriter outPut = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
                
                String outputString = fileContents + "\n" + inputString;
                outPut.println(outputString);
                outPut.close();
                
            } catch (IOException ex) {
                Logger.getLogger(IOWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else { // If the file does not exist, create a new log file.
            try (PrintWriter outPut = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
                
                outPut.println(inputString);
                outPut.close();
                
            } catch (IOException ex) {
                Logger.getLogger(IOWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
   }

   public static String exportReportToFile(String inputString, String fileName) {
        File file = new File(discoverFilePath() + fileName + ".txt");

        try (PrintWriter outPut = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {

            outPut.println(inputString);
            outPut.close();
            
            String result = "Exported report to file located at: " + file + ".";

            System.out.println(result);
            
            return result;

        } catch (IOException ex) {
            Logger.getLogger(IOWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Export attempt failed.";
    }
}
