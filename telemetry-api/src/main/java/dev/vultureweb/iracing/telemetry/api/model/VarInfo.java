package dev.vultureweb.iracing.telemetry.api.model;

import java.nio.ByteBuffer;

public record VarInfo(int type, int offset, int count, boolean countAsTime) {

   public static int VAR_INFO_SIZE = 16;

   public static VarInfo fromByteBuffer(ByteBuffer buffer) {
      if(VAR_INFO_SIZE != buffer.remaining()) {
         throw new IllegalArgumentException("buffer size for VarInfo should be 16 bytes");
      }
      int type = buffer.getInt();
      int offset = buffer.getInt();
      int count = buffer.getInt();
      int countAsTime = buffer.getInt();
      return new VarInfo(type, offset, count, countAsTime == 1);
   }
}
