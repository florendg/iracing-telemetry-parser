package dev.vultureweb.iracing.sessioninfo.reader;

import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class SessionInfoReaderTest {

    @Test
    void testReadSessionInfo() {
        var sessionInfo = SessionInfoReader.readSessionInfo("SessionNum: 1");
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.set("SessionNum", new IntNode(1));
        assertEquals(node, sessionInfo);
    }

}