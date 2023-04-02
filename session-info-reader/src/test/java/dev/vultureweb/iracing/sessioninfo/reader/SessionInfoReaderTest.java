package dev.vultureweb.iracing.sessioninfo.reader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class SessionInfoReaderTest {

    @Test
    void testReadSessionInfo() {
        var sessionInfoReader = new SessionInfoReader();
        var sessionInfo = sessionInfoReader.readSessionInfo("SessionNum: 1");
        assertEquals(new ObjectNode(JsonNodeFactory.instance), sessionInfo);
    }

}