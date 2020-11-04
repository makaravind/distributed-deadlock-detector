package com.bits.models.messages;

import com.bits.cmh.Process;

public class ReplyMessage extends Message{
    public ReplyMessage(Process i, Process j, Process k) {
        super(MessageType.REPLY, i, j, k);
    }
}
