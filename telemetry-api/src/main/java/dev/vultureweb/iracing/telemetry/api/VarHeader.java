package dev.vultureweb.iracing.telemetry.api;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public record VarHeader(String name, String description, String unit, VarInfo info) {

   public static VarHeader fromByteBuffer(ByteBuffer buffer)  {
      var varInfo = VarInfo.fromByteBuffer(buffer.slice(0,VarInfo.VAR_INFO_SIZE).order(ByteOrder.LITTLE_ENDIAN));

      byte[] rawBuffer = buffer.array();
      String name = new String(rawBuffer, 16, 32, StandardCharsets.US_ASCII).replaceAll("\\x00", "");
      String description = new String(rawBuffer, 48, 64, StandardCharsets.US_ASCII).replaceAll("\\x00", "");
      String unit = new String(rawBuffer, 112, 32, StandardCharsets.US_ASCII).replaceAll("\\x00", "");
      return new VarHeader(name, description, unit, varInfo);
   }
}
