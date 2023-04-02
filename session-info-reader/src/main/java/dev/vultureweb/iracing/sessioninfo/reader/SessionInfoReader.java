package dev.vultureweb.iracing.sessioninfo.reader;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLParser;

public class SessionInfoReader {
    JsonNode readSessionInfo(final String sessionInfoYaml) {
        try {
            var mapper = new ObjectMapper(new YAMLFactory());
            mapper.findAndRegisterModules();
            var value = mapper.readTree(sessionInfoYaml);
            return value;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
