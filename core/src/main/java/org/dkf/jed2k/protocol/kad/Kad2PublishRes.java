package org.dkf.jed2k.protocol.kad;

import lombok.Data;
import org.dkf.jed2k.exception.JED2KException;
import org.dkf.jed2k.protocol.Serializable;
import org.dkf.jed2k.protocol.UInt8;
import org.dkf.jed2k.protocol.Unsigned;

import java.nio.ByteBuffer;

/**
 * Created by inkpot on 19.01.2017.
 */
@Data
public class Kad2PublishRes implements Serializable {
    private KadId fileId = new KadId();
    private UInt8 count = Unsigned.uint8();

    @Override
    public ByteBuffer get(ByteBuffer src) throws JED2KException {
        return count.get(fileId.get(src));
    }

    @Override
    public ByteBuffer put(ByteBuffer dst) throws JED2KException {
        return count.put(fileId.put(dst));
    }

    @Override
    public int bytesCount() {
        return fileId.bytesCount() + count.bytesCount();
    }
}
