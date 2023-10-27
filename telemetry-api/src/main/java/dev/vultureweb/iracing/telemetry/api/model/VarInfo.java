package dev.vultureweb.iracing.telemetry.api.model;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.ByteOrder;

public record VarInfo(Type type, int offset, int count, boolean countAsTime) {

    private static final MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(
            ValueLayout.JAVA_INT.withName("type"),
            ValueLayout.JAVA_INT.withName("offset"),
            ValueLayout.JAVA_INT.withName("count"),
            ValueLayout.JAVA_INT.withName("countAsTime")
    );

    public static VarInfo fromMemorySegment(MemorySegment memorySegment) {
        var type = Type.fromCode(memorySegment.get(ValueLayout.JAVA_INT, 0));
        var offset = memorySegment.get(ValueLayout.JAVA_INT.withOrder(ByteOrder.LITTLE_ENDIAN), ValueLayout.JAVA_INT.byteSize());
        var count = memorySegment.get(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT.byteSize() * 2);
        boolean countAsTime = memorySegment.get(ValueLayout.JAVA_INT, ValueLayout.JAVA_INT.byteSize() * 3) == 1;
        return new VarInfo(type, offset, count, countAsTime);
    }

    public static MemoryLayout getMemoryLayout() {
        return MEMORY_LAYOUT;
    }
}
