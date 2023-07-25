package dev.vultureweb.iracing.telemetry.app;


import dev.vultureweb.iracing.telemetry.api.TelemetryApi;

import java.io.InputStream;
import java.util.UUID;

public class Main {
   public static void main(String[] args) {
      InputStream stream = Main.class.getResourceAsStream("dallaraf3.ibt");
      var telemetry = new TelemetryApi();
      UUID telemetryUid = telemetry.loadRTelemetry(stream);
      System.out.println(telemetry.getVarNames(telemetryUid));
   }
}
