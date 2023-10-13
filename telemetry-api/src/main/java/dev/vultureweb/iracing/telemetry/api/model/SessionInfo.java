package dev.vultureweb.iracing.telemetry.api.model;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.ByteBuffer;

public record SessionInfo(int update, int length, int offset) {

    private static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("update"),
            ValueLayout.JAVA_INT.withName("length"),
            ValueLayout.JAVA_INT.withName("offset")
    );

    public static final SessionInfo fromMemorySegment(MemorySegment segment) {
        return new SessionInfo(
                segment.get(ValueLayout.JAVA_INT, 0),
                segment.get(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT.byteSize()),
                segment.get(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT.byteSize() * 2));
    }

    public static SessionInfo fromByteBuffer(ByteBuffer buffer) {
        if (12 != buffer.remaining()) {
            throw new IllegalArgumentException("input for Header should be 12 bytes long");
        }
        return new SessionInfo(
                buffer.getInt(),
                buffer.getInt(),
                buffer.getInt()
        );
    }

    public static MemoryLayout getLayout() {
        return MEMORY_LAYOUT;
    }
}
