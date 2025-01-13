package loggerlibrary.core;

import loggerlibrary.message.Level;
import loggerlibrary.sink.Sink;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LoggerConfigBuilder is a builder class that helps construct a LoggerConfig object.
 * It allows setting up multiple sinks for different log levels, and configuring the threading model and write mode.
 */
public class LoggerConfigBuilder {

    private Map<Level, List<Sink>> levelToSinksMap = new HashMap<>();  // Map log levels to sinks
    private ThreadModel threadModel = ThreadModel.MULTI;  // Default thread model is multi-threaded
    private WriteMode writeMode = WriteMode.ASYNC;  // Default write mode is asynchronous

    /**
     * Adds sinks to a specific log level.
     *
     * @param level The log level (DEBUG, INFO, WARN, ERROR, FATAL).
     * @param sinks The list of sinks (FileSink, ConsoleSink, etc.) to be tied to the specified log level.
     * @return The LoggerConfigBuilder instance to allow method chaining.
     */
    public LoggerConfigBuilder addSinks(Level level, List<Sink> sinks) {
        levelToSinksMap.put(level, sinks);
        return this;
    }

    /**
     * Sets the threading model for the logger (SINGLE or MULTI).
     *
     * @param threadModel The thread model to use (SINGLE or MULTI).
     * @return The LoggerConfigBuilder instance to allow method chaining.
     */
    public LoggerConfigBuilder setThreadModel(ThreadModel threadModel) {
        this.threadModel = threadModel;
        return this;
    }

    /**
     * Sets the write mode for the logger (SYNC or ASYNC).
     *
     * @param writeMode The write mode to use (SYNC or ASYNC).
     * @return The LoggerConfigBuilder instance to allow method chaining.
     */
    public LoggerConfigBuilder setWriteMode(WriteMode writeMode) {
        this.writeMode = writeMode;
        return this;
    }

    /**
     * Builds and returns the LoggerConfig object with the specified configuration.
     *
     * @return A new LoggerConfig object with the set configurations (log level to sinks, threading model, and write mode).
     */
    public LoggerConfig build() {
        return new LoggerConfig(levelToSinksMap, threadModel, writeMode);
    }
}

