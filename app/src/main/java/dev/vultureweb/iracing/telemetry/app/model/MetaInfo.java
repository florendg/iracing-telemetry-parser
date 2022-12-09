package dev.vultureweb.iracing.telemetry.app.model;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public record MetaInfo(Header header, SessionInfo sessionInfo, VarHeaderInfo varHeaderInfo, BufferInfo bufferInfo) {

   public static final int META_INFO_BLOCK_SIZE = 144;
   public static MetaInfo fromByteBuffer(ByteBuffer buffer)  {
      return new MetaInfo(
            Header.fromByteBuffer(buffer.slice(0,12).order(ByteOrder.LITTLE_ENDIAN)),
            SessionInfo.fromByteBuffer(buffer.slice(12,12).order(ByteOrder.LITTLE_ENDIAN)),
            VarHeaderInfo.fromByteBuffer(buffer.slice(24,8).order(ByteOrder.LITTLE_ENDIAN)),
            BufferInfo.fromByteBuffer(buffer.slice(32,12).order(ByteOrder.LITTLE_ENDIAN))
      );
   }
}
