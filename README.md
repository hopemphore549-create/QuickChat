# QuickChat - Part 2

## Student Information
Student Number: ST10478149

## Project Description
QuickChat is a console-based chat app written in Java (NetBeans, Maven).
Part 1 handles user registration and login. Part 2 lets a logged-in user
send, discard or store messages.

## Features
- Only a user who has logged in successfully can use QuickChat.
- Welcome message: "Welcome to QuickChat."
- Menu: 1) Send Messages, 2) Show recently sent messages (Coming Soon), 3) Quit.
- The user chooses how many messages to enter.
- Each message gets a random 10-digit ID, a message number, a recipient
  with an international code, and text of up to 250 characters.
- A message hash is created, for example 00:0:HITONIGHT.
- The user can Send, Disregard or Store a message.
- Stored messages are saved in messages.json.
- The total number of sent messages is shown.

## Tests
JUnit 5 tests are in MessageTest.java. GitHub Actions runs them
automatically on every push (see the Actions tab).

## How to run
Open the project in NetBeans and run Main.java.
