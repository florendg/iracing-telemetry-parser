package dev.vultureweb.iracing.telemetry.api.model;

import java.nio.ByteBuffer;
import java.util.Map;

public record Telemetry(SessionInfo sessionInfo, Map<String,VarHeader> varHeaders,BufferInfo bufferInfo, ByteBuffer data) {


}
