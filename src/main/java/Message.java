/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author hopem
 */
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class Message {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final List<Message> messages = new ArrayList<>();
    private static int nextMessageNumber = 0;
    private static int totalMessagesSent = 0;

    private final String messageID;
    private final int messageNumber;
    private final String recipient;
    private final String messageText;
    private final String messageHash;
    private String status;

    public Message(String recipient, String messageText) {
        this.messageID = String.format("%010d",
                RANDOM.nextLong(10_000_000_000L));
        this.messageNumber = nextMessageNumber++;
        this.recipient = recipient == null ? "" : recipient.trim();
        this.messageText = messageText == null ? "" : messageText.trim();
        this.messageHash = createMessageHash();
        this.status = "Pending";
    }

    public boolean checkMessageID() {
        return messageID.matches("\\d{1,10}");
    }

    public String checkRecipientCell() {
        // South African international format, e.g. +27718693002.
        if (recipient.matches("^\\+27[1-9]\\d{8}$")) {
            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    public String checkMessageLength() {
        if (messageText.length() <= 250) {
            return "Message ready to send.";
        }

        return "Message exceeds 250 characters by "
                + (messageText.length() - 250)
                + "; please reduce the size.";
    }

    public String createMessageHash() {
        String firstTwoIDDigits = messageID.substring(0, 2);

        String trimmed = messageText.trim();
        String[] words = trimmed.isEmpty()
                ? new String[0] : trimmed.split("\\s+");

        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 0
                ? words[words.length - 1] : "";

        // Remove punctuation such as ? , . ! so "tonight?" becomes "tonight"
        firstWord = firstWord.replaceAll("[^A-Za-z0-9]", "");
        lastWord = lastWord.replaceAll("[^A-Za-z0-9]", "");

        return (firstTwoIDDigits + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
    }

    // 1 = Send, 2 = Disregard, 3 = Store.
    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                if (!checkMessageID()
                        || !checkRecipientCell().equals(
                                "Cell phone number successfully captured.")
                        || messageText.length() > 250) {
                    return "Message could not be sent. Check the message details.";
                }

                status = "Sent";
                messages.add(this);
                totalMessagesSent++;
                return "Message successfully sent.";

            case 2:
                status = "Disregarded";
                return "Press 0 to delete the message.";

            case 3:
                if (!checkMessageID()
                        || !checkRecipientCell().equals(
                                "Cell phone number successfully captured.")
                        || messageText.length() > 250) {
                    return "Message could not be stored. Check the message details.";
                }

                status = "Stored";
                messages.add(this);

                if (!storeMessage()) {
                    messages.remove(this);
                    status = "Pending";
                    return "Message could not be stored.";
                }

                return "Message successfully stored.";

            default:
                return "Invalid option.";
        }
    }

    public String printMessages() {
        StringBuilder result = new StringBuilder();

        for (Message message : messages) {
            result.append(message).append("\n\n");
        }

        return result.toString();
    }

    public int returnTotalMessages() {
        return totalMessagesSent;
    }

    // Alternative spelling sometimes used in assignment instructions.
    public int returnTotalMessagess() {
        return returnTotalMessages();
    }

    public boolean storeMessage() {
        Path file = Path.of("messages.json");

        String jsonObject = "{"
                + "\"messageID\":\"" + escape(messageID) + "\","
                + "\"messageHash\":\"" + escape(messageHash) + "\","
                + "\"recipient\":\"" + escape(recipient) + "\","
                + "\"message\":\"" + escape(messageText) + "\","
                + "\"status\":\"" + escape(status) + "\""
                + "}";

        try {
            String existing = Files.exists(file)
                    ? Files.readString(file, StandardCharsets.UTF_8).trim()
                    : "";

            if (existing.isEmpty()) {
                Files.writeString(file, "[" + jsonObject + "]",
                        StandardCharsets.UTF_8);
            } else {
                if (!existing.startsWith("[") || !existing.endsWith("]")) {
                    return false;
                }

                String contents = existing.substring(
                        1, existing.length() - 1).trim();

                String updated = contents.isEmpty()
                        ? "[" + jsonObject + "]"
                        : "[" + contents + "," + jsonObject + "]";

                Files.writeString(file, updated, StandardCharsets.UTF_8);
            }

            return true;
        } catch (IOException e) {
            System.out.println("JSON storage error: " + e.getMessage());
            return false;
        }
    }

    private String escape(String value) {
        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    public String getMessageID() {
        return messageID;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + messageText;
    }
}