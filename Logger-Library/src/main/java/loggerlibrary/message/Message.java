package loggerlibrary.message;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Message class represents a log message containing various components such as content, log level,
 * namespace, timestamp, and a unique tracking ID. It formats the log message for output.
 */
public class Message {

    private String content;  // The actual log message content
    private Level level;  // The severity level of the log (DEBUG, INFO, WARN, ERROR, FATAL)
    private String namespace;  // The part of the application sending the log message
    private LocalDateTime timestamp;  // The time the log message was generated
    private String trackingId;  // A unique identifier for tracking the message

    /**
     * Constructs a new Message with the specified content, log level, and namespace.
     * Automatically captures the current timestamp and generates a unique tracking ID.
     *
     * @param content The actual content of the log message.
     * @param level The log level associated with this message (DEBUG, INFO, WARN, ERROR, FATAL).
     * @param namespace The namespace or component in the application that generated the log message.
     */
    public Message(String content, Level level, String namespace) {
        this.content = content;
        this.level = level;
        this.namespace = namespace;
        this.timestamp = LocalDateTime.now();  // Automatically sets the current timestamp
        this.trackingId = generateTrackingId();  // Generate a unique tracking ID at runtime
    }

    /**
     * Generates a unique tracking ID for the log message.
     *
     * @return A unique identifier generated using UUID.
     */
    private String generateTrackingId() {
        return UUID.randomUUID().toString();  // Generates a random UUID as a tracking ID
    }

    /**
     * Formats the log message by adding the timestamp, tracking ID, log level, and other details.
     * The timestamp follows the format "yyyy-MM-dd HH:mm:ss,SSS".
     *
     * @return The formatted log message string, including level, timestamp, tracking ID, namespace, and content.
     */
    public String formatMessage() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS");
        String formattedTimestamp = this.timestamp.format(formatter);

        return String.format("%s [%s] %s %s - %s", level, formattedTimestamp, trackingId, namespace, content);
    }

    // Getters for accessing the individual properties of the message

    public Level getLevel() {
        return level;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}

