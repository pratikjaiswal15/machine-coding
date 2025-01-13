package loggerlibrary.sink;

import loggerlibrary.message.Message;

/**
 * ConsoleSink is an implementation of the Sink interface that logs messages to the console.
 */
public class ConsoleSink implements Sink {

    /**
     * Logs the message by printing it to the console.
     *
     * @param message The message to be logged.
     */
    @Override
    public void logMessage(Message message) {
        // Print the formatted message to the console
        System.out.println(message.formatMessage());
    }
}
