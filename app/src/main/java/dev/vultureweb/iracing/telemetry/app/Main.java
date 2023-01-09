package dev.vultureweb.iracing.telemetry.app;

import dev.vultureweb.iracing.telemetry.api.VarHeader;
import dev.vultureweb.iracing.telemetry.app.model.*;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {

        InputStream stream = Main.class.getResourceAsStream("dallarap217_indianapolis.ibt");
        try (stream) {
            assert (stream != null);

            MetaInfo metaInfo = MetaInfo.fromByteBuffer(ByteBuffer.wrap(stream.readNBytes(MetaInfo.META_INFO_BLOCK_SIZE)));

            VarHeaderInfo varHeaderInfo = metaInfo.varHeaderInfo();
            List<VarHeader> varHeaders = new ArrayList<>();
            for (int i = 0; i < varHeaderInfo.numberOfVars(); i++) {
                VarHeader varHeader = VarHeader.fromByteBuffer(ByteBuffer.wrap(stream.readNBytes(VarHeaderInfo.VAR_HEADER_BLOCK_SIZE))
                        .order(ByteOrder.LITTLE_ENDIAN));
                varHeaders.add(varHeader);
            }

            SessionInfo sessionInfo = metaInfo.sessionInfo();
            byte[] si = stream.readNBytes(sessionInfo.length());

            BufferInfo bufferInfo = metaInfo.bufferInfo();
            while (stream.available() >= bufferInfo.length()) {
                ByteBuffer rawBuffer = ByteBuffer.wrap(stream.readNBytes(bufferInfo.length())).order(ByteOrder.LITTLE_ENDIAN);
                printBuffer(rawBuffer,varHeaders);
            }

        } catch (IOException exception) {
            System.err.println("Error reading telemetry " + exception);
        }
    }

    static void printBuffer(ByteBuffer sample, List<VarHeader> varHeaders) {
        for(VarHeader header : varHeaders) {
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
