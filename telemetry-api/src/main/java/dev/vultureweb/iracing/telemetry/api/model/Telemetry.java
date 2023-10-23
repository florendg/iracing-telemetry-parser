package dev.vultureweb.iracing.telemetry.api.model;

import dev.vultureweb.iracing.sessioninfo.reader.SessionInfo;

import java.util.List;

public record Telemetry(SessionInfo sessionMetaInfo, List<VarHeader> varHeaders, BufferInfo bufferInfo, byte[] data) {


}
