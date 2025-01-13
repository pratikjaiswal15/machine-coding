package loggerlibrary.core;

import loggerlibrary.message.Level;
import loggerlibrary.message.Message;
import loggerlibrary.sink.Sink;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Logger is a Singleton class responsible for logging messages to various sinks (e.g., file, console, database, AwsS3).
 * It supports multi-threaded and asynchronous logging, as well as synchronous, single-threaded logging.
 */
public class Logger {

    private LoggerConfig config;  // Holds logger configuration settings
    private ExecutorService executorService;  // Manages thread execution for asynchronous logging

    /**
     * Private constructor to initialize the logger with the provided configuration.
     * It initializes the executor service based on the thread model (single-threaded or multi-threaded).
     *
     * @param config LoggerConfig object that holds settings for log levels, sinks, and execution modes.
     */
    private Logger(LoggerConfig config) {
        this.config = config;
        initializeExecutor();
    }

    /**
     * Initializes the executor based on the thread model (single-thread or multi-thread).
     * This determines how log messages are processed (in a single or multiple threads).
     */
    private void initializeExecutor() {
        if ("MULTI".equalsIgnoreCase(String.valueOf(config.getThreadModel()))) {
            this.executorService = Executors.newFixedThreadPool(5);  // Multi-threaded mode
            // this.executorService = Executors.newCachedThreadPool();  // Multi-threaded mode

        } else {
            this.executorService = Executors.newSingleThreadExecutor();  // Single-threaded mode
        }
    }

    /**
     * Inner static class for lazy-loading the Singleton instance of Logger.
     */
    private static class LoggerHelper {
        private static Logger INSTANCE;

        /**
         * Provides the default logger instance with default configuration if no custom configuration is provided.
         *
         * @return The singleton Logger instance.
         */
        private static Logger getInstance() {
            if (INSTANCE == null) {
                INSTANCE = new Logger(LoggerFactory.getDefaultConfig());
            }
            return INSTANCE;
        }

        /**
         * Resets the logger with a new configuration and shuts down any existing executor service.
         *
         * @param config The custom LoggerConfig to reset the logger with.
         */
        private static void resetInstance(LoggerConfig config) {
            if (INSTANCE != null) {
                INSTANCE.shutdown();  // Clean up the previous instance's executor
            }
            INSTANCE = new Logger(config);
        }
    }

    /**
     * Initializes or resets the Logger with a custom configuration.
     *
     * @param customConfig The custom LoggerConfig object to use for the logger.
     */
    public static void initialize(LoggerConfig customConfig) {
        LoggerHelper.resetInstance(customConfig);
    }

    /**
     * Returns the singleton Logger instance. Initializes with default configuration if not initialized.
     *
     * @return The current logger instance (default or custom).
     */
    public static Logger getInstance() {
        return LoggerHelper.getInstance();
    }

    /**
     * Logs a message to the appropriate sinks based on the provided log level.
     * The message is processed either synchronously or asynchronously depending on the configuration.
     *
     * @param level The log level (INFO, DEBUG, WARN, ERROR, FATAL).
     * @param messageContent The content of the message.
     * @param namespace The part of the application (namespace) sending the message.
     */
    public void log(Level level, String messageContent, String namespace) {

        System.out.println("ordinal" + level.ordinal() + " level " + level);
        if(level.getLevel() >= level.ordinal()) {

        }
        List<Sink> sinks = config.getSinksForLevel(level);  // Get the sinks associated with the log level


        if (sinks != null) {
            Message message = new Message(messageContent, level, namespace);  // Create a Message object

            for (Sink sink : sinks) {
                Runnable logTask = () -> sink.logMessage(message);  // Define the logging task

                if ("ASYNC".equalsIgnoreCase(String.valueOf(config.getWriteMode()))) {
                    executorService.submit(logTask);  // Run asynchronously
                } else {
                    logTask.run();  // Run synchronously
                }
            }
        }
    }

    /**
     * Logs a message with INFO level.
     *
     * @param message The content of the message.
     * @param namespace The part of the application (namespace) sending the message.
     */
    public void info(String message, String namespace) {
        log(Level.INFO, message, namespace);
    }

    /**
     * Logs a message with WARN level.
     *
     * @param message The content of the message.
     * @param namespace The part of the application (namespace) sending the message.
     */
    public void warn(String message, String namespace) {
        log(Level.WARN, message, namespace);
    }

    /**
     * Logs a message with ERROR level.
     *
     * @param message The content of the message.
     * @param namespace The part of the application (namespace) sending the message.
     */
    public void error(String message, String namespace) {
        log(Level.ERROR, message, namespace);
    }

    /**
     * Logs a message with FATAL level.
     *
     * @param message The content of the message.
     * @param namespace The part of the application (namespace) sending the message.
     */
    public void fatal(String message, String namespace) {
        log(Level.FATAL, message, namespace);
    }

    /**
     * Logs a message with DEBUG level.
     *
     * @param message The content of the message.
     * @param namespace The part of the application (namespace) sending the message.
     */
    public void debug(String message, String namespace) {
        log(Level.DEBUG, message, namespace);
    }

    /**
     * Shuts down the logger and terminates the executor service.
     * Waits up to 5 seconds for any remaining tasks to complete before forcing shutdown.
     */
    public void shutdown() {
        if (executorService != null) {
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();  // Force shutdown if not terminated in 5 seconds
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
            }
        }
    }
}

