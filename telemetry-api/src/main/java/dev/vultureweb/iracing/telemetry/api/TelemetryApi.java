package dev.vultureweb.iracing.telemetry.api;

import dev.vultureweb.iracing.sessioninfo.reader.SessionInfoReader;
import dev.vultureweb.iracing.telemetry.api.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TelemetryApi {

   private static final System.Logger LOG = System.getLogger(TelemetryApi.class.getName());

   public Telemetry loadRTelemetry(final InputStream dataStream) {

      try (dataStream) {
         assert (dataStream != null);

         MetaInfo metaInfo = MetaInfo.fromByteBuffer(ByteBuffer.wrap(dataStream.readNBytes(MetaInfo.META_INFO_BLOCK_SIZE)));

         VarHeaderInfo varHeaderInfo = metaInfo.varHeaderInfo();
         Map<String,VarHeader> varHeaders = new HashMap<>();
         for (int i = 0; i < varHeaderInfo.numberOfVars(); i++) {
            VarHeader varHeader = VarHeader.fromByteBuffer(ByteBuffer.wrap(dataStream.readNBytes(VarHeader.VAR_HEADER_BLOCK_SIZE))
                  .order(ByteOrder.LITTLE_ENDIAN));
            varHeaders.put(varHeader.name(),varHeader);
         }

         SessionInfo sessionInfo = metaInfo.sessionInfo();
         byte[] si = dataStream.readNBytes(sessionInfo.length());

         String sessionYaml = new String(si, StandardCharsets.US_ASCII);
         var sessionInfoJson = SessionInfoReader.readSessionInfo(sessionYaml);
         BufferInfo bufferInfo = metaInfo.bufferInfo();

         ByteBuffer data = ByteBuffer.wrap(dataStream.readAllBytes()).order(ByteOrder.LITTLE_ENDIAN);
         return new Telemetry(sessionInfo, varHeaders, bufferInfo, data);

      } catch (IOException exception) {
         LOG.log(System.Logger.Level.ERROR, "Error reading telemetry data", exception);
         throw new RuntimeException(exception);
      }

   }

   public List<String> getVarNames(Telemetry telemetry) {
      return telemetry.varHeaders().keySet().stream().collect(Collectors.toList());
   }

   public String getSessionInfo() {
      return "TODO";
   }
}
