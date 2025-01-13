package loggerlibrary.core;

import loggerlibrary.message.Level;
import loggerlibrary.sink.Sink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.mockito.Mockito.*;

public class LoggerLevelFilteringTest {

    private Logger logger;
    private Sink mockConsoleSink;
    private Sink mockFileSink;

    @BeforeEach
    public void setUp() {
        mockConsoleSink = mock(Sink.class);
        mockFileSink = mock(Sink.class);

        LoggerConfig config = new LoggerConfigBuilder()
                .addSinks(Level.INFO, Arrays.asList(mockConsoleSink, mockFileSink))
                .addSinks(Level.ERROR, Arrays.asList(mockFileSink))
                .build();

        Logger.initialize(config);
        logger = Logger.getInstance();
    }

    @Test
    public void testLogMessageRoutedAtInfoLevel() {
        logger.info("Info message", "TestService");

        // Only INFO level sinks should be invoked
        verify(mockConsoleSink, times(1)).logMessage(any());
        verify(mockFileSink, times(1)).logMessage(any());
    }

    @Test
    public void testLogMessageRoutedAtErrorLevel() {
        logger.error("Error message", "ErrorService");

        // Only ERROR level sinks should be invoked
        verify(mockConsoleSink, never()).logMessage(any());
        verify(mockFileSink, times(1)).logMessage(any());
    }
}
