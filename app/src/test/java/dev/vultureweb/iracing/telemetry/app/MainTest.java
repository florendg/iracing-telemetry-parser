package dev.vultureweb.iracing.telemetry.app;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    @Test
    void shouldCorrectlyConvertLittleEndianByteArrayToInt() {
        byte[] input = {0x01,0x02,0x03,0x04};
        int result = Main.convertBytesToIntLittleEndian(input);
        assertEquals(0x04030201, result);
    }

    @Test
    void shouldCorrectlyPositionNullValues() {
        byte[] input = {0x02,0x00,0x00,0x00};
        int result = Main.convertBytesToIntLittleEndian(input);
        assertEquals(0x00000002, result);
    }
}