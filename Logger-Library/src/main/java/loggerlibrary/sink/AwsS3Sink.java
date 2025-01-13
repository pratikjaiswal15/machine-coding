package loggerlibrary.sink;

import loggerlibrary.message.Message;

/**
 * AwsS3Sink is an implementation of the Sink interface that logs messages to a AWS S3 bucket.
 */
public class AwsS3Sink implements Sink {

    private final String bucketName;
    private final String objectName;

    /**
     * Constructor for AwsS3Sink.
     *
     * @param bucketName The name of the S3 bucket where logs will be "uploaded".
     * @param objectName The object name (prefix path) in the S3 bucket. Defaults to "logs/" if not provided.
     */
    public AwsS3Sink(String bucketName, String objectName) {
        this.bucketName = bucketName;
        this.objectName = objectName != null ? objectName : "logs/";
    }

    /**
     * Simulates logging a message to AWS S3.
     *
     * Logs a formatted message that would be uploaded to S3, including the bucket and object name.
     *
     * @param message The message to be logged.
     */
    @Override
    public void logMessage(Message message) {
        // Simulate an S3 upload by printing a mock output
        String logOutput = String.format("Mock upload to S3 (Bucket: %s, Object: %s)", bucketName, objectName);
        System.out.println(logOutput);

        // Print the formatted log message to the console
        System.out.println(message.formatMessage());
    }
}
