package dev.vultureweb.iracing.telemetry.api.model;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

public record SessionMetaInfo(int update, int length, int offset) {

    private static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("update"),
            ValueLayout.JAVA_INT.withName("length"),
            ValueLayout.JAVA_INT.withName("offset")
    );

    public static final SessionMetaInfo fromMemorySegment(MemorySegment segment) {
        return new SessionMetaInfo(
                segment.get(ValueLayout.JAVA_INT, 0),
                segment.get(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT.byteSize()),
                segment.get(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT.byteSize() * 2));
    }

    public static MemoryLayout getLayout() {
        return MEMORY_LAYOUT;
    }
}
