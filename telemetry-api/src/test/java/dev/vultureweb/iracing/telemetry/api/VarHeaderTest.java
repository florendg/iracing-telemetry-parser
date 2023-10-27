package dev.vultureweb.iracing.telemetry.api;


import dev.vultureweb.iracing.telemetry.api.model.VarHeader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.lang.foreign.MemorySegment;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.junit.jupiter.api.Assertions.*;


class VarHeaderTest {

   ByteBuffer buffer = ByteBuffer.allocate((int)VarHeader.getMemoryLayout().byteSize());
   MemorySegment memorySegment = MemorySegment.ofBuffer(buffer);


   @BeforeEach
   void setUp() {
      buffer.order(ByteOrder.LITTLE_ENDIAN);
      buffer.putInt(Integer.MAX_VALUE);
      buffer.putInt(2);
      buffer.putInt(3);
      buffer.putInt(1);
      for(int i = 0; i < 16; i++) {
         buffer.put((byte)'x');
      }
      for(int i = 0; i < 16; i++) {
         buffer.put((byte)0x00);
      }
      for(int i = 0; i < 32; i++) {
         buffer.put((byte)'y');
      }
      for(int i = 0; i < 32; i++) {
         buffer.put((byte)0x00);
      }
      for(int i = 0; i < 32; i++) {
         buffer.put((byte)'z');
      }
      buffer.rewind();
   }

   @Test
   @Disabled
   void shouldCorrectlyParseTheBuffer() {
      VarHeader header = VarHeader.fromMemorySegment(memorySegment);
      assertNotNull(header);
      assertAll("header",
            () -> assertEquals("xxxxxxxxxxxxxxxx", header.name()),
            () -> assertEquals("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy", header.description()),
            () -> assertEquals("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz", header.unit()),
            () -> assertEquals(Integer.MAX_VALUE, header.info().type()),
            () -> assertEquals(2, header.info().offset()),
            () -> assertEquals(3, header.info().count()),
            () -> assertTrue(header.info().countAsTime()));
   }
}