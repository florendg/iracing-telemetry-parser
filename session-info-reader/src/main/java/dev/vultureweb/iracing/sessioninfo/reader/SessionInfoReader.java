package dev.vultureweb.iracing.sessioninfo.reader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class SessionInfoReader {

   public static JsonNode readSessionInfo(final String sessionInfoYaml) {
      try {
         var mapper = new ObjectMapper(new YAMLFactory());
         mapper.findAndRegisterModules();
         var fullTree = mapper.readTree(sessionInfoYaml);
         var weekendInfo = fullTree.get("WeekendInfo");
         var sessionInfo = fullTree.get("SessionInfo");
         var carSetup = fullTree.get("CarSetup");
         var driverInfo = fullTree.get("DriverInfo");
         var info = new Info(weekendInfo, sessionInfo, carSetup, driverInfo);
         return mapper.valueToTree(info);
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

   public record Info(JsonNode weekendInfo, JsonNode sessionInfo, JsonNode carSetup, JsonNode driverInfo){}
}
