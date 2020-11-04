package com.bits.cmh;

import com.bits.models.InitiatorInfo;
import com.bits.models.messages.Message;
import com.bits.models.messages.QueryMessage;
import com.bits.models.messages.ReplyMessage;

public interface IProcess {
    boolean isEngagingQuery(Message query);
    void sendQuery(InitiatorInfo initiator);
    void onQueryReceive(InitiatorInfo initiator, QueryMessage message);
    void onReplyReceive(InitiatorInfo initiator, ReplyMessage message);
    void stop();
    void prep(int allProcessesCount);
}
