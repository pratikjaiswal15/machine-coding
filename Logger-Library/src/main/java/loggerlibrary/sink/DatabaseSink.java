package loggerlibrary.sink;

import loggerlibrary.message.Message;

/**
 * DatabaseSink is an implementation of the Sink interface that logs messages to a database.
 */
public class DatabaseSink implements Sink {

    private final String dbHost;
    private final String dbPort;

    /**
     * Constructor to initialize the DatabaseSink with host and port.
     *
     * @param dbHost The database host address.
     * @param dbPort The database port number.
     */
    public DatabaseSink(String dbHost, String dbPort) {
        this.dbHost = dbHost;
        this.dbPort = dbPort;
    }

    /**
     * Logs the message to the database by simulating an insertion process.
     *
     * @param message The message to be logged in the database.
     */
    @Override
    public void logMessage(Message message) {
        // Simulate a database insert
        System.out.println("Inserting into DB (" + dbHost + ":" + dbPort + "):");
        // Output the formatted log message as if it's being inserted into the DB
        System.out.println(message.formatMessage());
    }
}
