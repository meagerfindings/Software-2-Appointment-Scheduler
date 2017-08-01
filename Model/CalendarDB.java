package Model;

import static Model.Appointment.getAppointmentData;
import static Model.Time.getLocalTime;
import static Model.Time.getTimeStamp;
import static Reports.AppointmentTypeReport.addTypeEntry;
import static Reports.AppointmentTypeReport.getTypeReport;
import static Reports.ClientAppointmentReport.addAppointmentEntry;
import static Reports.ClientAppointmentReport.getClientReportData;
import Reports.ConsultantScheduleReport;
import static Reports.ConsultantScheduleReport.getConsultantReportData;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import javafx.scene.control.Alert;

/**
 *
 * @author matgreten
 */
public class CalendarDB {
    
    private static String loggedInUser = "Darh Maul";
    
    /**
     * @return the loggedInUser
     */
    public static String getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * @param aLoggedInUser the loggedInUser to set
     */
    public static void setLoggedInUser(String aLoggedInUser) {
        loggedInUser = aLoggedInUser;
    }
            
    private Connection databaseConnection = null;
    
    private static boolean remindersChecked = false;
    
     /**
     * @return the remindersChecked
     */
    public static boolean isRemindersChecked() {
        return remindersChecked;
    }

    public static void setRemindersChecked() {
        remindersChecked = true;
    }
    

    public static void setRemindersUnChecked() {
        remindersChecked = false;
    }
    
    
    
    public Connection connectDB(){
        
        if (databaseConnection == null){ // If the connection is not active setup the connection.
            try {
                Class.forName("com.mysql.jdbc.Driver");
                databaseConnection = DriverManager.getConnection("jdbc:mysql://<IP ADDRESSS HERE>/U03zHj","U03zHj","<PASSWORD HERE>");

            } catch (SQLException e) {
                System.out.println("SQLException: "+e.getMessage());
                System.out.println("SQLState: "+e.getSQLState());
                System.out.println("VendorError: "+e.getErrorCode());
            }  catch (ClassNotFoundException e){
                e.printStackTrace();
            } 
            
        } // If the connection is already established return the connection.
        
        return databaseConnection; // Returns database connection so we can call it from other classes.
        
    }

    public static int getPrimaryKeyNoFK(String targetColumn, String tableName, String conditionColumn, String condition)  throws IOException, SQLException {
        
        CalendarDB dbConnection = new CalendarDB();
        
        String queryString = "SELECT " + targetColumn + " FROM " + tableName + " WHERE " + conditionColumn + " = ?";
        
        PreparedStatement tableKey = dbConnection.connectDB().prepareStatement(queryString);

        tableKey.setString(1, condition);
        
        System.out.println(tableKey);
        
        ResultSet rs = tableKey.executeQuery();
        
        if (rs.next() == true) {
            
            int result = rs.getInt(1);
            System.out.println(condition + " exists in the " + tableName + "table, returning the key: " + result + ".");

            
            return result;
            
            
        } else { // If the entry is not in the table, create a new entry and return the primary key.
            
            Timestamp timeStamp = getTimeStamp();
            
            System.out.println(condition + " did not exist, creating entry in the " + tableName + " table.");
            
            int keyValue = nextKey(targetColumn, tableName);
            
            String insertString = "INSERT INTO " + tableName + "(`" + targetColumn + "`, `" + tableName + "`, `createDate`, `createdBy`, `lastUpdateBy`) VALUES (?, ?, ?, '" + getLoggedInUser() + "', '" + getLoggedInUser() + "')";

            PreparedStatement insertData = dbConnection.connectDB().prepareStatement(insertString);

            insertData.setInt(1, keyValue);
            insertData.setString(2, condition);
            insertData.setTimestamp(3, timeStamp);

            System.out.println(insertData);

            insertData.execute();
            
            return keyValue;
        }
    }
    
    public static int getPrimaryKeyWithFK(String targetColumn, String tableName, String foreignKeyName, int foreignKey, String conditionColumn, String condition)  throws IOException, SQLException {
        
        CalendarDB dbConnection = new CalendarDB();
        
        String queryString = "SELECT " + targetColumn + " FROM " + tableName + " WHERE " + conditionColumn + " = ?";
        
        PreparedStatement tableKey = dbConnection.connectDB().prepareStatement(queryString);

        tableKey.setString(1, condition);
        
        System.out.println(tableKey);
        
        ResultSet rs = tableKey.executeQuery();
        
        
        if (rs.next() == true) {
            
            int result = rs.getInt(1);
            System.out.println(condition + " exists in the " + tableName + "table, returning the key: " + result + ".");
            
            return result;
            
        } else { // If the entry is not in the table, create a new entry and return the primary key.
            
            Timestamp timeStamp = getTimeStamp();
            
            System.out.println(condition + " did not exist, creating entry in the " + tableName + " table.");
            
            int keyValue = nextKey(targetColumn, tableName);
            
            String insertString = "INSERT INTO " + tableName + "(`" + targetColumn + "`, `" + tableName + "`, `" + foreignKeyName + "`, `createDate`, `createdBy`, `lastUpdateBy`) VALUES (?, ?, " + foreignKey + ", ?, '" + getLoggedInUser() + "', '" + getLoggedInUser() + "')";

            PreparedStatement insertData = dbConnection.connectDB().prepareStatement(insertString);

            insertData.setInt(1, keyValue);
            insertData.setString(2, condition);
            insertData.setTimestamp(3, timeStamp);

            System.out.println(insertData);

            insertData.execute();
        
            return keyValue;
        }
    }
    
    public static int getAddressPrimaryKey(String targetColumn, String tableName, String foreignKeyName, int foreignKey, String conditionColumn, String add1, String add2, String zip, String phone)  throws IOException, SQLException {
        
        CalendarDB dbConnection = new CalendarDB();
        
        String queryString = "SELECT " + targetColumn + " FROM " + tableName + " WHERE " + conditionColumn + " = ? AND address2 = ? AND phone = ? AND " + foreignKeyName + " = ?";
        
        PreparedStatement tableKey = dbConnection.connectDB().prepareStatement(queryString);

        tableKey.setString(1, add1);
        tableKey.setString(2, add2);
        tableKey.setString(3, phone);
        tableKey.setInt(4, foreignKey);
        
        System.out.println(tableKey);
        
        ResultSet rs = tableKey.executeQuery();
        
        if (rs.next() == true) {
            
            int result = rs.getInt(1);
            System.out.println(add1 + " exists in the " + tableName + "table, returning the key: " + result + ".");

            return result;
            
        } else { // If the entry is not in the table, create a new entry and return the primary key.
            
            Timestamp timeStamp = getTimeStamp();
            
            System.out.println(add1 + " did not exist, creating entry in the " + tableName + " table.");
            
            int keyValue = nextKey(targetColumn, tableName);
            
            String insertString = "INSERT INTO " + tableName + "(`" + targetColumn + "`, `" + tableName + "`, `address2`, `postalCode`, `phone`, `" + foreignKeyName + "`, `createDate`, `createdBy`, `lastUpdateBy`) VALUES (?, ?, ?, ?, ?, " + foreignKey + ", ?, '" + getLoggedInUser() + "', '" + getLoggedInUser() + "')";

            PreparedStatement insertData = dbConnection.connectDB().prepareStatement(insertString);

            insertData.setInt(1, keyValue);
            insertData.setString(2, add1);
            insertData.setString(3, add2);
            insertData.setString(4, zip);
            insertData.setString(5, phone);
            insertData.setTimestamp(6, timeStamp);

            System.out.println(insertData);

            insertData.execute();
        
            return keyValue;
        }
    }
    
    public static int getCustomerPrimaryKey(String customerId, String customer, String addressId, int addressKey, String customerName, String inputName)  throws IOException, SQLException {
        
        CalendarDB dbConnection = new CalendarDB();
        Timestamp timeStamp = getTimeStamp();

        int keyValue = nextKey(customerId, customer);
        String insertString = "INSERT INTO " + customer + "(`" + customerId + "`, `" + customerName + "`, `" + addressId + "`, `active`, `createDate`, `createdBy`, `lastUpdateBy`) VALUES (?, ?, " + addressKey + ", 1, ?, '" + getLoggedInUser() + "', '" + getLoggedInUser() + "')";
        PreparedStatement insertData = dbConnection.connectDB().prepareStatement(insertString);

        insertData.setInt(1, keyValue);
        insertData.setString(2, inputName);
        insertData.setTimestamp(3, timeStamp);

        System.out.println(insertData);

        try {
            insertData.execute();      
        } catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
            System.out.println("SQLState: "+e.getSQLState());
            System.out.println("VendorError: "+e.getErrorCode());
        } finally {
            
            /*
            * Thivent, P. (2010, February 8). Closing Database Connections in Java. Retrieved from <https://stackoverflow.com/a/2225275>
            * Pascal's answer to this Stack Overflow question provided context 
            * and understanding to the uCertify text's explanation of how to 
            * close Java SQL connections. Throughout the CalendarDB Class, and 
            * MainScreenContorller classes his pattern was used as a reference
            * for my connection disconnections.
            */
            
            try { insertData.close(); } catch (SQLException e) { /* ignored */ }
        }

        return keyValue;
    }
    
    private static int nextKey(String targetColumn, String tableName) throws SQLException{ // Generates the next available primary key value by checking for largest primary key existing in table and incrementing
        
        String primaryKeyQuery = "SELECT MAX(" + targetColumn + ") AS '" + targetColumn + "' FROM " + tableName + ""; // SELEC MAX method insp8ired by https://dev.mysql.com/doc/refman/8.0/en/example-maximum-column.html .
        
        CalendarDB dbKeyCheck = new CalendarDB();
        Statement stmt = dbKeyCheck.connectDB().createStatement();
        
        ResultSet rs = stmt.executeQuery(primaryKeyQuery);
        
        int result = 0; // Default to zero so the first entry will have a primary key of one if we do not have an entry in a table
        
        try {
            if (rs.next()){ // If the table is not empty, get the larget primary key
             result = rs.getInt(1);
            }      
        } catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
            System.out.println("SQLState: "+e.getSQLState());
            System.out.println("VendorError: "+e.getErrorCode());
        } finally {
            try { stmt.close(); } catch (SQLException e) { /* ignored */ }
        }
        
        result += 1; // Increment the returned key by 1.
        return result;
    }
    
    public static boolean deleteEntry(String delQuery) throws SQLException{ 
        boolean result = false;
        CalendarDB deleteSession = new CalendarDB();
        Statement stmt = deleteSession.connectDB().createStatement();
        try {
            stmt.execute(delQuery);
            result = true;
        } catch (SQLException e) {
            result = false;
            System.out.println("SQLException: "+e.getMessage());
            System.out.println("SQLState: "+e.getSQLState());
            System.out.println("VendorError: "+e.getErrorCode());
        } finally {
            try { stmt.close(); } catch (Exception e) { /* ignored */ }
        }            
        return result;
    }
    
    public static void updateCustomerName(String cusName, int primaryKey) throws SQLException {
        String modifyQuery = "UPDATE `customer` SET `customerName`= ? , `lastUpdateBy`= '" + getLoggedInUser() + "' WHERE `customerId`= ?";
        CalendarDB cusModify = new CalendarDB();
        PreparedStatement stmt = cusModify.connectDB().prepareStatement(modifyQuery);

        stmt.setString(1, cusName);
        stmt.setInt(2, primaryKey);

        System.out.println(stmt);
        
        try {
            stmt.execute();
        } catch (SQLException e) {
                System.out.println("SQLException: "+e.getMessage());
                System.out.println("SQLState: "+e.getSQLState());
                System.out.println("VendorError: "+e.getErrorCode());
        } finally {
            try { stmt.close(); } catch (SQLException e) { /* ignored */ }
        }
    }
    
    public static void updateCustomerStatus(String inputActive, int primaryKey) throws SQLException {
        String modifyQuery = "UPDATE `customer` SET `active`= ? , `lastUpdateBy`= '" + getLoggedInUser() + "' WHERE `customerId`= ?";
        CalendarDB cusModify = new CalendarDB();
        PreparedStatement stmt = cusModify.connectDB().prepareStatement(modifyQuery);

        int activeValue = 1;
        if (inputActive.equals("Inactive")){
            activeValue = 0;
        }

        stmt.setInt(1, activeValue);
        stmt.setInt(2, primaryKey);

        System.out.println(stmt);
        try {
            stmt.execute();
            
        } catch (SQLException e) {
                System.out.println("SQLException: "+e.getMessage());
                System.out.println("SQLState: "+e.getSQLState());
                System.out.println("VendorError: "+e.getErrorCode());
        } finally {
            try { stmt.close(); } catch (SQLException e) { /* ignored */ }
        }
    }
    
    public static void updateCustomerFK(int primaryKey, int foreignKey) throws SQLException {
        String modifyQuery = "UPDATE `customer` SET `addressId`= ? , `lastUpdateBy`= '" + getLoggedInUser() + "' WHERE `customerId`= ?";
        CalendarDB cusModify = new CalendarDB();
        PreparedStatement stmt = cusModify.connectDB().prepareStatement(modifyQuery);

        stmt.setInt(1, foreignKey);
        stmt.setInt(2, primaryKey);

        System.out.println(stmt);
        try {
            stmt.execute();
        
        } catch (SQLException e) {
                System.out.println("SQLException: "+e.getMessage());
                System.out.println("SQLState: "+e.getSQLState());
                System.out.println("VendorError: "+e.getErrorCode());
        } finally {
            try { stmt.close(); } catch (SQLException e) { /* ignored */ }
        }
    }
    
    public static int createAppointment(int customerId, String title, String description, String location, String contact, String url, Timestamp start, Timestamp end) throws SQLException {

        String createAppt = "INSERT INTO `appointment` (`appointmentId`, `customerId`, `title`, `description`, `location`, `contact`, `url`, `start`, `end`, `createDate`, `createdBy`, `lastUpdateBy`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, '" + getLoggedInUser() + "', '" + getLoggedInUser() + "')";
        
        int primaryKey = nextKey("appointmentId", "appointment");
        CalendarDB crtAppt = new CalendarDB();
        PreparedStatement stmt = crtAppt.connectDB().prepareStatement(createAppt);

        stmt.setInt(1, primaryKey);
        stmt.setInt(2, customerId);
        stmt.setString(3, title);
        stmt.setString(4, description);
        stmt.setString(5, location);
        stmt.setString(6, contact);
        stmt.setString(7, url);
        Timestamp timeStamp = getTimeStamp();
        stmt.setTimestamp(8, start); //start time
        stmt.setTimestamp(9, end); //end time            
        stmt.setTimestamp(10, timeStamp); // created at timestamp

        System.out.println(stmt);

        try {
            stmt.execute();
        
        } catch (SQLException e) {
                primaryKey = -1;
                System.out.println("SQLException: "+e.getMessage());
                System.out.println("SQLState: "+e.getSQLState());
                System.out.println("VendorError: "+e.getErrorCode());
        } finally {
            try { stmt.close(); } catch (SQLException e) { /* ignored */ }
            
        }
        return primaryKey;
    }
    
    public static void modifyAppointment(int customerId, String title, String description, String location, String contact, String url, Timestamp start, Timestamp end, int primaryKey) throws SQLException {

        String modifyAppt = "UPDATE `appointment` SET `customerId` = ?, `title`= ?, `description`= ?, `location`= ?, `contact`= ?, `url`=?, `start`= ?, `end`= ?, `lastUpdateBy`= '" + getLoggedInUser() + "' WHERE `appointmentId`= ?";;
        CalendarDB modAppt = new CalendarDB();
        PreparedStatement stmt = modAppt.connectDB().prepareStatement(modifyAppt);

        stmt.setInt(1, customerId);
        stmt.setString(2, title);
        stmt.setString(3, description);
        stmt.setString(4, location);
        stmt.setString(5, contact);
        stmt.setString(6, url);
        stmt.setTimestamp(7, start); //start time
        stmt.setTimestamp(8, end); //end time 
        stmt.setInt(9, primaryKey);

        System.out.println(stmt);
        try {
            stmt.execute();
        } catch (SQLException e) {
                System.out.println("SQLException: "+e.getMessage());
                System.out.println("SQLState: "+e.getSQLState());
                System.out.println("VendorError: "+e.getErrorCode());
        } finally {
            try { stmt.close(); } catch (SQLException e) { /* ignored */ }
        }
    }
    
    public static String[] getAppointmentAdminDetails(int primaryKey) throws SQLException { // Converts SQL datetime into local date timestamp
        
        String[] result = new String[4];
        CalendarDB dbConnection = new CalendarDB();
        String queryString = "SELECT `createdBy`, `createDate`, `lastUpdateBy`, `lastUpdate` FROM `appointment` WHERE `appointmentId` = ? ";
        PreparedStatement apointmentConnection = dbConnection.connectDB().prepareStatement(queryString);
        apointmentConnection.setInt(1, primaryKey);
        ResultSet rs = apointmentConnection.executeQuery();
        
        try {
            if (rs.next()){ // If the table is not empty
                result[0] = rs.getString("createdBy");
                result[1] = getLocalTime(rs.getTimestamp("createDate")).toString();
                result[2] = rs.getString("lastUpdateBy");
                result[3] = getLocalTime(rs.getTimestamp("lastUpdate")).toString();
            } else { // If the table is empty
                result[0] = "failed search";
            }
        } catch (SQLException e) {
                System.out.println("SQLException: "+e.getMessage());
                System.out.println("SQLState: "+e.getSQLState());
                System.out.println("VendorError: "+e.getErrorCode());
        } finally {
            try {rs.close(); } catch (SQLException e) { /* ignored */ }
            try { apointmentConnection.close(); } catch (SQLException e) { /* ignored */ }
        }

        return result;
    }
    
    public static String getAppointmentTypesReport() throws SQLException {
        String sqlQuery =   "SELECT MONTHNAME(start), description FROM U03zHj.appointment " +
                                "INNER JOIN customer " +
                                "ON appointment.customerId = customer.customerId " +
                                "WHERE YEAR(start) = YEAR(NOW()) " + "AND customer.active = 1 " +
                                "ORDER BY start";
        
        CalendarDB dbConnection = new CalendarDB();
        PreparedStatement reportConnection = dbConnection.connectDB().prepareStatement(sqlQuery);
        ResultSet rs = reportConnection.executeQuery();
        
        try {
            while (rs.next()){ // If the table is not empty
                
                System.out.println("Adding to the report: " + rs.getString("MONTHNAME(start)") + " : " + rs.getString("description"));
                addTypeEntry(rs.getString("MONTHNAME(start)"), rs.getString("description"));
            } 
            
            System.out.println("finished adding to report.");
        
        } catch (SQLException e) {
                System.out.println("SQLException: "+e.getMessage());
                System.out.println("SQLState: "+e.getSQLState());
                System.out.println("VendorError: "+e.getErrorCode());
        } finally {
            try {rs.close(); } catch (SQLException e) { /* ignored */ }
            try {reportConnection.close(); } catch (SQLException e) { /* ignored */ }            
        }
        
        System.out.println("\nAppointment Types by Month Report:");
        
        String[] result = new String[getTypeReport().size()];
        
        for(int i = 0; i < getTypeReport().size();  i++){
            result[i] = getTypeReport().get(i).getMonthData();
            System.out.println(result[i]);
        }
        
        String messageDetails = "";
        
        for(int i = 0; i < getTypeReport().size();  i++){
            messageDetails += result[i];
        }

        return messageDetails;
    }
    
    public static String getClientAppointmentsReport() throws SQLException {
        String sqlQuery =   "SELECT customerName, start, end, title, description, active FROM U03zHj.appointment\n" +
                            "INNER JOIN U03zHj.customer\n" +
                            "ON U03zHj.appointment.customerId = U03zHj.customer.customerId \n" +
                            "INNER JOIN U03zHj.address\n" +
                            "ON U03zHj.customer.addressId = U03zHj.address.addressId\n" +
                            "ORDER BY customerName, start, end";
        
        CalendarDB dbConnection = new CalendarDB();
        PreparedStatement reportConnection = dbConnection.connectDB().prepareStatement(sqlQuery);
        ResultSet rs = reportConnection.executeQuery();
            
        try {
            while (rs.next()){ // If the table is not empty

                // Parse out dates, hours, minutes, and am/pm for start and end times
                Timestamp rawStartDate = rs.getTimestamp("start");
                Timestamp localRawStart = getLocalTime(rawStartDate);
                LocalDate startDate = localRawStart.toLocalDateTime().toLocalDate();
                String semiRawStartHr = new SimpleDateFormat("HH").format(localRawStart);
                String convertedStartMin = new SimpleDateFormat("mm").format(localRawStart);
                String startAmPm = "AM";

                int semiConvStartHr = Integer.parseInt(semiRawStartHr);
                int convertedStartHr = semiConvStartHr;

                if (semiConvStartHr > 11){
                    startAmPm = "PM";

                    if (semiConvStartHr > 12){
                        convertedStartHr = semiConvStartHr - 12;
                    }
                } else if (semiConvStartHr == 0){
                    convertedStartHr = 12;
                }

                Timestamp rawStopDate = rs.getTimestamp("end");
                Timestamp localRawStop = getLocalTime(rawStopDate);
                String semiRawStopHr = new SimpleDateFormat("HH").format(localRawStop);
                String convertedStopMin = new SimpleDateFormat("mm").format(localRawStop);
                String stopAmPm = "AM";

                int semiConvStopHr = Integer.parseInt(semiRawStopHr);
                int convertedStopHr = semiConvStopHr;

                if (semiConvStopHr > 11){
                    stopAmPm = "PM";

                    if (semiConvStopHr > 12){
                        convertedStopHr = semiConvStopHr - 12;
                    }
                } else if (semiConvStopHr == 0){
                    convertedStopHr = 12;
                }

                String stringStopHr = Integer.toString(convertedStopHr);

                if (convertedStopHr < 10){
                    stringStopHr = "0" + convertedStopHr;
                }
                
                String startTime = convertedStartHr + ":" + convertedStartMin + " " + startAmPm;
                String endTime = convertedStopHr + ":" + convertedStopMin + " " + stopAmPm;
                
                String customerName = rs.getString("customerName");
                
                if(rs.getInt("active") == 0){
                    customerName += " - INACTIVE";
                }
                addAppointmentEntry(customerName, startDate, startTime, endTime, rs.getString("title") , rs.getString("description"));
            } 
            
            System.out.println("Finished adding to report.");
        
        } catch (SQLException e) {
                System.out.println("SQLException: "+e.getMessage());
                System.out.println("SQLState: "+e.getSQLState());
                System.out.println("VendorError: "+e.getErrorCode());
        } finally {
            try {rs.close(); } catch (SQLException e) { /* ignored */ }
            try {reportConnection.close(); } catch (SQLException e) { /* ignored */ }            
        }
        
        System.out.println("\nClient Appointments Report:");
        String messageDetails = getClientReportData();
        
        return messageDetails;
    }
    
    public static String getConsultantsAppointmentsReport() throws SQLException {
        String sqlQuery = "SELECT appointment.lastUpdateBy, customerName, start, end, title, description FROM U03zHj.appointment\n" +
                            "INNER JOIN U03zHj.customer\n" +
                            "ON U03zHj.appointment.customerId = U03zHj.customer.customerId \n" +
                            "INNER JOIN U03zHj.address\n" +
                            "ON U03zHj.customer.addressId = U03zHj.address.addressId\n" +
                            "WHERE customer.active = 1 \n" +
                            "ORDER BY customerName, start, end;";
        
        CalendarDB dbConnection = new CalendarDB();
        PreparedStatement reportConnection = dbConnection.connectDB().prepareStatement(sqlQuery);
        ResultSet rs = reportConnection.executeQuery();
        
        try {
            while (rs.next()){ // If the table is not empty
                
                // Parse out dates, hours, minutes, and am/pm for start and end times
                Timestamp rawStartDate = rs.getTimestamp("start");
                Timestamp localRawStart = getLocalTime(rawStartDate);
                LocalDate startDate = localRawStart.toLocalDateTime().toLocalDate();
                String semiRawStartHr = new SimpleDateFormat("HH").format(localRawStart);
                String convertedStartMin = new SimpleDateFormat("mm").format(localRawStart);
                String startAmPm = "AM";

                int semiConvStartHr = Integer.parseInt(semiRawStartHr);
                int convertedStartHr = semiConvStartHr;

                if (semiConvStartHr > 11){
                    startAmPm = "PM";

                    if (semiConvStartHr > 12){
                        convertedStartHr = semiConvStartHr - 12;
                    }
                } else if (semiConvStartHr == 0){
                    convertedStartHr = 12;
                }

                Timestamp rawStopDate = rs.getTimestamp("end");
                Timestamp localRawStop = getLocalTime(rawStopDate);
                String semiRawStopHr = new SimpleDateFormat("HH").format(localRawStop);
                String convertedStopMin = new SimpleDateFormat("mm").format(localRawStop);
                String stopAmPm = "AM";

                int semiConvStopHr = Integer.parseInt(semiRawStopHr);
                int convertedStopHr = semiConvStopHr;

                if (semiConvStopHr > 11){
                    stopAmPm = "PM";

                    if (semiConvStopHr > 12){
                        convertedStopHr = semiConvStopHr - 12;
                    }
                } else if (semiConvStopHr == 0){
                    convertedStopHr = 12;
                }

                String stringStopHr = Integer.toString(convertedStopHr);

                if (convertedStopHr < 10){
                    stringStopHr = "0" + convertedStopHr;
                }
                
                String startTime = convertedStartHr + ":" + convertedStartMin + " " + startAmPm;
                String endTime = convertedStopHr + ":" + convertedStopMin + " " + stopAmPm;
                
                        
                ConsultantScheduleReport.addAppointmentEntry(rs.getString("customerName"), startDate, startTime, endTime, rs.getString("title") , rs.getString("description"), rs.getString("lastUpdateBy"));
            } 
            
            System.out.println("Finished adding to report.");
        
        } catch (SQLException e) {
                System.out.println("SQLException: "+e.getMessage());
                System.out.println("SQLState: "+e.getSQLState());
                System.out.println("VendorError: "+e.getErrorCode());
        } finally {
            try {rs.close(); } catch (SQLException e) { /* ignored */ }
            try {reportConnection.close(); } catch (SQLException e) { /* ignored */ }
        }
        
        System.out.println("\nConsultant Schedule Report:");
        
        String messageDetails = getConsultantReportData();
        
        return messageDetails;
    }  
    
    public static void createReminder(int appointmentId, Timestamp startTime) throws SQLException {

        String createRem = "INSERT INTO `reminder` (`reminderId`, `appointmentId`, `reminderDate`, `createdDate`, `createdBy`, `snoozeIncrement`, `snoozeIncrementTypeId`, `remindercol`) VALUES (?, ?, ?, ?, '" + getLoggedInUser() + "',0,0,0)";
        int primaryKey = nextKey("reminderId", "reminder");

        CalendarDB crtRem = new CalendarDB();
        PreparedStatement stmt = crtRem.connectDB().prepareStatement(createRem);

        stmt.setInt(1, primaryKey);
        stmt.setInt(2, appointmentId);

        //Create our reminder time
        long longTime = startTime.getTime(); // our initial start time in long format
        long decrementTime = -15*60*1000; // time to delay by in nanoseconds
        Timestamp reminderTime = new Timestamp(longTime + decrementTime);

        stmt.setTimestamp(3, reminderTime); //start time

        Timestamp timeStamp = getTimeStamp();
        stmt.setTimestamp(4, timeStamp); // created at timestamp

        System.out.println(stmt);
            
        try {
            stmt.execute();
            
        } catch (SQLException e) {
                System.out.println("SQLException: "+e.getMessage());
                System.out.println("SQLState: "+e.getSQLState());
                System.out.println("VendorError: "+e.getErrorCode());
        } finally {
            try {stmt.close(); } catch (SQLException e) { /* ignored */ }
        }
    }
    
    public static void modifyReminder(int appointmentId, Timestamp startTime) throws SQLException {

        String modifyRem = "UPDATE `reminder` SET `reminderDate` = ? WHERE `appointmentId`= ?";
        
        CalendarDB modRem = new CalendarDB();
        PreparedStatement stmt = modRem.connectDB().prepareStatement(modifyRem);

        //Create our reminder time
        long longTime = startTime.getTime(); // our initial start time in long format
        long decrementTime = -15*60*1000; // time to delay by in nanoseconds
        Timestamp reminderTime = new Timestamp(longTime + decrementTime);

        stmt.setTimestamp(1, reminderTime); //start time
        stmt.setInt(2, appointmentId);

        System.out.println(stmt);
        
        try {
            stmt.execute();
            
        } catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
            System.out.println("SQLState: "+e.getSQLState());
            System.out.println("VendorError: "+e.getErrorCode());
        } finally {
            try {
                stmt.close(); 
            } catch (SQLException e) {
             // Ignore this catch as we are just closing the connection.
            }
        }
    }
    
    public static void retrieveReminders() throws SQLException{ //retrieves appointment alerts        
        if (isRemindersChecked() == false){
            String retrieveRem = "SELECT reminder.appointmentId, reminderDate, appointment.createdBy FROM U03zHj.reminder " +
                                    "INNER JOIN U03zHj.appointment " +
                                    "ON U03zHj.reminder.appointmentId = U03zHj.appointment.appointmentId " +
                                    "INNER JOIN U03zHj.customer " +
                                    "ON U03zHj.appointment.customerId = U03zHj.customer.customerId " +
                                    "INNER JOIN U03zHj.address " +
                                    "ON U03zHj.customer.addressId = U03zHj.address.addressId " +
                                    "WHERE YEARWEEK(`start`, 1) = YEARWEEK(NOW(), 1) AND customer.active = 1 " +
                                    "AND appointment.lastUpdateBy = '" + getLoggedInUser() + "' " +
                                    "ORDER BY reminderDate, customerName, start, end";
            
            CalendarDB retRem = new CalendarDB();
            PreparedStatement stmt = retRem.connectDB().prepareStatement(retrieveRem);

            ResultSet rs = stmt.executeQuery();

            setRemindersChecked();
                
            try {
                while (rs.next() == true) { // If the sql query returns a result, check to see if the reminder needs to be displayed
                    
                    Timestamp reminder = rs.getTimestamp("reminderDate");
                    
                    // Calculate Start time based on reminder time
                    long longTime = reminder.getTime(); // our initial reminder time in long format
                    long incrementTime = 15*60*1000; // time to advance by in nanoseconds
                    Timestamp calcStartTime = new Timestamp(longTime + incrementTime);
                    
                    Timestamp currentTime = getTimeStamp();
                    
                    if (currentTime.after(reminder) == true && currentTime.before(calcStartTime)){ // If the current time is between the reminer timestamp and the calculated start time, display an alert
                        
                        int appId = rs.getInt("appointmentId");
                        
                        for(int i = 0; i < getAppointmentData().size();  i++){ // Iterate over the appointment list to find the appointment that matches based on the appointment primary key and the reminder foreign key.
                            if (getAppointmentData().get(i).getPK()== appId){
                                
                                Appointment reminderAppointment = getAppointmentData().get(i);
                                
                                String title = reminderAppointment.getTitle();
                                String customer = reminderAppointment.getName();
                                String startTime = reminderAppointment.getStartHr() + ":" + reminderAppointment.getStartMin() + " " + reminderAppointment.getStartAMPM();
                                String endTime = reminderAppointment.getStopHr() + ":" + reminderAppointment.getStopMin() + " " + reminderAppointment.getStopAMPM();
                                String location = reminderAppointment.getLocation();
                                String phone = reminderAppointment.getPhone();
                                String description = reminderAppointment.getDescription();
                                String contact = reminderAppointment.getContact();
                                String url = reminderAppointment.getUrl();

                                String host = rs.getString("lastUpdateBy");

                                String messageDetails;

                                messageDetails = host + " has an upcoming appointment with " + customer + 
                                                    " beginning at " + startTime + " and lasting till " + endTime + ".\n" + 
                                                    "Descripiton: " + description + "\n" +
                                                    "Location: " + location + "\n" +
                                                    "Customer Phone: " + phone + "\n" +
                                                    "Contact: " + contact + "\n" +
                                                    "URL: " + url;
                                
                                System.out.println(messageDetails);
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Upcoming Appointment: " + title);
                                alert.setHeaderText("Upcoming Appointment: " + title);
                                alert.setContentText(messageDetails);
                                alert.showAndWait();
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                System.out.println("SQLException: "+e.getMessage());
                System.out.println("SQLState: "+e.getSQLState());
                System.out.println("VendorError: "+e.getErrorCode());
            } finally {
                try {stmt.close(); } catch (SQLException e) { /* ignored */ }
                try {rs.close(); } catch (SQLException e) { /* ignored */ }
            }
        } else {
            System.out.println("We have already checked for reminders recently.");
        }
    }
    
}