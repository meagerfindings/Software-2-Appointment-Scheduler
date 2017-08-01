# Software II Advanced Java Concepts Course Submission

_**The requirements provided for this assignment have been included below the Reference List section. Guiding explanations, examples, and specific locations in the source code are referenced in the requirements section to assist in identifying where in the source code these requirements are met.**_

# Table of Contents
<!-- TOC depthFrom:1 depthTo:6 withLinks:1 updateOnSave:1 orderedList:0 -->

- [Software II Advanced Java Concepts Course Submission](#software-ii-advanced-java-concepts-course-submission)
- [Table of Contents](#table-of-contents)
	- [Assumptions](#assumptions)
		- [User accounts](#user-accounts)
	- [Reference List](#reference-list)
	- [SOFTWARE II](#software-ii)
		- [Competencies:](#competencies)
		- [Task 1: Java Application Development](#task-1-java-application-development)
			- [Introduction](#introduction)
			- [Scenario](#scenario)
		- [Requirements:](#requirements)
			- [_A. Create a log-in form that can determine the user's location and translate log-in and error control messages (e.g., "The username and password did not match.") into two languages._](#a-create-a-log-in-form-that-can-determine-the-users-location-and-translate-log-in-and-error-control-messages-eg-the-username-and-password-did-not-match-into-two-languages)
			- [_B. Provide the ability to enter and maintain customer records in the database, including name, address, and phone number._](#b-provide-the-ability-to-enter-and-maintain-customer-records-in-the-database-including-name-address-and-phone-number)
			- [_C. Write lambda expression(s) to schedule and maintain appointments, capturing the type of appointment and a link to the specific customer record in the database._](#c-write-lambda-expressions-to-schedule-and-maintain-appointments-capturing-the-type-of-appointment-and-a-link-to-the-specific-customer-record-in-the-database)
			- [_D. Provide the ability to view the calendar by month and by week._](#d-provide-the-ability-to-view-the-calendar-by-month-and-by-week)
			- [_E. Provide the ability to automatically adjust appointment times based on user time zones and daylight saving time._](#e-provide-the-ability-to-automatically-adjust-appointment-times-based-on-user-time-zones-and-daylight-saving-time)
			- [_F. Write exception controls to prevent each of the following. You may use the same mechanism of exception control more than once, but you must incorporate at least two different mechanisms of exception control._](#f-write-exception-controls-to-prevent-each-of-the-following-you-may-use-the-same-mechanism-of-exception-control-more-than-once-but-you-must-incorporate-at-least-two-different-mechanisms-of-exception-control)
				- [- _scheduling an appointment outside business hours_](#-scheduling-an-appointment-outside-business-hours)
				- [- _scheduling overlapping appointments_](#-scheduling-overlapping-appointments)
				- [- _entering nonexistent or invalid customer data_](#-entering-nonexistent-or-invalid-customer-data)
				- [- _entering an incorrect username and password_](#-entering-an-incorrect-username-and-password)
			- [_G. Use lambda expressions to create standard pop-up and alert messages._](#g-use-lambda-expressions-to-create-standard-pop-up-and-alert-messages)
			- [_H. Write code to provide reminders and alerts 15 minutes in advance of an appointment, based on the user's log-in._](#h-write-code-to-provide-reminders-and-alerts-15-minutes-in-advance-of-an-appointment-based-on-the-users-log-in)
			- [_I. Provide the ability to generate each of the following reports:_](#i-provide-the-ability-to-generate-each-of-the-following-reports)
				- [Example Output from Reports](#example-output-from-reports)
			- [_J. Provide the ability to track user activity by recording timestamps for user log-ins in a .txt file. Each new record should be appended to the log file, if the file already exists._](#j-provide-the-ability-to-track-user-activity-by-recording-timestamps-for-user-log-ins-in-a-txt-file-each-new-record-should-be-appended-to-the-log-file-if-the-file-already-exists)
	- [Application Screenshots](#application-screenshots)
		- [Login Prompt](#login-prompt)
			- [Login in Spanish](#login-in-spanish)
			- [Login Error in Spanish](#login-error-in-spanish)
		- [Main Appointment Screen](#main-appointment-screen)
		- [Add Appointment](#add-appointment)
		- [Modify Appointment](#modify-appointment)
		- [View Appointment Details](#view-appointment-details)
		- [Main Customer Screen](#main-customer-screen)
		- [Modify Customer](#modify-customer)

<!-- /TOC -->

## Assumptions

- The MySQL database this project was querying was maintained by my school. I have removed the SQL connection's host IP and the user profile's password from the `CalendarDB` file.

  - With this in mind, I have exported the MySQL data to replicate the tables, schemas, and data present at the time of submission. This file is in the root of this repository, titled: _MySQL_data_export.sql_.

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
            databaseConnection = DriverManager.getConnection("jdbc:mysql://[HOST IP REDACTED]/U03zHj","U03zHj","[PASSWORD REDACTED]");

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

##### Example Output from Reports

**Appointment Types by Month Report:**

```
February
----------
Customer Calibration: 0
Demo: 1
Emergency Customer Assistance: 0
Initial Consultation: 0
Routine Check-In: 0
Troubleshooting: 0

May
-----
Customer Calibration: 0
Demo: 0
Emergency Customer Assistance: 0
Initial Consultation: 0
Routine Check-In: 1
Troubleshooting: 0

June
------
Customer Calibration: 0
Demo: 1
Emergency Customer Assistance: 0
Initial Consultation: 1
Routine Check-In: 1
Troubleshooting: 3

July
------
Customer Calibration: 1
Demo: 2
Emergency Customer Assistance: 4
Initial Consultation: 1
Routine Check-In: 3
Troubleshooting: 0

August
--------
Customer Calibration: 0
Demo: 0
Emergency Customer Assistance: 0
Initial Consultation: 1
Routine Check-In: 0
Troubleshooting: 0
```

**Appointments by Client:**

```
Aaron Weiss
-------------
2017-07-29 12:26 PM - 4:00 PM Title: DB Close Test Description: Demo

Aaron WeissToo
----------------
2017-07-02 12:13 PM - 1:14 PM Title: mewithoutyou concert Description: Routine Check-In

Allost Macarkeys
------------------
2017-07-27 12:00 PM - 2:00 PM Title: Test without CalendarDB in Main_Class folder Description: Emergency Customer Assistance

Billy Corgan - INACTIVE
-------------------------
2017-06-14 7:01 AM - 3:17 PM Title: agadg Description: Troubleshooting
2017-07-14 4:14 PM - 5:16 PM Title: asga Description: Customer Calibration
2017-07-18 2:14 AM - 1:16 AM Title: Testing Description: Customer Calibration
2017-07-26 3:15 AM - 2:18 PM Title: Smashing Pumpkins Show Description: Customer Calibration

Boba Fett
-----------
2017-05-15 4:00 PM - 5:00 PM Title: Interrogate Description: Routine Check-In
2017-06-27 12:00 AM - 9:00 PM Title: Solo Pickup Description: Routine Check-In

El Bartman
------------
2017-06-04 2:11 PM - 3:13 PM Title: Testing out Empty Description Description: Demo
2017-07-22 9:57 AM - 12:00 PM Title: Remidner for today Description: Routine Check-In

Mats Valk0 - INACTIVE
-----------------------
2017-06-14 3:00 PM - 12:00 PM Title: Month and Appointment Type Test Description: Routine Check-In
2017-07-11 1:14 PM - 1:16 PM Title: Testing inactive users Description: Routine Check-In
2017-07-14 7:01 AM - 7:02 AM Title: 12512525 Description: Troubleshooting
2017-07-19 2:14 AM - 1:17 PM Title: kjhdfjk,asdhfklas;df Description: Customer Calibration

Matt Belamy
-------------
2017-02-06 8:14 AM - 2:17 PM Title: Month test 2 Description: Demo
2017-08-23 3:13 AM - 2:17 PM Title: Month test Description: Initial Consultation

Mitth'raw'nuruodo
-------------------
2017-06-20 12:15 PM - 6:00 PM Title: Discuss Surrender (of nemesis) Description: Troubleshooting
2017-07-16 12:00 PM - 12:00 PM Title: Reminder 2 Test Description: Demo
2017-07-19 9:14 AM - 12:16 PM Title: Testing Description: Emergency Customer Assistance

New Customer Test
-------------------
2017-07-31 11:11 AM - 2:14 PM Title: New Appointment Test Description: Initial Consultation

Wampa
-------
2017-06-11 8:14 AM - 5:16 PM Title: Automate this appointment Description: Initial Consultation
2017-06-14 12:00 PM - 1:00 PM Title: asdfsadf Description: Troubleshooting
2017-06-25 3:12 PM - 4:13 PM Title: Reminder Test 5 Description: Troubleshooting
2017-07-08 2:15 PM - 2:16 PM Title: Week of July 8 Description: Emergency Customer Assistance
2017-07-17 12:00 PM - 2:00 PM Title: Reminder Test Description: Emergency Customer Assistance
2017-07-21 12:00 PM - 12:00 PM Title: Reminder Test 3 Description: Customer Calibration
2017-07-26 3:21 PM - 5:14 PM Title: Remind all the things Description: Routine Check-In
2017-07-29 4:45 PM - 5:00 PM Title: Capture Luke Description: Customer Calibration
```

**Appointments by Consultant Report**

```
test
------
2017-07-29 12:26 PM - 4:00 PM Customer: Aaron Weiss Title: DB Close Test Description: Demo
2017-07-27 12:00 PM - 2:00 PM Customer: Allost Macarkeys Title: Test without CalendarDB in Main_Class folder Description: Emergency Customer Assistance
2017-05-15 4:00 PM - 5:00 PM Customer: Boba Fett Title: Interrogate Description: Routine Check-In
2017-06-04 2:11 PM - 3:13 PM Customer: El Bartman Title: Testing out Empty Description Description: Demo
2017-07-31 11:11 AM - 2:14 PM Customer: New Customer Test Title: New Appointment Test Description: Initial Consultation
2017-07-29 4:45 PM - 5:00 PM Customer: Wampa Title: Capture Luke Description: Customer Calibration

mat greten
------------
2017-07-02 12:13 PM - 1:14 PM Customer: Aaron WeissToo Title: mewithoutyou concert Description: Routine Check-In

Jaba the Hutt
---------------
2017-06-27 12:00 AM - 9:00 PM Customer: Boba Fett Title: Solo Pickup Description: Routine Check-In

Darh Maul
-----------
2017-07-22 9:57 AM - 12:00 PM Customer: El Bartman Title: Remidner for today Description: Routine Check-In
2017-02-06 8:14 AM - 2:17 PM Customer: Matt Belamy Title: Month test 2 Description: Demo
2017-07-16 12:00 PM - 12:00 PM Customer: Mitth'raw'nuruodo Title: Reminder 2 Test Description: Demo
2017-07-19 9:14 AM - 12:16 PM Customer: Mitth'raw'nuruodo Title: Testing Description: Emergency Customer Assistance
2017-06-14 12:00 PM - 1:00 PM Customer: Wampa Title: asdfsadf Description: Troubleshooting
2017-06-25 3:12 PM - 4:13 PM Customer: Wampa Title: Reminder Test 5 Description: Troubleshooting
2017-07-08 2:15 PM - 2:16 PM Customer: Wampa Title: Week of July 8 Description: Emergency Customer Assistance
2017-07-17 12:00 PM - 2:00 PM Customer: Wampa Title: Reminder Test Description: Emergency Customer Assistance
2017-07-21 12:00 PM - 12:00 PM Customer: Wampa Title: Reminder Test 3 Description: Customer Calibration
2017-07-26 3:21 PM - 5:14 PM Customer: Wampa Title: Remind all the things Description: Routine Check-In

Test Account
--------------
2017-08-23 3:13 AM - 2:17 PM Customer: Matt Belamy Title: Month test Description: Initial Consultation

Mat
-----
2017-06-20 12:15 PM - 6:00 PM Customer: Mitth'raw'nuruodo Title: Discuss Surrender (of nemesis) Description: Troubleshooting

Randal Munroe
---------------
2017-06-11 8:14 AM - 5:16 PM Customer: Wampa Title: Automate this appointment Description: Initial Consultation
```

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

## Application Screenshots

### Login Prompt

![Login Screen](https://github.com/meagerfindings/Software-2-Appointment-Scheduler/blob/master/images/login.png)

#### Login in Spanish

![Spanish Login Screen](https://github.com/meagerfindings/Software-2-Appointment-Scheduler/blob/master/images/login_spanish.png)

#### Login Error in Spanish

![Spanish Login Error](https://github.com/meagerfindings/Software-2-Appointment-Scheduler/blob/master/images/login_spanish_error.png)

### Main Appointment Screen

![Main Appointment Screen](https://github.com/meagerfindings/Software-2-Appointment-Scheduler/blob/master/images/main_screen.png)

### Add Appointment

![Add Appointment Screen](https://github.com/meagerfindings/Software-2-Appointment-Scheduler/blob/master/images/add_appointment.png)

### Modify Appointment

![Modify Appointment Screen](https://github.com/meagerfindings/Software-2-Appointment-Scheduler/blob/master/images/modify_appointment.png)

### View Appointment Details

![View Appointment Details Screen](https://github.com/meagerfindings/Software-2-Appointment-Scheduler/blob/master/images/view_appointment_details.png)

### Main Customer Screen

![Customers Main Screen](https://github.com/meagerfindings/Software-2-Appointment-Scheduler/blob/master/images/customers.png)

### Modify Customer

![Modify Customer Screen](https://github.com/meagerfindings/Software-2-Appointment-Scheduler/blob/master/images/modify_customer.png)

[[https://github.com/meagerfindings/Software-2-Appointment-Scheduler/blob/master/images/modify_customer.png|modifycustomerscreen]]

