package dev.vultureweb.iracing.telemetry.api.model;

import java.nio.ByteBuffer;

public record BufferInfo(int count, int length, int offset) {

   public static BufferInfo fromByteBuffer(ByteBuffer buffer) {
      if (12 != buffer.remaining()) {
         throw new IllegalArgumentException("input for BufferInfo should be 12 bytes long");
      }
      return new BufferInfo(
            buffer.getInt(),
            buffer.getInt(),
            buffer.getInt()
      );
   }
}
