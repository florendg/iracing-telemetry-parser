package dev.vultureweb.iracing.telemetry.api.model;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

public record VarHeaderInfo(int numberOfVars, int offset) {

    private static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("numberOfVars"),
            ValueLayout.JAVA_INT.withName("offset")
    );

    public static VarHeaderInfo fromMemorySegment(MemorySegment segment) {
        return new VarHeaderInfo(
                segment.get(ValueLayout.JAVA_INT, 0),
                segment.get(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT.byteSize())
        );
    }

    public static MemoryLayout getLayout() {
        return MEMORY_LAYOUT;
    }

}
