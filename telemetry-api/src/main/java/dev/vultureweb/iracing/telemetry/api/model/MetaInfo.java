package dev.vultureweb.iracing.telemetry.api.model;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public record MetaInfo(Header header, SessionInfo sessionInfo, VarHeaderInfo varHeaderInfo, BufferInfo bufferInfo) {

    public static final int META_INFO_BLOCK_SIZE = 144;

    public static MemoryLayout layout = MemoryLayout.structLayout(
            Header.getLayout().withName("header"),
            SessionInfo.getLayout().withName("sessionInfo"),
            VarHeaderInfo.getLayout().withName("varHeaderInfo"),
            BufferInfo.getLayout().withName("bufferInfo")
    );

    public static MetaInfo fromMemorySegment(MemorySegment segment) {
        return new MetaInfo(
                Header.fromMemorySegment(segment.asSlice(0, Header.getLayout().byteSize())),
                SessionInfo.fromMemorySegment(segment.asSlice(Header.getLayout().byteSize(),
                        SessionInfo.getLayout().byteSize())),
                VarHeaderInfo.fromMemorySegment(segment.asSlice(Header.getLayout().byteSize() +
                        SessionInfo.getLayout().byteSize(), VarHeaderInfo.getLayout().byteSize())),
                BufferInfo.fromMemorySegment(segment.asSlice(Header.getLayout().byteSize() +
                        SessionInfo.getLayout().byteSize() +
                        VarHeaderInfo.getLayout().byteSize(), BufferInfo.getLayout().byteSize())));
    }
}
