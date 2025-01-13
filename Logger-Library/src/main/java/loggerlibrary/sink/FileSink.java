package loggerlibrary.sink;

import loggerlibrary.message.Message;

import java.io.*;
import java.nio.file.*;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.zip.GZIPOutputStream;

/**
 * FileSink is an implementation of the Sink interface that logs messages to a file.
 * It supports log rotation based on file size and compression of older log files.
 */
public class FileSink implements Sink {

    private final String filePath;
    private final long maxFileSize;  // Maximum file size in bytes before rotation
    private final int maxBackupFiles;  // Maximum number of backup log files to keep
    private final Object lock = new Object();  // Synchronization lock


    /**
     * Constructor to initialize FileSink with file path, max file size, and max backup files.
     *
     * @param filePath      The path of the log file.
     * @param maxFileSize   Maximum file size in bytes before the log rotates.
     * @param maxBackupFiles Maximum number of backup files to keep.
     */
    public FileSink(String filePath, long maxFileSize, int maxBackupFiles) {
        this.filePath = filePath;
        this.maxFileSize = maxFileSize;
        this.maxBackupFiles = maxBackupFiles;
        createLogFile();
    }

    /**
     * Creates the log file and ensures the parent directory exists.
     */
    private void createLogFile() {
        try {
            File logFile = new File(filePath);
            File parentDir = logFile.getParentFile();

            // Create directories if they don't exist
            if (parentDir != null && !parentDir.exists()) {
                System.out.println("Creating directory: " + parentDir.getAbsolutePath());
                parentDir.mkdirs();
            }

            // Create the file if it doesn't exist
            if (!logFile.exists()) {
                System.out.println("Creating log file: " + logFile.getAbsolutePath());
                logFile.createNewFile();
            }

        } catch (IOException e) {
            System.err.println("Error creating log file: " + e.getMessage());
        }
    }

    /**
     * Log a message to the file. If the file exceeds the max size, it triggers log rotation.
     *
     * @param message The log message to be written to the file.
     */
    @Override
    public void logMessage(Message message) {
        synchronized (lock) {
            try {
                File logFile = new File(filePath);


                // Check if the log file size exceeds the max size for rotation
                if (logFile.length() >= maxFileSize) {
                    System.out.println("File size exceeded, rotating logs...");
                    rotateLogFiles();  // Rotate the logs when file size is exceeded
                }

                // Write the log message
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                    writer.write(message.formatMessage());
                    writer.newLine();
                }

            } catch (IOException e) {
                System.err.println("Error writing to log file: " + e.getMessage());
            }
        }
    }

    /**
     * Rotates the log files when the current file exceeds the maximum size.
     * It renames the existing log files and compresses older files.
     *
     * @throws IOException if an error occurs during file rotation.
     */
    private void rotateLogFiles() throws IOException {
        synchronized (lock) {
            System.out.println("Rotating log files...");

            // Ensure the current log file is closed before rotating
            closeLogFile();

            // Get a list of existing log files in the directory that match the pattern (e.g., application.log.1)
            Path parentDir = Paths.get(filePath).getParent();
            System.out.println("Parent directory: " + parentDir);
            List<Path> logFiles = Files.list(parentDir)
                    .filter(path -> path.getFileName().toString().startsWith(Paths.get(filePath).getFileName().toString()))
                    .filter(path -> path.getFileName().toString().matches(".*\\.gz$|.*\\.\\d$"))
                    .sorted(Comparator.comparing(this::getLogFileIndex).reversed())  // Sort by index
                    .collect(Collectors.toList());


            System.out.println("All log files" + logFiles);
            // Debugging log to see the current log files to be rotated
            System.out.println("Found " + logFiles.size() + " log files to rotate.");

            // Shift log files by renaming them (e.g., application.log.1.gz -> application.log.2.gz)
            for (Path logFile : logFiles) {
                int index = getLogFileIndex(logFile);
                if (index < maxBackupFiles) {
                    System.out.println("Renaming: " + logFile + " to " + logFile.resolveSibling(getRotatedFileName(index + 1)));
                    moveLogFileWithRetry(logFile, logFile.resolveSibling(getRotatedFileName(index + 1)));
                } else {
                    System.out.println("Deleting old log file: " + logFile);
                    Files.delete(logFile);
                }
            }

            // Move the current log file to .1 and compress it
            Path currentLog = Paths.get(filePath);
            if (Files.exists(currentLog)) {
                System.out.println("Renaming current log file to .1 and compressing...");
                moveLogFileWithRetry(currentLog, currentLog.resolveSibling(getRotatedFileName(1)));  // Rename to .1
                compressLogFile(Paths.get(filePath + ".1"));  // Compress to .1.gz
            }

            // Create a new empty log file
            createLogFile();
        }
    }

    /**
     * Moves log files with retry logic in case of file access issues.
     *
     * @param source      The source file to be moved.
     * @param destination The destination where the source file will be moved.
     * @throws IOException if the file cannot be moved after retries.
     */
    private void moveLogFileWithRetry(Path source, Path destination) throws IOException {
        int attempts = 0;
        boolean success = false;

        while (attempts < 5 && !success) {  // Retry up to 5 times
            try {
                // Use atomic move for safety
                Files.move(source, destination, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Moved file: " + source + " to " + destination);
                success = true;
            } catch (IOException e) {
                attempts++;
                long waitTime = (long) Math.pow(2, attempts);  // Exponential backoff: 2^attempts seconds
                System.err.println("Error moving file: " + source + " -> " + destination + ", retrying (" + attempts + ") in " + waitTime + " seconds...");
                try {
                    TimeUnit.SECONDS.sleep(waitTime);  // Wait before retrying
                } catch (InterruptedException ignored) {
                }
            }
        }

        if (!success) {
            throw new IOException("Failed to move file: " + source + " -> " + destination);
        }
    }

    /**
     * Compresses a log file using GZIP compression.
     *
     * @param logFile The path to the log file to be compressed.
     * @throws IOException if an error occurs during file compression.
     */
    private void compressLogFile(Path logFile) throws IOException {
        System.out.println("Compressing log file: " + logFile);

        try (FileInputStream fis = new FileInputStream(logFile.toFile());
             FileOutputStream fos = new FileOutputStream(logFile.toFile() + ".gz");
             GZIPOutputStream gzip = new GZIPOutputStream(fos)) {

            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                gzip.write(buffer, 0, len);
            }

        } catch (IOException e) {
            System.err.println("Error compressing log file: " + e.getMessage());
        }

        // Delete the uncompressed file after compression
        Files.delete(logFile);
    }

    /**
     * Generates the rotated file name based on the file index.
     *
     * @param index The index of the rotated log file.
     * @return The file name for the rotated log.
     */
    private String getRotatedFileName(int index) {
        return Paths.get(filePath).getFileName().toString() + "." + index + (index == 1 ? "" : ".gz");
    }

    /**
     * Extracts the rotation index from the file name.
     *
     * @param path The path to the log file.
     * @return The rotation index for the log file.
     */
    private int getLogFileIndex(Path path) {
        String fileName = path.getFileName().toString();
        String[] parts = fileName.split("\\.");
        try {
            return Integer.parseInt(parts[parts.length - (fileName.endsWith(".gz") ? 2 : 1)]);
        } catch (NumberFormatException e) {
            return 0;  // Default to 0 if no number is found
        }
    }

    /**
     * Closes the log file by flushing any remaining data before rotation.
     */
    private void closeLogFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.flush();  // Flush any remaining data to the log file before rotating
        } catch (IOException e) {
            System.err.println("Error closing log file: " + e.getMessage());
        }
    }
}
