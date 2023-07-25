package dev.vultureweb.iracing.telemetry.app;


import dev.vultureweb.iracing.telemetry.api.TelemetryApi;

import java.io.InputStream;
import java.util.UUID;

public class Main {
   private static final System.Logger LOGGER = System.getLogger(Main.class.getName());
   public static void main(String[] args) {
      try(InputStream stream = Main.class.getResourceAsStream("dallaraf3.ibt")) {
         if(stream == null) throw new Exception("Could not find file");
         var telemetry = new TelemetryApi();
         UUID telemetryUid = telemetry.loadRTelemetry(stream);
         System.out.println(telemetry.getVarNames(telemetryUid));
      } catch (Exception e) {
         LOGGER.log(System.Logger.Level.ERROR, e.getMessage(), e);
      }
   }
}
