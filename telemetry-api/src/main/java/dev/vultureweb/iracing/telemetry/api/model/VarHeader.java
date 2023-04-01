package dev.vultureweb.iracing.telemetry.api.model;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public record VarHeader(String name, String description, String unit, VarInfo info) {

   public static final int VAR_HEADER_BLOCK_SIZE = 144;

   public static VarHeader fromByteBuffer(ByteBuffer buffer) {
      if (VAR_HEADER_BLOCK_SIZE != buffer.remaining()) {
         throw new IllegalArgumentException("buffer size for VarHeader should be " + VAR_HEADER_BLOCK_SIZE + " bytes");
      }
      var varInfo = VarInfo.fromByteBuffer(buffer.slice(0, VarInfo.VAR_INFO_SIZE).order(ByteOrder.LITTLE_ENDIAN));

      byte[] rawBuffer = buffer.array();
      String name = new String(rawBuffer, VarInfo.VAR_INFO_SIZE, 32, StandardCharsets.US_ASCII).replaceAll("\\x00", "");
      String description = new String(rawBuffer, 48, 64, StandardCharsets.US_ASCII).replaceAll("\\x00", "");
      String unit = new String(rawBuffer, 112, 32, StandardCharsets.US_ASCII).replaceAll("\\x00", "");
      return new VarHeader(name, description, unit, varInfo);
   }
}
