package dev.vultureweb.iracing.telemetry.app.model;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public record VarHeader(String name, String description, String unit, VarInfo info) {

   public static VarHeader fromByteBuffer(ByteBuffer buffer)  {
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
}
