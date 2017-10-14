package com.serotonin.messaging;

public abstract interface IncomingResponseMessage extends IncomingMessage
{
  public abstract WaitingRoomKey getWaitingRoomKey();
}

/* Location:           C:\Users\Lenovo\Desktop\seroUtils.jar
 * Qualified Name:     com.serotonin.messaging.IncomingResponseMessage
 * JD-Core Version:    0.6.2
 */