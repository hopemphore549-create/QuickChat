/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

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
}