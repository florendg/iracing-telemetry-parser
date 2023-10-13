package dev.vultureweb.iracing.telemetry.api.model;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SequenceLayout;
import java.lang.foreign.ValueLayout;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public record VarHeader(String name, String description, String unit, VarInfo info) {

    private static MemoryLayout MEMORY_LAYOUT = MemoryLayout.structLayout(
            VarInfo.getMemoryLayout(),
            MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE).withName("name"),
            MemoryLayout.sequenceLayout(64, ValueLayout.JAVA_BYTE).withName("description"),
            MemoryLayout.sequenceLayout(32, ValueLayout.JAVA_BYTE).withName("unit")
    );

    public static VarHeader fromMemorySegment(MemorySegment segment) {

        return new VarHeader(
                segment.asSlice(VarInfo.getMemoryLayout().byteSize(), 32).getUtf8String(0),
                segment.asSlice(VarInfo.getMemoryLayout().byteSize() + 32 ,64).getUtf8String(0),
                segment.asSlice(VarInfo.getMemoryLayout().byteSize() + 32 +64,32).getUtf8String(0),
                VarInfo.fromMemorySegment(segment.asSlice(0, VarInfo.getMemoryLayout().byteSize()))
        );
    }

    public static MemoryLayout getMemoryLayout() {
        return MEMORY_LAYOUT;
    }

}
