package loggerlibrary.core;

import loggerlibrary.message.Level;
import loggerlibrary.message.Message;
import loggerlibrary.sink.Sink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class LoggerTest {

    private Sink mockSink;
    private Logger logger;

    @BeforeEach
    public void setUp() {
        // Create a mock sink
        mockSink = Mockito.mock(Sink.class);

        // Set up logger configuration with synchronous mode to ensure immediate log writing
        LoggerConfig config = new LoggerConfigBuilder()
                .addSinks(Level.INFO, Arrays.asList(mockSink))
                .addSinks(Level.ERROR, Arrays.asList(mockSink))
                .setThreadModel(ThreadModel.SINGLE)  // Use single-threaded model for simplicity in testing
                .setWriteMode(WriteMode.SYNC)       // Ensure logs are processed synchronously
                .build();

        // Initialize the logger with the custom configuration
        Logger.initialize(config);

        // Get the logger instance after initialization
        logger = Logger.getInstance();
    }

    @Test
    public void testLogMessageToSinks() {
        // Create a test message
        // Message testMessage = new Message("Test content", Level.INFO, "TestNamespace");

        // Call the logger to log a message
        logger.info("Test content", "TestNamespace");

        // Verify that the mock sink received the message
        verify(mockSink, times(1)).logMessage(Mockito.any(Message.class));
    }

    @Test
    public void testErrorMessageToSinks() {
        // Call the logger to log an error message
        logger.error("Error occurred", "ErrorService");

        // Verify that the mock sink received the error message
        verify(mockSink, times(1)).logMessage(Mockito.any(Message.class));
    }
}
