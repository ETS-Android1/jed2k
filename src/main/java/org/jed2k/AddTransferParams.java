package org.jed2k;

import org.jed2k.exception.JED2KException;
import org.jed2k.protocol.*;
import org.jed2k.protocol.tag.Tag;

import java.nio.ByteBuffer;

/**
 * Created by inkpot on 31.07.2016.
 */
public class AddTransferParams implements Serializable {
    public final Hash hash = new Hash();
    public final UInt64 size = new UInt64();
    public final ByteContainer<UInt16> filepath = new ByteContainer<UInt16>(Unsigned.uint16());
    public final UInt8 paused = Unsigned.uint8();
    public Optional<TransferResumeData> resumeData = new Optional(TransferResumeData.class);

    public AddTransferParams() {

    }

    public AddTransferParams(final Hash h, final long size, final String filepath, final boolean paused) throws JED2KException {
        hash.assign(h);
        this.size.assign(size);
        this.filepath.assignString(filepath);
        this.paused.assign(paused?1:0);
    }

    @Override
    public ByteBuffer get(ByteBuffer src) throws JED2KException {
        return resumeData.get(paused.get(filepath.get(size.get(hash.get(src)))));
    }

    @Override
    public ByteBuffer put(ByteBuffer dst) throws JED2KException {
        assert(hash != null);
        assert(filepath != null);
        return resumeData.put(paused.put(filepath.put(size.put(hash.put(dst)))));
    }

    @Override
    public int bytesCount() {
        return hash.bytesCount() + size.bytesCount() + filepath.bytesCount() + paused.bytesCount() + resumeData.bytesCount();
    }
}
