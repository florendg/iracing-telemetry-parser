package dev.vultureweb.iracing.telemetry.api.model;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

public record Header(int version, int status, int tickRate) {

    private static MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("version"),
            ValueLayout.JAVA_INT.withName("status"),
            ValueLayout.JAVA_INT.withName("tickRate")
    );

    public static Header fromMemorySegment(MemorySegment segment) {
        return new Header(
                segment.get(ValueLayout.JAVA_INT, 0),
                segment.get(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT.byteSize()),
                segment.get(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT.byteSize() * 2));
    }

    public static MemoryLayout getLayout() {
        return MEMORY_LAYOUT;
    }
}
