package loggerlibrary;

import loggerlibrary.core.*;
import loggerlibrary.message.Level;
import loggerlibrary.sink.AwsS3Sink;
import loggerlibrary.sink.ConsoleSink;
import loggerlibrary.sink.FileSink;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        final String customFilePath = "logs/custom/application.log";
        final String awsS3Bucket = "MyBucket";
        final String awsS3Object = "logs/";
        final long maxFileSize = 5 * 1024;  // 5 mb file size for rotation
        final int maxBackupFiles = 3;

        // Option 1: Use the default logger configuration (lazy initialization)
        Logger logger = Logger.getInstance();
        logger.debug("Default logger: Debugging", "UserService");
        logger.info("Default logger: Application started", "MainService");
        logger.warn("Default logger: Low disk space", "DiskService");
        logger.error("Default logger: Error connecting database", "DatabaseService");
        logger.fatal("Default logger: System broke", "HealthService");

        // Option 2: Custom logger configuration (custom sinks for error level)
        LoggerConfig customConfig = new LoggerConfigBuilder()
                .addSinks(Level.INFO, Arrays.asList(new ConsoleSink(), new FileSink(customFilePath, maxFileSize, maxBackupFiles)))  // Custom file & console for INFO
                .addSinks(Level.ERROR, Arrays.asList(new FileSink(customFilePath, maxFileSize, maxBackupFiles), new AwsS3Sink(awsS3Bucket, awsS3Object)))  // Custom file & S3 for ERROR
                .setThreadModel(ThreadModel.SINGLE)  // Single-threaded model
                .setWriteMode(WriteMode.SYNC)  // Synchronous logging
                .build();

        // Re-initialize the logger with custom configuration
        Logger.initialize(customConfig);

        // Use the logger to log many messages to trigger rotation
        logger = Logger.getInstance();

//        for (int i = 0; i < 100; i++) {
//            logger.info("Test log message #" + i, "MainService");
//        }

        // Shutdown the logger
        logger.shutdown();
    }
}

