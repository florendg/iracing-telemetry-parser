package dev.vultureweb.iracing.telemetry.app;


import dev.vultureweb.iracing.telemetry.api.TelemetryApi;
import dev.vultureweb.iracing.telemetry.api.model.Telemetry;
import dev.vultureweb.iracing.telemetry.api.model.VarHeader;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.List;

public class Main {
   public static void main(String[] args) {
      InputStream stream = Main.class.getResourceAsStream("dallaraf3.ibt");
      Telemetry telemetry = new TelemetryApi().loadRTelemetry(stream);
      System.out.println(telemetry);
   }

   static void printBuffer(ByteBuffer sample,List<VarHeader> headers) {
      for(var header : headers) {
         switch (header.info().type()) {
            case 5 -> System.out.print(sample.getDouble(header.info().offset()));
            case 2, 3 -> System.out.print(sample.getInt(header.info().offset()));
            case 1 -> System.out.print(sample.get(header.info().offset()));
            case 4 -> System.out.print(sample.getFloat(header.info().offset()));
         }
         System.out.print(";");
      }

      System.out.println();
   }

}
