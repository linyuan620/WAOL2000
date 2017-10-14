package com.serotonin.io.messaging;

import com.serotonin.util.queue.ByteQueue;

@Deprecated
public abstract interface MessageParser
{
  public abstract MessageResponse parseResponse(ByteQueue paramByteQueue)
    throws Exception;

  public abstract MessageRequest parseRequest(ByteQueue paramByteQueue)
    throws Exception;
}

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.io.messaging.MessageParser
 * JD-Core Version:    0.6.2
 */