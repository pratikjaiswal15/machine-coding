package loggerlibrary.core;

import loggerlibrary.sink.Sink;
import loggerlibrary.message.Level;

import java.util.List;
import java.util.Map;

/**
 * LoggerConfig holds the configuration settings for the Logger.
 * It includes mappings between log levels and sinks, as well as options for the threading model and write mode.
 */
public class LoggerConfig {

    private final Map<Level, List<Sink>> levelToSinkMap;  // Maps log levels to sinks
    private final ThreadModel threadModel;  // Specifies the threading model (SINGLE or MULTI)
    private final WriteMode writeMode;  // Specifies the write mode (SYNC or ASYNC)

    /**
     * Constructor to initialize the Logger configuration.
     *
     * @param levelToSinkMap A map of log levels (DEBUG, INFO, etc.) to the list of sinks (FileSink, ConsoleSink, etc.) for that level.
     * @param threadModel The threading model to use (SINGLE or MULTI).
     * @param writeMode The write mode (SYNC or ASYNC) to specify whether logs are written synchronously or asynchronously.
     */
    public LoggerConfig(Map<Level, List<Sink>> levelToSinkMap, ThreadModel threadModel, WriteMode writeMode) {
        this.levelToSinkMap = levelToSinkMap;
        this.threadModel = threadModel;
        this.writeMode = writeMode;
    }

    /**
     * Retrieves the list of sinks associated with the provided log level.
     *
     * @param level The log level (DEBUG, INFO, WARN, ERROR, FATAL).
     * @return The list of sinks (FileSink, ConsoleSink, etc.) that are tied to the given log level.
     */
    public List<Sink> getSinksForLevel(Level level) {
//
//        Fatal - 1, console
//        Error - 2 , file
//        log.fatal = console, file
//        log.error = file
        return levelToSinkMap.get(level);
    }

    /**
     * Retrieves the threading model for the logger.
     * @return The thread model being used (SINGLE or MULTI).
     */
    public ThreadModel getThreadModel() {
        return threadModel;
    }

    /**
     * Retrieves the write mode for the logger.
     * @return The write mode being used (SYNC or ASYNC).
     */
    public WriteMode getWriteMode() {
        return writeMode;
    }
}

