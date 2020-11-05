package com.bits.cmh;

import com.bits.models.InitiatorInfo;
import com.bits.models.messages.Message;
import com.bits.models.messages.QueryMessage;
import com.bits.models.messages.ReplyMessage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Process implements IProcess {
    private boolean stopped = false;
    private final int ID;
    private Set<Process> dependents = new HashSet<>();
    private HashMap<Integer, Integer> num = new HashMap<>();
    private HashMap<Integer, Boolean> waiting = new HashMap<>();
    private Process lastEngagingQuerySender;

    public Process(int ID) {
        this.ID = ID;
    }

    public Set<Process> getDependents() {
        return dependents;
    }

    public void setDependents(Set<Process> dependents) {
        this.dependents = dependents;
    }

    public HashMap<Integer, Integer> getNum() {
        return num;
    }

    public void setNum(HashMap<Integer, Integer> num) {
        this.num = num;
    }

    public HashMap<Integer, Boolean> getWaiting() {
        return waiting;
    }

    public void setWaiting(HashMap<Integer, Boolean> waiting) {
        this.waiting = waiting;
    }

    public int getID() {
        return ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Process process = (Process) o;
        return ID == process.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }

    @Override
    public String toString() {
        List<Integer> dependentsIds = dependents.stream().map(Process::getID).collect(Collectors.toList());
        return "Process{" +
                "P" + ID +
                ", num=" + num +
                ", wait=" + waiting +
                ", dependents=" + dependentsIds +
                '}';
    }

    @Override
    public boolean isEngagingQuery(Message query) {
        return !Optional.ofNullable(this.waiting.get(query.getI().getID())).orElse(false);
    }

    @Override
    public void sendQuery(InitiatorInfo initiatorInfo) {
        this.num.put(initiatorInfo.getInitiator().getID(), this.dependents.size());
        this.waiting.put(initiatorInfo.getInitiator().getID(), true);
        for (Process dependent : this.dependents) {
            QueryMessage newQuery = new QueryMessage(initiatorInfo.getInitiator(), this, dependent);
            dependent.onQueryReceive(initiatorInfo, newQuery);
        }
    }

    @Override
    public void onQueryReceive(InitiatorInfo initiator, QueryMessage message) {
        if (this.stopped) {
            return;
        }

        System.out.println("query received : " + message);
        if (this.isEngagingQuery(message)) {
            this.lastEngagingQuerySender = message.getJ();
            this.sendQuery(initiator);
        } else if (this.waiting.get(initiator.getInitiator().getID())) {
            ReplyMessage newReply = new ReplyMessage(initiator.getInitiator(), this, message.getJ());
            message.getJ().onReplyReceive(initiator, newReply);
        }
    }

    @Override
    public void onReplyReceive(InitiatorInfo initiator, ReplyMessage message) {
        if (this.stopped) {
            return;
        }

        System.out.println("reply received : " + message);
        if (this.waiting.get(initiator.getInitiator().getID())) {
            int updatedCurrent = this.num.get(initiator.getInitiator().getID()) - 1;
            this.num.put(initiator.getInitiator().getID(), updatedCurrent);
            if (updatedCurrent <= 0) {
                if (message.getI().equals(message.getK())) {
                    initiator.getController().onDeadlockDetected(this);
                } else {
                    ReplyMessage newReply = new ReplyMessage(initiator.getInitiator(), this, this.lastEngagingQuerySender);
                    this.lastEngagingQuerySender.onReplyReceive(initiator, newReply);
                }
            }
        }
    }

    @Override
    public void stop() {
        if (this.stopped) {
            return;
        }
        this.stopped = true;
        this.num = null;
        this.waiting = null;
        this.lastEngagingQuerySender = null;
        for (Process dependent : this.dependents) {
            dependent.stop();
        }
    }

    @Override
    public void prep(int allProcessesCount) {
        this.stopped = false;
        this.waiting = new HashMap<>();
        this.num = new HashMap<>();
        this.lastEngagingQuerySender = null;
    }
}
