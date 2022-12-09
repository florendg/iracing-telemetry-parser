package dev.vultureweb.iracing.telemetry.app.model;

import java.nio.ByteBuffer;

public record VarHeaderInfo(int numberOfVars, int offset) {

   public static final int VAR_HEADER_BLOCK_SIZE = 144;
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
