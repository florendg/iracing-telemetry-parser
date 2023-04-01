package dev.vultureweb.iracing.telemetry.api.model;

import java.nio.ByteBuffer;

public record SessionInfo(int update, int length, int offset) {
   public static SessionInfo fromByteBuffer(ByteBuffer buffer) {
      if (12 != buffer.remaining()) {
         throw new IllegalArgumentException("input for Header should be 12 bytes long");
      }
      return new SessionInfo(
            buffer.getInt(),
            buffer.getInt(),
            buffer.getInt()
      );
   }
}
