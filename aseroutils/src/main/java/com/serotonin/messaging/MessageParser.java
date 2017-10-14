package com.serotonin.messaging;

import com.serotonin.util.queue.ByteQueue;

public abstract interface MessageParser
{
  public abstract IncomingMessage parseMessage(ByteQueue paramByteQueue)
    throws Exception;
}

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.messaging.MessageParser
 * JD-Core Version:    0.6.2
 */