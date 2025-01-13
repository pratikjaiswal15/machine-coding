package loggerlibrary.sink;

import loggerlibrary.message.Message;

/**
 * Sink is an interface that represents a destination for log messages.
 * Any class that implements this interface can be used as a logging sink,
 * such as writing to a file, database, console, or external services like AWS S3.
 */
public interface Sink {

    /**
     * Log a message to the sink. Each implementing class defines how the message is handled.
     *
     * @param message The log message to be written to the sink.
     */
    void logMessage(Message message);
}

