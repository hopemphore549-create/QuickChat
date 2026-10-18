/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author hopem
 */
import java.util.Scanner;

public class QuickChat {

    public static void startAfterSuccessfulLogin(Scanner input) {

        System.out.println("Welcome to QuickChat.");

        int messageLimit;

        System.out.print("How many messages would you like to enter? ");

        try {
            messageLimit = Integer.parseInt(input.nextLine());

            if (messageLimit < 0) {
                System.out.println("Please enter a valid number.");
                return;
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
            return;
        }

        int messagesProcessed = 0;
        int totalSent = 0;

        while (true) {

            System.out.println("\n1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Choose an option: ");

            String option = input.nextLine();

            switch (option) {

                case "1":

                    if (messagesProcessed >= messageLimit) {
                        System.out.println(
                                "You have reached your message limit.");
                        break;
                    }

                    System.out.print(
                            "Enter recipient number with country code: ");
                    String recipient = input.nextLine();

                    System.out.print("Enter your message: ");
                    String text = input.nextLine();

                    Message newMessage = new Message(recipient, text);

                    System.out.println(
                            "Message ID generated: "
                            + newMessage.getMessageID());

                    if (!newMessage.checkRecipientCell().equals(
                            "Cell phone number successfully captured.")) {

                        System.out.println(
                                newMessage.checkRecipientCell());
                        break;
                    }

                    if (text.length() > 250) {
                        System.out.println(
                                newMessage.checkMessageLength());
                        break;
                    }

                    System.out.println("Message ready to send.");

                    System.out.println(
                            "Message Hash: "
                            + newMessage.getMessageHash());

                    System.out.println("\nMessage details:");
                    System.out.println(newMessage);

                    System.out.println("\n1) Send Message");
                    System.out.println("2) Disregard Message");
                    System.out.println("3) Store Message to send later");

                    System.out.print("Choose an option: ");

                    String action = input.nextLine();

                    try {
                        int choice = Integer.parseInt(action);

                        if (choice < 1 || choice > 3) {
                            System.out.println("Invalid option.");
                            break;
                        }

                        String result = newMessage.SentMessage(choice);
                        System.out.println(result);

                        if (choice == 1) {
                            totalSent++;
                            messagesProcessed++;

                            System.out.println(
                                    "Total messages sent: " + totalSent);

                        } else if (choice == 3
                                && result.equals(
                                        "Message successfully stored.")) {

                            messagesProcessed++;
                        }

                    } catch (NumberFormatException e) {
                        System.out.println(
                                "Please enter 1, 2, or 3.");
                    }

                    break;

                case "2":

                    System.out.println("Coming Soon.");
                    break;

                case "3":

                    System.out.println(
                            "Total messages sent: " + totalSent);
                    System.out.println("Goodbye!");
                    return;

                default:

                    System.out.println(
                            "Invalid option. Please try again.");
                    break;
            }
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        startAfterSuccessfulLogin(input);
    }
}