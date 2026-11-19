/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author hopem
 */
import java.util.ArrayList;
import java.util.Scanner;

public class StoredMessageMenu {
// Shows the Stored Messages menu and calls the method for the chosen option

    public static void show(Scanner scanner) {

        // Read messages.json into the storedMessages array
        Message.loadStoredFromJson();

        String option = "";

        while (!option.equals("g")) {
            System.out.println();
            System.out.println("===== STORED MESSAGES =====");
            System.out.println("a) Display sender and recipient of all stored messages");
            System.out.println("b) Display the longest stored message");
            System.out.println("c) Search for a message ID");
            System.out.println("d) Search all messages for a recipient");
            System.out.println("e) Delete a message using its hash");
            System.out.println("f) Display report of all messages");
            System.out.println("g) Back to main menu");
            System.out.print("Choose an option: ");

            option = scanner.nextLine().trim().toLowerCase();

            switch (option) {
                case "a":
                    System.out.println(Message.displaySenderAndRecipient());
                    break;

                case "b":
                    System.out.println("Longest stored message: "
                            + Message.displayLongestMessage());
                    break;

                case "c":
                    System.out.print("Enter the message ID: ");
                    String id = scanner.nextLine().trim();
                    System.out.println("Recipient: " + Message.findRecipientByID(id));
                    System.out.println("Message: " + Message.searchByMessageID(id));
                    break;

                case "d":
                    System.out.print("Enter the recipient number: ");
                    String recipient = scanner.nextLine().trim();
                    ArrayList<String> found = Message.searchByRecipient(recipient);
                    if (found.isEmpty()) {
                        System.out.println("No messages found for that recipient.");
                    }
                    for (String text : found) {
                        System.out.println(text);
                    }
                    break;

                case "e":
                    System.out.print("Enter the message hash: ");
                    String hash = scanner.nextLine().trim().toUpperCase();
                    System.out.println(Message.deleteByHash(hash));
                    Message.saveStoredToJson();
                    break;

                case "f":
                    System.out.println(Message.displayReport());
                    break;

                case "g":
                    break;

                default:
                    System.out.println("Invalid option. Please choose a to g.");
            }
        }
    }
}
