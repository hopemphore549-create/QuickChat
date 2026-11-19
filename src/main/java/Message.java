import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// The Message class stores one message and holds the arrays
// and methods used by the Stored Messages menu (Part 3).
public class Message {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final List<Message> messages = new ArrayList<>();
    private static int nextMessageNumber = 0;
    private static int totalMessagesSent = 0;

    // ===== The 5 arrays from the task =====
    public static final ArrayList<Message> sentMessages = new ArrayList<>();
    public static final ArrayList<Message> disregardedMessages = new ArrayList<>();
    public static final ArrayList<Message> storedMessages = new ArrayList<>();
    public static final ArrayList<String> messageHashes = new ArrayList<>();
    public static final ArrayList<String> messageIDs = new ArrayList<>();

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

    // Second constructor: we give the ID, number and status ourselves.
    // Used for the test data and for reading messages back from the JSON file.
    public Message(String messageID, int messageNumber, String recipient,
            String messageText, String status) {
        this.messageID = messageID;
        this.messageNumber = messageNumber;
        this.recipient = recipient == null ? "" : recipient.trim();
        this.messageText = messageText == null ? "" : messageText.trim();
        this.messageHash = createMessageHash();
        this.status = status;
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
                addToArrays(this);
                return "Message successfully sent.";

            case 2:
                status = "Disregarded";
                addToArrays(this);
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

                addToArrays(this);
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

    // Adds this message to the end of messages.json (Part 2).
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

    // =====================================================================
    // PART 3 CODE STARTS HERE
    // =====================================================================

    // Puts a message into the right array (by its status)
    // and saves its hash and ID in their own arrays.
    public static void addToArrays(Message m) {
        if (m.status.equals("Sent")) {
            sentMessages.add(m);
        } else if (m.status.equals("Stored")) {
            storedMessages.add(m);
        } else if (m.status.equals("Disregarded")) {
            disregardedMessages.add(m);
        } else {
            return;
        }
        messageHashes.add(m.messageHash);
        messageIDs.add(m.messageID);
    }

    // Empties all the arrays (used by the unit tests).
    public static void resetArrays() {
        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();
        messageHashes.clear();
        messageIDs.clear();
        messages.clear();
    }

    // ATTRIBUTION: Reads messages.json into the storedMessages array.
    // The JSON parsing uses the Gson library (Google, 2024).
    // Gson User Guide: https://github.com/google/gson/blob/main/UserGuide.md
    // File reading uses java.nio.file.Files.readString (Oracle, 2024):
    // https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/nio/file/Files.html
    public static void loadStoredFromJson() {
        for (Message m : storedMessages) {
            messageHashes.remove(m.messageHash);
            messageIDs.remove(m.messageID);
        }
        storedMessages.clear();

        Path file = Path.of("messages.json");
        if (!Files.exists(file)) {
            return;
        }

        try {
            String json = Files.readString(file, StandardCharsets.UTF_8);
            if (json.isBlank()) {
                return;
            }

            JsonArray array = JsonParser.parseString(json).getAsJsonArray();
            for (JsonElement element : array) {
                JsonObject obj = element.getAsJsonObject();

                String savedHash = obj.get("messageHash").getAsString();
                int number = 0;
                try {
                    number = Integer.parseInt(savedHash.split(":")[1]);
                } catch (Exception e) {
                    number = 0;
                }

                Message m = new Message(
                        obj.get("messageID").getAsString(),
                        number,
                        obj.get("recipient").getAsString(),
                        obj.get("message").getAsString(),
                        "Stored");

                storedMessages.add(m);
                messageHashes.add(m.messageHash);
                messageIDs.add(m.messageID);
            }
        } catch (Exception e) {
            System.out.println("Could not read messages.json: " + e.getMessage());
        }
    }

    // ATTRIBUTION: Writes the storedMessages array back into messages.json.
    // The JSON writing uses the Gson library (Google, 2024).
    // Gson User Guide: https://github.com/google/gson/blob/main/UserGuide.md
    public static void saveStoredToJson() {
        List<Map<String, String>> list = new ArrayList<>();
        for (Message m : storedMessages) {
            Map<String, String> map = new LinkedHashMap<>();
            map.put("messageID", m.messageID);
            map.put("messageHash", m.messageHash);
            map.put("recipient", m.recipient);
            map.put("message", m.messageText);
            map.put("status", "Stored");
            list.add(map);
        }

        try {
            Files.writeString(Path.of("messages.json"),
                    new Gson().toJson(list), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("JSON storage error: " + e.getMessage());
        }
    }

    // a. Sender and recipient of all stored messages
    public static String displaySenderAndRecipient() {
        if (storedMessages.isEmpty()) {
            return "No stored messages.";
        }
        StringBuilder sb = new StringBuilder();
        for (Message m : storedMessages) {
            sb.append("Sender: You | Recipient: ").append(m.recipient).append("\n");
        }
        return sb.toString();
    }

    // b. Longest stored message
    public static String displayLongestMessage() {
        if (storedMessages.isEmpty()) {
            return "No stored messages.";
        }
        String longest = "";
        for (Message m : storedMessages) {
            if (m.messageText.length() > longest.length()) {
                longest = m.messageText;
            }
        }
        return longest;
    }

    // c. Search for a message ID: gives the message text
    public static String searchByMessageID(String id) {
        for (Message m : allMessages()) {
            if (m.messageID.equals(id)) {
                return m.messageText;
            }
        }
        return "Message ID not found.";
    }

    // c. Search for a message ID: gives the recipient
    public static String findRecipientByID(String id) {
        for (Message m : allMessages()) {
            if (m.messageID.equals(id)) {
                return m.recipient;
            }
        }
        return "Message ID not found.";
    }

    // d. All sent or stored messages for one recipient
    public static ArrayList<String> searchByRecipient(String recipient) {
        ArrayList<String> results = new ArrayList<>();
        for (Message m : sentMessages) {
            if (m.recipient.equals(recipient)) {
                results.add(m.messageText);
            }
        }
        for (Message m : storedMessages) {
            if (m.recipient.equals(recipient)) {
                results.add(m.messageText);
            }
        }
        return results;
    }

    // e. Delete a message using its hash
    public static String deleteByHash(String hash) {
        String result = removeFrom(storedMessages, hash);
        if (result == null) {
            result = removeFrom(sentMessages, hash);
        }
        if (result == null) {
            result = removeFrom(disregardedMessages, hash);
        }
        if (result == null) {
            result = "Message hash not found.";
        }
        return result;
    }

    private static String removeFrom(ArrayList<Message> list, String hash) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).messageHash.equals(hash)) {
                Message m = list.remove(i);
                messageHashes.remove(m.messageHash);
                messageIDs.remove(m.messageID);
                messages.remove(m);
                return "Message: \"" + m.messageText + "\" successfully deleted.";
            }
        }
        return null;
    }

    // f. Report with the full details
    public static String displayReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== MESSAGE REPORT =====\n\n");
        for (Message m : sentMessages) {
            sb.append(m).append("\nStatus: Sent\n\n");
        }
        for (Message m : storedMessages) {
            sb.append(m).append("\nStatus: Stored\n\n");
        }
        return sb.toString();
    }

    // Joins the three arrays so we can search all messages at once.
    private static List<Message> allMessages() {
        List<Message> all = new ArrayList<>();
        all.addAll(sentMessages);
        all.addAll(storedMessages);
        all.addAll(disregardedMessages);
        return all;
    }

    // =====================================================================
    // PART 3 CODE ENDS HERE
    // =====================================================================

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
