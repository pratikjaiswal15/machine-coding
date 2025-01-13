package loggerlibrary.core;

/**
 * The WriteMode enum defines the modes of writing log messages in the logger.
 * It specifies whether the logger should handle log writes synchronously or asynchronously.
 */
public enum WriteMode {
    /**
     * SYNC: The logger will write log messages synchronously.
     * This means that the logger will wait for each log message to be written to the sink (e.g., file, console)
     * before proceeding to handle the next log message.
     *
     * Suitable for cases where consistency and ensuring that each log is immediately written is important.
     */
    SYNC,
    /**
     * ASYNC: The logger will write log messages asynchronously.
     * This means that the logger will queue log messages and write them in the background, allowing the application
     * to continue processing without waiting for log writes to complete.
     *
     * Suitable for high-performance applications where non-blocking logging is critical.
     */
    ASYNC
}
