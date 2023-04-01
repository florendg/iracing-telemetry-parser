package dev.vultureweb.iracing.telemetry.api.model.type;

import java.nio.ByteBuffer;

public sealed interface IrType<T> permits IrDouble, IrFloat {

    T convert(ByteBuffer buffer);

}
