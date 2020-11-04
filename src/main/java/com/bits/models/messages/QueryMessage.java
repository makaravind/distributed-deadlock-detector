package com.bits.models.messages;

import com.bits.cmh.Process;

public class QueryMessage extends Message {

    public QueryMessage(Process i, Process j, Process k) {
        super(MessageType.QUERY, i, j, k);
    }
}
