package dev.vultureweb.iracing.telemetry.api;

import dev.vultureweb.iracing.sessioninfo.reader.SessionInfoReader;
import dev.vultureweb.iracing.telemetry.api.model.*;

import java.io.IOException;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class TelemetryApi {

    private static final System.Logger LOG = System.getLogger(TelemetryApi.class.getName());

    private final Map<UUID, Telemetry> telemetryCache = new HashMap<>();

    public UUID loadTelemetry(final FileChannel reader) {
        try (var arena = Arena.ofConfined()) {
            var mappedFile = reader.map(FileChannel.MapMode.READ_ONLY, 0, reader.size(), arena);
            var metaInfo = MetaInfo.fromMemorySegment(mappedFile.asSlice(0, MetaInfo.META_INFO_BLOCK_SIZE));

            var varHeaderInfo = metaInfo.varHeaderInfo();
            // Read var headers you need the slice that represents the var headers
            // it starts after the meta info block and ends at the offset of the var headers
            var size = varHeaderInfo.numberOfVars() * VarHeader.getMemoryLayout().byteSize();
            List<VarHeader> varHeaders = parseVarHeaders(mappedFile.asSlice(varHeaderInfo.offset(), size), varHeaderInfo);

            //Read session info starts after the var headers and ends at the offset of the buffer info
            //offset is meta info block size + var header layout size * number of vars
            var sessionMetaInfo = metaInfo.sessionMetaInfo();
            byte[] si = mappedFile.asSlice(sessionMetaInfo.offset(), sessionMetaInfo.length()).toArray(ValueLayout.JAVA_BYTE);

            String sessionYaml = new String(si, StandardCharsets.US_ASCII);
            var sessionInfo = SessionInfoReader.readSessionInfo(sessionYaml);
            //Read buffer info starts after the session info and ends at the end of the file
            BufferInfo bufferInfo = metaInfo.bufferInfo();
            var data = mappedFile.asSlice(bufferInfo.offset(), bufferInfo.length()).toArray(ValueLayout.JAVA_BYTE);
            UUID uuid = UUID.randomUUID();
            telemetryCache.put(uuid,new Telemetry(sessionInfo, varHeaders, bufferInfo, data));
            return uuid;
        } catch (IOException exception) {
            LOG.log(System.Logger.Level.ERROR, "Error reading telemetry data", exception);
            return null;
        }
    }

    public Telemetry getTelemetry(UUID uuid) {
        return telemetryCache.get(uuid);
    }

    private List<VarHeader> parseVarHeaders(MemorySegment segment, VarHeaderInfo varHeaderInfo) {
        List<VarHeader> varHeaders = new ArrayList<>();
        for (int i = 0; i < varHeaderInfo.numberOfVars(); i++) {
            var varHeader = VarHeader.fromMemorySegment(segment.asSlice(i * VarHeader.getMemoryLayout().byteSize(), VarHeader.getMemoryLayout().byteSize()));
            varHeaders.add(varHeader);
        }
        return varHeaders;
    }

    public String getSessionInfo() {
        return "TODO";
    }
}
