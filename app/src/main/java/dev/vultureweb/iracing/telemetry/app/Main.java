package dev.vultureweb.iracing.telemetry.app;

import dev.vultureweb.iracing.telemetry.app.model.Header;
import dev.vultureweb.iracing.telemetry.app.model.SessionInfo;
import dev.vultureweb.iracing.telemetry.app.model.VarHeader;
import dev.vultureweb.iracing.telemetry.app.model.VarInfo;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Main {
   public static void main(String[] args) {

      InputStream stream = Main.class.getResourceAsStream("test.ibt");
      try (stream) {
         assert (stream != null);
         Header header = extractHeader(stream.readNBytes(4), stream.readNBytes(4), stream.readNBytes(4));
         SessionInfo sessionInfo = extractSessionInfo(stream.readNBytes(4), stream.readNBytes(4), stream.readNBytes(4));
         int numberOfVars = convertBytesToIntLittleEndian(stream.readNBytes(4));
         int varHeaderOffset = convertBytesToIntLittleEndian(stream.readNBytes(4));
         int numberOfBuffers = convertBytesToIntLittleEndian(stream.readNBytes(4));
         int bufferLength = convertBytesToIntLittleEndian(stream.readNBytes(4));
         int bufferOffset = convertBytesToIntLittleEndian(stream.readNBytes(4));

         long skipped = stream.skip(100);
         assert( 100 == skipped);

         List<VarHeader> varHeaders = new ArrayList<>();
         for (int i = 0; i < numberOfVars; i++) {
            VarHeader varHeader = buildHeader(ByteBuffer.wrap(stream.readNBytes(144)).order(ByteOrder.LITTLE_ENDIAN));
            varHeaders.add(varHeader);
            System.out.println(varHeader);
         }
         byte[] si = stream.readNBytes(sessionInfo.length());
         String yaml = new String(si);
         System.out.println(yaml);
         while(stream.available() >= 1006) {
            ByteBuffer rawBuffer = ByteBuffer.wrap(stream.readNBytes(bufferLength)).order(ByteOrder.LITTLE_ENDIAN);
            System.out.println(" " + stream.available() + " " + rawBuffer.getDouble() + " " + rawBuffer.getInt(8) );
         }
      } catch (IOException exception) {
         System.err.println("Error reading telemetry " + exception);
      }
   }

   private static VarHeader buildHeader(ByteBuffer buffer) throws IOException {
      int type = buffer.getInt();
      int offset = buffer.getInt();
      int count = buffer.getInt();
      int countAsTime = buffer.getInt();
      VarInfo info = new VarInfo(type, offset, count, countAsTime == 1);
      byte[] rawBuffer = buffer.array();
      String name = new String(rawBuffer, 16, 32, StandardCharsets.US_ASCII).replaceAll("\\x00", "");
      String description = new String(rawBuffer, 48, 64, StandardCharsets.US_ASCII).replaceAll("\\x00", "");
      String unit = new String(rawBuffer, 112, 32, StandardCharsets.US_ASCII).replaceAll("\\x00", "");
      return new VarHeader(name, description, unit, info);
   }

   static int convertBytesToIntLittleEndian(byte[] input) {
      if (4 != input.length) {
         throw new IllegalArgumentException("input should be 4 bytes long");
      }
      return ((input[3] & 0xFF) << 24) |
             ((input[2] & 0xFF) << 16) |
             ((input[1] & 0xFF) << 8) |
             ((input[0] & 0xFF));

   }

   static Header extractHeader(byte[] version, byte[] status, byte[] tickRate) {
      if (12 != (version.length + status.length + tickRate.length)) {
         throw new IllegalArgumentException("input for Header should be 12 bytes long");
      }
      return new Header(
            convertBytesToIntLittleEndian(version),
            convertBytesToIntLittleEndian(status),
            convertBytesToIntLittleEndian(tickRate));
   }

   static SessionInfo extractSessionInfo(byte[] update, byte[] length, byte[] offset) {
      if (12 != (update.length + length.length + offset.length)) {
         throw new IllegalArgumentException("input for Header should be 12 bytes long");
      }
      return new SessionInfo(
            convertBytesToIntLittleEndian(update),
            convertBytesToIntLittleEndian(length),
            convertBytesToIntLittleEndian(offset)
      );
   }
}
