package dev.vultureweb.iracing.telemetry.app.model.type;

import java.nio.ByteBuffer;

public final class IrFloat implements IrType<Float> {

    private static final int SIZE_IN_BYTES = 4;

    @Override
    public Float convert(ByteBuffer buffer) {
        if(SIZE_IN_BYTES != buffer.remaining()) {
            throw new IllegalArgumentException("buffer should contain " + SIZE_IN_BYTES + " bytes.");
        }
        return buffer.getFloat();
    }
}
