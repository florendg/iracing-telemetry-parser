package dev.vultureweb.iracing.telemetry.api.model;

import com.fasterxml.jackson.databind.JsonNode;

import java.nio.ByteBuffer;
import java.util.Map;

public record Telemetry(JsonNode sessionInfo, Map<String,VarHeader> varHeaders, BufferInfo bufferInfo, ByteBuffer data) {


}
