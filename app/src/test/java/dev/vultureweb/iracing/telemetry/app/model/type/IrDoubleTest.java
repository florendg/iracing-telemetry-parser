package dev.vultureweb.iracing.telemetry.app.model.type;

import dev.vultureweb.iracing.telemetry.api.model.type.IrDouble;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IrDoubleTest {

    @Test
    void shouldThrowIllegalArgumentExceptionWhenBufferContainsNotEnoughBytes() {
        IrDouble type = new IrDouble();
        byte[] input = {0x01,0x02,0x03,0x04,0x05,0x06,0x07};
        ByteBuffer buffer = ByteBuffer.wrap(input).order(ByteOrder.LITTLE_ENDIAN);
        assertThrows(IllegalArgumentException.class,()-> type.convert(buffer));
    }

    @Test
    @Disabled
    void shouldReturnTheProperDoubleValueRepresentedInTheBuffer() {
        IrDouble type =new IrDouble();
        byte[] input = {0x12,0x00,0x00,0x00,0x00,0x00,0x00,0x00};
        ByteBuffer buffer = ByteBuffer.wrap(input);
        assertEquals(18.0,type.convert(buffer));
    }

}