package dev.vultureweb.iracing.sessioninfo.reader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class SessionInfoReader {

   public static SessionInfo readSessionInfo(final String sessionInfoYaml) {
      try {
         var mapper = new ObjectMapper(new YAMLFactory());
         mapper.findAndRegisterModules();
         var fullTree = mapper.readTree(sessionInfoYaml);
         var weekendInfo = fullTree.get("WeekendInfo");
         var sessionInfo = fullTree.get("SessionInfo");
         var carSetup = fullTree.get("CarSetup");
         var driverInfo = fullTree.get("DriverInfo");
         return new SessionInfo(weekendInfo, sessionInfo, carSetup, driverInfo);
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }
}
