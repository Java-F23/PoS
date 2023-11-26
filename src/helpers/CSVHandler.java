package helpers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVHandler {

    /**
     * Converts an array of strings into a CSV string.
     * This method takes an array of strings as input, joins them into a single
     * string with commas in between,
     * and returns the resulting CSV string.
     *
     * @param args the array of strings to be converted into a CSV string.
     * @return the CSV string.
     */
    public static String convertToCSV(String... args) {
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append(arg).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * Writes a single message to a CSV file.
     * This method takes a filename, a message, and a boolean indicating whether to
     * append to the file or overwrite it.
     * It creates a FileWriter and a PrintWriter, writes the message to the file,
     * and then closes the PrintWriter.
     * If an exception occurs during this process, it prints the stack trace of the
     * exception.
     *
     * @param filename the name of the file to write to.
     * @param message  the message to write to the file.
     * @param append   whether to append to the file (true) or overwrite it (false).
     */
    public static void writeMessageToCsv(String filename, String message, boolean append) {
        try {
            // Create a FileWriter object
            FileWriter fileWriter = new FileWriter(filename, append);

            // Create a PrintWriter object
            PrintWriter printWriter = new PrintWriter(fileWriter);

            // Write the message to the CSV file
            printWriter.println(message);

            // Close the PrintWriter
            printWriter.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Writes multiple messages to a CSV file.
     * This method takes a filename, a list of messages, and a boolean indicating
     * whether to append to the file or overwrite it.
     * It creates a FileWriter and a PrintWriter, writes each message to the file,
     * and then closes the PrintWriter.
     * If an exception occurs during this process, it prints the stack trace of the
     * exception.
     *
     * @param filename the name of the file to write to.
     * @param messages the list of messages to write to the file.
     * @param append   whether to append to the file (true) or overwrite it (false).
     */
    public static void writeMessageToCsv(String filename, List<String> messages, boolean append) {
        try {
            // Create a FileWriter object
            FileWriter fileWriter = new FileWriter(filename, append);

            // Create a PrintWriter object
            PrintWriter printWriter = new PrintWriter(fileWriter);

            // Write the messages to the CSV file
            for (String message : messages) {
                printWriter.println(message);
            }

            // Close the PrintWriter
            printWriter.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Clears a file by creating a new FileOutputStream and immediately closing it.
     * This effectively truncates the file, removing all of its contents.
     * If an IOException occurs during this process, it prints the stack trace of
     * the exception.
     *
     * @param filename the name of the file to clear.
     */
    public static void clearFile(String filename) {
        try {
            new FileOutputStream(filename).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads all lines from a CSV file into a list of strings.
     * This method takes a filename, opens a BufferedReader on the file, reads each
     * line into a string,
     * adds the string to a list, and then returns the list.
     * If an IOException occurs during this process, it prints the stack trace of
     * the exception.
     *
     * @param fileName the name of the file to read from.
     * @return a list of strings, where each string is a line from the file.
     */
    public static List<String> readFromCsv(String fileName) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

}