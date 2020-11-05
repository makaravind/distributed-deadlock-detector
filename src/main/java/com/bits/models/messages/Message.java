package com.bits.models.messages;

import com.bits.cmh.Process;
import java.util.Objects;

enum MessageType {
    REPLY,
    QUERY;
}

public class Message {
    private final MessageType type;
    private final Process i;
    private final Process j;
    private final Process k;

    public Message(MessageType type, Process i, Process j, Process k) {
        this.type = type;
        this.i = i;
        this.j = j;
        this.k = k;
    }

    public MessageType getType() {
        return type;
    }

    public Process getI() {
        return i;
    }

    public Process getJ() {
        return j;
    }

    public Process getK() {
        return k;
    }

    @Override
    public String toString() {
        return "Message{" +
                type +
                "(" + i.getID() + "," + j.getID() + "," + k.getID() + ")" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return type == message.type &&
                Objects.equals(i, message.i) &&
                Objects.equals(j, message.j) &&
                Objects.equals(k, message.k);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, i, j, k);
    }
}
