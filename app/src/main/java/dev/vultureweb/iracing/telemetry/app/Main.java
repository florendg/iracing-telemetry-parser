package dev.vultureweb.iracing.telemetry.app;

import dev.vultureweb.iracing.telemetry.api.TelemetryApi;

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
      if(args.length != 1) {
         System.out.println("Usage: java -jar <jarfile> <ibtfile>");
         System.exit(1);
      }
      var file = new File(args[0]);
      try(var reader = FileChannel.open(file.toPath(),openOptionSet)) {
         var uuid = new TelemetryApi().loadTelemetry(reader);
      } catch (Exception e) {
         LOGGER.log(System.Logger.Level.ERROR, e.getMessage(), e);
      }
   }
}
