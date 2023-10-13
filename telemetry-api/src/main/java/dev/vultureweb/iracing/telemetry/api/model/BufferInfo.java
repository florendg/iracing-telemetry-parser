package dev.vultureweb.iracing.telemetry.api.model;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.ByteBuffer;

public record BufferInfo(int count, int length, int offset) {

    private static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("count"),
            ValueLayout.JAVA_INT.withName("length"),
            ValueLayout.JAVA_INT.withName("offset")
    );

    public static BufferInfo fromMemorySegment(MemorySegment memorySegment) {
        return new BufferInfo(
                memorySegment.get(ValueLayout.JAVA_INT, 0),
                memorySegment.get(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT.byteSize()),
                memorySegment.get(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT.byteSize() * 2)
        );
    }

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

    public static MemoryLayout getLayout() {
        return MEMORY_LAYOUT;
    }
}
