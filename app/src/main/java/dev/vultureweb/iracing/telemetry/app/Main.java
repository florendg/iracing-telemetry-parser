package dev.vultureweb.iracing.telemetry.app;

import java.io.*;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        URL input = Main.class.getResource("/test.ibt");
        if (input == null) {
            System.err.println("Input file not found");
            System.exit(-1);
        }
        try (BufferedInputStream stream = new BufferedInputStream(new FileInputStream(input.getPath()))) {

            int version = convertBytesToIntLittleEndian(stream.readNBytes(4));
            System.out.println("version: " + version);


        } catch (IOException exception) {
            System.err.println("Error reading telemetry");
        }
    }

    public static int convertBytesToIntLittleEndian(byte[] input) {
        return ((input[3] & 0xFF) << 24) |
                ((input[2] & 0xFF) << 16) |
                ((input[1] & 0xFF) << 8) |
                ((input[0] & 0xFF) << 0);
    }
}
