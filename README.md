# QuickChat - Part 3

## Student Information
Name: Hope Mamphadile Mokoena
Student Number: ST10478149
Module: Programming 

## About the app
QuickChat is a Java console app. A user registers and logs in, then can send, store or disregard messages. Part 3 adds arrays to keep the messages and a "Stored Messages" menu to work with them.

## Features
- Registration and login (Part 1)
- Send, disregard or store a message, with a message ID and message hash (Part 2)
- Stored messages are saved in messages.json using the Gson library
- Part 3 arrays, filled by the user with no hard-coding:
  - Sent messages
  - Disregarded messages
  - Stored messages (read from the JSON file)
  - Message hashes
  - Message IDs

## Main menu
1) Send Messages
2) Show recently sent messages
3) Quit
4) Stored Messages

## Stored Messages menu
a) Display the sender and recipient of all stored messages
b) Display the longest stored message
c) Search for a message ID and show its recipient and message
d) Search all messages for a recipient
e) Delete a message using its message hash
f) Display a report of all messages
g) Back to the main menu

## How to run
1. Open the project in NetBeans.
2. Right-click Main.java and choose Run File.
3. Register, log in, then choose a menu option.

## Unit tests
The tests are in MessageTest.java (JUnit 5). They cover Parts 1 to 3, including:
- Sent messages array correctly populated
- Display the longest message
- Search for a message ID
- Search messages for a recipient
- Delete a message using a message hash
- Display the report

The tests run automatically on GitHub Actions every time code is pushed.
