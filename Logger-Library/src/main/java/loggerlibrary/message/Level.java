package loggerlibrary.message;

/**
 * Level defines the different log levels used in the logger library.
 * Each level represents the severity of the log message.
 */
public enum Level {


    /** DEBUG level: Used for fine-grained informational events useful for debugging. */
    DEBUG(5) ,

    /** INFO level: General informational messages that highlight the progress of the application. */
    INFO(4),

    /** WARN level: Indicates a potential problem or unexpected situation in the application. */
    WARN(3),

    /** ERROR level: Error events that might allow the application to continue running. */
    ERROR(2),

    /** FATAL level: Severe error events that likely lead to the termination of the application. */
    FATAL(1);

    private int level;
    Level(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}

