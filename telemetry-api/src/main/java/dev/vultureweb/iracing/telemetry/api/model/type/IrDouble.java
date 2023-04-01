package dev.vultureweb.iracing.telemetry.api.model.type;

import java.nio.ByteBuffer;

public final class IrDouble implements IrType<Double> {

    private static final int SIZE_IN_BYTES=8;

    private double value;

    @Override
    public Double convert(ByteBuffer buffer) {
        if(SIZE_IN_BYTES != buffer.remaining()) {
            throw new IllegalArgumentException("buffer should contain " + SIZE_IN_BYTES + " bytes.");
        }
        return buffer.getDouble();
    }
}
