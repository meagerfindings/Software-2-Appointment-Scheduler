# Advanced Java Concepts Course Submission

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
