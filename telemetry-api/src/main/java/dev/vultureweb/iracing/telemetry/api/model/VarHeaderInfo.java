package dev.vultureweb.iracing.telemetry.api.model;

import java.nio.ByteBuffer;

public record VarHeaderInfo(int numberOfVars, int offset) {


   public static VarHeaderInfo fromByteBuffer(ByteBuffer buffer) {
      if (8 != buffer.remaining()) {
         throw new IllegalArgumentException("input for VarHeaderInfo should be 12 bytes long");
      }
      return new VarHeaderInfo(
            buffer.getInt(),
            buffer.getInt()
      );
   }
}
