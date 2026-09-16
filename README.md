# Chat App - Part 1

## Student Information

**Student Number:** ST10478149

## Project Description

This project is a console-based Chat App developed for Part 1 of the
Programming assignment. The application implements user registration
and login functionality.

## Registration

The application allows a user to register by entering:

- First name
- Last name
- Username
- Password
- South African cellphone number

The application validates the username, password and cellphone number
according to the assignment requirements.

## Login

The application allows a registered user to log in using their username
and password.

The application displays an appropriate message when the login is
successful or unsuccessful.

## Testing

JUnit tests are used to test the registration and login functionality.

The tests include valid and invalid:

- Username
- Password
- Cellphone number
- Login details

## Technologies Used

- Java
- Maven
- NetBeans
- JUnit 5
- Git
- GitHub

## How to Run

1. Open the project in NetBeans.
2. Build the Maven project.
3. Run `Main.java`.
4. Follow the instructions displayed in the console.

## Project Files

### Login.java
Contains the registration validation and login functionality.

### Main.java
Runs the console-based application and allows the user to enter
registration and login information.

### LoginTest.java
Contains JUnit tests for the registration and login functionality.

### pom.xml
Contains the Maven project configuration and JUnit dependency.