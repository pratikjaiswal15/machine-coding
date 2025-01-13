package loggerlibrary.core;

import loggerlibrary.message.Level;
import loggerlibrary.sink.ConsoleSink;
import loggerlibrary.sink.DatabaseSink;
import loggerlibrary.sink.FileSink;
import loggerlibrary.sink.AwsS3Sink;

import java.util.Arrays;

/**
 * LoggerFactory is responsible for creating the default LoggerConfig.
 * It defines the default logging behavior, including the sinks for each log level and other configuration options.
 */
public class LoggerFactory {

    // Static variables for sink configurations
    private static final String filePath = "logs/application.log";  // Default file path for logs
    private static final String dbHost = "localHost";  // Default database host
    private static final String dbPort = "8083";  // Default database port
    private static final String awsS3Bucket = "log-bucket";  // Default AWS S3 bucket name
    private static final String awsS3Objet = "logs";  // Default AWS S3 object path
    private static final long maxFileSize = 5 * 1024 * 1024;  // 5 MB file size limit for log rotation
    private static final int maxBackupFiles = 5;  // Maximum number of backup log files

    /**
     * Creates and returns the default LoggerConfig with predefined settings for various log levels.
     * The configuration includes multiple sinks for each level, thread model, and write mode.
     *
     * @return A LoggerConfig object with the default configuration:
     * - DEBUG: ConsoleSink
     * - INFO: ConsoleSink and FileSink
     * - WARN: ConsoleSink and FileSink
     * - ERROR: ConsoleSink and DatabaseSink
     * - FATAL: ConsoleSink, DatabaseSink, and AwsS3Sink
     * - Thread model: MULTI (multi-threaded)
     * - Write mode: ASYNC (asynchronous)
     */
    public static LoggerConfig getDefaultConfig() {
        return new LoggerConfigBuilder()
                .addSinks(Level.DEBUG, Arrays.asList(new ConsoleSink()))  // DEBUG logs go to console
                .addSinks(Level.INFO, Arrays.asList(new ConsoleSink(), new FileSink(filePath, maxFileSize, maxBackupFiles)))  // INFO logs go to console and file
                .addSinks(Level.WARN, Arrays.asList(new ConsoleSink(), new FileSink(filePath, maxFileSize, maxBackupFiles)))  // WARN logs go to console and file
                .addSinks(Level.ERROR, Arrays.asList(new ConsoleSink(), new DatabaseSink(dbHost, dbPort)))  // ERROR logs go to console and database
                .addSinks(Level.FATAL, Arrays.asList(new ConsoleSink(), new DatabaseSink(dbHost, dbPort), new AwsS3Sink(awsS3Bucket, awsS3Objet)))  // FATAL logs go to console, database, and AWS S3
                .setThreadModel(ThreadModel.MULTI)  // Use multi-threaded logging by default
                .setWriteMode(WriteMode.ASYNC)  // Use asynchronous logging by default
                .build();
    }
}

