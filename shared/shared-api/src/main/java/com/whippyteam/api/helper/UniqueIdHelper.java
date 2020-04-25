package com.whippyteam.api.helper;

import java.nio.ByteBuffer;
import java.util.UUID;

public class UniqueIdHelper {

    public static byte[] getBytesFromUuid(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());

        return bb.array();
    }

    public static UUID getUuidFromBytes(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        long high = byteBuffer.getLong();
        long low = byteBuffer.getLong();

        return new UUID(high, low);
    }

    private UniqueIdHelper() {
    }

}
