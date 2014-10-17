package org.jed2k.protocol.search;

import java.nio.ByteBuffer;

import org.jed2k.protocol.ByteContainer;
import org.jed2k.protocol.ProtocolException;
import org.jed2k.protocol.UInt16;

import static org.jed2k.Utils.sizeof;

public class NumericEntry extends SearchEntry {
    
    private static long UINT_MAX = 0xffffffffl;
    private long value;
    private byte operator;
    private ByteContainer<UInt16> tag;    
    
    NumericEntry(long value, byte operator, ByteContainer<UInt16> tag) {
        this.value = value;
        this.operator = operator;
        this.tag = tag;
        assert(tag != null);
    }
    
    @Override
    public ByteBuffer get(ByteBuffer src) throws ProtocolException {
        assert(false);
        return src;
    }

    @Override
    public ByteBuffer put(ByteBuffer dst) throws ProtocolException {
        if (value <= UINT_MAX) {
            dst.put(SearchRequest.SEARCH_TYPE_UINT32);
        } else { 
            dst.put(SearchRequest.SEARCH_TYPE_UINT64);
        }
        
        dst.put(operator);
        
        if (value <= UINT_MAX) {
            dst.putInt((int)value);
        } else { 
            dst.putLong(value);
        }
        
        return tag.put(dst);
    }

    @Override
    public int size() {
        return sizeof(SearchRequest.SEARCH_TYPE_UINT32) + ((value <= UINT_MAX)?sizeof(value)/2:sizeof(value)) + tag.size();        
    }

    @Override
    public Operator getOperator() {
        return Operator.OPER_NONE;        
    }

    @Override
    public boolean isOperator() {
        return false;
    }
    
}
