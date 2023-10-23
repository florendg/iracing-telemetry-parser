package dev.vultureweb.iracing.telemetry.api.model;

import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;

public record MetaInfo(Header header, SessionMetaInfo sessionMetaInfo, VarHeaderInfo varHeaderInfo, BufferInfo bufferInfo) {

    public static final int META_INFO_BLOCK_SIZE = 144;

    public static MemoryLayout layout = MemoryLayout.structLayout(
            Header.getLayout().withName("header"),
            SessionMetaInfo.getLayout().withName("sessionInfo"),
            VarHeaderInfo.getLayout().withName("varHeaderInfo"),
            BufferInfo.getLayout().withName("bufferInfo")
    );

    public static MetaInfo fromMemorySegment(MemorySegment segment) {
        return new MetaInfo(
                Header.fromMemorySegment(segment.asSlice(0, Header.getLayout().byteSize())),
                SessionMetaInfo.fromMemorySegment(segment.asSlice(Header.getLayout().byteSize(),
                        SessionMetaInfo.getLayout().byteSize())),
                VarHeaderInfo.fromMemorySegment(segment.asSlice(Header.getLayout().byteSize() +
                        SessionMetaInfo.getLayout().byteSize(), VarHeaderInfo.getLayout().byteSize())),
                BufferInfo.fromMemorySegment(segment.asSlice(Header.getLayout().byteSize() +
                        SessionMetaInfo.getLayout().byteSize() +
                        VarHeaderInfo.getLayout().byteSize(), BufferInfo.getLayout().byteSize())));
    }
}
