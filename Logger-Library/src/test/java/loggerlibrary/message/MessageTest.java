package loggerlibrary.message;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @Test
    public void testMessageFormatting() {
        Message message = new Message("Test content", Level.INFO, "TestNamespace");

        // Actual message includes dynamic timestamp and tracking ID, so we verify the static parts.
        String formattedMessage = message.formatMessage();

        // Check that the formatted message contains the expected parts
        assertTrue(formattedMessage.contains("INFO"), "Message should contain the log level INFO");
        assertTrue(formattedMessage.contains("TestNamespace"), "Message should contain the namespace 'TestNamespace'");
        assertTrue(formattedMessage.contains("Test content"), "Message should contain the log content 'Test content'");
    }

    @Test
    public void testMessageLevel() {
        Message message = new Message("Test content", Level.ERROR, "ErrorNamespace");

        assertSame(message.getLevel(), Level.ERROR, "Message should have ERROR level");
        assertEquals("ErrorNamespace", message.getNamespace(), "Message should have 'ErrorNamespace'");
    }
}
