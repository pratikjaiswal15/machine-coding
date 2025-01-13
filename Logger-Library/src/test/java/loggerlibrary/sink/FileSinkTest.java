package loggerlibrary.sink;

import loggerlibrary.message.Message;
import loggerlibrary.message.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileSinkTest {

    private static final String TEST_FILE_PATH = "logs/test/test_application.log";
    private FileSink fileSink;

    @BeforeEach
    public void setUp() {
        // Set a small maxFileSize to trigger rotation quickly (1KB)
        fileSink = new FileSink(TEST_FILE_PATH, 1024, 2);  // 1KB rotation limit, 2 backups
    }

    @Test
    public void testLogRotation() throws IOException, InterruptedException {
        // Create a test message
        Message message = new Message("Test log message", Level.INFO, "TestService");

        // Write multiple messages to exceed the rotation size
        for (int i = 0; i < 200; i++) {
            fileSink.logMessage(message);
        }

        // Wait more time to allow file compression to finish
        TimeUnit.SECONDS.sleep(5);

        // Verify the main log file still exists
        File logFile = new File(TEST_FILE_PATH);
        assertTrue(Files.exists(logFile.toPath()), "Main log file should still exist");

        // Verify the rotated log files exist (compressed and uncompressed versions)
        Path rotatedFile1 = Path.of(TEST_FILE_PATH + ".1.gz");
        Path rotatedFile2 = Path.of(TEST_FILE_PATH + ".2.gz");

        assertTrue(Files.exists(rotatedFile1), "Rotated log file .1.gz should exist");
        assertTrue(Files.exists(rotatedFile2), "Rotated log file .2.gz should exist");
    }

    @Test
    public void testLogFileCreation() {
        Message message = new Message("Test log file creation", Level.INFO, "TestService");
        fileSink.logMessage(message);

        File logFile = new File(TEST_FILE_PATH);
        assertTrue(logFile.exists(), "Log file should be created");
    }
}
