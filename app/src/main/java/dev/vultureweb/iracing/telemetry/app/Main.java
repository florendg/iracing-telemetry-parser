package dev.vultureweb.iracing.telemetry.app;

import dev.vultureweb.iracing.telemetry.api.TelemetryApi;
import dev.vultureweb.iracing.telemetry.api.model.Type;

import java.io.File;
import java.nio.channels.FileChannel;
import java.nio.file.OpenOption;
import java.util.Set;

import static java.nio.file.StandardOpenOption.SPARSE;
import static java.nio.file.StandardOpenOption.READ;

public class Main {
   private static final System.Logger LOGGER = System.getLogger(Main.class.getName());

   private static final Set<OpenOption> openOptionSet = Set.of(SPARSE,READ);

   public static void main(String[] args) {
      final var telemetryApi = new TelemetryApi();
      if(args.length != 1) {
         System.out.println("Usage: java -jar <jarfile> <ibtfile>");
         System.exit(1);
      }
      var file = new File(args[0]);
      try(var reader = FileChannel.open(file.toPath(),openOptionSet)) {
         var uuid = telemetryApi.loadTelemetry(reader);
         // LOGGER.log(System.Logger.Level.INFO, "Loaded telemetry with uuid {0}", telemetryApi.getTelemetry(uuid));
         var telemetry = telemetryApi.getTelemetry(uuid);
         telemetry.varHeaders().stream()
             .filter(varHeader -> varHeader.info().type() == Type.DOUBLE)
             .forEach(varHeader -> LOGGER.log(System.Logger.Level.INFO, "Found bitfield {0}", varHeader));
      } catch (Exception e) {
         LOGGER.log(System.Logger.Level.ERROR, e.getMessage(), e);
      }
   }
}
