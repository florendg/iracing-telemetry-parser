package dev.vultureweb.iracing.telemetry.app.model;

import java.nio.ByteBuffer;

public record Header(int version, int status, int tickRate) {

   public static Header fromByteBuffer(ByteBuffer buffer) {
      if (12 != buffer.remaining()) {
         throw new IllegalArgumentException("input for Header should be 12 bytes long");
      }
      return new Header(buffer.getInt(), buffer.getInt(), buffer.getInt());
   }
}
