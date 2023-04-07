package dev.vultureweb.iracing.sessioninfo.reader;


import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class SessionInfoReaderTest {

   @Test
   void testReadSessionInfo() {
      ObjectNode expected = JsonNodeFactory.instance.objectNode();
      expected.put("weekendInfo", JsonNodeFactory.instance.nullNode());
      expected.put("sessionInfo", JsonNodeFactory.instance.nullNode());
      expected.put("carSetup", JsonNodeFactory.instance.nullNode());
      expected.put("driverInfo", JsonNodeFactory.instance.nullNode());
      var sessionInfo = SessionInfoReader.readSessionInfo("SessionNum: 1");
      assertEquals(expected, sessionInfo);
   }

}