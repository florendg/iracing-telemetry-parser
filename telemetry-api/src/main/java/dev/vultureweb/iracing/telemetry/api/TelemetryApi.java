package dev.vultureweb.iracing.telemetry.api;


import dev.vultureweb.iracing.telemetry.api.model.*;

import java.io.IOException;
import java.lang.foreign.Arena;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class TelemetryApi {

    private static final System.Logger LOG = System.getLogger(TelemetryApi.class.getName());

    private final Map<UUID, Telemetry> telemetryCache = new HashMap<>();

    public UUID loadTelemetry(final FileChannel reader) {
        try (var arena = Arena.ofConfined()) {
            var mappedFile = reader.map(FileChannel.MapMode.READ_ONLY, 0, reader.size(), arena);
            var metaInfo = MetaInfo.fromMemorySegment(mappedFile.asSlice(0, MetaInfo.META_INFO_BLOCK_SIZE));

            VarHeaderInfo varHeaderInfo = metaInfo.varHeaderInfo();
            // Read var headers you need the slice that represents the var headers
            // it starts after the meta info block and ends at the offset of the var headers
            //var  varHeaderSlice = mappedFile.asSlice(MetaInfo.META_INFO_BLOCK_SIZE, varHeaderLayoutSize * numberOfVars);
//            Map<String,VarHeader> varHeaders = new HashMap<>();
//            for (int i = 0; i < varHeaderInfo.numberOfVars(); i++) {
//                VarHeader varHeader = VarHeader.fromByteBuffer(ByteBuffer.wrap(dataStream.readNBytes(VarHeader.VAR_HEADER_BLOCK_SIZE))
//                        .order(ByteOrder.LITTLE_ENDIAN));
//                varHeaders.put(varHeader.name(),varHeader);
//            }

            //Read session info starts after the var headers and ends at the offset of the buffer info
            //offset is meta info block size + var header layout size * number of vars
//            SessionInfo sessionInfo = metaInfo.sessionInfo();
//            byte[] si = dataStream.readNBytes(sessionInfo.length());
//
//            String sessionYaml = new String(si, StandardCharsets.US_ASCII);
//            var sessionInfoJson = SessionInfoReader.readSessionInfo(sessionYaml);
            //Read buffer info starts after the session info and ends at the end of the file
//            BufferInfo bufferInfo = metaInfo.bufferInfo();
//
//            ByteBuffer data = ByteBuffer.wrap(dataStream.readAllBytes()).order(ByteOrder.LITTLE_ENDIAN);
            UUID uuid = UUID.randomUUID();
            //telemetryCache.put(uuid,new Telemetry(sessionInfoJson, varHeaders, bufferInfo, data));
            return uuid;
        } catch (IOException exception) {
            LOG.log(System.Logger.Level.ERROR, "Error reading telemetry data", exception);
            return null;
        }
    }

    public String getSessionInfo() {
        return "TODO";
    }
}
