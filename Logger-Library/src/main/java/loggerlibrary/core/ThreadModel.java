package loggerlibrary.core;


/**
 * The ThreadModel enum defines the thread models used in the logger for handling log messages.
 * It specifies whether the logger should use single-threaded or multi-threaded execution.
 */
public enum ThreadModel {

    /**
     * SINGLE: The logger will use a single thread to handle all log messages.
     * This is suitable for applications where log messages are infrequent or low-volume,
     * and where thread-safety is not a major concern.
     */
    SINGLE,
    /**
     * MULTI: The logger will use multiple threads to handle log messages concurrently.
     * This is suitable for high-volume logging where performance is critical and multiple messages
     * need to be processed in parallel.
     */
    MULTI
}
