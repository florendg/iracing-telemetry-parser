package dev.vultureweb.iracing.telemetry.app;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

public class Main {
    public static void main(String[] args) {

        InputStream input = Main.class.getResourceAsStream("test.ibt");
        assert(input != null);
        try (BufferedInputStream stream = new BufferedInputStream(input)) {
            //stream.mark(1000);
            int version = convertBytesToIntLittleEndian(stream.readNBytes(4));
            int status = convertBytesToIntLittleEndian(stream.readNBytes(4));
            int tickRate = convertBytesToIntLittleEndian(stream.readNBytes(4));
            int sessionInfoUpdate = convertBytesToIntLittleEndian(input.readNBytes(4));
            int sessionInfoLength = convertBytesToIntLittleEndian(stream.readNBytes(4));
            int sessionInfoOffset = convertBytesToIntLittleEndian(stream.readNBytes(4));
            int numberOfVars = convertBytesToIntLittleEndian(stream.readNBytes(4));
            int varHeaderOffset = convertBytesToIntLittleEndian(stream.readNBytes(4));
            int numberOfBuffers = convertBytesToIntLittleEndian(stream.readNBytes(4));
            int bufferLength = convertBytesToIntLittleEndian(stream.readNBytes(4));
            int bufferOffset = convertBytesToIntLittleEndian(stream.readNBytes(4));

            stream.skip(120);
            for(int i=0 ; i < numberOfVars ; i++) {
                buildHeader(stream);
            }
        } catch (IOException exception) {
            System.err.println("Error reading telemetry " + exception);
        }
    }

    private static void buildHeader(BufferedInputStream stream) throws IOException {
        byte[] header = new byte[32];
        byte[] description = new byte[64];
        byte[] unit = new byte[48];
        int length = stream.readNBytes(header,0,32);
        length = stream.readNBytes(description,0,64);
        length = stream.readNBytes(unit,0,48);
        for(int i=0 ; i < header.length; i++) {
            Byte b = header[i];
            if(b == 0x00) header[i] = 0x20;
        }
        for(int i=0 ; i < description.length; i++) {
            Byte b = description[i];
            if(b == 0x00) description[i] = 0x20;
        }
        for(int i=0 ; i < unit.length; i++) {
            Byte b = unit[i];
            if(b == 0x00) unit[i] = 0x20;
        }
        String h = new String(header, Charset.defaultCharset());
        String d = new String(description, Charset.defaultCharset());
        String u = new String(unit, Charset.defaultCharset());
        System.out.println(h + ":" + d + ":" + u);
    }

    public static int convertBytesToIntLittleEndian(byte[] input) {
        int value =  ((input[3] & 0xFF) << 24) |
                ((input[2] & 0xFF) << 16) |
                ((input[1] & 0xFF) << 8) |
                ((input[0] & 0xFF) << 0);
        return value;
    }
}
