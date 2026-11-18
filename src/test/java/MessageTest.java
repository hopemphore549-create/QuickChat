/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testMessageID() {
        Message message = new Message(
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertTrue(message.checkMessageID());
        assertEquals(10, message.getMessageID().length());
    }

    @Test
    public void testValidRecipientNumber() {
        Message message = new Message(
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertEquals(
                "Cell phone number successfully captured.",
                message.checkRecipientCell()
        );
    }

    @Test
    public void testInvalidRecipientNumber() {
        Message message = new Message(
                "08575975889",
                "Hi Keegan, did you receive the payment?"
        );

        assertEquals(
                "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                message.checkRecipientCell()
        );
    }

    @Test
    public void testMessageLengthSuccess() {
        Message message = new Message(
                "+27718693002",
                "A".repeat(250)
        );

        assertEquals(
                "Message ready to send.",
                message.checkMessageLength()
        );
    }

    @Test
    public void testMessageLengthFailure() {
        Message message = new Message(
                "+27718693002",
                "A".repeat(251)
        );

        assertEquals(
                "Message exceeds 250 characters by 1; please reduce the size.",
                message.checkMessageLength()
        );
    }

    @Test
    public void testMessageHashFormat() {
        Message message = new Message(
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        String expectedHash =
                message.getMessageID().substring(0, 2)
                + ":" + message.getMessageNumber()
                + ":HITONIGHT";

        assertEquals(expectedHash, message.createMessageHash());
    }

    @Test
    public void testSendMessage() {
        Message message = new Message(
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertEquals(
                "Message successfully sent.",
                message.SentMessage(1)
        );

        assertEquals("Sent", message.getStatus());
    }

    @Test
    public void testDisregardMessage() {
        Message message = new Message(
                "+27718693002",
                "Hi Keegan, did you receive the payment?"
        );

        assertEquals(
                "Press 0 to delete the message.",
                message.SentMessage(2)
        );

        assertEquals("Disregarded", message.getStatus());
    }

    @Test
    public void testStoreMessage() {
        Message message = new Message(
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?"
        );

        assertEquals(
                "Message successfully stored.",
                message.SentMessage(3)
        );

        assertEquals("Stored", message.getStatus());
    }

    @Test
    public void testInvalidMessageOption() {
        Message message = new Message(
                "+27718693002",
                "Hello"
        );

        assertEquals(
                "Invalid option.",
                message.SentMessage(4)
        );
    }
    
    // ===== PART 3 TESTS =====

    private void loadTestData() {
        Message.resetArrays();
        Message.addToArrays(new Message("0000000001", 1, "+27834557896",
                "Did you get the cake?", "Sent"));
        Message.addToArrays(new Message("0000000002", 2, "+27838884567",
                "Where are you? You are late! I have asked you to be on time.", "Stored"));
        Message.addToArrays(new Message("0000000003", 3, "+27834484567",
                "Yohoooo, I am at your gate.", "Disregarded"));
        Message.addToArrays(new Message("0000000004", 4, "0838884567",
                "It is dinner time!", "Sent"));
        Message.addToArrays(new Message("0000000005", 5, "+27838884567",
                "Ok, I am leaving without you.", "Stored"));
    }

    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        loadTestData();

        ArrayList<String> actual = new ArrayList<>();
        for (Message m : Message.sentMessages) {
            actual.add(m.getMessageText());
        }

        ArrayList<String> expected = new ArrayList<>(
                Arrays.asList("Did you get the cake?", "It is dinner time!"));

        assertEquals(expected, actual);
    }

    @Test
    public void testDisplayLongestMessage() {
        loadTestData();

        assertEquals(
                "Where are you? You are late! I have asked you to be on time.",
                Message.displayLongestMessage());
    }

    @Test
    public void testSearchForMessageID() {
        loadTestData();

        assertEquals("It is dinner time!",
                Message.searchByMessageID("0000000004"));
        assertEquals("0838884567",
                Message.findRecipientByID("0000000004"));
    }

    @Test
    public void testSearchMessagesForRecipient() {
        loadTestData();

        ArrayList<String> expected = new ArrayList<>(Arrays.asList(
                "Where are you? You are late! I have asked you to be on time.",
                "Ok, I am leaving without you."));

        assertEquals(expected, Message.searchByRecipient("+27838884567"));
    }

    @Test
    public void testDeleteMessageUsingHash() {
        loadTestData();

        assertEquals(
                "Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.",
                Message.deleteByHash("00:2:WHERETIME"));
    }

    @Test
    public void testDisplayReport() {
        loadTestData();

        String report = Message.displayReport();

        assertTrue(report.contains("Message Hash: 00:1:DIDCAKE"));
        assertTrue(report.contains("Recipient: +27834557896"));
        assertTrue(report.contains("Message: Did you get the cake?"));
    }
}
