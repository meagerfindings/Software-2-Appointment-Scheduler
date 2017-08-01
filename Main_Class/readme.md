# Practical assessment: Software II Advanced Java Concepts – C195

_**The requirements provided for this assignment have been included below the Reference List section. Guiding explanations, examples, and specific locations in the source code are referenced in the requirements section to assist in identifying where in the source code these requirements are met.**_

# Table of Contents

## Assumptions

- Business hours are from 7 AM to 6 PM Local Time Monday through Sunday.
- Checks for overlapping appointments are performed for the logged in user against the logged in user's appointments and not against other users appointments.

  - This enables multiple users to have separate schedules that can overlap.

- Reminders for upcoming appointments will be shown to the user who last updated the appointment, they are considered the owner of the appointment.

  - If the appointment has never been modified this will be shown to the user who created the appointment.

- Reminders are refreshed at initial login and then again after an appointment is created or modified in case the appointment is within the next 15 minutes.

- Reports and logs are saved to the local Downloads folder on the user profile logged into the computer.

  - Mac example: `/Users/matgreten/Downloads/report_client_appointments.txt.`
  - Windows example: `C:\Users\MatGreten\Downloads\report_client_appointments.txt`

### User accounts

The following have been setup for users to login with:

```
Account 1:
username: test
password: test

Account 2:
username: Mat Greten
password: endgame
```

## Reference List

- Oracle. (2015). _Oracle Java Tutorials: System Properties_. Retrieved from <https://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html>
- Oracle. (2016). _Oracle Java Documents: Class Timestamp_. Retrieved from <https://docs.oracle.com/javase/8/docs/api/java/sql/Timestamp.html>
- Shoikana. (2017, June 9). _Timestamp Conversion to Instant in Java Adds Unnecessary Time Offset_. Retrieved from <https://stackoverflow.com/a/44462605>
- Thivent, P. (2010, February 8). _Closing Database Connections in Java_. Retrieved from <https://stackoverflow.com/a/2225275>

--------------------------------------------------------------------------------

## SOFTWARE II

### Competencies:

- 4025.1.5: Database and File Server Applications - The graduate produces database and file server applications using advanced constructs in a high-level programming language to meet business requirements.

- 4025.1.6: Lambda - The graduate incorporates lambda expressions in application development to meet business requirements more efficiently.

- 4025.1.7: Collections (Streams and Filters) - The graduate incorporates streams and filters in application development to manipulate data more efficiently.

- 4025.1.8: Localization API & Date/Time API - The graduate applies the localization API and date/time API in application development to support end-users in various geographical regions.

- 4025.1.9: Advanced Exception Control - The graduate incorporates advanced exception control mechanisms in application development for improving user experience and application stability.

### Task 1: Java Application Development

#### Introduction:

Throughout your career in software design and development, you will be asked to create applications with various features and criteria based on a variety of business requirements. For this assessment, you will create your own Java application with requirements that mirror those you will encounter in a real-world job assignment.

The skills you will showcase in this assessment are also directly relevant to technical interview questions for future employment. This application should become a portfolio piece for you to show to future employers.

Several attachments and links have been included to help you complete this task. Refer to the "MySQL Virtual Access Instructions" attachment for help accessing the database for your application. Note that this database is for functional purposes only and does not include any pre-existing data. The attached "Database ERD" shows the entity relationship diagram (ERD) for this database, which you can reference as you create your application.

The preferred integrated development environment (IDE) for this assignment is NetBeans. Use the web link "NetBeans Installation Instructions" to install this connector. If you choose to use another IDE, you must export your project into NetBeans format for submission.

When you have completed this task, you must submit a zip file with all the necessary code files to compile, support, and run your application.

#### Scenario:

_You are working for a software company that has been contracted to develop a scheduling desktop user interface application. The contract is with a global consulting organization that conducts business in multiple languages and has main offices in Phoenix, Arizona; New York, New York; and London, England. The consulting organization has provided a MySQL database that your application must pull data from. The database is used for other systems and therefore its structure cannot be modified._

_The organization outlined specific business requirements that must be included as part of the application. From these requirements, a system analyst at your company created solution statements for you to implement in developing the application. These statements are listed in the requirements section._

### Requirements:

_Your submission must be your original work. No more than a combined total of 30% of the submission and no more than a 10% match to any one individual source can be directly quoted or closely paraphrased from sources, even if cited correctly. Use the Turnitin Originality Report available in Taskstream as a guide for this measure of originality._

_You must use the rubric to direct the creation of your submission because it provides detailed criteria that will be used to evaluate your work. Each requirement below may be evaluated by more than one rubric aspect. The rubric aspect titles may contain hyperlinks to relevant portions of the course._

#### _A. Create a log-in form that can determine the user's location and translate log-in and error control messages (e.g., "The username and password did not match.") into two languages._

- Uncommenting line 22 in the Matgreten_software_2_calendar.java class within the Main_Class folder will set the locale and language to Spain.
- The class LoginPrompt, within the View_Controller folder, will respond to this locale by utilizing a location check through a lambda declared in line 40 and actually checked during the `initialize()` method for the class.
- The various alerts for exiting the application from the login screen, incorrect logins alerts and messages, buttons, titles, labels, and text field prompts will all respond to the locale for Spain and display their texts in Spanish rather than English.

#### _B. Provide the ability to enter and maintain customer records in the database, including name, address, and phone number._

- In the GUI, customer creation and modification is performed during the creation and modification of customer appointments.

  - In the main screen, click on either `Add Appointment` or `Modify Appointment`, from the appointment screen, click the `Choose Customer` button. Once in the main Customer's screen, click the `New customer` button or click on a customer row and select `Modify Customer` button.

- Source code:

  - Classes to setup windows for customer selection, creation, and modification can be found in the View_Controller folder:

    - CustomerAdd.fxml
    - CustomerAddController.java
    - CustomerMain.fxml
    - CustomerMainController.java
    - CustomerModify.fxml
    - CustomerModifyController.java

  - Within the Model folder, the Customer.java class contains the code to create, hold, and modify our Customer objects.

#### _C. Write lambda expression(s) to schedule and maintain appointments, capturing the type of appointment and a link to the specific customer record in the database._

**Lambda Usage Examples:**

- Initializations of treeviews and selection listeners within the following files implement. Lambdas:

  - MainScreenController class, lines 486-498.
  - MainScreenMonthViewController class, lines 554-566.
  - MainScreenWeekViewController class, lines 552-564.
  - CustomerMainController class, lines 362-380

- CustomerMainController class search:

  - Initialization of filteredCustomers list with lambda search results.
  - Initialization of search listener for customer table: Line 372.
  - Lambda function that takes in a customer object and compares the values of our search against this object's properties, lines 151-162.

- AppointmentAddController Class Alert Creations:

  - `AddAlert` Functional interface declared: Lines 546-549.
  - Lines 128-136: Lambda declared to be used throughout the class to generate alert messages:

    ```
    private final AddAlert throwAddErrorMessage = (String title, String header, String context) -> {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
        return false;
    };
    ```

  - The lambda below appears throughout the class as: `throwAddErrorMessage.addApptAlert(title, header, context)` with input varying depending on the alert needing to be shown, greatly reducing redundant code.

- AppointmentModifyController Class Alert Creations:

  - `ModAlert` Functional interface declared: Lines 556-559.
  - Lines 100-107: Lambda `throwModErrorMessage` declared to be used throughout the class to generate alert messages.
  - The lambda below appears through the class as: `throwModErrorMessage.modAppAlert(title, header, context)` with input varying depending on the alert needing to be shown, greatly reducing redundant code.

**Appointment Creation and Modification:**

- Within the source code, Appointment objects are created, held, and modified within the Appointment.java class within the Model folder.
- Within the source code, GUI interactions for Appointments are created through the following classes:

  - AppointmentAdd.fxml
  - AppointmentAddController.java
  - AppointmentModify.fxml
  - AppointmentModifyController.java
  - MainScreen.fxml
  - MainScreenController.java
  - MainScreenMonthView.fxml
  - MainScreenMonthViewController.java
  - MainScreenWeekView.fxml
  - MainScreenWeekViewController.java

#### _D. Provide the ability to view the calendar by month and by week._

- The following classes contain methods, triggered by ratio buttons in a togglegroup, to switch to screenviews that limit the treeview to either all appointments, the current week, or the current month:

  - MainScreen.fxml
  - MainScreenController.java
  - MainScreenMonthView.fxml
  - MainScreenMonthViewController.java
  - MainScreenWeekView.fxml
  - MainScreenWeekViewController.java

- The `loadCustomerDatabase()` method's input in each controller class limits the results returned by the SQL query to those necessary in the relevant view.

  - For example, the MainScreenMonthViewController calls a modified query, _line 380_, that limits the results to only be records with an appointment start date within the current month:

    ```
    "SELECT appointmentId, title, description, location, contact, url, customer.customerId, start, end, customerName, phone, appointment.createdBy FROM appointment " +
    "INNER JOIN customer " +
    "ON appointment.customerId = customer.customerId " +
    "INNER JOIN address " +
    "ON customer.addressId = address.addressId " +
    "WHERE YEAR(`start`) = YEAR(NOW()) AND MONTH(`start`) = MONTH(NOW()) AND customer.active = 1 " +
    "ORDER BY start, end, title";
    ```

#### _E. Provide the ability to automatically adjust appointment times based on user time zones and daylight saving time._

The following methods in the Time.java class within the Model folder converts GMT/UTC timestamps from the database into the local timezone and daylight savings time:

- Line 31: `public static Timestamp getTimeStamp()`
- Line 81: `public static Timestamp convertDate(String inputString)`
- Line 105: `public static String datePiecesToString(LocalDate inputDate, int inputHr, String inputMin, String inputAMPM)`
- Line 138: `public static Timestamp getLocalTime(Timestamp inputStamp)`

#### _F. Write exception controls to prevent each of the following. You may use the same mechanism of exception control more than once, but you must incorporate at least two different mechanisms of exception control._

##### - _scheduling an appointment outside business hours_

- Appointment start and end times are compared to our business hours within the following classes and locations:

  - AppointmentAddController, lines 278-330.

  - AppointmentModifyController, lines 293-340.

##### - _scheduling overlapping appointments_

- Since scheduling a conflicting appointment will not trigger a SQL exception, this is handled by comparing the appointment to be added, against the appointment objects created from our previous queries of the database.

  - AppointmentAddController, lines 368-401.
  - AppointmentModifyController, lines 365-399.

##### - _entering nonexistent or invalid customer data_

- Empty fields and invalid characters are checked both when creating customers and when modifying customers:

  - CustomerAddController, lines 102-227.
  - CustomerModifyController, lines 113-236.

- In order to prevent appointments from referencing deleted customers, deletion of customers has been disabled. Instead, users can set customers to inactive or active when creating or modifying customers.

##### - _entering an incorrect username and password_

- Within the LoginPrompt class in the View_Controller folder, this is handled in the `submitButtonClicked(ActionEvent event)` method which calls the `incorrectLogin(String enteredUserName)` method, defined in lines 102-119, if an incorrect username or password is entered.

**Examples of different methods of exception control:**

- Try/catch blocks are used in the database queries throughout the application. One such example is within the CalendarDB class within the Model folder in lines 62-75:

  ```
  public Connection connectDB(){
    if (databaseConnection == null){ // If the connection is not active setup the connection.
        try {
            Class.forName("com.mysql.jdbc.Driver");
            databaseConnection = DriverManager.getConnection("jdbc:mysql://52.206.157.109/U03zHj","U03zHj","53688129053");

        } catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
            System.out.println("SQLState: "+e.getSQLState());
            System.out.println("VendorError: "+e.getErrorCode());
        }  catch (ClassNotFoundException e){
            e.printStackTrace();
        }
  ```

- Throws clauses are also used throughout the application to allow for the handling of IOExceptions and SQLExceptions. One example may be found within the CustomerAddController class within the View_Controller folder at line 92 within the `saveButtonClicked()` method:

  ```
  @FXML
  boolean saveButtonClicked(ActionEvent event) throws IOException, SQLException {
  ```

#### _G. Use lambda expressions to create standard pop-up and alert messages._

- Examples of lambda expressions being used in pop-up and alert messages can be found above within the examples provided for section _C_.

#### _H. Write code to provide reminders and alerts 15 minutes in advance of an appointment, based on the user's log-in._

- These alerts are triggered during the initialization of the MainScreenController class, which loads directly after a successful login in lines 500-505:

  ```
  //Check for reminders
       try {
           retrieveReminders();
       } catch (SQLException ex) {
           Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
       }
  ```

  - Retrieval of reminders is accomplished in the `retrieveReminders()` method the CalendarDB class within the Model folder.
  - The query, found at line 767, limits the results to only be appointments from active users, which appear during the current week, for the currently logged in user:

    ```
    "SELECT reminder.appointmentId, reminderDate, appointment.createdBy FROM U03zHj.reminder " +
    "INNER JOIN U03zHj.appointment " +
    "ON U03zHj.reminder.appointmentId = U03zHj.appointment.appointmentId " +
    "INNER JOIN U03zHj.customer " +
    "ON U03zHj.appointment.customerId = U03zHj.customer.customerId " +
    "INNER JOIN U03zHj.address " +
    "ON U03zHj.customer.addressId = U03zHj.address.addressId " +
    "WHERE YEARWEEK(`start`, 1) = YEARWEEK(NOW(), 1) AND customer.active = 1 " +
    "AND appointment.lastUpdateBy = '" + getLoggedInUser() + "' " +
    "ORDER BY reminderDate, customerName, start, end";
    ```

    - Reminders are created and modified at the appointment creation or modification time, directly after appointment records are created or updated.

      - AppointmentAddController, line 411: `createReminder(appointmentId, convertedStartDate);`
      - AppointmentModifyController, line 405: `modifyReminder(primaryKey, convertedStartDate);`

    - The methods for creation and modification of reminders are found within the CalendarDB class within the Model folder:

      - Line 697: `public static void createReminder(int appointmentId, Timestamp startTime)`
      - Line 732: `public static void modifyReminder(int appointmentId, Timestamp startTime)`

#### _I. Provide the ability to generate each of the following reports:_

```
- number of appointment types by month
- the schedule for each consultant
- one additional report of your choice
```

- Within the GUI:

  - The three required reports can be selected to be run form the dropdown menu labeled **Export Reports**, on the bottom left hand side of the main appointment view screen directly after login.
  - Once run, the reports display their results in an information pop-up and additionally save to the local **Downloads** folder on the computer.

- Within the Source Code:

  - The data structure objects for the three reports are found within the Reports folder within the following classes:

    - Consultant.java
    - ConsultantAppointment.java
    - Month.java
    - ReportCustomer.java

  - The classes containing the methods for running and retrieving these reports are also in the Reports folder:

    - AppointmentTypeReport.
    - ClientAppointment.java
    - ConsultantScheduleReport.java -

- The IOWriter class in the Reports folder contains the methods used to write these reports to disk.

#### _J. Provide the ability to track user activity by recording timestamps for user log-ins in a .txt file. Each new record should be appended to the log file, if the file already exists._

- Both successful and unsuccessful login attempts are recorded to the local **Downloads** folder, in a file titled: _scheduler_logs.txt_.

- The IOWriter class in the Reports folder contains the methods used to write the log file to disk. These methods are called from within the LoginPrompt class within the View_Controller folder.

- Example output in log file:

  ```
  2017-07-29 14:59:57.000000977: 'test' successfully logged in.
  2017-07-29 15:19:45.000000045: 'test' successfully logged in.
  2017-07-29 15:20:52.000000638: Unsuccessful Login Attempt with username: 'teset'.
  2017-07-29 15:20:57.000000414: 'test' successfully logged in.
  2017-07-29 15:22:22.000000637: 'mat greten' successfully logged in.
  2017-07-29 15:23:35.000000251: 'mat greten' successfully logged in.
  2017-07-29 15:25:09.000000557: 'test' successfully logged in.
  2017-07-29 15:29:20.000000307: 'test' successfully logged in.
  ```
