package GUI;

import java.io.*;
import java.io.FileWriter;
import java.io.PrintWriter;

public class SaveMsgToCsv {

    public static void writeMessageToCsv(String filename, String message) {
        try {
            // Create a FileWriter object
            FileWriter fileWriter = new FileWriter(filename);

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
}