package dev.vultureweb.iracing.sessioninfo.reader;

import com.fasterxml.jackson.databind.JsonNode;

public record SessionInfo(JsonNode weekendInfo, JsonNode sessionInfo, JsonNode carSetup, JsonNode driverInfo){}
