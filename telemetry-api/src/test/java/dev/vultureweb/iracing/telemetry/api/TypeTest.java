package dev.vultureweb.iracing.telemetry.api;

import dev.vultureweb.iracing.telemetry.api.model.Type;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TypeTest {

   @Test
   void fromCode() {
      assertEquals(Type.BOOLEAN, Type.fromCode(1));
      assertEquals(Type.INTEGER, Type.fromCode(2));
      assertEquals(Type.BITFIELD, Type.fromCode(3));
      assertEquals(Type.FLOAT, Type.fromCode(4));
      assertEquals(Type.DOUBLE, Type.fromCode(5));
   }

   @Test
   void getCode() {
      assertEquals(1, Type.BOOLEAN.getCode());
      assertEquals(2, Type.INTEGER.getCode());
      assertEquals(3, Type.BITFIELD.getCode());
      assertEquals(4, Type.FLOAT.getCode());
      assertEquals(5, Type.DOUBLE.getCode());
   }

   @Test
   void values() {
      assertEquals(5, Type.values().length);
   }

}