package helpers;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVHandler {

    public static String convertToCSV(String... args) {
        StringBuilder sb = new StringBuilder();
        for (String arg : args) {
            sb.append(arg).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

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

    public static void clearFile(String filename) {
        try {
            new FileOutputStream(filename).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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